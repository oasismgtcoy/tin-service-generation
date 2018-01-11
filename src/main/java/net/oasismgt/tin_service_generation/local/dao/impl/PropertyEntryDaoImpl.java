package net.oasismgt.tin_service_generation.local.dao.impl;


import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.oasismgt.tin_service_generation.local.dao.PropertyEntryDao;
import net.oasismgt.tin_service_generation.local.model.PropertyEntry;


@Repository
public class PropertyEntryDaoImpl implements PropertyEntryDao{

	private SessionFactory sessionFactory;
	
	@Autowired 
	public PropertyEntryDaoImpl(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
		
	 private Session getSession() {
		return sessionFactory.getCurrentSession();		 
	 }
	 Criterion between(String name, Object obj1, Object obj2){
		  return Restrictions.between(name, obj1, obj2);
	 }
	Criteria getCriteria() {		
		 return getSession().createCriteria(PropertyEntry.class);
	}
	
	@Override
	public Long add(PropertyEntry t) {		
		return (Long) getSession().save(t);
	}

	@Override
	public PropertyEntry get(Long id) {
		return getSession().byId(PropertyEntry.class).load(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PropertyEntry> getAll() {
		return getCriteria().list();
	}

	@Override
	public PropertyEntry update(PropertyEntry t) {
		return (PropertyEntry) getSession().merge(t);
	}

	@Override
	public void delete(PropertyEntry t) {
		getSession().delete(t);
	}
 
	@Override
	public PropertyEntry getByName(String name) {
		return (PropertyEntry)getCriteria().add(Restrictions.like("name",name)).uniqueResult();
	}


}
