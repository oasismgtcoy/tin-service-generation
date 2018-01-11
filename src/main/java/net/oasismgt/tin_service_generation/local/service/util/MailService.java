package net.oasismgt.tin_service_generation.local.service.util;


/**
 *
 * @author Nkemjika Nwokocha
 */


public interface MailService {	
    public boolean sendEmail(String subject,String content,String from,String to);
    public boolean sendEmail(String subject,String content,String from,String to,String... bcc);
    String techsupport1="techsupport-local@taxservices.gov.ng";
	String techsupport2="nnwokocha@oasismgt.net";
	public void sendDailyReport(String from,String to,String subject,String textContent, byte[] bytes);
	public void sendDailyPerformanceReport(String from,String to,String subject,String textContent, byte[] bytes);

}
