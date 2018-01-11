package net.oasismgt.tin_service_generation.local.service.util;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;


public interface TinHistoryForkAndProcess {
	/*public SingleTinRequestWrapper fork(CorporateTinGenerationRequest corporateTinGenerationReques);
	public BatchTinRequestWrapper forkByStartedProcessing(Boolean started);
	public BatchTinRequestWrapper fork(LocalDateTime begin,LocalDateTime end);*/
	public void notifyProcessing(String rcNumber);
	public void notifyProcessing(Collection<String> rcNumbers);
	public void notifyProcessed(String rcNumber,String tin);
	public void notifyProcessed(Map<String,String> rcNumbers);
}
