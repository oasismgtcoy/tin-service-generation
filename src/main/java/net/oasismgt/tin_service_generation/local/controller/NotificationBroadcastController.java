package net.oasismgt.tin_service_generation.local.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;

//@Controller
//@EnableScheduling
public class NotificationBroadcastController {
	
	@Autowired private AccountSessionManager accountSessionManager;
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;	
	@Autowired private DateTimeUtilities dateTimeUtilities;
	
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total RC received : last day
	@Scheduled(cron = "0  0  0 ? * 2 *", zone = "GMT+1:00") // Total RC received : last week
	@Scheduled(cron = "0  0  0 1 * * *", zone = "GMT+1:00") // Total RC received : last month
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total RC received : last year
	
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total RC sent : last day
	@Scheduled(cron = "0  0  0 ? * 2 *", zone = "GMT+1:00") // Total RC sent : last week
	@Scheduled(cron = "0  0  0 1 * * *", zone = "GMT+1:00") // Total RC sent : last month
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total RC sent : last year
	
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total TIN received : last day
	@Scheduled(cron = "0  0  0 ? * 2 *", zone = "GMT+1:00") // Total TIN received : last week
	@Scheduled(cron = "0  0  0 1 * * *", zone = "GMT+1:00") // Total TIN received : last month
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total TIN received : last year
	
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total TIN Notification sent : last day
	@Scheduled(cron = "0  0  0 ? * 2 *", zone = "GMT+1:00") // Total TIN Notification sent : last week
	@Scheduled(cron = "0  0  0 1 * * *", zone = "GMT+1:00") // Total TIN Notification sent : last month
	@Scheduled(cron = "0  0  0 * * * *", zone = "GMT+1:00") // Total TIN Notification sent : last year
	
	public void userdashboard(HttpServletRequest req,Model model, RedirectAttributes fmodel){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			
		}

		model.addAttribute("pageTitle", "Today's Report");
		model.addAttribute("incoming_rc_count",tinGenerationRequestHistoryService.getCountByDateRequested(LocalDate.now().atStartOfDay(),LocalDateTime.now(),null));	
		model.addAttribute("outgoing_rc_count",tinGenerationRequestHistoryService.getCountByDateOfDepartureToJtb(LocalDate.now().atStartOfDay(),LocalDateTime.now()));	
		model.addAttribute("incoming_tin_count", tinGenerationRequestHistoryService.getCountByDateResponded(LocalDate.now().atStartOfDay(),LocalDateTime.now(),null));
		model.addAttribute("rc_numbers_count", tinGenerationRequestHistoryService.getCountAll());
		
	}
	

	
	public String notSent(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login"; 
		}
		model.addAttribute("tableTitle", "INBOUND CORPORATE RC-NUMBERs FROM MDA");
		
		if(rcNumber!=null&&!rcNumber.trim().isEmpty())
			return processSingleRC(model,rcNumber);		
		if(startDate != null && endDate != null && !startDate.trim().isEmpty() && !endDate.trim().isEmpty()){
			model.addAttribute("incoming_rc_numbers",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.parse(startDate,dateTimeUtilities.getDateFormatter()).atStartOfDay(),LocalDate.parse(endDate,dateTimeUtilities.getDateFormatter()).atStartOfDay().plusHours(24),null));	
			model.addAttribute("pageTitle", startDate+" to "+endDate);
			return "rc_tin_report_list";
		}
		model.addAttribute("incoming_rc_numbers",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.now().atStartOfDay(),LocalDateTime.now(),null));	
		model.addAttribute("pageTitle", "today");
		return "rc_tin_report_list";		
	}
	
	public String rcreords(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		model.addAttribute("tableTitle", "CORPORATE RC-NUMBER TO TIN RECORD");
		
		if(rcNumber!=null&&!rcNumber.trim().isEmpty())
			return processSingleRC(model,rcNumber);		
		if(startDate != null && endDate != null && !startDate.trim().isEmpty() && !endDate.trim().isEmpty()){
			model.addAttribute("incoming_rc_numbers_records",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.parse(startDate,dateTimeUtilities.getDateFormatter()).atStartOfDay(),LocalDate.parse(endDate,dateTimeUtilities.getDateFormatter()).atStartOfDay().plusHours(24)));	
			model.addAttribute("pageTitle", startDate+" to "+endDate);
			return "rc_tin_report_list";
		}
		model.addAttribute("incoming_rc_numbers_records",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.now().atStartOfDay(),LocalDateTime.now()));	
		model.addAttribute("pageTitle", "today");
		return "rc_tin_report_list";		
	}
	
	public String sent(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		model.addAttribute("tableTitle", "OUTBOUND CORPORATE RC-NUMBERs TO JTB");
		if(rcNumber!=null&&!rcNumber.trim().isEmpty())
			return processSingleRC(model,rcNumber);
		
		if(startDate != null && endDate != null && !startDate.trim().isEmpty() && !endDate.trim().isEmpty()){
			model.addAttribute("outgoing_rc_numbers",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.parse(startDate,dateTimeUtilities.getDateFormatter()).atStartOfDay(),LocalDate.parse(endDate,dateTimeUtilities.getDateFormatter()).atStartOfDay().plusHours(24),MessageStatus.RECEPT_ACKNOWLEDGED));	
			model.addAttribute("pageTitle", startDate+" to "+endDate);
			return "rc_tin_report_list";
		}
		model.addAttribute("outgoing_rc_numbers",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.now().atStartOfDay(),LocalDateTime.now(),MessageStatus.RECEPT_ACKNOWLEDGED));	
		model.addAttribute("pageTitle", "today");
		return "rc_tin_report_list";		
	}
	
	public String allRc(HttpServletRequest req,Model model, RedirectAttributes fmodel,String startDate,String endDate,String rcNumber){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		model.addAttribute("tableTitle", "INBOUND CORPORATE TIN FROM JTB");
		if(rcNumber!=null&&!rcNumber.trim().isEmpty())
			return processSingleRC(model,rcNumber);
		
		if(startDate != null && endDate != null && !startDate.trim().isEmpty() && !endDate.trim().isEmpty()){
			model.addAttribute("incoming_rc_tin",tinGenerationRequestHistoryService.getAllByResponseDate(LocalDate.parse(startDate,dateTimeUtilities.getDateFormatter()).atStartOfDay(),LocalDate.parse(endDate,dateTimeUtilities.getDateFormatter()).atStartOfDay().plusHours(24),MessageStatus.HAS_TIN));	
			model.addAttribute("pageTitle", startDate+" to "+endDate);
			return "rc_tin_report_list";
		}
		model.addAttribute("incoming_rc_tin", tinGenerationRequestHistoryService.getAllByResponseDate(LocalDate.now().atStartOfDay(),LocalDateTime.now(),MessageStatus.HAS_TIN));
		model.addAttribute("pageTitle", "today");
		return "rc_tin_report_list";		
	}
	
	private String processSingleRC(Model model,String rcNumber){
		TinGenerationRequestHistory history = tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
		if(history==null){
			model.addAttribute("incoming_rc_numbers",Collections.emptyList());	
			model.addAttribute("pageTitle", rcNumber);
			return "rc_tin_report_list";
		}
		if(MessageStatus.HAS_TIN.equals(history.getMessageStatus()))
			model.addAttribute("incoming_rc_tin",Arrays.asList(history));	
		else if(MessageStatus.NOT_SENT.equals(history.getMessageStatus()))
			model.addAttribute("incoming_rc_numbers",Arrays.asList(history));	
		else if(MessageStatus.RECEPT_ACKNOWLEDGED.equals(history.getMessageStatus()))
			model.addAttribute("outgoing_rc_numbers",Arrays.asList(history));	
				
		model.addAttribute("pageTitle", rcNumber);
		return "rc_tin_report_list";
	}
	
}
