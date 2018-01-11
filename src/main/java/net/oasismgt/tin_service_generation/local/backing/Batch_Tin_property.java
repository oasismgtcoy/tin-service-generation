package net.oasismgt.tin_service_generation.local.backing;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

public class Batch_Tin_property implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35858773031281L;
	
	private String token;
	private Collection<Tin_property> tin_properties;
	
	
	public Batch_Tin_property(){
		tin_properties=new ArrayList<>();
	}


	public Collection<Tin_property> getTin_properties() {
		return tin_properties;
	}


	public void setTin_properties(Collection<Tin_property> tin_properties) {
		this.tin_properties = tin_properties;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
