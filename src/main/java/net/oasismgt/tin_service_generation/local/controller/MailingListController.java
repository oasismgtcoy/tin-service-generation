package net.oasismgt.tin_service_generation.local.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.oasismgt.tin_service_generation.local.model.MailingList;
import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;
import net.oasismgt.tin_service_generation.local.service.MailingListService;



@Controller
public class MailingListController {
	@Autowired
	private MailingListService mailingListService;
	@Autowired private AccountSessionManager accountSessionManager;
	
	@RequestMapping(value = "mailingList_list", method = RequestMethod.GET)	
	public ModelAndView mailingList(Model m,HttpServletRequest req) {
		if (!accountSessionManager.isUserSessionActive(req))return new ModelAndView("redirect:login");
	     	m.addAttribute("list",mailingListService.getAll());
		    return new ModelAndView("mailingList");				
	}
	@RequestMapping(value = "mailingList_new", method = RequestMethod.GET)
	public ModelAndView mailingListNew(Model m,HttpServletRequest req) {
		if (!accountSessionManager.isUserSessionActive(req))return new ModelAndView("redirect:login");
	     	m.addAttribute("mail", new MailingList());
		    return new ModelAndView("MailingListAdd");			
		
	}
	
	@RequestMapping(value = "mailingList_add", method = RequestMethod.POST)
	public ModelAndView mailingListAdd(Model m, HttpServletRequest req, @ModelAttribute MailingList mail) {
	System.err.println("here");
	System.err.println(mail.getFullName());
		if (!accountSessionManager.isUserSessionActive(req)) return new ModelAndView("redirect:login");  
		        mailingListService.add(mail);
		      
		     return new ModelAndView("redirect:" + "mailingList_list");			
			

	}

	@RequestMapping(value = "mailingList_view_{id}", method = RequestMethod.GET)
	public ModelAndView mailingListView(Model m,HttpServletRequest req, Long id) {
		
		if (!accountSessionManager.isUserSessionActive(req))return new ModelAndView("redirect:login");
		 m.addAttribute("mail",mailingListService.get(id));
		
		    return new ModelAndView("MailingListEdit");			
		
	}
	@RequestMapping(value = "mailingList_update", method = RequestMethod.POST)
	public ModelAndView mailingListUpdate(Model m,HttpServletRequest req, @ModelAttribute MailingList mail) {		
		if (!accountSessionManager.isUserSessionActive(req))return new ModelAndView("redirect:login");
				 
		     mailingListService.update(mail);		  
		 
		 return new ModelAndView("redirect:" + "mailingList_list");								
	}
	@RequestMapping(value = "mailingList_delete")
	public ModelAndView mailingListDelete(Model m,HttpServletRequest req, @RequestParam("colId") String id) {
		if (!accountSessionManager.isUserSessionActive(req))return new ModelAndView("redirect:login");
		MailingList s =  mailingListService.get(Long.parseLong(id));
		  if (s != null)
			  mailingListService.delete(s);
		 return new ModelAndView("redirect:" + "mailingList_list");			
				
		
	}
}
