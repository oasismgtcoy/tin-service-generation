package net.oasismgt.tin_service_generation.local.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import net.oasismgt.tin_service_generation.local.model.MailingList;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;
import net.oasismgt.tin_service_generation.local.service.MailingListService;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.MailService;

@Controller
public class PerfomanceController {
	@Autowired
	private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	@Autowired
	private MailService mailService;
	@Autowired
	private MailingListService mailingListService;
	@Autowired
	private AccountSessionManager accountSessionManager;

	String gincomingRcCount = null;
	String gnotificationCount = null;
	String gnonotificationCount = null;
	String gaverageTime = null;
	String goverallEfficiency = null;
	String gpassedtoTinCount = null;
	DecimalFormat df = new DecimalFormat("#.00");

	LocalDateTime startDate = LocalDate.now().atStartOfDay();
	LocalDateTime endDate = startDate.plusHours(24).minusSeconds(1);

	@RequestMapping(value = "performance_report", method = RequestMethod.GET)
	public String payment(Model model, HttpServletRequest req) throws Exception {

		sendPerformanceReport();
		return "report";
	}

	@Scheduled(cron = "0 59 23 * * *", zone = "GMT+1:00")
	public void sendPerformanceReport() throws Exception {
		ByteArrayOutputStream outputStream = null;

		// now write the PDF content to the output stream
		outputStream = new ByteArrayOutputStream();

		writeReportPdf(outputStream);

		byte[] bytes = outputStream.toByteArray();

		Collection<MailingList> mailingList = mailingListService.getAll();

		String sDate = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  mm:ss"));
		String today = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		// LocalDateTime endDate = startDate.plusHours(24).minusSeconds(1);
		String eDate = endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm:ss"));

		for (MailingList mail : mailingList) {
			String subject = "Tax Services System Efficiency report for " + today;
			String body = "<p>" + " Dear " + mail.getFullName() + "," + " </p>" + "<p>"
					+ "Please find below the system performance summary for the records received from the corporate registry from "
					+ " <strong>" + today + " </strong>" + "to " + "<strong> " + today + "</strong>" + " </p>  "
					+ "<table  style = 'border: 1px solid black; padding: 4px; width:650px;' >" + " <tr>" + "<td>"
					+ " <strong>" + "Start Date" + " </strong>" + "</td>" + " <td>" + sDate + "</td>" + " <td>"
					+ " <strong>" + "End Date" + " </strong>" + "</td>" + " <td> " + eDate + "</td>" + "  </tr>"
					+ " </table> "
					+ " <table style = 'border: 1px solid black; padding: 4px; margin-top:10px; width: 650px;'>"
					+ " <tr>" + " <td>" + "<strong> " + "Total Number of Corporate Records Received" + "</strong>"
					+ "</td>" + " <td>" + gincomingRcCount + "</td>" + "</tr>" +  " <tr>" + " <td>" + "<strong> " + "Total Number of records passed to the TIN registry" + "</strong>"
					+ "</td>" + " <td>" + gpassedtoTinCount + "</td>" + "</tr>" +"<tr>" + " <td>" + "<strong>"
					+ "Total number of taxpayer(s) notified with TIN information" + "</strong>" + " </td>" + " <td>"
					+ gnotificationCount + " </td>" + "<tr>" + " <td>" + "<strong>"
					+ "Total number of records without TIN" + "</strong>" + " </td>" + " <td>" + gnonotificationCount
					+ " </td>" + "</tr>" + "</tr>" + "<tr>" + "<td>" + "<strong>"
					+ " Overall average time for TIN generation & notification per tax payer" + "</strong>" + " </td>"
					+ "  <td>" + gaverageTime + "</td>" + "</tr>" + "<tr>" + "<td>" + "<strong>"
					+ "Overall system efficiency (throughput time >= 70% within current SLA)" + "</strong>" + " </td>"
					+ "  <td>" + goverallEfficiency + "</td>" + "</tr>" + "</table>" + " <br><" + "br>" + "<p>"
					+ "Attached is a PDF file containing this more details of the report." + "</p>" + "<br>" + "<br>"
					+ "<p>" + "Yours faithfully," + "</p>" + "<p>" + "Tax Services Report Team" + "</p>" + "<p>"
					+ " Visit: www.taxservices.gov.ng  |  E-mail: support@taxservices.gov.ng  |" + "</p>";

			mailService.sendDailyPerformanceReport("reports@taxservices.gov.ng", mail.getEmail(), subject, body, bytes);
		}

	}

	public class HeaderFooterPageEvent extends PdfPageEventHelper {

		public void onStartPage(PdfWriter writer, Document document) {

			Rectangle rect = writer.getBoxSize("rectangle");

			// FIRS Header Logo
			Image firslogo = null;
			try {
				ClassPathResource res = new ClassPathResource("../../resources/FIRS-Logo1.png");
				// firsLogo = Image.getInstance(res.getURI().toString());
				firslogo = Image.getInstance(res.getURI().toString());
				// firslogo =
				// Image.getInstance("/isds-tin-service-generation/src/main/webapp/resources/FIRS-Logo.png");

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

			firslogo.scaleAbsolute(240, 65);
			firslogo.setAbsolutePosition((rect.getLeft() + rect.getRight()) / 2 - 275, rect.getTop() - 30);
			firslogo.setAlignment(Element.ALIGN_LEFT);
			try {
				writer.getDirectContent().addImage(firslogo);
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// text header
			Font headerFont = new Font(FontFamily.COURIER, 12, Font.BOLD);

			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase("Business Performance Report", headerFont), 350, 798, 0);

			// JTB Header Logo
			Image jtblogo = null;
			try {
				ClassPathResource res = new ClassPathResource("../../resources/jtblogo.png");
				// firsLogo = Image.getInstance(res.getURI().toString());
				jtblogo = Image.getInstance(res.getURI().toString());
				// jtblogo =
				// Image.getInstance("/isds-tin-service-generation/src/main/webapp/resources/jtblogo.png");
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
			jtblogo.scaleAbsolute(60, 60);
			jtblogo.setAlignment(Image.ALIGN_RIGHT);

			Chunk chunk = new Chunk(jtblogo, 8, -45);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(chunk),
					rect.getRight(), 830, 0);

		}

		public void onEndPage(PdfWriter writer, Document document) {

			Font numberFont = new Font(FontFamily.COURIER, 8, Font.BOLD);

			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
					new Phrase("Page " + document.getPageNumber(), numberFont), 550, 30, 0);

		}

	}

	protected PdfNumber orientation = PdfPage.PORTRAIT;

	public void setOrientation(PdfNumber orientation) {
		this.orientation = orientation;
	}

	public void writeReportPdf(OutputStream outputStream) throws Exception {

		Document document = new Document(PageSize.A4);

		document.setMargins(20, 20, 100, 100);

		PdfWriter writer = PdfWriter.getInstance(document, outputStream);

		document.open();

		ClassPathResource cres = new ClassPathResource("../../resources/PerformanceCover.pdf");
		String coverUrl = cres.getURI().toString();
		PdfReader cover = new PdfReader(coverUrl);
		try {
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

			String longDate = startDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy ")).toUpperCase();

			Font dateFont = new Font(FontFamily.TIMES_ROMAN, 30, Font.BOLD);
			dateFont.setColor(BaseColor.WHITE);
			ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(longDate, dateFont),
					160, 35, 0);

			// document.add(new Paragraph( new Phrase("Date Holder")));
			document.add(img);
		} catch (Exception e) {

		}

		document.newPage();
		Rectangle rect = new Rectangle(30, 30, 550, 800);
		writer.setBoxSize("rectangle", rect);

		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);

		String today = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy "));

		try {
			ClassPathResource res = new ClassPathResource("../../resources/FIRS-Logo1.png");

			Image firsLogo = Image.getInstance(res.getURI().toString());
			firsLogo.scaleAbsolute(240, 65);
			firsLogo.setAlignment(Image.ALIGN_LEFT);
			firsLogo.setAbsolutePosition(30, 770);
			document.add(firsLogo);
		} catch (Exception e) {

		}
		try {
			ClassPathResource res = new ClassPathResource("../../resources/jtblogo.png");
			Image jtbLogo = Image.getInstance(res.getURI().toString());
			jtbLogo.scaleAbsolute(50, 50);
			jtbLogo.setAlignment(Image.ALIGN_RIGHT);
			jtbLogo.setAbsolutePosition(500, 780);
			document.add(jtbLogo);

		} catch (Exception e) {

		}

		Font rFont = new Font(FontFamily.COURIER, 20, Font.BOLD);
		Paragraph rParagraph = new Paragraph("DAILY TAX SERVICES BUSINESS PERFORMANCE REPORT", rFont);
		rParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(rParagraph);

		Font dateFont = new Font(FontFamily.COURIER, 15, Font.BOLD);
		Paragraph dateParagraph = new Paragraph("DATE: " + today, dateFont);
		dateParagraph.setAlignment(Paragraph.ALIGN_CENTER);
		dateParagraph.setSpacingAfter(20);
		document.add(dateParagraph);

		Font f = new Font(FontFamily.COURIER, 14, Font.BOLD);
		Paragraph titleParagraph = new Paragraph("Report Summary", f);
		document.add(titleParagraph);

		// Create Summary Table
		PdfPTable summaryTable = new PdfPTable(4);
		summaryTable.setWidthPercentage(100.0f);
		summaryTable.setWidths(new float[] { 2.0f, 2.0f, 2.0f, 2.0f });
		summaryTable.setSpacingBefore(20);
		summaryTable.setSpacingAfter(5);

		// define font for bold font
		Font boldFont = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
		Font normalFont = new Font(Font.FontFamily.COURIER, 8);

		String sDate = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  mm:ss"));

		PdfPCell scell = new PdfPCell();
		scell.setPadding(7);

		scell.setPhrase(new Phrase("Start Date", boldFont));
		summaryTable.addCell(scell);

		scell.setPhrase(new Phrase(sDate, normalFont));
		summaryTable.addCell(scell);

		scell.setPhrase(new Phrase("End Date", boldFont));
		summaryTable.addCell(scell);

		scell.setPhrase(new Phrase(endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy  hh:mm:ss")), normalFont));
		summaryTable.addCell(scell);

		document.add(summaryTable);

		Font boldfont = new Font(FontFamily.COURIER, 8, Font.BOLD);
		Font notefont = new Font(FontFamily.COURIER, 8);

		// set Row span
		PdfPTable countTable = new PdfPTable(2);
		countTable.setWidthPercentage(100.0f);
		countTable.setWidths(new float[] { 2.0f, 2.0f });
		countTable.setSpacingBefore(20);
		countTable.setSpacingAfter(5);

		// Define Incoming RC Number Table Properties
		PdfPTable incomingRCTable = new PdfPTable(10);
		incomingRCTable.setWidthPercentage(100.0f);
		incomingRCTable.setWidths(new float[] { 3.0f, 4.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, });
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

		/*cell.setPhrase(new Phrase("Date of Registration", headerfont));
		incomingRCTable.addCell(cell);*/

		cell.setPhrase(new Phrase("Date/Time of record receipt into Tax Services", headerfont));
		incomingRCTable.addCell(cell);		
		
		cell.setPhrase(new Phrase("Date/Time of record passed to the TIN registry", headerfont));
		incomingRCTable.addCell(cell);

		cell.setPhrase(new Phrase("Date/Time of TIN generation", headerfont));
		incomingRCTable.addCell(cell);
				
		cell.setPhrase(new Phrase("Date/Time of TIN Notification (via email)", headerfont));
		incomingRCTable.addCell(cell);
		
		cell.setPhrase(new Phrase("Transmittal time to TIN registry per record", headerfont));
		incomingRCTable.addCell(cell);		
		
		cell.setPhrase(new Phrase("TIN generation time per record  (minutes)", headerfont));
		incomingRCTable.addCell(cell);

		cell.setPhrase(new Phrase("System Efficiency per  Record", headerfont));
		incomingRCTable.addCell(cell);

		cell.setPhrase(new Phrase("Remarks(within SLA/out of SLA)Incorporated with +/- 120 minutes SLA", headerfont));
		incomingRCTable.addCell(cell);

		Long RcCount = tinGenerationRequestHistoryService.getCountByDateRequested(startDate, endDate, null);
		gincomingRcCount = RcCount.toString();
		Collection<TinGenerationRequestHistory> incomingRcDetails = tinGenerationRequestHistoryService
				.getAllByDateRequested(startDate, endDate);

		Long sumDuration = 0L;
		Double overallEfficiency = 0.0;
		Long notificationCount = 0L;
		Long noNotificationCount = 0L;
        Long passedtoTinCount = 0L;
		// details for incoming RC

		if (incomingRcDetails.size() > 0) {
			for (TinGenerationRequestHistory tx : incomingRcDetails) {
				incomingRCTable.setHeaderRows(1);

				Long duration = 0L;
				Double efficiency = 0.0;
				incomingRCTable.addCell(new Phrase(tx.getRcno(), FontFactory.getFont(FontFactory.COURIER, 8)));

				incomingRCTable
						.addCell(new Phrase(tx.getName_of_organization(), FontFactory.getFont(FontFactory.COURIER, 8)));

				/*incomingRCTable.addCell(new Phrase(tx.getCorporateTinGenerationRequest().getDate_of_registration(),
						FontFactory.getFont(FontFactory.COURIER, 8)));
*/
				// Received From MDA
				if (tx.getDateOfArrivalFromSourceMda() == null) {
					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));
				} else {
					incomingRCTable.addCell(new Phrase(
							tx.getDateOfArrivalFromSourceMda().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
							FontFactory.getFont(FontFactory.COURIER, 8)));
				}
				
				
				// Departure to JTB
				if (tx.getDateOfDepartureToJtb() == null) {
					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));
				} else {
					
					passedtoTinCount++;
					incomingRCTable.addCell(new Phrase(
							tx.getDateOfDepartureToJtb().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
							FontFactory.getFont(FontFactory.COURIER, 8)));
				}
				
				
				
				// Date of TIN Generation
				if (tx.getDateOfArrivalFromJtb() == null) {
					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));
				} else {

					incomingRCTable.addCell(
							new Phrase(tx.getDateOfArrivalFromJtb().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
									FontFactory.getFont(FontFactory.COURIER, 8)));

				}

				// Date of Email Notification
				if (tx.getNotificationDepartureDate() == null) {
					//noNotificationCount++;
					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));
				} else {

					notificationCount++;
					incomingRCTable.addCell(new Phrase(
							tx.getNotificationDepartureDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
							FontFactory.getFont(FontFactory.COURIER, 8)));

				}
				
				// Transmittal Date
				if (tx.getDateOfDepartureToJtb() != null && tx.getDateOfArrivalFromSourceMda() != null) {

					String dura = tx.getSumDifference(tx.getDateOfDepartureToJtb(),
							tx.getDateOfArrivalFromSourceMda());
					
					incomingRCTable.addCell(new Phrase(dura, FontFactory.getFont(FontFactory.COURIER, 8)));

					
				} else {

					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));

				}

				
				if (tx.getNotificationDepartureDate() != null && tx.getDateOfArrivalFromSourceMda() != null) {

					String dura = tx.getSumDifference(tx.getDateOfArrivalFromSourceMda(),
							tx.getNotificationDepartureDate());
					duration = tx.getDiffInMinutes(tx.getDateOfArrivalFromSourceMda(),
							tx.getNotificationDepartureDate());

					incomingRCTable.addCell(new Phrase(dura, FontFactory.getFont(FontFactory.COURIER, 8)));

					sumDuration += duration;

					efficiency = tx.getSystemEfficiency(duration);

				} else {

					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));

				}

				if (efficiency > 0) {

					overallEfficiency += efficiency;

					incomingRCTable.addCell(
							new Phrase(df.format(efficiency) + " %", FontFactory.getFont(FontFactory.COURIER, 8)));
				} else {
					incomingRCTable.addCell(new Phrase("OPEN", FontFactory.getFont(FontFactory.COURIER, 8)));
				}

				if (efficiency > 0) {

					incomingRCTable
							.addCell(new Phrase(tx.getSLA(efficiency), FontFactory.getFont(FontFactory.COURIER, 8)));

				} else {
					incomingRCTable.addCell(new Phrase("See Note 1", FontFactory.getFont(FontFactory.COURIER, 8)));
				}

			}

		}
		else {
			incomingRCTable.addCell(new Phrase("No Record", FontFactory.getFont(FontFactory.COURIER, 8)));

		}
		scell.setPhrase(new Phrase("Total Number of Corporate Records Received", boldFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase(RcCount.toString(), normalFont));
		countTable.addCell(scell);

		
		scell.setPhrase(new Phrase("Total Number of records passed to the TIN registry", boldFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase(passedtoTinCount.toString(), normalFont));
		countTable.addCell(scell);
		
		scell.setPhrase(new Phrase("Total number of taxpayer(s) notified with TIN information", boldFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase(notificationCount.toString(), normalFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase("Total number of records without TIN", boldFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase(noNotificationCount.toString(), normalFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase("Overall average time for TIN generation & notification per tax payer", boldFont));
		countTable.addCell(scell);

		TinGenerationRequestHistory t = new TinGenerationRequestHistory();
		Long avgTime = 0L;
		String convertAvg = null;
		String overallEff = null;
		if (RcCount > 0) {

			avgTime = t.getAverageTime(sumDuration, RcCount);
			convertAvg = t.timeConvert(avgTime);
			overallEff = df.format(t.getOverallEfficiency(overallEfficiency, RcCount)) + " %";

		} else {
			convertAvg = "NO RECORD";
			overallEff = "OPEN";
		}

		scell.setPhrase(new Phrase(convertAvg, normalFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase("Overall system efficiency (throughput time >= 70% within current SLA)", boldFont));
		countTable.addCell(scell);

		scell.setPhrase(new Phrase(overallEff, normalFont));
		countTable.addCell(scell);

		document.add(countTable);

		document.add(new Paragraph(new Phrase("Note:", notefont)));
		document.add(new Paragraph(
				"a. TIN generation time per record  = Date/Time of TIN Notification (via email) - Date/Time of record receipt into Tax Services ",
				notefont));
		document.add(new Paragraph(
				"b. Overall Average time for TIN generation and notification = Sum total of TIN generation time per record  / Total Number of Corporate Records Received ",
				notefont));
		document.add(new Paragraph(
				"c. System Efficiency per  Record = (250 minutes / Duration to generate TIN ) * 100% ", notefont));
		document.add(new Paragraph(
				"d. Overall system efficiency = sum total of System Efficiency per Record / Total Number of Corporate Records Received ",
				notefont));
		document.add(new Paragraph(
				"e. Remarks (within SLA/out of SLA). If efficiency is greater than 70% then remarks is “within SLA” otherwise it is “Out  of SLA”. Incorporated with +/- 120 minutes SLA ",
				notefont));
		document.add(new Paragraph(
				"f. Transmittal time to TIN registry per record = Date/Time of record passed to the TIN registry - Date/Time of record receipt into Tax Services ",
				notefont));		
		document.newPage();

		gnotificationCount = notificationCount.toString();
		gnonotificationCount = noNotificationCount.toString();
		gaverageTime = convertAvg;
		goverallEfficiency = overallEff;
		gpassedtoTinCount = passedtoTinCount.toString();
		
		document.add(
				new Paragraph("List of Corporate records Received ", new Font(Font.FontFamily.COURIER, 10, Font.BOLD)));
		document.add(incomingRCTable);

		document.add(new Paragraph("Note 1:", notefont));
		document.add(new Paragraph(
				" The efficiency indices of this record are yet to be determined at the time of report generation either due to erroneous data fields or incomplete data records or inability for the TIN registry to generate TIN or unavailable email service for notifications. ",
				notefont));

		document.close();
		cover.close();

	}

}
