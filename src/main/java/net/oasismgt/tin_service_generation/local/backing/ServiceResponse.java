package net.oasismgt.tin_service_generation.local.backing;

import java.io.Serializable;

import net.oasismgt.tin_service_generation.local.constants.ResponseStatus;


public class ServiceResponse implements Serializable{

	
	private static final long serialVersionUID = -6973692007033960391L;
	
	private String status_code;
	private String status_message;
	private String description;
	private Tin_property tin_properties=null;
		
	
	public ServiceResponse(){
		ResponseStatus responseStatus=ResponseStatus.UNKNOWN_ERROR;
		setStatus_code(responseStatus.getCode()) ;
		setStatus_message(responseStatus.getMessage());
		setDescription(responseStatus.getMessage());
	}
	public ServiceResponse(ResponseStatus responseStatus){
		setStatus_code(responseStatus.getCode()) ;
		setStatus_message(responseStatus.getMessage());
		setDescription(responseStatus.getMessage());
	}
	
	public String getStatus_code() {
		return status_code;
	}		
	public String getStatus_message() {
		return status_message;
	}
	public String getDescription() {
		return description;
	}
	public ServiceResponse setDescription(String description) {
		this.description = description;
		return this;
	}
	public ServiceResponse setResponseStatus(ResponseStatus responseStatus) {
		setStatus_code(responseStatus.getCode()) ;
		setStatus_message(responseStatus.getMessage());
		setDescription(responseStatus.getMessage());
		return this;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public void setStatus_message(String status_message) {
		this.status_message = status_message;
	}
	public Tin_property getTin_properties() {
		return tin_properties;
	}
	public void setTin_properties(Tin_property tin_properties) {
		this.tin_properties = tin_properties;
	}
}
