package net.oasismgt.tin_service_generation.local.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.oasismgt.tin_service_generation.local.dao.TaxOfficeDao;
import net.oasismgt.tin_service_generation.local.model.TaxOffice;
import net.oasismgt.tin_service_generation.local.model.StampdutyOffice;

@Repository
public class TaxOfficeDaoImpl implements TaxOfficeDao {
	
	@Autowired	private SessionFactory sessionFactory;
	
	private Session getSession() {
	 	 return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("deprecation")
	private Criteria getCriteria() {
	  return getSession().createCriteria(TaxOffice.class);
	}
	
	@Override
	public Long add(TaxOffice taxOffice) {
		return (Long) getSession().save(taxOffice);
	}

	@Override
	public TaxOffice get(Long id) {
		return getSession().byId(TaxOffice.class).load(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TaxOffice getByName(String name) {
		return (TaxOffice) getCriteria().add(Restrictions.like("name",name)).list().stream().findFirst().orElse(null);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public TaxOffice getByCode(String name) {
		return (TaxOffice) getCriteria().add(Restrictions.or(Restrictions.like("code",name),Restrictions.like("jtbId",name),Restrictions.like("firsId",name))).list().stream().findFirst().orElse(null);	
	}

	@Override
	public TaxOffice update(TaxOffice TaxOffice) {
		return (TaxOffice) getSession().merge(TaxOffice);
	}

	@Override
	public void remove(TaxOffice TaxOffice) {
		getSession().delete(TaxOffice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TaxOffice> getAll() {
		return getCriteria().list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<TaxOffice> getAll(StampdutyOffice stampdutyOffice) {
		return getCriteria().add(Restrictions.eq("stampdutyOffice",stampdutyOffice)).list();
	}

   }

