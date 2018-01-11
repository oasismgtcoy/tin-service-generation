package net.oasismgt.tin_service_generation.local.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;

@Controller
public class AccountController {
	
	@Autowired private AccountSessionManager accountSessionManager;
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String index(Model model){			
		return "index";
	}
	
	@RequestMapping(value="/report",method=RequestMethod.GET)
	public String report(Model model){			
		return "front";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model,RedirectAttributes fmodel){		
		model.addAttribute("current_index_widget", "base::login_widget");
		model.addAttribute("page_title","Welcome");		
		return "redirect:/";
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String loginPost(HttpServletRequest req,Model model, RedirectAttributes fmodel,String username,String password){
		
		if(("admin".equals(username)&&"@password@1234".equals(password))){
			accountSessionManager.login(req,username);
			model.addAttribute("current_index_widget", "base::login_widget");
			model.addAttribute("page_title","Welcome");		
			return "redirect:/dashboard";
		}else if(("administrator@taxservices.gov.ng".equals(username)&&"password101".equals(password))){
			accountSessionManager.login(req,username);
			model.addAttribute("current_index_widget", "base::login_widget");
			model.addAttribute("page_title","Welcome");		
			return "redirect:/administrator/dashboard";
		}
		
		
		fmodel.addFlashAttribute("errmessage", "Incorrect username or password.");
		fmodel.addFlashAttribute("isError", true);
		accountSessionManager.logout(req);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest req,Model model){		
		accountSessionManager.logout(req);
		return "redirect:/login";
	}
	@RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public String root(HttpServletRequest req,Model model, RedirectAttributes fmodel){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}

		model.addAttribute("pageTitle", "Incoming RC/Tin Requests");
		model.addAttribute("rc_tin_list",tinGenerationRequestHistoryService.getAllByDateRequested(LocalDate.now().atStartOfDay(),LocalDateTime.now()));	
		model.addAttribute("jtb_rc_tin_list",tinGenerationRequestHistoryService.getAllByResponseDate(LocalDate.now().atStartOfDay(),LocalDateTime.now()));	
		model.addAttribute("backMessage", "today's incoming RC/Tin request");
		return "dashboard";
	}
	
	
	/*@RequestMapping(value="/admin/portal/system/settings",method=RequestMethod.GET)
	public String setup(HttpServletRequest req,Model model,RedirectAttributes fmodel){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		
		
		model.addAttribute("lgas",lgaService.getAll().stream().sorted((a,b) -> a.getName().compareToIgnoreCase(b.getName())).collect(Collectors.toList()));
		model.addAttribute("taxOffices",taxOfficeService.getAll().stream().sorted((a,b) -> a.getName().compareToIgnoreCase(b.getName())).collect(Collectors.toList()));
		model.addAttribute("states",stateService.getAll().stream().sorted((a,b) -> a.getName().compareToIgnoreCase(b.getName())).collect(Collectors.toList()));
		model.addAttribute("preferences",propertyEntryService.getAll());
		return "setup";
	}*/

	/*@RequestMapping(value="/update-state-taxoffice",method=RequestMethod.POST)
	public String setState(HttpServletRequest req,Model model, RedirectAttributes fmodel,String stateName,String taxOfficeName, Long taxOfficeId){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(stateName==null || stateName.isEmpty() || taxOfficeName==null || taxOfficeName.isEmpty()){
			fmodel.addFlashAttribute("errmessage", "State name and tax office name are required");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/dashboard";
		}	
		
		State state = stateService.getByName(stateName);
		if(state!=null){
			if(taxOfficeName!=null && !taxOfficeName.trim().isEmpty()){
				state.setTaxOfficeName(taxOfficeName);	
				stateService.update(state);
				changeTinRequestStateTaxOffice(stateName,taxOfficeName);
				fmodel.addFlashAttribute("errmessage", "State "+stateName+" updated");
				return "redirect:/admin/portal/system/settings";
			}
			if(taxOfficeId!=null && -1 != taxOfficeId){
				TaxOffice to=taxOfficeService.get(taxOfficeId);
				if(to!=null){
					state.setTaxOffice(to);	
					state.setTaxOfficeName(to.getName());	
					stateService.update(state);
					changeTinRequestStateTaxOffice(stateName,taxOfficeName);
					fmodel.addFlashAttribute("errmessage", "State "+stateName+" updated");
					return "redirect:/admin/portal/system/settings";
				}
				
			}
		}
		
		fmodel.addFlashAttribute("errmessage", "State "+stateName+" not updated");
		return "redirect:/admin/portal/system/settings";
		
	}
	@RequestMapping(value="/admin/portal/lga/update/rc_record-lga-name",method=RequestMethod.POST)
	public String updateLga(HttpServletRequest req,Model model, RedirectAttributes fmodel,String existingLgaName,String newLgaName){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(existingLgaName==null || existingLgaName.isEmpty() || newLgaName==null || newLgaName.isEmpty()){
			fmodel.addFlashAttribute("errmessage", "Lga name and tax office name are required");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/admin/portal/system/settings";
		}
		
		
		Collection<CorporateTinGenerationRequest> list = corporateTinGenerationRequestService.getAllByLga(existingLgaName);
		list.parallelStream().peek(r->r.setLga(newLgaName)).forEach(r->corporateTinGenerationRequestService.update(r));
		fmodel.addFlashAttribute("errmessage", "Updated "+list.size()+" record(s)");
		return "redirect:/admin/portal/system/settings";
	}
	@RequestMapping(value="/admin/portal/taxoffice/update/rc_record-tax_office-name",method=RequestMethod.POST)
	public String updateTaxOffice(HttpServletRequest req,Model model, RedirectAttributes fmodel,String existingTaxOfficeName,String newTaxOfficeName){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(existingTaxOfficeName==null || existingTaxOfficeName.isEmpty() || newTaxOfficeName==null || newTaxOfficeName.isEmpty()){
			fmodel.addFlashAttribute("errmessage", "Lga name and tax office name are required");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/admin/portal/system/settings";
		}
		
		
		Collection<CorporateTinGenerationRequest> list = corporateTinGenerationRequestService.getAllByTaxOffice(existingTaxOfficeName);
		list.parallelStream().peek(r->r.setTax_office(newTaxOfficeName)).forEach(r->corporateTinGenerationRequestService.update(r));
		fmodel.addFlashAttribute("errmessage", "Updated "+list.size()+" record(s)");
		return "redirect:/admin/portal/system/settings";
	}
	@RequestMapping(value="/update-lga-taxoffice",method=RequestMethod.POST)
	public String setLga(HttpServletRequest req,Model model, RedirectAttributes fmodel,String lgaName,String taxOfficeName,Long taxOfficeId){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(lgaName==null || lgaName.isEmpty() || taxOfficeName==null || taxOfficeName.isEmpty()){
			fmodel.addFlashAttribute("errmessage", "Lga name and tax office name are required");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/admin/portal/system/settings";
		}	
		
		Lga lga = lgaService.getByName(lgaName);
		if(lga!=null){
			if(taxOfficeName!=null && !taxOfficeName.trim().isEmpty()){
				lga.setTaxOfficeName(taxOfficeName);	
				lgaService.update(lga);
				changeTinRequestLgaTaxOffice(lgaName,taxOfficeName);
				fmodel.addFlashAttribute("errmessage", "LGA "+lgaName+" updated");
				return "redirect:/admin/portal/system/settings";
			}
			if(taxOfficeId!=null && -1 != taxOfficeId){
				TaxOffice to=taxOfficeService.get(taxOfficeId);
				if(to!=null){
					lga.setTaxOffice(to);	
					lga.setTaxOfficeName(to.getName());	
					lgaService.update(lga);
					changeTinRequestLgaTaxOffice(lgaName,taxOfficeName);
					fmodel.addFlashAttribute("errmessage", "Lga "+lgaName+" updated");
					return "redirect:/admin/portal/system/settings";
				}				
			}
		}
		fmodel.addFlashAttribute("errmessage", "LGA "+lgaName+" not updated");
		return "redirect:/admin/portal/system/settings";
		
	}
	@RequestMapping(value="/add-taxoffice",method=RequestMethod.POST)
	public String addTaxoffice(HttpServletRequest req,Model model, RedirectAttributes fmodel,TaxOffice taxOffice){	
		if(!accountSessionManager.isUserSessionActive(req)){
			fmodel.addFlashAttribute("errmessage", "You must login to proceed.");
			fmodel.addFlashAttribute("isError", true);
			accountSessionManager.logout(req);
			return "redirect:/login";
		}
		if(taxOffice==null  || taxOffice.getName()==null || taxOffice.getName().trim().isEmpty() || taxOffice.getJtbId()==null || taxOffice.getJtbId().trim().isEmpty() ){
			fmodel.addFlashAttribute("errmessage", "Tax Office name and jtb Code are required");
			fmodel.addFlashAttribute("isError", true);
			return "redirect:/admin/portal/system/settings";
		}			
		return "redirect:/admin/portal/system/settings";
		
	}
	
	private void changeTinRequestStateTaxOffice(String name,String taxOfficename){
		corporateTinGenerationRequestService.getAllByState(name).parallelStream().forEach(r->{			
			r.setTax_office(taxOfficename);
			corporateTinGenerationRequestService.update(r);
		});
	}
	private void changeTinRequestLgaTaxOffice(String name,String taxOfficename){
		corporateTinGenerationRequestService.getAllByLga(name).parallelStream().forEach(r->{			
			r.setTax_office(taxOfficename);
			corporateTinGenerationRequestService.update(r);
		});
	}
	
	@RequestMapping(value = "/admin/portal/preference/new-update", method = RequestMethod.POST)	
	public String mailingGroup(RedirectAttributes fmodel,PropertyEntry propertyEntry,HttpServletRequest req) {
		if (!accountSessionManager.isUserSessionActive(req))return null;
		
		if(propertyEntry.getName()==null||propertyEntry.getName().trim().isEmpty()||propertyEntry.getValue()==null||propertyEntry.getValue().trim().isEmpty()){
			fmodel.addFlashAttribute("errmsg","Sorry  name and value are required.");			
		    return "redirect:/admin/portal/system/settings";
		}
		
		PropertyEntry existing = propertyEntryService.getByName(propertyEntry.getName());
		if(existing!=null){
			existing.setValue(propertyEntry.getValue());
			propertyEntryService.update(existing);
			propertyEntry.setId(100l);
		    return "redirect:/admin/portal/system/settings";
		}
		try{
			propertyEntryService.add(propertyEntry);
			propertyEntry.setId(100l);
		    return "redirect:/admin/portal/system/settings";
		}catch(Exception e){
			propertyEntry.setId(101l);
			fmodel.addFlashAttribute("errmsg",e.toString());	
		    return "redirect:/admin/portal/system/settings";
		}		
						
	}
	
	*/
}
