package net.oasismgt.tin_service_generation.local.service.util;

import java.time.LocalDateTime;

import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;

public interface TinRequestPusherService{
	public void pushSingleToEndpoint(TinGenerationRequestHistory history);
	public void pushBulkToEndpoint(LocalDateTime start,LocalDateTime end);
	public void pushAllToEndpoint();
}
