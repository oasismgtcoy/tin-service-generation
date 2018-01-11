package net.oasismgt.tin_service_generation.local.service.util.impl;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.controller.TinGenerationController;
import net.oasismgt.tin_service_generation.local.model.PropertyEntry;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.PropertyEntryService;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;
import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;
import net.oasismgt.tin_service_generation.local.service.util.MailService;
import net.oasismgt.tin_service_generation.local.service.util.Transformer;



@Service
@Transactional
@EnableScheduling
public class WriterTinRequestPusherServiceImpl{
	
	@Autowired private DateTimeUtilities dateTimeUtilities;
	@Autowired private MailService mailService;
	
	@Autowired private Transformer transformer;
	@Autowired private PropertyEntryService propertyEntryService;
	@Autowired private TinGenerationRequestHistoryService tinGenerationRequestHistoryService;
	
	
	@Scheduled(fixedDelay=1000*60*60)
	public void pushRcToJtb(){				
		PropertyEntry propertyEntry=propertyEntryService.getByName("custom.taskqueue-mode-automatic-relay-rc_number-to-jtb");	
		boolean processBatch = (propertyEntry!=null && Boolean.valueOf(propertyEntry.getValue()));
		if(!processBatch)return;
		pushRecursively();
	}
	 
	private void pushRecursively(){
		long size  = tinGenerationRequestHistoryService.getCountByDateRequested(MessageStatus.NOT_SENT);
		if(size<1)return;			
		System.out.println("About to push all "+size+" Rc(s) to jtb");		
		while(size>100){			
			pushBulkToEndpoint(tinGenerationRequestHistoryService.getAll(MessageStatus.NOT_SENT, 100));
			long newSize   = tinGenerationRequestHistoryService.getCountByDateRequested(MessageStatus.NOT_SENT);
			if(newSize==size)
			try {
				Thread.sleep(1000);
			 } catch (InterruptedException e) {
				e.printStackTrace();
			}
			size=newSize;			
		}
		if(size>0)pushBulkToEndpoint(tinGenerationRequestHistoryService.getAll(MessageStatus.NOT_SENT));		
	}

	public void pushBulkToEndpoint(Collection<TinGenerationRequestHistory> list) {
		System.out.println("About to push slit of "+list.size()+" Rc(s) to jtb");
		list.parallelStream().forEach(h->pushSingleToEndpoint(h));
	}
	public void pushSingleToEndpoint(TinGenerationRequestHistory history) {		
		TinGenerationController.forwardToRemoteJtb(mailService,dateTimeUtilities,true,propertyEntryService,null,transformer,tinGenerationRequestHistoryService,history.getCorporateTinGenerationRequest(),history.getRequestSource());
	}	
}
