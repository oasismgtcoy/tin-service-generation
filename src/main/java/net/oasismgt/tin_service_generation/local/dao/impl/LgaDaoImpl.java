package net.oasismgt.tin_service_generation.local.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.oasismgt.tin_service_generation.local.dao.LgaDao;
import net.oasismgt.tin_service_generation.local.model.Lga;
import net.oasismgt.tin_service_generation.local.model.State;

@Repository
public class LgaDaoImpl implements LgaDao {
	
@Autowired	private SessionFactory sessionFactory;
	
	private Session getSession() {
	 	 return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("deprecation")
	private Criteria getCriteria() {
	  return getSession().createCriteria(Lga.class);
	}
	
	@Override
	public Long add(Lga Lga) {
		return (Long) getSession().save(Lga);
	}

	@Override
	public Lga get(Long id) {
		return getSession().byId(Lga.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Lga getByName(String name) {
		return (Lga) getCriteria().add(Restrictions.like("name",name)).list().stream().findFirst().orElse(null);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public Lga getByCode(String name) {
		return (Lga) getCriteria().add(Restrictions.like("code",name)).list().stream().findFirst().orElse(null);	
	}

	@Override
	public Lga update(Lga Lga) {
		return (Lga) getSession().merge(Lga);
	}

	@Override
	public void remove(Lga Lga) {		
		getSession().delete(Lga);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Lga> getAll() {
		return getCriteria().list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Lga> getAll(State state) {
		return getCriteria().add(Restrictions.like("state",state)).list();
	}

	
}
