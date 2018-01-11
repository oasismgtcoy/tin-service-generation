package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="corporate_tin_generation_request")
public class CorporateTinGenerationRequest implements Serializable{

	private static final long serialVersionUID = 985406093460634960L;
	//HASH = RCNO + ISDS_INTEGRATION_ID + PHONE_NO_PRIMARY +  MDA_TRANSACTION_ID + ISDS_INTEGRATION_KEY + COMPANY_NAME

	private Long id;
	private String rcno;
	private String mda_transaction_id;	
	private String tin;
	private String regno_of_organization; 
	private String name_of_organization;
	private String trade_name_of_organization;
	private String type_of_organization;//LIMITED BY GUARANTEE, PRIVATE LIMITED COMPANY, PRIVATE UNLIMITED COMPANY, PUBLIC LIMITED COMPANY, TRUSTEESHIP, FEDERAL MDA, STATE MDA, LIMITED BY GUARANTEE
	private String line_of_business;
	
	private String date_of_registration;
	private String date_of_incorporation;
	private String date_of_commencement;
	
	
	private String phone_no_primary;
	private String phone_no_alternative;
	private String e_mail_primary;
	private String e_mail_alternative;
			
	private String house_no;		
	private String street_name;	
	private String city; 
	private String lga;
	private String state;
	  	
	private String financial_year_begin;
	private String financial_year_end;
	private String representative_name;
	private String representative_address;
	private String representative_type; //T : Tax Agent, A : Auditor
	
	private String bank_name;
	private String bank_branch;
	private String bank_account_no;
	private String bank_name2;
	private String bank_branch2;
	private String bank_account_no2;
	private String branch_headquarters; //HQ,BR
	
	
	private String previous_taxpayer_number_used;
	private String previous_taxpayer_no_issuing_tax_authority_state;
	private String tax_office;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRcno() {
		return rcno;
	}
	public void setRcno(String rcno) {
		this.rcno = rcno;
	}
	public String getMda_transaction_id() {
		return mda_transaction_id;
	}
	public void setMda_transaction_id(String mda_transaction_id) {
		this.mda_transaction_id = mda_transaction_id;
	}
	
	public String getHouse_no() {
		return house_no;
	}
	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}
	public String getStreet_name() {
		return street_name;
	}
	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getLga() {
		return lga;
	}
	public void setLga(String lga) {
		this.lga = lga;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDate_of_registration() {
		return date_of_registration;
	}
	public void setDate_of_registration(String date_of_registration) {
		this.date_of_registration = date_of_registration;
	}
	
	public String getLine_of_business() {
		return line_of_business;
	}
	public void setLine_of_business(String line_of_business) {
		this.line_of_business = line_of_business;
	}
	public String getPhone_no_primary() {
		return phone_no_primary;
	}
	public void setPhone_no_primary(String phone_no_primary) {
		this.phone_no_primary = phone_no_primary;
	}
	public String getPhone_no_alternative() {
		return phone_no_alternative;
	}
	public void setPhone_no_alternative(String phone_no_alternative) {
		this.phone_no_alternative = phone_no_alternative;
	}
	
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getDate_of_incorporation() {
		return date_of_incorporation;
	}
	public void setDate_of_incorporation(String date_of_incorporation) {
		this.date_of_incorporation = date_of_incorporation;
	}
	public String getDate_of_commencement() {
		return date_of_commencement;
	}
	public void setDate_of_commencement(String date_of_commencement) {
		this.date_of_commencement = date_of_commencement;
	}
	public String getTax_office() {
		return tax_office;
	}
	public void setTax_office(String tax_office) {
		this.tax_office = tax_office;
	}
	public String getFinancial_year_end() {
		return financial_year_end;
	}
	public void setFinancial_year_end(String financial_year_end) {
		this.financial_year_end = financial_year_end;
	}
	public String getFinancial_year_begin() {
		return financial_year_begin;
	}
	public void setFinancial_year_begin(String financial_year_begin) {
		this.financial_year_begin = financial_year_begin;
	}
	public String getBranch_headquarters() {
		return branch_headquarters;
	}
	public void setBranch_headquarters(String branch_headquaters) {
		this.branch_headquarters = branch_headquaters;
	}
	public String getPrevious_taxpayer_number_used() {
		return previous_taxpayer_number_used;
	}
	public void setPrevious_taxpayer_number_used(String previous_taxpayer_number_used) {
		this.previous_taxpayer_number_used = previous_taxpayer_number_used;
	}
	public String getPrevious_taxpayer_no_issuing_tax_authority_state() {
		return previous_taxpayer_no_issuing_tax_authority_state;
	}
	public void setPrevious_taxpayer_no_issuing_tax_authority_state(
			String previous_taxpayer_no_issuing_tax_authority_state) {
		this.previous_taxpayer_no_issuing_tax_authority_state = previous_taxpayer_no_issuing_tax_authority_state;
	}
	public String getType_of_organization() {
		return type_of_organization;
	}
	public void setType_of_organization(String type_of_organization) {
		this.type_of_organization = type_of_organization;
	}
	public String getRepresentative_name() {
		return representative_name;
	}
	public void setRepresentative_name(String representative_name) {
		this.representative_name = representative_name;
	}
	public String getRepresentative_address() {
		return representative_address;
	}
	public void setRepresentative_address(String representative_address) {
		this.representative_address = representative_address;
	}
	public String getRepresentative_type() {
		return representative_type;
	}
	public void setRepresentative_type(String representative_type) {
		this.representative_type = representative_type;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	
	public String getBank_branch() {
		return bank_branch;
	}
	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}
	public String getBank_account_no() {
		return bank_account_no;
	}
	public void setBank_account_no(String bank_account_no) {
		this.bank_account_no = bank_account_no;
	}
	public String getBank_name2() {
		return bank_name2;
	}
	public void setBank_name2(String bank_name2) {
		this.bank_name2 = bank_name2;
	}
	public String getBank_branch2() {
		return bank_branch2;
	}
	public void setBank_branch2(String bank_branch_name2) {
		this.bank_branch2 = bank_branch_name2;
	}
	public String getBank_account_no2() {
		return bank_account_no2;
	}
	public void setBank_account_no2(String bank_account_no2) {
		this.bank_account_no2 = bank_account_no2;
	}

	
	public String getName_of_organization() {
		return name_of_organization;
	}
	public void setName_of_organization(String name_of_organization) {
		this.name_of_organization = name_of_organization;
	}
	public String getRegno_of_organization() {
		return regno_of_organization;
	}
	public void setRegno_of_organization(String regno_of_organization) {
		this.regno_of_organization = regno_of_organization;
	}
	public String getTrade_name_of_organization() {
		return trade_name_of_organization;
	}
	public void setTrade_name_of_organization(String trade_name_of_organization) {
		this.trade_name_of_organization = trade_name_of_organization;
	}
	
	public String getE_mail_primary() {
		return e_mail_primary;
	}
	public void setE_mail_primary(String e_mail_primary) {
		this.e_mail_primary = e_mail_primary;
	}
	public String getE_mail_alternative() {
		return e_mail_alternative;
	}
	public void setE_mail_alternative(String e_mail_alternative) {
		this.e_mail_alternative = e_mail_alternative;
	}
}
