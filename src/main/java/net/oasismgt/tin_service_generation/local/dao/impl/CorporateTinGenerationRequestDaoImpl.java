package net.oasismgt.tin_service_generation.local.dao.impl;

import static org.hibernate.criterion.Restrictions.or;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import net.oasismgt.tin_service_generation.local.dao.CorporateTinGenerationRequestDao;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;



@Repository
public  class CorporateTinGenerationRequestDaoImpl implements CorporateTinGenerationRequestDao{
	@Autowired private SessionFactory sessionFactory;
		
	private Session getSession() {
		 if(sessionFactory.isClosed())
			 return sessionFactory.openSession();
		 return sessionFactory.getCurrentSession();
	 }
	 
	/* Criterion between(String name, Object obj1, Object obj2){
		  return Restrictions.between(name, obj1, obj2);
	 }
	 Criterion isNull(String name){
		  return Restrictions.isNull(name);
	 }
	 Criterion isNotNull(String name){
		  return Restrictions.isNotNull(name);
	 }
	 Criterion like(String propertyName, String value,MatchMode matchMode){
		  return Restrictions.like(propertyName, value, matchMode);
	 }
	 Criterion ilike(String propertyName, String obj){
		  return Restrictions.ilike(propertyName, obj);
	 }
	 Criterion ilike(String propertyName, String value,MatchMode matchMode){
		  return Restrictions.ilike(propertyName, value, matchMode);
	 }
	 Criterion eq(String propertyName, Object value){
		  return Restrictions.eq(propertyName, value);
	 }*/
	
	Criterion like(String name, Object obj){
		  return Restrictions.like(name, obj);
	 }
	Criteria getCriteria() {
	 return getSession().createCriteria(CorporateTinGenerationRequest.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CorporateTinGenerationRequest> getAll() {
		return getCriteria().list();
	}
	

	@Override
	public Long add(CorporateTinGenerationRequest t) {
		return (Long) getSession().save(t);		
	}

	@Override
	public CorporateTinGenerationRequest get(Long id) {
		return getSession().byId(CorporateTinGenerationRequest.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId) {
		return (TinGenerationRequestHistory)getCriteria().add(or(like("rcno",rcOrMdaId),like("mda_transaction_id",rcOrMdaId))).list().stream().findFirst().orElse(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TinGenerationRequestHistory getByIsdsTransactionId(String isds_transaction_id) {
		return (TinGenerationRequestHistory)getCriteria().add(like("mda_transaction_id",isds_transaction_id)).list().stream().findFirst().orElse(null);
	}

	@Override
	public CorporateTinGenerationRequest update(CorporateTinGenerationRequest t) {
		return (CorporateTinGenerationRequest) getSession().merge(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CorporateTinGenerationRequest> getAllByState(String stateName) {
		return getCriteria().add(like("state",stateName)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CorporateTinGenerationRequest> getAllByLga(String lgaName) {
		return getCriteria().add(like("lga",lgaName)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<CorporateTinGenerationRequest> getAllByTaxOffice(String toName) {
		return getCriteria().add(like("tax_office",toName)).list();
	}
}
