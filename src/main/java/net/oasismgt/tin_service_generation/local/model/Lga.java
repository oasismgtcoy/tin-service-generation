package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Lga implements Serializable ,Comparable<Lga>{

	
	private static final long serialVersionUID = 7873325811829572383L;
	private Long id;
	private String code;
	private String name;
	private TaxOffice taxOffice;
	private String taxOfficeName;
	private State state;
	private String stateName;
	
	
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
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
	@ManyToOne
	@JoinColumn(name="state", referencedColumnName="id")
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public int compareTo(Lga o) {
		return name.compareTo(o.getName());
	}
	public boolean equals(Object obj){
		if(this==obj)return true;
		if(!(obj instanceof Lga))return false;
		Lga s=(Lga)obj;
		return this.getName().equals(s.getName()) && state==s.getState();
	}
	
	public int hashCode(){
		return name.hashCode();
	}
	public String toString(){
		return state+" - "+getName();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ManyToOne
	@JoinColumn(name="taxOffice", referencedColumnName="id")
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
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}
