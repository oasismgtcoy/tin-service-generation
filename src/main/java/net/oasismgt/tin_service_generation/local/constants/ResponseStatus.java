package net.oasismgt.tin_service_generation.local.constants;

public enum ResponseStatus {
	ASSESSMENT_COMPLETED_SUCCESSFUL("000","Assessment Completed Successfully"),
	ASSESSMENT_NOT_COMPLETED_SUCCESSFUL("001","Assessment Not Completed Successfully"),
	INVALID_HASH("002","Invalid Hash."),
	INVALID_MERCHANT_ID("003","Invalid Merchant Id"),
	INCOMPLETE_PROPERTIES("004","Incomplete Properties"),
	NO_AMOUNT_SENT("005","No amount sent"),
	TRANSACTION_ID_ALREADY_USED("006","Transaction Id already used"),
	LOOKUP_ERROR("007","Lookup error"),
	INVALID_TIN("008","Invalid TIN"),
	VALID_TIN("009","Valid TIN"),
	INVALID_CERTIFICATE_NUMBER("010","Invalid Certificate Number"),
	VALID_CERTIFICATE_NUMBER("011","Valid Certificate Number"),
	REQUEST_RECEIVED_AND_IN_PROGRESS("012","Request was received successfully"),
	REQUEST_RECEIVED_AND_IN_QUEUE("013","Request received and it's being processed"),
	REQUEST_IN_PROGRESS("014","Request In Progress"),
	REQUEST_DECLINED("015","Request Declined"),
	REQUEST_COMPLETED("016","Request Completed"),
	RETRY_REQUEST("017","Retry Request"),
	CONFLICT("018","Conflict"),
	VALID_PROPERTY("019","Valid Property"),
	INVALID_PROPERTY("020","Invalid Property"),
	SERVICE_ERROR("021","Service Error"),
	SERVICE_NOT_AVAILABLE("022","Service Not Available"),
	SERVICE_SUSPENDED("023","Service Suspended"),
	COMPLETED_SUCCESSFULLY("024","Completed Successfully"),
	INTERRUPTED("025","Interrupted"),
	FOUND("026","Found"),
	NOT_FOUND("027","Not Found"),
	NOT_COMPLETED("028","Not Completed"),
	ASSESSMENT_PROPERTY_MISSING("029","Assessment Property Missing"),
	INVALID_MDA_TRANSACTION_ID("030","Invalid MDA Transaction ID"),
	TIN_PROPERTY_MISSING("031","Tin Property Missing"),
	TAXPAYER_PROPERTY_MISSING("032","Taxpayer Property Missing"),
	MDA_TRANSACTION_ID_ALREADY_IN_USED("033","MDA Transaction ID Already In Used"),
	AUTH_PROPERTY_MISSING("034","Authentication/Authorization Property Missing"),
	ASSESSMENT_NOT_COMPLETED_YET("035","Assessment Not Completed Yet"),
	CREATED_SUCCESSFULLY("036","Created Successfully"),
	EMPTY_PAYLOAD("037","Empty Payload"),
	UNKNOWN_ERROR("999","Unknown Error");
	
	private ResponseStatus(String code, String message){
		this.code=code;
		this.message=message;
	}
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return code+" "+ message;
	}
	private String code;
	private String message;

}
