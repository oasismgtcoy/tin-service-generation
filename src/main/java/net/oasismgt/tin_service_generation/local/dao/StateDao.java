package net.oasismgt.tin_service_generation.local.dao;

import java.util.Collection;

import net.oasismgt.tin_service_generation.local.model.State;

public interface StateDao{
	
	public Long add(State State);
	public State get(Long id);
    public State getByName(String name);
    public State getByCode(String name);
    public State update(State State);
    public void remove(State State);
    public Collection<State> getAll();
    
}
