package net.oasismgt.tin_service_generation.local.service.util.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.service.util.MailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.core.task.TaskExecutor;

/**
 *
 * @author Nkemjika Nwokocha
 */

@Service
@Transactional
public class MailServiceImpl implements MailService{
	private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired  private JavaMailSender mailSender;
    @Autowired  private TaskExecutor taskExecutor;
    
    public boolean sendEmail(String subject,String content,String from,String to,String... bcc){
    	//SimpleMailMessage text=new SimpleMailMessage();   
    	
        taskExecutor.execute(()->mailSender.send((MimeMessage mimeMessage) -> {
        	try{
	            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	            message.setFrom(from);
	            message.setTo(to);
	            message.setBcc(bcc);
	            message.setSubject(subject);
	            message.setText(content, true);
	         //   message.addAttachment("testpdf.pdf", new ClassPathResource("doc/testpdf.pdf"));
        	}catch(Exception e){
        		e.printStackTrace();
        		logger.error(e.getMessage());
        	}
    }));
		return false;            
    }
    
    @Override
    public boolean sendEmail(String subject,String content,String from,String to){
    	
       //SimpleMailMessage text=new SimpleMailMessage();   
    	
            taskExecutor.execute(()->mailSender.send((MimeMessage mimeMessage) -> {
            	try{
		            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		            message.setFrom(from);
		            message.setTo(to);
		            message.setSubject(subject);
		            message.setText(content, true);
		         //   message.addAttachment("testpdf.pdf", new ClassPathResource("doc/testpdf.pdf"));
            	}catch(Exception e){
            		e.printStackTrace();
            		logger.error(e.getMessage());
            	}
        }));
			return false;            
    }

    @Override
   	public void sendDailyReport(String from, String to, String subject, String textContent, byte[] bytes) {
   		taskExecutor.execute(()->mailSender.send((MimeMessage mimeMessage) -> {
             	try{ 		 
             		
             	   //construct the pdf body part
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");       
                                		
   		            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
   		            message.setFrom(from);
   		            message.setTo(to);
   		            message.setSubject(subject);
   		            message.setText(textContent, true);
   		            message.addAttachment("Tax Services Report.pdf",dataSource);
             	}catch(SendFailedException e){
             		e.printStackTrace();
             	}
         })); 	
   		
   	}

	@Override
	public void sendDailyPerformanceReport(String from, String to, String subject, String textContent, byte[] bytes) {
		taskExecutor.execute(()->mailSender.send((MimeMessage mimeMessage) -> {
         	try{ 		 
         		
         	   //construct the pdf body part
                ByteArrayDataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");       
                            		
		            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		            message.setFrom(from);
		            message.setTo(to);
		            message.setSubject(subject);
		            message.setText(textContent, true);
		            message.addAttachment("Tax Services Performance Report.pdf",dataSource);
         	}catch(SendFailedException e){
         		e.printStackTrace();
         	}
     })); 	
	}
}
