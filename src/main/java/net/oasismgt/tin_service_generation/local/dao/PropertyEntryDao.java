package net.oasismgt.tin_service_generation.local.dao;

import java.util.Collection;

import net.oasismgt.tin_service_generation.local.model.PropertyEntry;

public interface PropertyEntryDao {
	
	public Long add(PropertyEntry t);
    public PropertyEntry get(Long id);
    public PropertyEntry getByName(String name);
    public Collection<PropertyEntry> getAll();
    public PropertyEntry update(PropertyEntry t);
    public void delete(PropertyEntry t);

}
