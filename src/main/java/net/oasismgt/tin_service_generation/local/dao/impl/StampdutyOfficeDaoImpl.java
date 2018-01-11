package net.oasismgt.tin_service_generation.local.dao.impl;

import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.oasismgt.tin_service_generation.local.dao.StampdutyOfficeDao;
import net.oasismgt.tin_service_generation.local.model.StampdutyOffice;


@Repository
public class StampdutyOfficeDaoImpl implements StampdutyOfficeDao {
	
	@Autowired	private SessionFactory sessionFactory;
	
	private Session getSession() {
	 	 return sessionFactory.getCurrentSession();
	 }
	@SuppressWarnings("deprecation")
	private Criteria getCriteria() {
	  return getSession().createCriteria(StampdutyOffice.class);
	}
	
	@Override
	public Long add(StampdutyOffice stampdutyOffice) {
		return (Long) getSession().save(stampdutyOffice);
	}

	@Override
	public StampdutyOffice get(Long id) {
		return getSession().byId(StampdutyOffice.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public StampdutyOffice getByName(String name) {
		return (StampdutyOffice) getCriteria().add(Restrictions.like("name",name)).list().stream().findFirst().orElse(null);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public StampdutyOffice getByCode(String name) {
		return (StampdutyOffice) getCriteria().add(Restrictions.or(Restrictions.like("code",name),Restrictions.like("jtbId",name),Restrictions.like("firsId",name))).list().stream().findFirst().orElse(null);	
	}

	@Override
	public StampdutyOffice update(StampdutyOffice stampdutyOffice) {
		return (StampdutyOffice) getSession().merge(stampdutyOffice);
	}

	@Override
	public void remove(StampdutyOffice stampdutyOffice) {
		getSession().delete(stampdutyOffice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<StampdutyOffice> getAll() {
		return getCriteria().list();
	}

}
