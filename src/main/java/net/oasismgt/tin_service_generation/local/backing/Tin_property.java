package net.oasismgt.tin_service_generation.local.backing;

import java.io.Serializable;

public class Tin_property implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 323147326983031281L;
	private String rc_number;
	private String mda_transaction_id;
	private String tin;
	private String name_of_organization;
	private String tax_office_code_jtb;
	private String tax_office_code_firs;
	private String date_of_issuance;
	private String date_of_task_queue_entrance;
	private String date_of_task_queue_left;
	
	public String getRc_number() {
		return rc_number;
	}
	public void setRc_number(String rc_number) {
		this.rc_number = rc_number;
	}
	public String getMda_transaction_id() {
		return mda_transaction_id;
	}
	public void setMda_transaction_id(String mda_transaction_id) {
		this.mda_transaction_id = mda_transaction_id;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getName_of_organization() {
		return name_of_organization;
	}
	public void setName_of_organization(String name_of_organization) {
		this.name_of_organization = name_of_organization;
	}
	public String getTax_office_code_jtb() {
		return tax_office_code_jtb;
	}
	public void setTax_office_code_jtb(String tax_office_code_jtb) {
		this.tax_office_code_jtb = tax_office_code_jtb;
	}
	public String getTax_office_code_firs() {
		return tax_office_code_firs;
	}
	public void setTax_office_code_firs(String tax_office_code_firs) {
		this.tax_office_code_firs = tax_office_code_firs;
	}
	public String getDate_of_issuance() {
		return date_of_issuance;
	}
	public void setDate_of_issuance(String date_of_issuance) {
		this.date_of_issuance = date_of_issuance;
	}

	@Override
	public int hashCode() {
		return rc_number.hashCode()+tin.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null)return false;
		if(this==obj)return true;
		
		if(obj instanceof Tin_property){
			Tin_property tp=(Tin_property)obj;
			return this.getRc_number().equals(tp.getRc_number());
		}
		return false;
	}
	public String getDate_of_task_queue_entrance() {
		return date_of_task_queue_entrance;
	}
	public void setDate_of_task_queue_entrance(String date_of_task_queue_entrance) {
		this.date_of_task_queue_entrance = date_of_task_queue_entrance;
	}
	public String getDate_of_task_queue_left() {
		return date_of_task_queue_left;
	}
	public void setDate_of_task_queue_left(String date_of_task_queue_left) {
		this.date_of_task_queue_left = date_of_task_queue_left;
	}
}
