package net.oasismgt.tin_service_generation.local.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.backing.ServiceResponse;
import net.oasismgt.tin_service_generation.local.backing.Tin_property;
import net.oasismgt.tin_service_generation.local.constants.ResponseStatus;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.local.model.Lga;
import net.oasismgt.tin_service_generation.local.model.PropertyEntry;
import net.oasismgt.tin_service_generation.local.model.State;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.LgaService;
import net.oasismgt.tin_service_generation.local.service.PropertyEntryService;
import net.oasismgt.tin_service_generation.local.service.StateService;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;
import net.oasismgt.tin_service_generation.local.service.util.MailService;
import net.oasismgt.tin_service_generation.local.service.util.Transformer;


@Controller
public class TinGenerationController {
			
	@Autowired private Transformer transformer;
	@Autowired private DateTimeUtilities dateTimeUtilities;
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	@Autowired private TaskExecutor taskExecutor;
	@Autowired private StateService stateService;
	@Autowired private LgaService lgaService;
	@Autowired private PropertyEntryService propertyEntryService;
	@Autowired	MailService mailService;
	
	//public final static String jtbHostEndpoint = "http://localhost:9093";
	public final static String jtbHostEndpoint = "http://41.242.49.62:9093";
	
	public final static String jtbEndpointUrlNew = jtbHostEndpoint+ "/tin-service/tin/generation/corporate/jtb/remote";
			
	//ACTIVELY_IN_USED - handles request coming from ISDS (live)
	@RequestMapping(value="/internal/tin-service/tin/generation/corporate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ServiceResponse> validate(HttpServletRequest req,@RequestHeader(required=false) String hash,@RequestHeader(required=false) String reqsrc,@RequestBody CorporateTinGenerationRequest corporateTinGenerationRequest) {
				
		/*if(!req.getLocalAddr().equals(req.getRemoteAddr()))
			return new ResponseEntity<ServiceResponse>(HttpStatus.UNAUTHORIZED);	
		*/	
		
		String endpointKey="rebib29973880UIBIUSD9VU97329813698689689BSBDJBJUuigiud";
		ServiceResponse response=new ServiceResponse(ResponseStatus.AUTH_PROPERTY_MISSING);
		if(notValid(hash))	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Service hash is required."),HttpStatus.OK);			
		if(notValid(corporateTinGenerationRequest.getRcno()))	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Service reqkey is required."),HttpStatus.OK);
		if(notValid(reqsrc))	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Service source is required."),HttpStatus.OK);	
				
		if(!transformer.getHash(reqsrc+corporateTinGenerationRequest.getRcno()+endpointKey).equals(hash))
			return new ResponseEntity<ServiceResponse>(response.setResponseStatus(ResponseStatus.INVALID_HASH).setDescription("Invalid service hash."),HttpStatus.OK);
				
		TinGenerationRequestHistory history=null;
		try{
			history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(corporateTinGenerationRequest.getRcno(), corporateTinGenerationRequest.getMda_transaction_id());
		}catch(Exception e){
			System.err.println(e);
			response.setResponseStatus(ResponseStatus.INTERRUPTED);
			mailService.sendEmail("Retrieving TinGenerationRequestHistory "+(corporateTinGenerationRequest.getRcno())+" "+(corporateTinGenerationRequest.getMda_transaction_id()), e.toString(),  MailService.techsupport1, MailService.techsupport2);	
			return new ResponseEntity<ServiceResponse>(response.setDescription("Ref 80 "+e.getMessage()), HttpStatus.OK);
		}
				
		if(history!=null){
			
			if(history.getTin()!=null&&!history.getTin().isEmpty()){
				try{
					response.setResponseStatus(ResponseStatus.FOUND);
					response.setDescription("Record already existing.");
					Tin_property tin_properties=new Tin_property();					
					tin_properties.setTin(history.getTin());				
					response.setTin_properties(tin_properties);
					return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
				}catch(Exception e){
					System.err.println(e);
					response.setResponseStatus(ResponseStatus.INTERRUPTED);
					mailService.sendEmail("Existing TinGenerationRequestHistory "+(history.getRcno())+" found, hence can not add to database", e.toString(),  MailService.techsupport1, MailService.techsupport2);	
					
					return new ResponseEntity<ServiceResponse>(response.setDescription("Line: "+e.getMessage()), HttpStatus.OK);
				}	
			}else{
				try{
					response.setResponseStatus(ResponseStatus.CONFLICT);
					response.setDescription("Initial request was made on "+(dateTimeUtilities.getDateTime(history.getDateRequested())));
					return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
				}catch(Exception e){
					System.err.println(e);
					response.setResponseStatus(ResponseStatus.INTERRUPTED);
					return new ResponseEntity<ServiceResponse>(response.setDescription("Line: 127 "+e.getMessage()), HttpStatus.OK);
				}	
			}
		}
		try{
			history=new TinGenerationRequestHistory();
			history.setDateRequested(dateTimeUtilities.getDateTime());
			history.setMda_transaction_id(corporateTinGenerationRequest.getMda_transaction_id());
			history.setRcno(corporateTinGenerationRequest.getRcno());
			history.setCorporateTinGenerationRequest(corporateTinGenerationRequest);
			history.setRequestSource(reqsrc);
			history.setName_of_organization(corporateTinGenerationRequest.getName_of_organization());
			history.setDateOfArrivalFromSourceMda(dateTimeUtilities.getDateTime());
											
			if(corporateTinGenerationRequest.getDate_of_commencement().contains("-"))
				corporateTinGenerationRequest.setDate_of_commencement(corporateTinGenerationRequest.getDate_of_commencement().replace("-", "/"));
			if(corporateTinGenerationRequest.getDate_of_incorporation().contains("-"))
				corporateTinGenerationRequest.setDate_of_incorporation(corporateTinGenerationRequest.getDate_of_incorporation().replace("-", "/"));
			if(corporateTinGenerationRequest.getDate_of_registration().contains("-"))
				corporateTinGenerationRequest.setDate_of_registration(corporateTinGenerationRequest.getDate_of_registration().replace("-", "/"));
			
			corporateTinGenerationRequest.setTax_office(assignTaxOffice(corporateTinGenerationRequest.getType_of_organization(),corporateTinGenerationRequest.getState(),corporateTinGenerationRequest.getLga()));
			corporateTinGenerationRequest.setPrevious_taxpayer_no_issuing_tax_authority_state("FIRS");
			corporateTinGenerationRequest.setPrevious_taxpayer_number_used("FROMCAC");			
			
			tinGenerationRequestHistoryService.add(history);
			forwardToRemoteJtb(mailService,dateTimeUtilities,false,propertyEntryService,taskExecutor,transformer,tinGenerationRequestHistoryService,corporateTinGenerationRequest,reqsrc);
			response=new ServiceResponse(ResponseStatus.REQUEST_RECEIVED_AND_IN_PROGRESS);			
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		}catch(Exception e){
			System.err.println(e);
			response.setResponseStatus(ResponseStatus.INTERRUPTED);
			mailService.sendEmail("Adding TinGenerationRequestHistory "+(history.getRcno())+" failed.", e.toString(),  MailService.techsupport1, MailService.techsupport2);				
			return new ResponseEntity<ServiceResponse>(response.setDescription("Ref 143 "+e.getMessage()), HttpStatus.OK);
		}	
	}
	

	public static String forwardToRemoteJtb(MailService mailService,DateTimeUtilities dateTimeUtilities,boolean sendNow,PropertyEntryService propertyEntryService, TaskExecutor taskExecutor,Transformer transformer,TinGenerationRequestHistoryService tinGenerationRequestHistoryService,CorporateTinGenerationRequest corporateTinGenerationRequest,String reqsrc){
				
		boolean autoRun= false;
		PropertyEntry runAutomatic = propertyEntryService.getByName("custom.mode-automatic-relay-rc_number-to-jtb");
		
		try{			
			autoRun = (runAutomatic != null && Boolean.parseBoolean(runAutomatic.getValue()));			
		}catch(Exception e){
			e.printStackTrace();
		}

		if(sendNow || autoRun){				
			if(taskExecutor==null)
				return fireToJtb(mailService,dateTimeUtilities,transformer,tinGenerationRequestHistoryService,corporateTinGenerationRequest, reqsrc);
			else
				taskExecutor.execute(()->fireToJtb(mailService,dateTimeUtilities,transformer,tinGenerationRequestHistoryService,corporateTinGenerationRequest, reqsrc));
			return "Batch process has started.";		
		}
		return "RC process in manuel mode";
	}
	
	public static String fireToJtb(MailService mailService,DateTimeUtilities dateTimeUtilities,Transformer transformer,TinGenerationRequestHistoryService tinGenerationRequestHistoryService,CorporateTinGenerationRequest corporateTinGenerationRequest,String reqsrc){
					
		String res = "No respones from web service endpoint";
		
			    HttpHeaders headers = new HttpHeaders();
				headers.add("Accept", "application/json");
				headers.add("Content-Type","application/json");
				headers.add("Authorization", "Basic XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");											
								
				final String endpointKey="rebib29973880UIBIUSD9VU97329813698689689BSBDJBJUuigiud";				
				headers.add("reqsrc",reqsrc);
				headers.add("hash",transformer.getHash(reqsrc+corporateTinGenerationRequest.getRcno()+endpointKey));
				headers.add("reqkey",corporateTinGenerationRequest.getRcno());			
								
				try{				
					if(corporateTinGenerationRequest.getStreet_name().length()>60)
						corporateTinGenerationRequest.setStreet_name(corporateTinGenerationRequest.getStreet_name().substring(0, 60));
					
					if(corporateTinGenerationRequest.getHouse_no().length()>15)
						corporateTinGenerationRequest.setHouse_no(corporateTinGenerationRequest.getHouse_no().substring(0, 15));
					
					if(corporateTinGenerationRequest.getName_of_organization().length()>100)
						corporateTinGenerationRequest.setName_of_organization(corporateTinGenerationRequest.getName_of_organization().substring(0, 100));
					
					if(corporateTinGenerationRequest.getCity()!=null && corporateTinGenerationRequest.getCity().length()>60)
						corporateTinGenerationRequest.setCity(corporateTinGenerationRequest.getCity().substring(0, 60));
					
					/*if("SHAGAMU".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("SAGAMU");
					else if("MUNICIPAL AREA COUNCIL".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("MUNICIPAL");
					else if("SHOMOLU".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("SOMOLU");
					else if("IFAKO-IJAIYE".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IFAKO/IJAIYE");
					else if("ONITSHA SOUTH".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ONITSHA-SOUTH");
					else if("ONITSHA NORTH".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ONITSHA-NORTH");
					else if("ETI OSA".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ETI-OSA");
					else if("IBEJU-LEKKI".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IBEJU/LEKKI");
					else if("OSHODI-ISOLO".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("OSHODI/ISOLO");							
					else if("NSIT-IBOM".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("NSIT IBOM");
					else if("AKOKO-EDO".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AKOKO EDO");
					else if("ANIOCHA SOUTH".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ANIOCHA-SOUTH");
					else if("IKPOBA OKHA".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IKPOBA/OKHA");
					else if("CALABAR MUNICIPAL".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("CALABAR MUNICIPALITY");
					else if("IBADAN NORTH-EAST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IBADAN NORTH EAST");
					else if("OSHIMILI SOUTH".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("OSHIMILI - SOUTH");
					else if("OBAFEMI OWODE".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("OBAFEMI/OWODE");
					else if("EZINIHITTE".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("Ezinihitte Mbaise");
					else if("ESAN NORTH-EAST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("Esan North East");
					else if("ADO-ODO/OTA".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ADO ODO-OTA");
					else if("IKA NORTH EAST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IKA NORTH- EAST");
					else if("ETI OSA".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ETI-OSA");
					else if("IKEDURU".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IKEDURU (IHO)");
					else if("ETSAKO WEST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ETSAKO  WEST");					
					else if("OBI NGWA".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("Obingwa");					
					else if("UHUNMWONDE".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("UHUNMWODE");
					else if("ILORIN WEST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ILORIN-WEST");
					else if("ILORIN SOUTH".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ILORIN-SOUTH");
					else if("IBADAN NORTH-WEST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IBADAN NORTH WEST");
					else if("IBADAN SOUTH-WEST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IBADAN SOUTH WEST");
					else if("IBADAN NORTH-EAST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IBADAN NORTH EAST");					
					else if("IBADAN NORTH-WEST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("IBADAN NORTH WEST");
					else if("DAWAKIN KUDU".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("DAWAKI KUDU");
					else if("AJEROMI-IFELODUN".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AJEROMI/IFELODUN");
					else if("ILORIN SOUTH".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ILORIN-SOUTH");
					else if("ABUA/ODUAL".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ABUA-ODUAL");
					else if("ADO-ODO/OTA".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("ADO ODO-OTA");
					else if("AFIKPO SOUTH (EDDA)".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AFIKPO SOUTH");
					else if("AIYEDIRE".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AIYEKIRE/GBONYIN");
					else if("AJEROMI-IFELODUN".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AJEROMI/IFELODUN");
					else if("AKOKO SOUTH-EAST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AKOKO SOUTH EAST");
					else if("AKOKO SOUTH-WEST".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AKOKO SOUTH WEST");
					else if("AKOKO-EDO".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AKOKO EDO");
					else if("AKUKU-TORU".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AKUKU TORU");
					else if("AJEROMI-IFELODUN".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("AJEROMI/IFELODUN");
					else if("DAWAKIN KUDU".equals(corporateTinGenerationRequest.getLga()))
						corporateTinGenerationRequest.setLga("DAWAKIN-KUDU");*/
																									
						RestTemplate restTemplate = new RestTemplate();			
						HttpEntity<CorporateTinGenerationRequest> entity = new HttpEntity<CorporateTinGenerationRequest>(corporateTinGenerationRequest,headers); 
						ResponseEntity<ServiceResponse> serviceResponse= restTemplate.exchange(jtbEndpointUrlNew, HttpMethod.POST, entity, ServiceResponse.class);
						
						if(HttpStatus.OK==serviceResponse.getStatusCode()){
							ServiceResponse response=serviceResponse.getBody();
							res=("JTB returned: "+response.getStatus_message()+" ("+response.getStatus_code()+") , "+response.getDescription());
							
							if(ResponseStatus.REQUEST_RECEIVED_AND_IN_PROGRESS.getCode().equals(response.getStatus_code()) ||ResponseStatus.REQUEST_RECEIVED_AND_IN_QUEUE.getCode().equals(response.getStatus_code())){
								TinGenerationRequestHistory history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(corporateTinGenerationRequest.getRcno());
								if(history!=null){
									history.setMessageStatus(MessageStatus.RECEPT_ACKNOWLEDGED);
									history.setDateOfDepartureToJtb(dateTimeUtilities.getDateTime());									 
									tinGenerationRequestHistoryService.update(history);
									res= "JTB returned: "+(response.getStatus_message())+" ("+response.getStatus_code()+") , "+(response.getDescription());
								}
							}
						}else{
							mailService.sendEmail("JTB returned failed with :  ",serviceResponse.getStatusCode()+"",  MailService.techsupport1, MailService.techsupport2);	
							res= "JTB returned failed with : "+(serviceResponse.getStatusCode());
						}
						
					} catch (RestClientException e){						
						 System.err.println(e);	
						// mailService.sendEmail(" Error on forwarding request to JTB.", e.toString(),  MailService.techsupport1, MailService.techsupport2);							
						 res= e.toString();
					}
				
			return res;	
	 }
		 
	private boolean notValid(String value){
		return (value==null||value.isEmpty());
	}
	private String assignTaxOffice(String type_of_organization,String st,String lgaName){
		
		Lga lga=lgaService.getByName(lgaName);
		if(lga!=null && lga.getTaxOfficeName()!= null && !lga.getTaxOfficeName().trim().isEmpty())
			return lga.getTaxOfficeName();
		
		if(lga!=null && lga.getState()!= null)
			return lga.getState().getTaxOfficeName();
		
		State state = stateService.getByName(st);
		if(state!=null)
			return state.getTaxOfficeName();
		
		StringBuilder taxOffice = new StringBuilder(); // MSTO, LTO, IETO, GBTO,  MTO,
		
		if("LIMITED BY GUARANTEE".equals(type_of_organization)){ 
			taxOffice.append("MSTO").append(" ").append(lga);			
		}else if("PRIVATE LIMITED COMPANY".equals(type_of_organization)){
			taxOffice.append("MSTO").append(" ").append(lga);			
		}else if("PRIVATE UNLIMITED COMPANY".equals(type_of_organization)){
			taxOffice.append("MSTO").append(" ").append(lga);			
		}else if("PUBLIC LIMITED COMPANY".equals(type_of_organization)){
			taxOffice.append("MSTO").append(" ").append(lga);			
		}else if("PUBLIC UNLIMITED COMPANY".equals(type_of_organization)){
			taxOffice.append("MSTO").append(" ").append(lga);	
		}else if("BUSINESS NAME".equals(type_of_organization)){
			taxOffice.append("MSTO").append(" ").append(lga);	
		}else if("TRUSTEESHIP".equals(type_of_organization)){
			taxOffice.append("MTO").append(" FCT");			
		}else if("FEDERAL MDA".equals(type_of_organization)){
			taxOffice.append("GBTO").append(" FCT");			
		}else if("STATE MDA".equals(type_of_organization)){
			taxOffice.append("GBTO").append(lga);			
		}else
			taxOffice.append("MSTO").append(" ").append(lga);			
		
		return taxOffice.toString();
	}
	
}