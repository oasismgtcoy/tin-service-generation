package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.LgaDao;
import net.oasismgt.tin_service_generation.local.model.Lga;
import net.oasismgt.tin_service_generation.local.model.State;
import net.oasismgt.tin_service_generation.local.service.LgaService;

@Service
@Transactional
public class LgaServiceImpl implements LgaService{	
	
	@Autowired private LgaDao lgaDao;
		
	public Long add(Lga lga){
		return lgaDao.add(lga);
	}
	public Lga get(Long id){
		return lgaDao.get(id);
	}
    public Lga getByName(String name){
    	return lgaDao.getByName(name);
    }
    public Lga getByCode(String name) {
		return lgaDao.getByCode(name);
	}
    public Lga update(Lga lga) {
		return lgaDao.update(lga);
	}
    public void remove(Lga lga) {
    	lgaDao.remove(lga);
	}
    public Collection<Lga> getAll(State state) {
		return lgaDao.getAll(state);
	}
    public Collection<Lga> getAll() {
		return lgaDao.getAll();
	} 
}
