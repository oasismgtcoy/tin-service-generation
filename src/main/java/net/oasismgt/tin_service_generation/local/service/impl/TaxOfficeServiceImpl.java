package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.TaxOfficeDao;
import net.oasismgt.tin_service_generation.local.model.StampdutyOffice;
import net.oasismgt.tin_service_generation.local.model.TaxOffice;
import net.oasismgt.tin_service_generation.local.service.TaxOfficeService;


@Service
@Transactional
public class  TaxOfficeServiceImpl implements TaxOfficeService{
	
	@Autowired private TaxOfficeDao taxOfficeDao;
	
	public Long add(TaxOffice taxOffice) {
		return taxOfficeDao.add(taxOffice);
	}
	public TaxOffice get(Long id) {
		return taxOfficeDao.get(id);
	}
    public TaxOffice getByName(String name) {
		return taxOfficeDao.getByName(name);
	}
    public TaxOffice update(TaxOffice taxOffice) {
		return taxOfficeDao.update(taxOffice);
	}
    public void remove(TaxOffice taxOffice) {
    	taxOfficeDao.remove(taxOffice);
	}
    public Collection<TaxOffice> getAll() {
		return taxOfficeDao.getAll();
	} 
    public Collection<TaxOffice> getAll(StampdutyOffice stampdutyOffice) {
		return taxOfficeDao.getAll(stampdutyOffice);
	}
	public TaxOffice getByCode(String name) {
		return taxOfficeDao.getByCode(name);
	}
}
