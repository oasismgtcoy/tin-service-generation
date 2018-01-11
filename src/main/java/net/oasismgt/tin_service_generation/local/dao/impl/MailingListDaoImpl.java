package net.oasismgt.tin_service_generation.local.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.oasismgt.tin_service_generation.local.dao.MailingListDao;

import net.oasismgt.tin_service_generation.local.model.MailingList;

@Repository
public class MailingListDaoImpl implements MailingListDao {

	
@Autowired	private SessionFactory sessionFactory;
	
	private Session getSession() {
	 	 return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("deprecation")
	private Criteria getCriteria() {
	  return getSession().createCriteria(MailingList.class);
	}
	@Override
	public Long add(MailingList m) {
		// TODO Auto-generated method stub
		return (Long) getSession().save(m);
	}

	@Override
	public MailingList get(Long id) {
		// TODO Auto-generated method stub
		return getSession().byId(MailingList.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<MailingList> getAll() {
		// TODO Auto-generated method stub
		return getCriteria().list();
	}

	@Override
	public MailingList update(MailingList m) {
		// TODO Auto-generated method stub
		return (MailingList) getSession().merge(m);
	}

	@Override
	public void delete(MailingList m) {
		// TODO Auto-generated method stub
		getSession().delete(m);
	}

}
