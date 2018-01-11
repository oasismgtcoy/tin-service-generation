package net.oasismgt.tin_service_generation.local.backing;

import java.io.Serializable;
import net.oasismgt.tin_service_generation.local.model.CorporateTinGenerationRequest;

public class Batch_Tin_Item_Unit implements Serializable {

	
	private static final long serialVersionUID = 3584059053031281L;
	
	private String token;
	private String rcno;
	private String taxservice_transaction_id;
	private ServiceResponse serviceResponse;
	private CorporateTinGenerationRequest corporateTinGenerationRequest;
		
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRcno() {
		return rcno;
	}
	public void setRcno(String rcno) {
		this.rcno = rcno;
	}
	public String getTaxservice_transaction_id() {
		return taxservice_transaction_id;
	}
	public void setTaxservice_transaction_id(String taxservice_transaction_id) {
		this.taxservice_transaction_id = taxservice_transaction_id;
	}
	public ServiceResponse getServiceResponse() {
		return serviceResponse;
	}
	public void setServiceResponse(ServiceResponse serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	public CorporateTinGenerationRequest getCorporateTinGenerationRequest() {
		return corporateTinGenerationRequest;
	}
	public void setCorporateTinGenerationRequest(CorporateTinGenerationRequest corporateTinGenerationRequest) {
		this.corporateTinGenerationRequest = corporateTinGenerationRequest;
	}
	
}
