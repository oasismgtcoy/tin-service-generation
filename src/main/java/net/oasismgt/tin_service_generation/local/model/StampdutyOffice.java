package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class StampdutyOffice implements Serializable ,Comparable<StampdutyOffice>{

	private static final long serialVersionUID = -4795409535478936394L;
	
	private Long id;
	private String name;
	private String code;
	private String jtbId;
	private String firsId;
	private String address;
	private State state;
	
	
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
	
	public String getJtbId() {
		return jtbId;
	}
	public void setJtbId(String jtbId) {
		this.jtbId = jtbId;
	}
	public String getFirsId() {
		return firsId;
	}
	public void setFirsId(String firsId) {
		this.firsId = firsId;
	}
	
	@Override
	public int compareTo(StampdutyOffice o) {
		return name.compareTo(o.getName());
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@OneToOne
	@JoinColumn(name="state",referencedColumnName="id")
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
}
