package net.oasismgt.tin_service_generation.local.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestController {
	
	@Autowired private ReportController reportController;
	
	
	@RequestMapping(value = "sendreport", method = RequestMethod.GET)
	public String sendReport(Model model, HttpServletRequest req) throws Exception {				
		reportController.sendReport();
		return "report";
	}
}
