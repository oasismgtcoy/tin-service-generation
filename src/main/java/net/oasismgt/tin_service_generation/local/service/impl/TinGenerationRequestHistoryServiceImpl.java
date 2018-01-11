package net.oasismgt.tin_service_generation.local.service.impl;


import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.dao.TinGenerationRequestHistoryDao;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.TinGenerationRequestHistoryService;

@Service
@Transactional
public class TinGenerationRequestHistoryServiceImpl implements TinGenerationRequestHistoryService{

    private TinGenerationRequestHistoryDao tinGenerationRequestHistoryDao;
	
	@Autowired
	public TinGenerationRequestHistoryServiceImpl(TinGenerationRequestHistoryDao tinGenerationRequestHistoryDao){		
		this.tinGenerationRequestHistoryDao=tinGenerationRequestHistoryDao;
	}
	
	@Override
	public Long add(TinGenerationRequestHistory t) {
		return tinGenerationRequestHistoryDao.add(t);
	}

	@Override
	public TinGenerationRequestHistory get(Long id) {
		return tinGenerationRequestHistoryDao.get(id);
	}

	@Override
	public TinGenerationRequestHistory getByTin(String tin) {
		return tinGenerationRequestHistoryDao.getByTin(tin);
	}

	@Override
	public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId) {
		return tinGenerationRequestHistoryDao.getByRcnoOrMdaTransactionId(rcOrMdaId);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo) {
		return tinGenerationRequestHistoryDao.getAllByDateRequested(dateRequestedFrom, dateRequestedTo);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAllByResponseDate(LocalDateTime tinResponseDateFrom,LocalDateTime tinResponseDateTo) {
		return tinGenerationRequestHistoryDao.getAllByResponseDate(tinResponseDateFrom, tinResponseDateTo);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAll() {
		return tinGenerationRequestHistoryDao.getAll();
	}

	@Override
	public TinGenerationRequestHistory update(TinGenerationRequestHistory t) {
		return tinGenerationRequestHistoryDao.update(t);
	}

	@Override
	public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcno, String mdaTransactionId) {
		return tinGenerationRequestHistoryDao.getByRcnoOrMdaTransactionId(rcno, mdaTransactionId);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getAllByDateRequested(dateRequestedFrom, dateRequestedTo, messageStatus);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getAll(messageStatus);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAllByResponseDate(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getAllByTinResponseDate(dateRequestedFrom,dateRequestedTo,messageStatus);
	}

	@Override
	public Long getCountByDateRequested(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo,
			MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getCountByDateRequested(dateRequestedFrom, dateRequestedTo, messageStatus);
	}
	@Override
	public Collection<TinGenerationRequestHistory> getAllByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getAllByDateOfDepartureToJtb(dateRequestedFrom,dateRequestedTo,messageStatus);
	}
	public Long getCountByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo){
		return tinGenerationRequestHistoryDao.getCountByDateOfDepartureToJtb(dateRequestedFrom, dateRequestedTo);
	}

	@Override
	public Long getCountByDateResponded(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo,
			MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getCountByDateResponded(dateRequestedFrom, dateRequestedTo, messageStatus);
	}

	@Override
	public Long getCountByDateRequested(MessageStatus messageStatus) {
		return tinGenerationRequestHistoryDao.getCountByDateRequested(messageStatus);
	}

	@Override
	public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus, int limit) {
		return tinGenerationRequestHistoryDao.getAll(messageStatus, limit);
	}

	@Override
	public Long getCountAll() {
		return tinGenerationRequestHistoryDao.getCountAll();
	}

	@Override
	public void delete(Long t) {
		tinGenerationRequestHistoryDao.delete(t);
	}

}
