package net.oasismgt.tin_service_generation.local.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import net.oasismgt.tin_service_generation.local.service.AccountSessionManager;

/**
 * This method manages the users session
 * */
@Service
@Transactional
public class AccountSessionManagerImpl implements AccountSessionManager{
	
    private HttpSession userSession;

    public void login(HttpServletRequest req,String username){
		 reset(req);     
         userSession = (HttpSession) req.getSession(true);
         userSession.setMaxInactiveInterval(3600 * 10);
         userSession.setAttribute("cAccount", username);
    }
	 	 
	 public boolean isUserSessionActive(HttpServletRequest req){
		 HttpSession userSession=null;  
    	 userSession = (HttpSession) req.getSession(false);
    	 return (userSession!=null && userSession.getAttribute("cAccount")!=null);
    	 
	 }
		
		 public void put(HttpServletRequest req,String key,Object value){			
			 HttpSession userSession=null;  
	    	 userSession = (HttpSession) req.getSession(false);
	    	  if(userSession!=null){
	    		  userSession.setAttribute(key, value);
	    	  }
						    
		 }
		 
		 public Object get(HttpServletRequest req,String key){
			 HttpSession userSession=null;  
	    	 userSession = (HttpSession) req.getSession(false);
	    	 if(userSession!=null)     
		            return  userSession.getAttribute(key);
		       else return null;
		 }
		 
	    public ModelAndView logout(HttpServletRequest req){
	    	HttpSession userSession=null;  
	    	 userSession = (HttpSession) req.getSession(false);
	        if(userSession!=null){
	        	userSession.invalidate();   
	        }
	        reset(req);
	        return new ModelAndView("redirect:login");
	    }
		
		@Override
		public void reset(HttpServletRequest req) {
			 userSession = (HttpSession) req.getSession(false);
	    	  if(userSession!=null){	    		
					userSession.setAttribute("cAccount", null);
					/*userSession.setAttribute("instrumentKind", null);
					userSession.setAttribute("transactionKind", null);
					userSession.setAttribute("transactionType", null);
					userSession.setAttribute("stampdutyTransaction", null);
					userSession.setAttribute("parties", null);
					userSession.setAttribute("witnesses", null);
					userSession.setAttribute("mdaAccess", null);
					userSession.setAttribute("principal", null);
					userSession.setAttribute("principals", null);
					userSession.setAttribute("mdaTransactionId", null);*/
					 
	    	  }
		}

}
