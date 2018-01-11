package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.PropertyEntryDao;
import net.oasismgt.tin_service_generation.local.model.PropertyEntry;
import net.oasismgt.tin_service_generation.local.service.PropertyEntryService;


@Service
@Transactional
public class PropertyEntryServiceImpl implements PropertyEntryService {

	@Autowired PropertyEntryDao propertyEntryDao;
	
	@Override
	public Long add(PropertyEntry t) {
		return propertyEntryDao.add(t);
	}

	@Override
	public PropertyEntry get(Long id) {
		return propertyEntryDao.get(id);
	}

	@Override
	public PropertyEntry getByName(String name) {
		return propertyEntryDao.getByName(name);
	}

	@Override
	public Collection<PropertyEntry> getAll() {
		return propertyEntryDao.getAll();
	}

	@Override
	public PropertyEntry update(PropertyEntry t) {
		return propertyEntryDao.update(t);
	}

	@Override
	public void delete(PropertyEntry t) {
		propertyEntryDao.delete(t);
	}

}
