package net.oasismgt.tin_service_generation.local.controller;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.oasismgt.tin_service_generation.local.backing.Batch_Tin_property;
import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.backing.ServiceResponse;
import net.oasismgt.tin_service_generation.local.backing.Tin_property;
import net.oasismgt.tin_service_generation.local.constants.ResponseStatus;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;
import net.oasismgt.tin_service_generation.local.service.util.MailService;
import net.oasismgt.tin_service_generation.local.service.util.TinNotificationService;
import net.oasismgt.tin_service_generation.local.service.util.Transformer;


@Controller
public class TinUpdateController {
		
	@Autowired private Transformer transformer;
	@Autowired private DateTimeUtilities dateTimeUtilities;
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	@Autowired private TinNotificationService tinNotificationService;
	@Autowired	MailService mailService;
	
	//ACTIVELY_IN_USED - handles response coming from JTB (live)
	@RequestMapping(value="/tin-service/tin/generation/corporate/notification/internal", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ServiceResponse> batchNotification(HttpServletRequest req,@RequestHeader(required=false) String hash,@RequestHeader(required=false) String reqsrc,@RequestBody Batch_Tin_property batch_Tin_property) {
		String endpointKey="rebib29973880UIBIUSD9VU97329813698689689BSBDJBJUuigiud";
		ServiceResponse response=new ServiceResponse(ResponseStatus.AUTH_PROPERTY_MISSING);
		if(notValid(reqsrc))	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Required parameter missing"),HttpStatus.UNAUTHORIZED);		
		if(!("41.242.49.62".equals(req.getRemoteAddr())) && !"YGO".equals(reqsrc))
			return new ResponseEntity<ServiceResponse>(HttpStatus.UNAUTHORIZED);	 
						
		if(notValid(batch_Tin_property.getToken()))	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Required parameter missing"),HttpStatus.UNAUTHORIZED);
		if(notValid(hash))	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Required parameter missing"),HttpStatus.UNAUTHORIZED);			
				
		if(!transformer.getHash(reqsrc+batch_Tin_property.getToken()+endpointKey).equals(hash))
			return new ResponseEntity<ServiceResponse>(response.setResponseStatus(ResponseStatus.INVALID_HASH).setDescription("Authentication Failed"),HttpStatus.UNAUTHORIZED);
			
		
		Collection<Tin_property> batchPayload=batch_Tin_property.getTin_properties();
		if(batchPayload!=null&& !batchPayload.isEmpty()){						
			batchPayload.parallelStream().forEach(t->processTinUpdate(req,t));
			response=new ServiceResponse(ResponseStatus.COMPLETED_SUCCESSFULLY);			
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);			
		}else{			
			mailService.sendEmail("TIN record from JTB is empty", "",  MailService.techsupport1, MailService.techsupport2);			
			response=new ServiceResponse(ResponseStatus.EMPTY_PAYLOAD);			
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);			
		}
		
	}
	
	private void processTinUpdate(HttpServletRequest req,Tin_property tin_property){

		if(tin_property.getRc_number()==null||tin_property.getRc_number().isEmpty()){
			mailService.sendEmail("Tin Service Empty RC Number Notification", "Tin: "+tin_property.getTin()+"  doesn't have a corresponding returned RC Number from JTB", "techsupprt@taxservices.gov.ng", MailService.techsupport2);
			return;
		}
		if(tin_property.getTin()==null||tin_property.getTin().isEmpty()){
			mailService.sendEmail("Tin Service Empty Tin Notification", "RC Number: "+tin_property.getRc_number()+" doesn't have a corresponding returned TIN from JTB", "techsupprt@taxservices.gov.ng", MailService.techsupport2);
			return;
		}
		
		TinGenerationRequestHistory history=null;
		try{
			history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(tin_property.getRc_number());
		}catch(Exception e){
			System.err.println(e);
			mailService.sendEmail("Tin Service Error Notification", "Error occured while retrieving TinGenerationRequestHistory for "+tin_property.getRc_number()+" ;"+e.getMessage(), "techsupprt@taxservices.gov.ng", MailService.techsupport2);
		}
				
		if(history==null){
			mailService.sendEmail("Tin Service Mismatch Notification", "RC Number: "+tin_property.getRc_number()+" with TIN: "+tin_property.getTin()+" not found on request pool", "techsupprt@taxservices.gov.ng", MailService.techsupport2);
			return;
		}else{
			if(MessageStatus.HAS_TIN.equals(history.getMessageStatus())){
				mailService.sendEmail("Tin Service Notification", "TinGenerationRequestHistory RC Number: "+history.getRcno()+" already has TIN: "+history.getTin()+" and another Tin was sent "+tin_property.getTin(),"techsupport@taxservices.gov.ng", MailService.techsupport2);
				return;
			}			
		}
			
		try{
			
			history.setTin(tin_property.getTin());
			history.getCorporateTinGenerationRequest().setTin(tin_property.getTin());
			history.setMessageStatus(MessageStatus.HAS_TIN);
			
			String issuanceDate = tin_property.getDate_of_issuance();
			
			if(issuanceDate==null||issuanceDate.isEmpty())
				history.setIssuanceDate(dateTimeUtilities.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			else{
								
				if(issuanceDate.contains("/")){
					try{
						String[] dateSplit = issuanceDate.split("/");
						Integer val1 = Integer.valueOf(dateSplit[0]);
						Integer val2 = Integer.valueOf(dateSplit[1]);
						Integer val3 = Integer.valueOf(dateSplit[2]);
						
						if(val1>12)      issuanceDate = String.format("%02d/%02d/%04d",val1,val2,val3);					
						else if(val2>12) issuanceDate = String.format("%02d/%02d/%04d",val2,val1,val3);	
					
					}catch(Exception e){
						System.out.println(e.toString());
						mailService.sendEmail("Error New Tin : replacePlaceHoldersAndSendEmail("+history.getTin()+")", e.toString(), MailService.techsupport1, MailService.techsupport2);	
					}
				}
				history.setIssuanceDate(issuanceDate);
				
			}
			history.setDateOfWriteToJtbFolder(LocalDateTime.parse(tin_property.getDate_of_task_queue_entrance(), dateTimeUtilities.getDateTimeFormatter()));
			history.setDateOfReadFromJtbFolder(LocalDateTime.parse(tin_property.getDate_of_task_queue_left(), dateTimeUtilities.getDateTimeFormatter()));
			history.setTinResponseDate(dateTimeUtilities.getDateTime());
			history.setDateOfArrivalFromJtb(dateTimeUtilities.getDateTime());
			tinNotificationService.sendNotification(req, tinGenerationRequestHistoryService.update(history));
			
		}catch(Exception e){
			System.err.println(e);
			mailService.sendEmail("Tin Service Error Notification", "Error occured while updating TinGenerationRequestHistory for "+tin_property.getRc_number()+" ;"+(e), "techsupprt@taxservices.gov.ng", MailService.techsupport2);		
		}	
	}
	
	private boolean notValid(String arg){
		return (arg==null||arg.isEmpty());
	}

}