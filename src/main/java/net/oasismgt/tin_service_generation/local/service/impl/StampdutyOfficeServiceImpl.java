package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.StampdutyOfficeDao;
import net.oasismgt.tin_service_generation.local.model.StampdutyOffice;
import net.oasismgt.tin_service_generation.local.service.StampdutyOfficeService;

@Service
@Transactional
public class StampdutyOfficeServiceImpl implements StampdutyOfficeService{
	
	@Autowired private StampdutyOfficeDao stampdutyOfficeDao;
	
	public Long add(StampdutyOffice stampdutyOffice) {
		return stampdutyOfficeDao.add(stampdutyOffice);
	}
	public StampdutyOffice get(Long id) {
		return stampdutyOfficeDao.get(id);
	}
    public StampdutyOffice getByName(String name) {
		return stampdutyOfficeDao.getByName(name);
	}
    public StampdutyOffice getByCode(String name) {
		return stampdutyOfficeDao.getByCode(name);
	}
    public StampdutyOffice update(StampdutyOffice stampdutyOffice) {
		return stampdutyOfficeDao.update(stampdutyOffice);
	}
    public void remove(StampdutyOffice stampdutyOffice) {
    	stampdutyOfficeDao.remove(stampdutyOffice);
	}
    public Collection<StampdutyOffice> getAll() {
		return stampdutyOfficeDao.getAll();
	} 
    
    
   
}
