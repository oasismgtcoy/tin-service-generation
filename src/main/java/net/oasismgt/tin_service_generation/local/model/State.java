package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class State implements Serializable ,Comparable<State>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7274866914158329562L;
	private Long id;
	private String name;
	private String code;
	private TaxOffice taxOffice;
	private String taxOfficeName;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name.toUpperCase();
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int compareTo(State o) {		
		return name.compareTo(o.getName());
	}
	public boolean equals(Object obj){
		if(this==obj)return true;
		if(!(obj instanceof State))return false;
		State s=(State)obj;
		return this.getName().equals(s.getName());
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	public String toString(){
		return getName();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@OneToOne
	@JoinColumn(name="taxOffice",referencedColumnName="id")
	public TaxOffice getTaxOffice() {
		return taxOffice;
	}
	public void setTaxOffice(TaxOffice taxOffice) {
		this.taxOffice = taxOffice;
	}
	public String getTaxOfficeName() {
		return taxOfficeName;
	}
	public void setTaxOfficeName(String taxOfficeName) {
		this.taxOfficeName = taxOfficeName;
	}
		
}
