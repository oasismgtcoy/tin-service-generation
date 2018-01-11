package net.oasismgt.tin_service_generation.local.service;


import java.time.LocalDateTime;
import java.util.Collection;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;


public interface TinGenerationRequestHistoryService{
	public Long add(TinGenerationRequestHistory t);
    public TinGenerationRequestHistory get(Long id);
    public TinGenerationRequestHistory getByTin(String tin);
    public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId);
    public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcno,String mdaTransactionId);
    public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo);
    public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo,MessageStatus messageStatus);
    public Collection<TinGenerationRequestHistory> getAllByResponseDate(LocalDateTime tinResponseDateFrom,LocalDateTime tinResponseDateTo);
    public Collection<TinGenerationRequestHistory> getAllByResponseDate(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus);
    public Collection<TinGenerationRequestHistory> getAll();
    public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus);
    public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus, int limit);
    public Long getCountByDateRequested(MessageStatus messageStatus);
    public TinGenerationRequestHistory update(TinGenerationRequestHistory t);
    public void delete(Long t);
    public Long getCountByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo,MessageStatus messageStatus);
    public Long getCountAll();
	public Long getCountByDateResponded(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo,MessageStatus messageStatus);
	public Long getCountByDateOfDepartureToJtb(LocalDateTime atStartOfDay, LocalDateTime now);
	public Collection<TinGenerationRequestHistory> getAllByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom,
			LocalDateTime dateRequestedTo, MessageStatus messageStatus);
}
