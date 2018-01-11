package net.oasismgt.tin_service_generation.local.service.util;

import javax.servlet.http.HttpServletRequest;

import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;

public interface TinNotificationService{
	public void sendNotification(HttpServletRequest req,TinGenerationRequestHistory history);
}
