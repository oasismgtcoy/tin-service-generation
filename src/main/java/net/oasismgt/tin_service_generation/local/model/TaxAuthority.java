package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class TaxAuthority implements Serializable ,Comparable<TaxAuthority>{
	
	private static final long serialVersionUID = -6150787132919989265L;
		
	private Long id;
	private String name;
	private String stateId;
	private String taId;
	private String code;
	private State state;	
	private Collection<StampdutyOffice> stampdutyOffices;
		
	public TaxAuthority(){
		stampdutyOffices=new HashSet<>();
	} 
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
	
	@OneToMany
	public Collection<StampdutyOffice> getStampDutyOffices() {
		return stampdutyOffices.stream().sorted().collect(Collectors.toSet());
	}
	public void setStampDutyOffices(Collection<StampdutyOffice> off) {
		this.stampdutyOffices = off;
	}
	@Transient
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String code) {
		this.stateId = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String cd) {
		this.code = cd;
	}
	@OneToOne
	public State getState(){
		return state;
	}
	public void setState(State state){
		this.state = state;
	}
	@Transient
	public String getTaId() {
		return taId;
	}

	public void setTaId(String taId) {
		this.taId = taId;
	}

	@Override
	public int compareTo(TaxAuthority o) {
		return name.compareTo(o.getName());
	}

}
