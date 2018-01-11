package net.oasismgt.tin_service_generation.local.configs;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import net.oasismgt.tin_service_generation.local.model.PropertyEntry;
import net.oasismgt.tin_service_generation.local.service.PropertyEntryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;

@Configuration
public class PropertyEntrySetupRunner implements CommandLineRunner {
	
	@Autowired private PropertyEntryService propertyEntryService;	
	@Autowired private DateTimeUtilities dateTimeUtilities;
	
	@Override
	public void run(String... args) throws Exception {
	
		PropertyEntry entry=propertyEntryService.getByName("custom.last-successful-tin-push");
		if(entry==null){		
			 entry=new PropertyEntry("custom.last-successful-tin-push",dateTimeUtilities.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			 propertyEntryService.add(entry);
		}
		entry=propertyEntryService.getByName("custom.mode-automatic-relay-rc_number-to-jtb");
		if(entry==null){		
			 entry=new PropertyEntry("custom.mode-automatic-relay-rc_number-to-jtb","true");
			 propertyEntryService.add(entry);
		}
		
		entry=propertyEntryService.getByName("custom.tin-generation.notification-html-template");
		if(entry==null){		
			 entry=new PropertyEntry("custom.tin-generation.notification-html-template",loadInMemoryHtml());
			 propertyEntryService.add(entry);
		}	
		entry=propertyEntryService.getByName("custom.tin-generation.notification-mailingList");
		if(entry==null){		
			 entry=new PropertyEntry("custom.tin-generation.notification-mailingList","nnwokocha@oasismgt.net,nwokochaeva@gmail.com,prolifikwares@yahoo.com");
			 propertyEntryService.add(entry);
		}	
		
	}
	
	private String loadInMemoryHtml(){
		StringBuilder html=new  StringBuilder();
		html.append("	<body>  ")
		.append("	<style>")
		.append("#cover{ width:700px; height:600px;}")
		.append("#wrapper{ width:60%; margin:0 auto; box-shadow:3px  3px 4px rgba(88,88,88,0.3); border:1px solid rgba(88,88,88,0.3); min-height:1300px; }")
		.append("header{ background:linear-gradient(to right,rgba(0,98,0,0.4),rgba(0,98,0,0.3),rgba(0,98,0,0.9));}")
		.append("#head{ display:table; }")
		.append("#flag{ display:table-cell; position:relative; top:14px; left:35px; height:50px; }")
		.append("#heading{ display:table-cell; position:relative; left:88px; top:-10px; }")
		.append("#tin{ display:table-cell; position:relative; top:7px; left:135px; height:60px; }")
		.append("#headNote{ margin-top:12px; margin-bottom:10px; margin-left:130px; margin-right:92px; }")
		.append("#mypara p{ margin-top:20px; float:right; margin-right:40px }")
		.append("#address{	margin-top:70px; margin-bottom:80px; margin-left:40px; margin-right:40px } ")
		.append("#jtb{ margin-left:46%; margin-bottom:-25px; }")
		.append("#tax-footer{ padding-top:20px; width:100%; margin-right:10px; background:linear-gradient(to right,rgba(0,98,0,0.4),rgba(0,98,0,0.3),rgba(0,98,0,0.9)); }")
		.append("@media screen and (max-width:500px){")
		.append("	tin{ display:none; }")
		.append("	flag{ display:none; }")
		.append("	heading{ position:relative; left:-1px; }")
		.append("	address{ margin-top:50px; margin-bottom:40px; margin-left:2px; margin-right:40px }")
		.append("	mypara p{ margin-top:20px; margin-bottom:20px; margin-left:px")
		.append("	headNote{ position:relative; left:-110px; }")
		.append("	date{ margin-bottom:10px; margin-left:-20px; }")
		.append("}")
		.append("</style>")
		.append("<div id=\"cover\" style=\"width:700px; height:600px;\">")
		.append("	<div id=\"wrapper\" style=\"width:60%; margin:0 auto; box-shadow:3px  3px 4px rgba(88,88,88,0.3); border:1px solid rgba(88,88,88,0.3); min-height:1300px;\" >")			
		.append("			<header style=\"background:linear-gradient(to right,rgba(0,98,0,0.4),rgba(0,98,0,0.3),rgba(0,98,0,0.9));\">")
		.append("				<div id=\"head\" style=\"display:table;\">")
		.append("					<div id=\"flag\" style=\"display:table-cell; position:relative; top:14px; left:35px; height:50px;\">")
		.append("						<img src=\"##flag##\"  width=\"100px\"/>")
		.append("					</div>")
		.append("					<div id=\"heading\" style=\"display:table-cell; position:relative; left:88px; top:-10px;\">")
		.append("						<h2>FEDERAL REPUBLIC OF  NIGERIA</h2>")
		.append("					</div>")
		.append("					<div id=\"tin\" style=\"display:table-cell; position:relative; top:7px; left:135px; height:60px; \">")
		.append("						<img src=\"##jtb_logo##\"  width=\"150px\"/>")
		.append("					</div>")
		.append("				</div>")
		.append("			</header>")
		.append("			<div id=\"mypara\"> ")
		.append("				<h3 id=\"headNote\" style=\"margin-top:12px; margin-bottom:10px; margin-left:130px; margin-right:92px;\">NEW TAX IDENTIFICATION NUMBER (TIN) NOTIFICATION</h3>")
		.append("				<p style=\"margin-top:20px; float:right; margin-right:40px\" id=\"date\">##message_date## </p>")
		.append("			</div>")
		.append("			<div id=\"address\" style=\"margin-top:70px; margin-bottom:80px; margin-left:40px; margin-right:40px\">")
		.append("				<p >The Managing Director/CEO <br/>")
		.append("					##company_name##<br/>")
		.append("					##company_address## <br/>")
		.append("					##company_address_wrap##")
		.append("				</p>")
		.append("				<p>Messrs ##tax_payer_name##;</p>")
		.append("				<p>We are pleased to welcome you to the Joint Tax Board (JTB) and Federal Inland Revenue Service (FIRS), ")
		.append("				the Federal Agencies responsible for helping you achieve your business goals while meeting your tax obligation")
		.append("				to the Government.  </p>")
		.append("				<p>Please find here, your new Taxpayer Identification Number (TIN) - ##tin##.  ")
		.append("					The date of issue of your TIN is ##issuance_date##, and the Tax Authority is FIRS.")
		.append("					Your TIN is Free.</p>")
		.append("					<p>We would also like to provide you some useful information and guidance in how to ")
		.append("				best manage this new and promising lifetime relationship.</p>")
		.append("				<p><strong>What is this much talked about Taxpayer Identification Number <strong> - </strong> TIN</strong><br/>")
		.append("					The TIN is a unique number allocated and issued to identify a person ")
		.append("				(individual or company) as a duly registered tax payer in Nigeria.  ")
		.append("				It is for use by that tax payer ALONE. Registration for tax purposes ")
		.append("				is a legal obligation of every person who is required to pay tax in Nigeria.</p>				")
		.append("				<p>Find below, additional information which will be useful for your transactions.</p>")
		.append("				<p><strong>TIN VERIFICATION:</strong> </p>")
		.append("				<p>For registered businesses who don<span>'</span>t know or have forgotten their TIN should click here:<br/>")
		.append("				   <a target=\"_blank\" href=\"http://apps.firs.gov.ng/tinverification\">http://apps.firs.gov.ng/tinverification</a> and type in your RC Number/BN Number to ascertain your (TIN).")
		.append("				</p>")
		.append("				<p>Your corresponding business name, the assigned TIN and Tax Office will be displayed.</p>")
		.append("				<p><b>TAX FILING:</b></p>")
		.append("				<p>In pursuance of FIRS (E) Act 2007, it is obligatory for taxpayers ")
		.append("				to file returns on their business activities as and when due. ")
		.append("				Some of the returns are tied to due dates in which filing and ")
		.append("				payment must be completed.</p>")
		.append("				<p>Note that you must file VAT returns and make payment not later than 21st of the month following the month of reporting</p>")
		.append("				<p>Companies Income Tax (CIT), returns shall be filed not later than six ")
		.append("				months after the end of the Accounting Year or 18 months after incorporation")
		.append("				of business whichever comes first.</p>")
		.append("				<p>For information on due dates for filing and payment for other taxes visit<a target=\"_blank\" href=\"http://firs.gov.ng\"> www.firs.gov.ng</a><p>")
		.append("				<p>It is possible for you to file and pay all Federal taxes")
		.append("				online through<a target=\"_blank\" href=\"https://efiling.firs.gov.ng\"> https://efiling.firs.gov.ng.</a></p>")
		.append("				<p>This can be done by completing the e-filing access application form,")
		.append("				obtainable from our website <a target=\"_blank\" href=\"http://firs.gov.ng\">(www.firs.gov.ng)</a> which you should subscribe to and submit at your tax office. </p>")
		.append("				<p>To obtain your Tax Clearance Certificate (TCC), ")
		.append("				please go to our site and apply online once you have paid all your ")
		.append("				taxes, you will be updated on the status of the application.</p>")
		.append("				<p> For any other information, please visit our website <a target=\"_blank\" href=\"http://firs.gov.ng\">www.firs.gov.ng</a></p>")
		.append("				<p>")
		.append("					For further enquiries, please contact: <br/>")
		.append("			<strong>FIRS</strong><br/>		 ")
		.append("					Taxpayer Service Department <br/>")
		.append("					15 Sokonde Crescent, Wuse Zone 5, Abuja <br/>")
		.append("					Phone: 08113955026; 08159490000; 08115902227; 08115902218;	 <br/>")
		.append("					Email: complaints@firs.gov.ng; enquiries@firs.gov.ng; servicom@firs.gov.ng")
		.append("				</p>")
		.append("				<p>")
		.append("					<strong>JTB	</strong>	 <br/>")
		.append("					Head Office <br/>")
		.append("					Plot 1863, Lee Kuan Yew Street Asokoro District <br/>")
		.append("					(Off Mohammed Mahatir Street) Asokoro, FCT, Abuja <br/>")
		.append("					Phone: 0700CALLJTB (07002255822) <br/>")
		.append("					Email: enquiries@jtb.gov.ng ")
		.append("				</p>")
		.append("			</div>	")
		.append("				<footer>")
		.append("					<img src=\"##footer_image##\" id=\"jtb\" style=\"margin-left:46%; margin-bottom:-25px;\" width=\"65px\" height=\"49px\"/>")
		.append("					<div id=\"tax-footer\" style=\"padding-top:20px; width:100%; margin-right:10px; background:linear-gradient(to right,rgba(0,98,0,0.4),rgba(0,98,0,0.3),rgba(0,98,0,0.9)); \"></div>				")
		.append("				</footer>")
		.append("		</div>	")
		.append("		</div>")
		.append("	<body>");
				
		return html.toString();
	}
}
