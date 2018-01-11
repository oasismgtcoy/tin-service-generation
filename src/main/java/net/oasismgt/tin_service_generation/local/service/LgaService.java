package net.oasismgt.tin_service_generation.local.service;

import java.util.Collection;

import net.oasismgt.tin_service_generation.local.model.Lga;
import net.oasismgt.tin_service_generation.local.model.State;

public interface LgaService{	
	
	public Long add(Lga lga);
	public Lga get(Long id);
    public Lga getByName(String name);
    public Lga getByCode(String name);
    public Lga update(Lga lga);
    public void remove(Lga lga);
    public Collection<Lga> getAll(State state);
    public Collection<Lga> getAll(); 
}
