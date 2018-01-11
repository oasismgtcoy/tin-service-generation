package net.oasismgt.tin_service_generation.local.constants;

public enum OrganisationType {
	BUSINESS_NAME("BUSINESS NAME"),LIMITED_BY_GUARANTEE("LIMITED BY GUARANTEE"), PRIVATE_LIMITED_COMPANY("PRIVATE LIMITED COMPANY"), PRIVATE_UNLIMITED_COMPANY("PRIVATE UNLIMITED COMPANY"), PUBLIC_LIMITED_COMPANY("PUBLIC LIMITED COMPANY"),PUBLIC_UNLIMITED_COMPANY("PUBLIC UNLIMITED COMPANY"), TRUSTEESHIP("TRUSTEESHIP"), FEDERAL_MDA("FEDERAL MDA"), STATE_MDA("STATE MDA");
	
	private OrganisationType(String type){
		this.type=type;
	}
	public String getType(){
		return type;
	}
	public String toString(){
		return getType();
	}
	private String type;
}
