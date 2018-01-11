package net.oasismgt.tin_service_generation.local.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


import net.oasismgt.tin_service_generation.local.service.util.MailService;
import net.oasismgt.tin_service_generation.local.model.*;
import net.oasismgt.tin_service_generation.local.service.*;
import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;


@Component 
@Controller
@EnableScheduling
public class ReportController {
	
	
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;	
	@Autowired private MailService mailService;
	@Autowired private MailingListService mailingListService;
	
	 LocalDateTime startDate = LocalDate.of(2017,Month.NOVEMBER, 28). atStartOfDay();	      	        
     LocalDateTime endDate = startDate.plusHours(24).minusSeconds(1);
    
	
	@RequestMapping(value = "sendreport", method = RequestMethod.GET)
	public String payment(Model model, HttpServletRequest req) throws Exception {
		
		sendReport();
		return "report";
	}
	
	
	    // Send daily report at interval
	    @Scheduled(cron = "0 59 23 * * *")
		public void sendReport() throws Exception{	
			
			ByteArrayOutputStream outputStream = null;			
			
	        //now write the PDF content to the output stream
	        outputStream = new ByteArrayOutputStream();
	       
	        writeReportPdf(outputStream);
	        byte[] bytes = outputStream.toByteArray();
	      
	    	Collection<MailingList> mailingList = mailingListService.getAll();	
	
		 
		   	        
	        String sDate = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  mm:ss"));	        
	        String today = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy "));
	        String eDate = endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm:ss"));
			
	       
	        
	        String incomingRcCount = tinGenerationRequestHistoryService.getCountByDateRequested(startDate,endDate,null).toString();	
	        String outgoingRcCount = tinGenerationRequestHistoryService.getCountByDateOfDepartureToJtb(startDate,endDate).toString();	
	        String incomingTinCount = tinGenerationRequestHistoryService.getCountByDateResponded(startDate,endDate,null).toString();	    
	        
	        for(MailingList mail : mailingList){			        	
		       String subject = "Daily Tax Services Report" ;
			   String body =  				
			   "<p>" +  " Dear "   + mail.getFullName()  + ","+ " </p>"  + 

			  "<p>" + " Please find below the total number of successful Corporate TIN transactions generated from"+ " <strong>"
			  + today + " </strong>" + "to " + "<strong> "+ today + "</strong>" + " </p>  " 
			  + "<table  style = 'border: 1px solid black; padding: 4px; width:650px;' >" + " <tr>" +"<td>"+ " <strong>" + "Start Date"+ " </strong>" + "</td>"+ " <td>"+ sDate +"</td>"+" <td>"+ " <strong>" +"End Date"+" </strong>"+ "</td>"
			  + " <td> " + eDate + "</td>" + "  </tr>" + " </table> "
			  + " <table style = 'border: 1px solid black; padding: 4px; margin-top:10px; width: 650px;'>"+ " <tr>" + " <td>"+ "<strong> "+" Total Number of Records Received From Corporate Registry"+ "</strong>"  +"</td>"+" <td>"+ incomingRcCount
			  +"</td>" + "</tr>"+"<tr>"+" <td>"+ "<strong>"+ "Total Number of Records sent to TIN Registry" + "</strong>" +" </td>" + " <td>"+ outgoingRcCount +" </td>"
			  + "</tr>"+"<tr>"+"<td>"+"<strong>"+" Total Number of Records received from TIN Registry"+ "</strong>" +" </td>"+ "  <td>" + incomingTinCount + "</td>"
			  + "</tr>"+"</table>"+ " <br><" + "br>" + "<p>" + "Please find attached a PDF file containing this more details of the report."
			  + "</p>"+ "<br>"+  "<br>" + "<p>" +"Yours faithfully,"+"</p>" + "<p>"+"Tax Services Report Team"+"</p>"
			  + "<p>"+" Visit: www.taxservices.gov.ng  |  E-mail: support@taxservices.gov.ng  |" +"</p>" ;
			  						  
			mailService.sendDailyReport("reports@taxservices.gov.ng", mail.getEmail(), subject, body,bytes);			
		   }	
			
        }
	
		
		public class HeaderFooterPageEvent extends PdfPageEventHelper {
			
			public void onStartPage(PdfWriter writer,Document document) {
				
								
			Rectangle rect = writer.getBoxSize("rectangle");
			 
			
			// FIRS Header Logo
			Image firslogo = null;
			try {
				ClassPathResource res = new ClassPathResource("../../resources/FIRS-Logo.png");      
			      // firsLogo = Image.getInstance(res.getURI().toString());
				firslogo = Image.getInstance(res.getURI().toString());
				//firslogo = Image.getInstance("/isds-tin-service-generation/src/main/webapp/resources/FIRS-Logo.png");
				
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			firslogo.scaleAbsolute(200, 30);	       
			firslogo.setAbsolutePosition((rect.getLeft() + rect.getRight()) / 2 - 275, rect.getTop() - 30);
			firslogo.setAlignment(Element.ALIGN_LEFT);          
		    try {
				writer.getDirectContent().addImage(firslogo);
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
						
			// JTB Header Logo 
			Image jtblogo = null;
			try {
				ClassPathResource res = new ClassPathResource("../../resources/jtblogo.png");      
			      // firsLogo = Image.getInstance(res.getURI().toString());
				jtblogo = Image.getInstance(res.getURI().toString());
				//jtblogo = Image.getInstance("/isds-tin-service-generation/src/main/webapp/resources/jtblogo.png");
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jtblogo.scaleAbsolute(30, 30);	       
			jtblogo.setAlignment(Image.ALIGN_RIGHT);
			
			Chunk chunk = new Chunk(jtblogo, 0, -30);			 
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(chunk), rect.getRight(), rect.getTop(), 0);			 
			     
	}
		   
		    public void onEndPage(PdfWriter writer, Document document) {
		    			    	
		    	Font  numberFont =new Font(FontFamily.COURIER,8,Font.BOLD);
		       
		    	ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page" + document.getPageNumber(), numberFont), 550, 30, 0);
		   
		    }

		}
		
		protected PdfNumber orientation = PdfPage.PORTRAIT;
		 
	    public void setOrientation(PdfNumber orientation) {
	        this.orientation = orientation;
	    }
		public void writeReportPdf(OutputStream outputStream) throws Exception {
			
	       Document document = new Document(PageSize.A4);
	       
	       document.setMargins(20, 20, 100, 100);
	       
	        PdfWriter writer =  PdfWriter.getInstance(document, outputStream);	        
	        document.open();
	        
	        
	        ClassPathResource cres = new ClassPathResource("../../resources/DailyReportCover.pdf");	        	      		      	    	   
	    	  String coverUrl = cres.getURI().toString();  
	    	  PdfReader cover = new PdfReader(coverUrl);
	         try{
	        	  PdfPTable table = new PdfPTable(1);
	        	  PdfImportedPage page = writer.getImportedPage(cover, 1);
	        	  table.getDefaultCell().setBorder(0);
	        	  table.setTotalWidth(PageSize.A4.getWidth());
	        	  table.setLockedWidth(true);
	              table.addCell(Image.getInstance(page));
	              
	              PdfContentByte canvas = writer.getDirectContent();
	              PdfTemplate template = canvas.createTemplate(table.getTotalWidth(), table.getTotalHeight());
	              table.writeSelectedRows(0, -1, 0, table.getTotalHeight(), template);
	              Image img = Image.getInstance(template);
	              img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
	              img.setAbsolutePosition(0, (PageSize.A4.getHeight() - table.getTotalHeight()) / 2);
	              
	             
	              String longDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy ")).toUpperCase();
	             
	              Font  dateFont =new Font(FontFamily.TIMES_ROMAN,30,Font.BOLD);
	              dateFont.setColor(BaseColor.WHITE);
	  	    	  ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(longDate, dateFont), 160, 35, 0);
	  	   
	             // document.add(new Paragraph( new Phrase("Date Holder")));
	              document.add(img);
		      }catch(Exception e){
		    	  
		      }	      
	        
	   
	        document.newPage();        
	        Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("rectangle", rect);
	        
	        
	        
	        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
	       writer.setPageEvent(event);
	       
	       
	              
	       String today = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy "));
	      try{
	    	  ClassPathResource res = new ClassPathResource("../../resources/FIRS-Logo.png"); 		      
			
	    	   Image   firsLogo = Image.getInstance(res.getURI().toString());  
		       firsLogo.scaleAbsolute(200, 30);
		       firsLogo.setAlignment(Image.ALIGN_LEFT);
		       firsLogo.setAbsolutePosition(30, 770);      
		       document.add(firsLogo);
	      }catch(Exception e){
	    	  
	      }
	      try{
	    	  ClassPathResource res = new ClassPathResource("../../resources/jtblogo.png"); 		      
			
	    	   
		       Image jtbLogo = Image.getInstance(res.getURI().toString());  
		       jtbLogo.scaleAbsolute(40, 40);	       
		       jtbLogo.setAlignment(Image.ALIGN_RIGHT);
		       jtbLogo.setAbsolutePosition(500, 770);
		       document.add(jtbLogo);
		       
	      }catch(Exception e){
	    	  
	      }	      
	       
	        Font rFont=new Font(FontFamily.COURIER,20,Font.BOLD);
	        Paragraph rParagraph =new Paragraph("DAILY TAX SERVICES REPORT", rFont );
	        rParagraph.setAlignment(Paragraph.ALIGN_CENTER);        
	        document.add(rParagraph);
	        
	        Font dateFont=new Font(FontFamily.COURIER,15,Font.BOLD);
	        Paragraph dateParagraph =new Paragraph("DATE: " + today, dateFont);
	        dateParagraph.setAlignment(Paragraph.ALIGN_CENTER);
	        dateParagraph.setSpacingAfter(20);
	        document.add(dateParagraph);
	        
	        Font f=new Font(FontFamily.COURIER,14,Font.BOLD);
	        Paragraph titleParagraph =new Paragraph("Report Summary" ,f);
	        document.add(titleParagraph);
	       
	        // Create Summary Table
	        PdfPTable summaryTable = new PdfPTable(4);
	        summaryTable.setWidthPercentage(100.0f);
	        summaryTable.setWidths(new float[] {2.0f, 2.0f,2.0f, 2.0f});
	        summaryTable.setSpacingBefore(20);
	        summaryTable.setSpacingAfter(5);            
	      
	        
	        
	        // define font for bold font
	        Font boldFont = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
	        Font normalFont = new Font(Font.FontFamily.COURIER, 8);
	        
	            
	        String sDate = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  mm:ss"));
	           	       
	        
	        PdfPCell scell = new PdfPCell();
	        scell.setPadding(7);
	        
	        scell.setPhrase(new Phrase("Start Date",boldFont));
	        summaryTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase(sDate,normalFont));
	        summaryTable.addCell( scell);
	        
	        scell.setPhrase(new Phrase("End Date",boldFont));
	        summaryTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase(endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm:ss")),normalFont));
	        summaryTable.addCell(scell);
	        	        
	        
	        document.add(summaryTable);	    
	        
	        String incomingRcCount = tinGenerationRequestHistoryService.getCountByDateRequested(startDate,endDate,null).toString();	
	        String outgoingRcCount = tinGenerationRequestHistoryService.getCountByDateOfDepartureToJtb(startDate,endDate).toString();	
	        String incomingTinCount = tinGenerationRequestHistoryService.getCountByDateResponded(startDate,endDate,null).toString();	    
	        
	        
	        // set Row span
	        
	        PdfPTable countTable = new PdfPTable(2);
	        countTable.setWidthPercentage(100.0f);
	        countTable.setWidths(new float[] {2.0f, 2.0f});
	        countTable.setSpacingBefore(20);
	        countTable.setSpacingAfter(5);  
	        	       
	       
	        scell.setPhrase(new Phrase("Total Number of Records Received From Corporate Registry",boldFont));
	        countTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase(incomingRcCount,normalFont));
	        countTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase("Total Number of Records sent to TIN Registry",boldFont));
	        countTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase(outgoingRcCount,normalFont));
	        countTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase("Total Number of Records received from TIN Registry",boldFont));
	        countTable.addCell(scell);
	        
	        scell.setPhrase(new Phrase(incomingTinCount,normalFont));
	        countTable.addCell(scell);
	        	       	        	       	        
	        document.add(countTable);	        
	    	   
	       
	       
	        // Define Incoming RC Number Table Properties         
	        PdfPTable incomingRCTable = new PdfPTable(3);
	        incomingRCTable.setWidthPercentage(100.0f);
	        incomingRCTable.setWidths(new float[] {3.0f,6.0f, 2.0f});
	        incomingRCTable.setSpacingBefore(10);
	               
	        
	        // define font for table header row
	        Font headerfont = FontFactory.getFont(FontFactory.COURIER);
	        headerfont.setColor(BaseColor.WHITE);
	        headerfont.setSize(7);
	        
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(new BaseColor(218, 41, 28));
	        cell.setPadding(5);
	        
	        // Set Up incoming rc Table Header        
	        cell.setPhrase(new Phrase("RC Number", headerfont));
	        incomingRCTable.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Name of Company", headerfont));
	        incomingRCTable.addCell(cell);         
	        
	        cell.setPhrase(new Phrase("Date of Registration", headerfont));
	        incomingRCTable.addCell(cell);
	        
	    
	               
	        Collection<TinGenerationRequestHistory> incomingRcDetails = tinGenerationRequestHistoryService.getAllByDateRequested(startDate,endDate);
	           
	           
	       // details for incoming RC
	        
	        if (incomingRcDetails.size()> 0){
	            incomingRCTable.setHeaderRows(1);
	        
	        	
	        	for (TinGenerationRequestHistory tx : incomingRcDetails) {       	      
	        	    incomingRCTable.addCell(new Phrase(tx.getRcno(), FontFactory.getFont(FontFactory.COURIER, 8)));
	        	    incomingRCTable.addCell(new Phrase(tx.getName_of_organization(), FontFactory.getFont(FontFactory.COURIER, 8)));
	        		incomingRCTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getDate_of_registration(), FontFactory.getFont(FontFactory.COURIER, 8)));
	        		          
	            }	
	        }
	        else{
	        	
	        	
	        	
	        	  incomingRCTable.addCell(new Phrase("No Record", FontFactory.getFont(FontFactory.COURIER, 8)));
	        	  	
	        	
	        }
	        
	     
	       
	        document.add(new Paragraph("List of Records Received From Corporate Registry ", new Font(Font.FontFamily.COURIER,10,Font.BOLD)));                      
	        document.add(incomingRCTable);
	        
	   			        
	        
		     // Define Incoming RC Number Table Properties         
		     PdfPTable outgoingRCTable = new PdfPTable(4);
		     outgoingRCTable.setWidthPercentage(100.0f);
		     outgoingRCTable.setWidths(new float[] {3.0f,5.0f, 4.0f, 4.0f});
		     outgoingRCTable.setSpacingBefore(15);
	        
	        // define table header cell
	        PdfPCell rccell = new PdfPCell();
	        rccell.setBackgroundColor(new BaseColor(218, 41, 28));
	        rccell.setPadding(5);
	        
	        // Set Up outgoing rc  Table Header        
	        rccell.setPhrase(new Phrase("RC Number", headerfont));
	        outgoingRCTable.addCell(rccell);
	         
	        rccell.setPhrase(new Phrase("Name of Company", headerfont));
	        outgoingRCTable.addCell(rccell);         
	        
	        rccell.setPhrase(new Phrase("Address", headerfont));
	        outgoingRCTable.addCell(rccell);
	        
	        rccell.setPhrase(new Phrase("Line of Business", headerfont));
	        outgoingRCTable.addCell(rccell);
	        
	       
	        
	        // details for outgoing RC
	        Collection<TinGenerationRequestHistory> outgoingRcDetails = tinGenerationRequestHistoryService.getAllByResponseDate(startDate,endDate);	
	       
	        
	        if(outgoingRcDetails.size() > 0)
	        {
	        	 outgoingRCTable.setHeaderRows(1);
	        
	        	for (TinGenerationRequestHistory tx : outgoingRcDetails) {       	
		        	
			        
		        	outgoingRCTable.addCell(new Phrase(tx.getRcno(), FontFactory.getFont(FontFactory.COURIER, 8)));
		        	outgoingRCTable.addCell(new Phrase(tx.getName_of_organization(), FontFactory.getFont(FontFactory.COURIER, 8)));
		        	outgoingRCTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getRepresentative_address(), FontFactory.getFont(FontFactory.COURIER, 8)));
		        	outgoingRCTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getLine_of_business(), FontFactory.getFont(FontFactory.COURIER, 8)));
	        		          
	              }	
	        	
	        }
	        else{
	        	System.err.println(" no records");
	        	outgoingRCTable.addCell(new Phrase("No Record", FontFactory.getFont(FontFactory.COURIER, 8)));
	        	  
	        }
	        
         
	        document.add(new Paragraph("List of Records sent to TIN Registry ", new Font(Font.FontFamily.COURIER,10,Font.BOLD)));                      
	        document.add(outgoingRCTable);
	        
	      
	        
	        // Define Incoming TIN Table Properties         
		     PdfPTable incomingTINTable = new PdfPTable(4);
		     incomingTINTable.setWidthPercentage(100.0f);
		     incomingTINTable.setWidths(new float[] {3.0f,5.0f, 4.0f, 4.0f});
		     incomingTINTable.setSpacingBefore(15);
	        
	    
	        // Set Up tin  Table Header   
	        PdfPCell tincell = new PdfPCell();
	        tincell.setBackgroundColor(new BaseColor(218, 41, 28));
	        tincell.setPadding(5);
	        
	             
	        tincell.setPhrase(new Phrase("Corporate TIN", headerfont));
	        incomingTINTable.addCell(tincell);
	         
	        tincell.setPhrase(new Phrase("Name of Company", headerfont));
	        incomingTINTable.addCell(tincell);         
	        
	        tincell.setPhrase(new Phrase("Date of Incorporation", headerfont));
	        incomingTINTable.addCell(tincell);
	        
	        tincell.setPhrase(new Phrase("State", headerfont));
	        incomingTINTable.addCell(tincell);
	        
	       
		       
	        // details for incoming TIN
	        Collection<TinGenerationRequestHistory> incomingTinDetails = tinGenerationRequestHistoryService.getAllByDateOfDepartureToJtb(startDate,endDate,null);
		     
	        
	        if(incomingTinDetails.size() > 0 ){
	        	
	        	 incomingTINTable.setHeaderRows(1);
	        	 for (TinGenerationRequestHistory tx : incomingTinDetails) {       	
 			        
	                 incomingTINTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getTin(), FontFactory.getFont(FontFactory.COURIER, 8)));
	                 incomingTINTable.addCell(new Phrase(tx.getName_of_organization(), FontFactory.getFont(FontFactory.COURIER, 8)));
	                 incomingTINTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getDate_of_incorporation(), FontFactory.getFont(FontFactory.COURIER, 8)));
	                 incomingTINTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getState(), FontFactory.getFont(FontFactory.COURIER, 8)));
	            		          
	                 }     
	        	
	        }
             	        
	        else{
	        	
	        	incomingTINTable.addCell(new Phrase("No Record", FontFactory.getFont(FontFactory.COURIER, 8)));
	        	
	        }
	        document.add(new Paragraph("List of Records received from TIN Registry ", new Font(Font.FontFamily.COURIER,10,Font.BOLD)));                      
	        document.add(incomingTINTable);
	               	       	        
	        document.close();
	        
	       
	   
	    }

	
}
