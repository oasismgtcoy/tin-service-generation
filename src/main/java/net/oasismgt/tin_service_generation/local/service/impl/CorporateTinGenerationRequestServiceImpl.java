package net.oasismgt.tin_service_generation.local.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.dao.CorporateTinGenerationRequestDao;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;
import net.oasismgt.tin_service_generation.local.service.CorporateTinGenerationRequestService;

@Service
@Transactional
public class CorporateTinGenerationRequestServiceImpl implements CorporateTinGenerationRequestService {

	@Autowired private CorporateTinGenerationRequestDao corporateTinGenerationRequestDao;
	
	@Override
	public Long add(CorporateTinGenerationRequest t) {
		return corporateTinGenerationRequestDao.add(t);
	}

	@Override
	public CorporateTinGenerationRequest get(Long id) {
		return corporateTinGenerationRequestDao.get(id);
	}

	@Override
	public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId) {
		return corporateTinGenerationRequestDao.getByRcnoOrMdaTransactionId(rcOrMdaId);
	}

	@Override
	public TinGenerationRequestHistory getByIsdsTransactionId(String isds_transaction_id) {
		return corporateTinGenerationRequestDao.getByIsdsTransactionId(isds_transaction_id);
	}

	@Override
	public Collection<CorporateTinGenerationRequest> getAll() {
		return corporateTinGenerationRequestDao.getAll();
	}

	@Override
	public CorporateTinGenerationRequest update(CorporateTinGenerationRequest t) {
		return corporateTinGenerationRequestDao.update(t);
	}

	@Override
	public Collection<CorporateTinGenerationRequest> getAllByState(String stateName) {
		return corporateTinGenerationRequestDao.getAllByState(stateName);
	}

	@Override
	public Collection<CorporateTinGenerationRequest> getAllByLga(String lgaName) {
		return corporateTinGenerationRequestDao.getAllByLga(lgaName);
	}
	
	@Override
	public Collection<CorporateTinGenerationRequest> getAllByTaxOffice(String toName) {
		return corporateTinGenerationRequestDao.getAllByTaxOffice(toName);
	}
	

}
