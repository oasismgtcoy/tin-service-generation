package net.oasismgt.tin_service_generation.local.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public interface AccountSessionManager {	
	public void put(HttpServletRequest req,String key,Object value);
	 public Object get(HttpServletRequest req,String key);
	 public boolean isUserSessionActive(HttpServletRequest req);
	 public void reset(HttpServletRequest req);
	 public void login(HttpServletRequest req,String username);
	 public ModelAndView logout(HttpServletRequest req);
}
