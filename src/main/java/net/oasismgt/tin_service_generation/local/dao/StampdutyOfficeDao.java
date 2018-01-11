package net.oasismgt.tin_service_generation.local.dao;

import java.util.Collection;

import net.oasismgt.tin_service_generation.local.model.StampdutyOffice;

public interface StampdutyOfficeDao{
	
	public Long add(StampdutyOffice stampdutyOffice);
	public StampdutyOffice get(Long id);
    public StampdutyOffice getByName(String name);
    public StampdutyOffice getByCode(String name);
    public StampdutyOffice update(StampdutyOffice stampdutyOffice);
    public void remove(StampdutyOffice stampdutyOffice);
    public Collection<StampdutyOffice> getAll(); 
    
}
