package net.oasismgt.tin_service_generation.local.service.util.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.local.model.PropertyEntry;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.PropertyEntryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;
import net.oasismgt.tin_service_generation.local.service.util.MailService;
import net.oasismgt.tin_service_generation.local.service.util.TinNotificationService;


@Service
@Transactional
public class TinNotificationServiceImpl implements TinNotificationService {

	@Autowired	private DateTimeUtilities dateTimeUtilities;
	@Autowired	MailService mailService;
	@Autowired private PropertyEntryService propertyEntryService;

	public void sendNotification(HttpServletRequest req,TinGenerationRequestHistory history){		
		replacePlaceHoldersAndSendEmail(req,history,loadHtmlTemmplate(req));
	}
	
	public String loadHtmlTemmplate(HttpServletRequest req){			
					
		String htmlContent="##jtb_logo## <br/>" 
				+ " ##footer_image## <br/>"
				+ " ##message_date## <br/>"
				+ "##company_name## <br/>"
				+ " ##company_address## <br/>"
				+ "##company_address_wrap## <br/>"
				+ "##tax_payer_name## <br/>"
				+ "##tin## <br/>"
				+ "##issuance_date## <br/>";		
								
			PropertyEntry	entry=propertyEntryService.getByName("custom.tin-generation.notification-html-template");
			if(entry!=null)		
				return (String) entry.getValue();		
					
			return htmlContent;
		}
	
	private void replacePlaceHoldersAndSendEmail(HttpServletRequest req,TinGenerationRequestHistory history,String htmlTemplate){
		CorporateTinGenerationRequest corporateTinGenerationRequest=history.getCorporateTinGenerationRequest();
				
		StringBuilder address=new StringBuilder();
		address.append(corporateTinGenerationRequest.getHouse_no())
			   .append(corporateTinGenerationRequest.getStreet_name()).append(", ");
		
		
		String issuanceDate = history.getIssuanceDate();
		
		if(issuanceDate==null||issuanceDate.trim().isEmpty())
			issuanceDate = dateTimeUtilities.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
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
				mailService.sendEmail("Error from Tin Generation Remote: replacePlaceHoldersAndSendEmail("+history.getTin()+")", e.toString(), MailService.techsupport1, MailService.techsupport2);	
			}
		}
		history.setIssuanceDate(issuanceDate);
		
		String emailBody= htmlTemplate.replace("##flag##", getServerUrl(req)+"/images/flag.png/")
			 	.replace("##jtb_logo##", getServerUrl(req)+"/images/jtb_logo_2.png/")
			 	.replace("##footer_image##", getServerUrl(req)+"/images/jtb_logo_1.png/")			 	
			 	.replace("##message_date##", dateTimeUtilities.getDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")))
			 	.replace("##company_name##", history.getName_of_organization())
			 	.replace("##company_address##", address.toString())
			 	.replace("##company_address_wrap##", corporateTinGenerationRequest.getCity()+", "+corporateTinGenerationRequest.getLga()+", "+corporateTinGenerationRequest.getState())
			 	.replace("##tax_payer_name##", history.getName_of_organization())
			 	.replace("##tin##", history.getTin())
			 	.replace("##issuance_date##", LocalDate.parse(history.getIssuanceDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
		
		String mailingList[]={"nnwokocha@oasismgt.net,nwokochaeva@gmail.com"};//"cokoye@oasismgt.net","maduka95@hotmail.com","akinelus@gmail.com","chykeokoye@yahoo.com","chike.ikechukwu@gmail.com"
		
		PropertyEntry	entry=propertyEntryService.getByName("custom.tin-generation.notification-mailingList");
		if(entry!=null)		
			mailingList=((String)entry.getValue()).split(",");	
		
		mailService.sendEmail("NEW TAX IDENTIFICATION NUMBER (TIN) NOTIFICATION", emailBody, "notifications@taxservices.gov.ng",corporateTinGenerationRequest.getE_mail_primary(),mailingList);
		history.setNotificationDepartureDate(dateTimeUtilities.getDateTime());
		System.out.println("Email sent for "+history.getTin()+" to "+corporateTinGenerationRequest.getE_mail_primary());
		
	}
	
		private String getServerUrl(HttpServletRequest req){
		StringBuilder serverUrl=new StringBuilder();
		serverUrl.append(req.getScheme()).append("://")
		.append(req.getServerName());
		serverUrl.append(":").append(req.getServerPort());	
		return serverUrl.toString();
	}
	
	
}

