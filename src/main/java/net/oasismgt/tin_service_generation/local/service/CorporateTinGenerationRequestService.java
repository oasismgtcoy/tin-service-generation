package net.oasismgt.tin_service_generation.local.service;

import java.util.Collection;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;
import net.oasismgt.tin_service_generation.local.model.TinGenerationRequestHistory;


public interface CorporateTinGenerationRequestService {
	
	public Long add(CorporateTinGenerationRequest t);
    public CorporateTinGenerationRequest get(Long id);
    public TinGenerationRequestHistory getByRcnoOrMdaTransactionId(String rcOrMdaId);
    public TinGenerationRequestHistory getByIsdsTransactionId(String isds_transaction_id);
    public Collection<CorporateTinGenerationRequest> getAll();
    public Collection<CorporateTinGenerationRequest> getAllByState(String stateName);
    public Collection<CorporateTinGenerationRequest> getAllByLga(String lgaName);
    public CorporateTinGenerationRequest update(CorporateTinGenerationRequest t);
	Collection<CorporateTinGenerationRequest> getAllByTaxOffice(String toName);

}
