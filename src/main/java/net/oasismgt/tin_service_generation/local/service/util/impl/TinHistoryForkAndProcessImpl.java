package net.oasismgt.tin_service_generation.local.service.util.impl;
/*
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.remote.jtb.backing.BatchTinRequestWrapper;
import net.oasismgt.tin_service_generation.remote.jtb.backing.SingleTinRequestWrapper;
import net.oasismgt.tin_service_generation.remote.jtb.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.remote.jtb.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.remote.jtb.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.remote.jtb.service.util.DateTimeUtilities;
import net.oasismgt.tin_service_generation.remote.jtb.service.util.TinHistoryForkAndProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import net.oasismgt.tin_service_generation.local.backing.Batch_Tin_Request_Response;

@Service
@Transactional
public class TinHistoryForkAndProcessImpl implements TinHistoryForkAndProcess {

	@Autowired  private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	@Autowired  private DateTimeUtilities dateTimeUtilities;
	private final String jtbEndpointUrlNew = "http://41.242.49.62:9093/tin-service/tin/generation/corporate/jtb/remote";
	@Autowired private TaskExecutor taskExecutor;
	@Override
	public SingleTinRequestWrapper fork(CorporateTinGenerationRequest corporateTinGenerationReques) {		
		return new SingleTinRequestWrapper(corporateTinGenerationReques);
	}

	@Override
	public BatchTinRequestWrapper fork(LocalDateTime begin, LocalDateTime end) {
		Collection<TinGenerationRequestHistory> all=tinGenerationRequestHistoryService.getAllByDateRequested(begin,end);
		if(!all.isEmpty()){
			BatchTinRequestWrapper batch=new BatchTinRequestWrapper();
			Collection<SingleTinRequestWrapper> res=new HashSet<SingleTinRequestWrapper>();			
			all.parallelStream().forEach(h->res.add(new SingleTinRequestWrapper(h.getCorporateTinGenerationRequest())));		
			batch.setRequestEntries(res);
			return batch;
		}
		return null;		
	}

	@Override
	public void notifyProcessing(String rcNumber) {
		TinGenerationRequestHistory history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
		if(history!=null){
			history.setStartedProcessing(true);
			tinGenerationRequestHistoryService.update(history);
		}
	}

	@Override
	public void notifyProcessing(Collection<String> rcNumbers) {
		rcNumbers.parallelStream().forEach(h->notifyProcessing(h));
	}

	@Override
	public void notifyProcessed(String rcNumber,String tin) {
		TinGenerationRequestHistory history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(rcNumber);
		if(history!=null){
			history.setFinishedProcessing(true);
			history.setTin(tin);
			history.setTinResponseDate(dateTimeUtilities.getDateTime());
			tinGenerationRequestHistoryService.update(history);
		}
	}

	@Override
	public void notifyProcessed(Map<String,String> rcNumbers) {
		rcNumbers.forEach((h,t)-> notifyProcessed(h,t));
	}

	@Override
	public BatchTinRequestWrapper forkByStartedProcessing(Boolean started) {
		Collection<TinGenerationRequestHistory> all=tinGenerationRequestHistoryService.getAllByStartedProcessing(started);
		if(!all.isEmpty()){
			BatchTinRequestWrapper batch=new BatchTinRequestWrapper();
			Collection<SingleTinRequestWrapper> res=new HashSet<SingleTinRequestWrapper>();			
			all.parallelStream().forEach(h->res.add(new SingleTinRequestWrapper(h.getCorporateTinGenerationRequest())));		
			batch.setRequestEntries(res);
			return batch;
		}
		return null;
	}

}

private void pushBatchToRemoteJtb(Batch_Tin_Request_Response batch_Tin_Request_Response,String reqsrc){
		
		taskExecutor.execute(()->{
		
			 HttpHeaders headers = new HttpHeaders();
				headers.add("Accept", "application/json");
				headers.add("Content-Type","application/json");
				headers.add("Authorization", "Basic XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");											
								
				final String endpointKey="rebib29973880UIBIUSD9VU97329813698689689BSBDJBJUuigiud";				
				headers.add("reqsrc",reqsrc);
				headers.add("hash",transformer.getHash(reqsrc+batch_Tin_Request_Response.getToken()+endpointKey));
				headers.add("reqkey",batch_Tin_Request_Response.getToken());			
				
				try{				
					
						RestTemplate restTemplate = new RestTemplate();			
						HttpEntity<Batch_Tin_Request_Response> batch_Tin_Request = new HttpEntity<Batch_Tin_Request_Response>(batch_Tin_Request_Response,headers); 
						
						//System.out.println("About forwarding JTB Tin Generation Service");
						ResponseEntity<Batch_Tin_Request_Response> batch_Tin_Response= restTemplate.exchange(jtbEndpointUrlNew, HttpMethod.POST, batch_Tin_Request, Batch_Tin_Request_Response.class);
						//System.out.println("Returned from JTB Successfully.");
						
						if(HttpStatus.OK==batch_Tin_Response.getStatusCode()){
							Batch_Tin_Request_Response responseBody=batch_Tin_Response.getBody();
							
							//System.out.println("JTB returned: "+responseBody.getStatus_message()+" ("+responseBody.getStatus_code()+") , "+responseBody.getDescription());
							/*if(ResponseStatus.REQUEST_RECEIVED_AND_IN_PROGRESS.getCode().equals(responseBody.getStatus_code()) ||ResponseStatus.REQUEST_RECEIVED_AND_IN_QUEUE.getCode().equals(responseBody.getStatus_code())){
								TinGenerationRequestHistory history=tinGenerationRequestHistoryService.getByRcnoOrMdaTransactionId(batch_Tin_Request.getRcno());
								if(history!=null){
									history.setMessageStatus(MessageStatus.RECEPT_ACKNOWLEDGED);
									tinGenerationRequestHistoryService.update(history);
								}
							}*/


