package net.oasismgt.tin_service_generation.local.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.oasismgt.tin_service_generation.local.dao.StateDao;
import net.oasismgt.tin_service_generation.local.model.State;

@Repository
public class StateDaoImpl implements StateDao {

	@Autowired	private SessionFactory sessionFactory;
	
	private Session getSession() {
	 	 return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("deprecation")
	private Criteria getCriteria() {
	  return getSession().createCriteria(State.class);
	}
	
	@Override
	public Long add(State State) {
		return (Long) getSession().save(State);
	}

	@Override
	public State get(Long id) {
		return getSession().byId(State.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public State getByName(String name) {
		return (State) getCriteria().add(Restrictions.like("name",name)).list().stream().findFirst().orElse(null);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public State getByCode(String name) {
		return (State) getCriteria().add(Restrictions.like("code",name)).list().stream().findFirst().orElse(null);	
	}

	@Override
	public State update(State State) {
		return (State) getSession().merge(State);
	}

	@Override
	public void remove(State State) {
		getSession().delete(State);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<State> getAll() {
		return getCriteria().list();
	}


}
