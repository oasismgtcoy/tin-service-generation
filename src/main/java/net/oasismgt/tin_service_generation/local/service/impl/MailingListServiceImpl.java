package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.MailingListDao;
import net.oasismgt.tin_service_generation.local.model.MailingList;
import net.oasismgt.tin_service_generation.local.service.MailingListService;

@Service
@Transactional
public class MailingListServiceImpl implements MailingListService {

	
	@Autowired private MailingListDao mailingListDao;
	
	@Override
	public Long add(MailingList m) {
		// TODO Auto-generated method stub
		return mailingListDao.add(m);
	}

	@Override
	public MailingList get(Long id) {
		// TODO Auto-generated method stub
		return mailingListDao.get(id);
	}

	@Override
	public Collection<MailingList> getAll() {
		// TODO Auto-generated method stub
		return mailingListDao.getAll();
	}

	@Override
	public MailingList update(MailingList m) {
		// TODO Auto-generated method stub
		return mailingListDao.update(m);
	}

	@Override
	public void delete(MailingList m) {
		// TODO Auto-generated method stub
		mailingListDao.delete(m);
	}

}
