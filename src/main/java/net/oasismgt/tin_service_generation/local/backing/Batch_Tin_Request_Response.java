package net.oasismgt.tin_service_generation.local.backing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Batch_Tin_Request_Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2184094579196455546L;
	private String token;
	private ServiceResponse serviceResponse;
	
	private Collection<Batch_Tin_Item_Unit> batch_Tin_Item_Unit;
	
	public Batch_Tin_Request_Response (){
		setBatch_Tin_Item_Unit(new ArrayList<>());
	}

	public Collection<Batch_Tin_Item_Unit> getBatch_Tin_Item_Unit() {
		return batch_Tin_Item_Unit;
	}

	public void setBatch_Tin_Item_Unit(Collection<Batch_Tin_Item_Unit> batch_Tin_Item_Unit) {
		this.batch_Tin_Item_Unit = batch_Tin_Item_Unit;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ServiceResponse getServiceResponse() {
		return serviceResponse;
	}

	public void setServiceResponse(ServiceResponse serviceResponse) {
		this.serviceResponse = serviceResponse;
	}
	

}
