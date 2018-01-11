package net.oasismgt.tin_service_generation.local.dao;


import java.time.LocalDateTime;
import java.util.Collection;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;


public interface TinGenerationRequestHistoryDao{
	public Long add(TinGenerationRequestHistory t);
    public TinGenerationRequestHistory get(Long id);
    public TinGenerationRequestHistory getByTin(String tin);
    public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId);
    public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcno,String mdaTransactionId);
    public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo,MessageStatus messageStatus);
    public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo);
    public Collection<TinGenerationRequestHistory> getAllByResponseDate(LocalDateTime tinResponseDateFrom,LocalDateTime tinResponseDateTo);
    public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus);
    public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus, int limit);
    public Collection<TinGenerationRequestHistory> getAll();
    public TinGenerationRequestHistory update(TinGenerationRequestHistory t);
	public Collection<TinGenerationRequestHistory> getAllByTinResponseDate(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus);
	public void delete(Long t);
	public Long getCountByDateRequested(MessageStatus messageStatus);
	public Long getCountAll();
	public Long getCountByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo,MessageStatus messageStatus);
	public Long getCountByDateResponded(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo,MessageStatus messageStatus);
	public Long getCountByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo);
	public Collection<TinGenerationRequestHistory> getAllByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom,
			LocalDateTime dateRequestedTo, MessageStatus messageStatus);
	
}
