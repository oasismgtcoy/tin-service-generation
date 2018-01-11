package net.oasismgt.tin_service_generation.local.service;

import java.util.Collection;

import net.oasismgt.tin_service_generation.local.model.StampdutyOffice;
import net.oasismgt.tin_service_generation.local.model.TaxOffice;



public interface TaxOfficeService{
	
	public Long add(TaxOffice taxOffice);
	public TaxOffice get(Long id);
    public TaxOffice getByName(String name);
    public TaxOffice update(TaxOffice taxOffice);
    public void remove(TaxOffice taxOffice);
    public Collection<TaxOffice> getAll(); 
    public Collection<TaxOffice> getAll(StampdutyOffice stampdutyOffice);
	TaxOffice getByCode(String name);
}
