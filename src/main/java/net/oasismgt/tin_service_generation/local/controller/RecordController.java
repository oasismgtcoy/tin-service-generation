package net.oasismgt.tin_service_generation.local.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.backing.ServiceResponse;
import net.oasismgt.tin_service_generation.local.constants.ResponseStatus;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;
import net.oasismgt.tin_service_generation.local.service.CorporateTinGenerationRequestService;
import net.oasismgt.tin_service_generation.local.service.PropertyEntryService;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;
import net.oasismgt.tin_service_generation.local.service.util.MailService;
import net.oasismgt.tin_service_generation.local.service.util.TinNotificationService;
import net.oasismgt.tin_service_generation.local.service.util.Transformer;

@Controller
public class RecordController {
	
	@Autowired private AccountSessionManager accountSessionManager;
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	@Autowired private CorporateTinGenerationRequestService corporateTinGenerationRequestService;
	@Autowired private Transformer transformer;
	@Autowired private TinNotificationService tinNotificationService;
	@Autowired private MailService mailService;
	@Autowired private PropertyEntryService propertyEntryService;
	@Autowired private DateTimeUtilities dateTimeUtilities;
	
	

	@RequestMapping(value="/incoming/rc-number/not-sent",method=RequestMethod.GET)
	public String notSent(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}

		model.addAttribute("pageTitle", "All Pending Rc Numbers");
		model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAll(MessageStatus.NOT_SENT));	
		model.addAttribute("backMessage", "All Rc Number Not Sent");
		return "rc_tin_list";		
	}
	@RequestMapping(value="/incoming/rc-number/sent",method=RequestMethod.GET)
	public String sent(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}

		model.addAttribute("pageTitle", "All Outgoing Rc Numbers");
		model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAll(MessageStatus.RECEPT_ACKNOWLEDGED));	
		model.addAttribute("backMessage", "All Outgoing Rc Numbers");
		return "rc_tin_list";		
	}
	@RequestMapping(value="/incoming/rc-number/all",method=RequestMethod.GET)
	public String allRc(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}

		model.addAttribute("pageTitle", "All Incoming Rc Numbers");
		model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAll());	
		model.addAttribute("backMessage", "All Incoming Rc Numbers");
		return "rc_tin_list";		
	}
	@RequestMapping(value="/incoming/tin/all",method=RequestMethod.GET)
	public String tinRecieved(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}

		model.addAttribute("pageTitle", "All Incoming TIN");
		model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAll(MessageStatus.HAS_TIN));	
		model.addAttribute("backMessage", "All Incoming TIN");
		return "rc_tin_list";		
	}
	@RequestMapping(value="/rc-number-tin/all",method=RequestMethod.GET)
	public String rcRecieved(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}

		model.addAttribute("pageTitle", "All RC-NUMBER TIN");
		model.addAttribute("all_rc_tin_list",tinGenerationRequestHistoryService.getAll());	
		model.addAttribute("backMessage", "All Incoming TIN");
		return "rc_tin_list";		
	}
	@RequestMapping(value="/rc-number-tin/delete",method=RequestMethod.GET)
	public String rcRecieved(HttpServletRequest req,Model model, RedirectAttributes fmodel,Long id){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		tinGenerationRequestHistoryService.delete(id);
		return "redirect:/rc-number-tin/all";		
	}
	
	@RequestMapping(value="/incoming/request/rc-tin",method=RequestMethod.GET)
	public String getRecord(HttpServletRequest req,Model model, RedirectAttributes fmodel,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		
		if(rcNumber!=null&&!rcNumber.isEmpty()){
			TinGenerationRequestHistory hist=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
			if(hist!=null){
				model.addAttribute("tin_request",hist);
				return "rc_tin_view";
			}
		}
		
		model.addAttribute("pageTitle", "Incoming RC/Tin Requests");
		model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAll());
		return "rc_tin_list";		
	}
	@RequestMapping(value="/tin-generation/query",method=RequestMethod.POST)
	public String allRespone(HttpServletRequest req,Model model, RedirectAttributes fmodel,String type,String startDate,String endDate){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(type==null || type.isEmpty() || startDate==null || startDate.isEmpty() || endDate==null || endDate.isEmpty()){
			fmodel.addFlashAttribute("errmessage", "All search criteria is required");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/dashboard";
		}		
		
		 LocalDate sDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));	
		 LocalDate eDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		 
		 model.addAttribute("pageTitle", "All "+type+" RC/Tin");
		 
		 if(!MessageStatus.HAS_TIN.equals(MessageStatus.valueOf(type)))
			 model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAllByDateRequested(sDate.atStartOfDay(),eDate.atStartOfDay().plusHours(24),MessageStatus.valueOf(type)));
		 else
			 model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAllByResponseDate(sDate.atStartOfDay(),eDate.atStartOfDay().plusHours(24),MessageStatus.valueOf(type)));
			
		 String message="";
		 if("RECEPT_ACKNOWLEDGED".equals(type))
			 message="acknowledged recieved from JTB";			
		  else  if("SENT".equals(type))
			  message="unacknowledged sent to JTB";		
		 else if("NOT_SENT".equals(type))
			 message="not sent to JTB";		
		 else  if("HAS_TIN".equals(type))
			 message="got TIN  from JTB";	
		 
		model.addAttribute("backMessage", message);
		model.addAttribute("type", type);		 
		model.addAttribute("isError", false);
		return "dashboard";		
	}
		
	@RequestMapping(value="/incoming/request/rc-tin/update",method=RequestMethod.POST)
	public String postRecord(HttpServletRequest req,Model model, RedirectAttributes fmodel,String rcNumber,String new_tax_office_name,Long new_tax_office_id){
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(rcNumber==null){
			fmodel.addFlashAttribute("errmessage", "Rc number not provided");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/incoming/request/rc-tin";
		}
		if(new_tax_office_name!=null && !new_tax_office_name.trim().isEmpty()){
			TinGenerationRequestHistory hist=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
			if(hist==null){
				fmodel.addFlashAttribute("errmessage", "Tin GenerationRequestHistory not provided");
				fmodel.addFlashAttribute("isError", true);
				return "redirect:/incoming/request/rc-tin";
			}
			CorporateTinGenerationRequest corporateTinGenerationRequest=hist.getCorporateTinGenerationRequest();
			corporateTinGenerationRequest.setTax_office(new_tax_office_name);
			
			try{
				corporateTinGenerationRequestService.update(corporateTinGenerationRequest);
			}catch(Exception e){
				fmodel.addFlashAttribute("errmessage", e);
				System.err.println(e.getMessage());
				return "redirect:/incoming/request/rc-tin";
			}
			
			fmodel.addFlashAttribute("errmessage","Updated.");
			model.addAttribute("tin_request",hist);
			model.addAttribute("tax_offices",Collections.emptyList());
			return "rc_tin_view";
		}
		
		fmodel.addFlashAttribute("errmessage","new_tax_office_name is null or empty");
		return "redirect:/incoming/request/rc-tin";
	}
	@RequestMapping(value="/incoming/request/rc-tin/send",method=RequestMethod.GET)
	public String sendRecord(HttpServletRequest req,Model model, RedirectAttributes fmodel,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		
		if(rcNumber!=null&&!rcNumber.isEmpty()){
			TinGenerationRequestHistory hist=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
			if(hist!=null){
				fmodel.addFlashAttribute("errmessage",TinGenerationController.forwardToRemoteJtb(mailService,dateTimeUtilities,true,propertyEntryService,null,transformer,tinGenerationRequestHistoryService,hist.getCorporateTinGenerationRequest(),hist.getRequestSource()));
				return "redirect:/incoming/rc-number/not-sent";
			}
		}
		fmodel.addFlashAttribute("errmessage", "RC Record not found");
		return "redirect:/incoming/request/rc-tin";		
	}
	@RequestMapping(value="/incoming/request/rc-tin/send/batch",method=RequestMethod.GET)
	public String sendAllRecord(HttpServletRequest req,Model model, RedirectAttributes fmodel,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		tinGenerationRequestHistoryService.getAll(MessageStatus.NOT_SENT).
		parallelStream().forEach(h->TinGenerationController.forwardToRemoteJtb(mailService,dateTimeUtilities,false,propertyEntryService,null,transformer,tinGenerationRequestHistoryService, h.getCorporateTinGenerationRequest(),h.getRequestSource()));
		fmodel.addFlashAttribute("errmessage", "Batch request running...");
		return "redirect:/incoming/rc-number/not-sent";
	}
	@RequestMapping(value="/incoming/request/rc-tin/reverse",method=RequestMethod.GET)
	public String reverseRecord(HttpServletRequest req,Model model, RedirectAttributes fmodel,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		
		if(rcNumber!=null&&!rcNumber.isEmpty()){
			TinGenerationRequestHistory history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
			if(history!=null){				
							
				String reqsrc="LOC"; 
				String currentServer=req.getServerName();
				if("stampduty.gov.ng".equals(currentServer)||"173.247.250.69".equals(currentServer))
					reqsrc="LJN";
				else if("198.57.202.118".equals(currentServer))
					reqsrc="K2B";
				
				model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAll(MessageStatus.RECEPT_ACKNOWLEDGED));					
				model.addAttribute("backMessage", fireRcRecallToJtb( mailService, dateTimeUtilities, transformer, tinGenerationRequestHistoryService, history, reqsrc));
				model.addAttribute("pageTitle", "acknowledged sent to JTB");
				model.addAttribute("type", MessageStatus.RECEPT_ACKNOWLEDGED.name());		 
				model.addAttribute("isError", false);
				return "redirect:/incoming/rc-number/sent";		
			}
		}
		
		return "redirect:/dashboard";		
	}
	@RequestMapping(value="/notification/rc-tin/send-email",method=RequestMethod.GET)
	public String sendNotification(HttpServletRequest req,Model model, RedirectAttributes fmodel,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		
		if(rcNumber!=null&&!rcNumber.isEmpty()){
			TinGenerationRequestHistory hist=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
			if(hist!=null){
				tinNotificationService.sendNotification(req, hist);
				fmodel.addFlashAttribute("errmessage", "Notification sent to '"+hist.getCorporateTinGenerationRequest().getE_mail_primary()+"'");
				return "redirect:/incoming/tin/all";
			}
		}		
		return "redirect:/incoming/request/rc-tin";		
	}
	
	@RequestMapping(value="/notification/rc-tin/send-email/batch",method=RequestMethod.GET)
	public String sendNotificationBatch(HttpServletRequest req,Model model, RedirectAttributes fmodel){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		tinGenerationRequestHistoryService.getAll(MessageStatus.HAS_TIN)
		.stream().forEach(h->sentNotificationEmail(req,h));
		fmodel.addFlashAttribute("errmessage", "Sending batch email notification");		
		return "redirect:/incoming/request/rc-tin";		
	}
	
	public static String fireRcRecallToJtb(MailService mailService,DateTimeUtilities dateTimeUtilities,Transformer transformer,TinGenerationRequestHistoryService tinGenerationRequestHistoryService,TinGenerationRequestHistory history,String reqsrc){
		
		String res = "No respones from web service endpoint";
		
		CorporateTinGenerationRequest corporateTinGenerationRequest=new CorporateTinGenerationRequest();
		corporateTinGenerationRequest.setRcno(history.getRcno());
		
	    HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type","application/json");
		headers.add("Authorization", "Basic XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");											
						
		final String endpointKey="rebib29973880UIBIUSD9VU97329813698689689BSBDJBJUuigiud";				
		headers.add("reqsrc",reqsrc);
		headers.add("hash",transformer.getHash(reqsrc+corporateTinGenerationRequest.getRcno()+endpointKey));
		headers.add("reqkey",corporateTinGenerationRequest.getRcno());			
		
		String url = TinGenerationController.jtbHostEndpoint+"/tin-service/tin/recall/corporate/jtb/remote";
		try{
		
				RestTemplate restTemplate = new RestTemplate();			
				HttpEntity<CorporateTinGenerationRequest> entity = new HttpEntity<CorporateTinGenerationRequest>(corporateTinGenerationRequest,headers); 
				ResponseEntity<ServiceResponse> serviceResponse= restTemplate.exchange(url, HttpMethod.POST, entity, ServiceResponse.class);
				
				if(HttpStatus.OK==serviceResponse.getStatusCode()){
					ServiceResponse response=serviceResponse.getBody();
					res=("JTB returned: "+response.getStatus_message()+" ("+response.getStatus_code()+") , "+response.getDescription());
					
					if(ResponseStatus.REQUEST_COMPLETED.getCode().equals(response.getStatus_code())){				
							history.setLastUpdatedDate(dateTimeUtilities.getDateTime());					
							history.setMessageStatus(MessageStatus.NOT_SENT);
							tinGenerationRequestHistoryService.update(history);
							return "JTB returned: "+(response.getStatus_message())+" ("+response.getStatus_code()+") , "+(response.getDescription());				
					}
					
				}else{
					mailService.sendEmail("On recalling RC record, JTB returned failed with :  ",serviceResponse.getStatusCode()+"",  MailService.techsupport1, MailService.techsupport2);	
					res= "JTB returned failed with : "+(serviceResponse.getStatusCode());
				}
					
			} catch (RestClientException e){						
				 System.err.println(e);	
				 mailService.sendEmail(" Error on forwarding request to JTB.", e.toString(),  MailService.techsupport1, MailService.techsupport2);							
				 return e.toString();
			}

		return res;
	}
	private void sentNotificationEmail(HttpServletRequest req,TinGenerationRequestHistory history){

		if(history!=null){
			try{
				history.getCorporateTinGenerationRequest().setTin(history.getTin());
				tinNotificationService.sendNotification(req, history);
				tinGenerationRequestHistoryService.update(history);	
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println(e.toString());
				}
			}catch(Exception e){
				System.out.println(e.toString());
				 mailService.sendEmail("Error on sending notification mail to TIN owner of RC: "+history.getRcno(), e.toString(),  MailService.techsupport1, MailService.techsupport2);							
			}
			
		}
	
	
	}
}
