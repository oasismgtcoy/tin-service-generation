package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.StateDao;
import net.oasismgt.tin_service_generation.local.model.State;
import net.oasismgt.tin_service_generation.local.service.StateService;


@Service
@Transactional
public class  StateServiceImpl implements StateService{
	
	@Autowired private StateDao stateDao;
	
	public Long add(State state) {
		return stateDao.add(state);
	}
	public State get(Long id) {
		return stateDao.get(id);
	}
    public State getByName(String name) {
		return stateDao.getByName(name);
	}
    public State getByCode(String name) {
		return stateDao.getByCode(name);
	}
    public State update(State state) {
		return stateDao.update(state);
	}
    public void remove(State state) {
    	stateDao.remove(state);
	}
    public Collection<State> getAll() {
		return stateDao.getAll();
	}
    
}
