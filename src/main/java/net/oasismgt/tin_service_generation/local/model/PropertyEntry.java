package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PropertyEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8453310675030746962L;
	private Long id;
	private String name;
	private String value;
	
	public PropertyEntry(){}
	
	public PropertyEntry(String name,String value){
		this.name=name;
		this.value=value;
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=20000)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this==obj)return true;
		if(!(obj instanceof PropertyEntry)) return false;
		PropertyEntry prop=(PropertyEntry)obj;
		return prop.getName().equals(getName());
	}

}
