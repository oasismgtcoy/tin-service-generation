package net.oasismgt.tin_service_generation.local.dao.impl;


import static org.hibernate.criterion.Restrictions.or;

import java.time.LocalDateTime;
import java.util.Collection;
//import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;
import net.oasismgt.tin_service_generation.local.dao.TinGenerationRequestHistoryDao;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;


@Repository
public class TinGenerationRequestHistoryDaoImpl implements TinGenerationRequestHistoryDao{

	private SessionFactory sessionFactory;
	
	@Autowired 
	public TinGenerationRequestHistoryDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
	
	
	 private Session getSession() {
		return sessionFactory.getCurrentSession();		 
	 }
	 private Criterion like(String name, Object obj){
		  return Restrictions.like(name, obj);
	 }
	 Criterion between(String name, Object obj1, Object obj2){
		  return Restrictions.between(name, obj1, obj2);
	 }
	/* CriteriaQuery getCriteriaQuery(){
		// return getSession().
	 }*/
	Criteria getCriteria() {		
		 return getSession().createCriteria(TinGenerationRequestHistory.class);
	}
	
	@Override
	@Transactional
	public Long add(TinGenerationRequestHistory t) {		
		Long id=(Long) getSession().save(t);
		return id;
	}

	@Override
	public TinGenerationRequestHistory get(Long id) {
		return getSession().byId(TinGenerationRequestHistory.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TinGenerationRequestHistory getByTin(String tin) {
		return (TinGenerationRequestHistory)getCriteria().add(like("tin",tin)).list().stream().findFirst().orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId) {
		return (TinGenerationRequestHistory)getCriteria().add(or(like("rcno",rcOrMdaId),like("mda_transaction_id",rcOrMdaId))).list().stream().findFirst().orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo) {
		return getCriteria().add(between("dateRequested", dateRequestedFrom, dateRequestedTo)).list();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAllByResponseDate(LocalDateTime tinResponseDateFrom,LocalDateTime tinResponseDateTo) {
		return getCriteria().add(between("tinResponseDate", tinResponseDateFrom, tinResponseDateTo)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAll() {
		return getCriteria().list();
	}


	@Override
	public TinGenerationRequestHistory update(TinGenerationRequestHistory t) {
		return (TinGenerationRequestHistory) getSession().merge(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcno, String mdaTransactionId) {
		return (TinGenerationRequestHistory)getCriteria().add(or(like("rcno",rcno),like("mda_transaction_id",mdaTransactionId))).list().stream().findFirst().orElse(null);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAllByDateRequested(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus) {
		if(messageStatus == null)
			return getCriteria().add(Restrictions.and(between("dateRequested", dateRequestedFrom, dateRequestedTo))).list();
		
		return getCriteria().add(Restrictions.and(between("dateRequested", dateRequestedFrom, dateRequestedTo),Restrictions.eq("messageStatus",  messageStatus ))).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus,int limit) {
		return getCriteria().add(Restrictions.eq("messageStatus",  messageStatus )).setFirstResult(0).setMaxResults(limit).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAllByTinResponseDate(LocalDateTime dateRequestedFrom,LocalDateTime dateRequestedTo, MessageStatus messageStatus) {
		return getCriteria().add(Restrictions.and(between("tinResponseDate", dateRequestedFrom, dateRequestedTo),Restrictions.eq("messageStatus",  messageStatus ))).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<TinGenerationRequestHistory> getAll(MessageStatus messageStatus) {
		return getCriteria().add(Restrictions.eq("messageStatus",  messageStatus )).list();
		
	}

	@Override
	public Long getCountByDateRequested(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo,MessageStatus messageStatus) {
		if(messageStatus==null)return (Long) getCriteria().add(Restrictions.and(between("dateRequested", dateRequestedFrom, dateRequestedTo))).setProjection(Projections.rowCount()).uniqueResult();
		return (Long) getCriteria().add(Restrictions.and(between("dateRequested", dateRequestedFrom, dateRequestedTo),Restrictions.eq("messageStatus",  messageStatus ))).setProjection(Projections.rowCount()).uniqueResult();
	}
	@Override
	public Long getCountByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo) {
		return (Long) getCriteria().add(Restrictions.and(Restrictions.isNotNull("dateOfDepartureToJtb"),between("dateOfDepartureToJtb", dateRequestedFrom, dateRequestedTo))).setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public Long getCountByDateResponded(LocalDateTime dateRequestedFrom, LocalDateTime dateRequestedTo,MessageStatus messageStatus) {
		if(messageStatus==null)return (Long) getCriteria().add(Restrictions.and(between("tinResponseDate", dateRequestedFrom, dateRequestedTo))).setProjection(Projections.rowCount()).uniqueResult();
		return (Long) getCriteria().add(Restrictions.and(between("tinResponseDate", dateRequestedFrom, dateRequestedTo),Restrictions.eq("messageStatus",  messageStatus ))).setProjection(Projections.rowCount()).uniqueResult();
	}


	@Override
	public Long getCountByDateRequested(MessageStatus messageStatus) {
		return (Long) getCriteria().add(Restrictions.eq("messageStatus",  messageStatus )).setProjection(Projections.rowCount()).uniqueResult();
	}


	@Override
	public Long getCountAll() {
		return (Long) getCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}


	@Override
	public void delete(Long t) {
		TinGenerationRequestHistory h=	getSession().get(TinGenerationRequestHistory.class, t);
		if(h!=null)getSession().delete(h);			
	}


	@Override
	public Collection<TinGenerationRequestHistory> getAllByDateOfDepartureToJtb(LocalDateTime dateRequestedFrom,
			LocalDateTime dateRequestedTo, MessageStatus messageStatus) {
		return getCriteria().add(Restrictions.and(Restrictions.isNotNull("dateOfDepartureToJtb"),between("dateOfDepartureToJtb", dateRequestedFrom, dateRequestedTo))).list();
		
	}
 
}
