package net.oasismgt.tin_service_generation.local.constants;


public enum State {
	
	ABIA("ABIA"),
	ADAMAWA("ADAMAWA"),
	AKWA_IBOM("AKWA IBOM"),
	ANAMBRA("ANAMBRA"),
	BAUCHI("BAUCHI"),
	BAYELSA("BAYELSA"),
	BENUE("BENUE"),
	BORNO("BORNO"),
	CROSS_RIVER("CROSS RIVER"),
	DELTA("DELTA"),
	EBONYI("EBONYI"),
	EDO("EDO"),
	EKITI("EKITI"),
	ENUGU("ENUGU"),
	FCT("FCT"),
	GOMBE("GOMBE"),
	IMO("IMO"),
	JIGAWA("JIGAWA"),
	KADUNA("KADUNA"),
	KANO("KANO"),
	KATSINA("KATSINA"),
	KEBBI("KEBBI"),
	KOGI("KOGI"),
	KWARA("KWARA"),
	LAGOS("LAGOS"),
	NASSARAWA("NASSARAWA"),
	NIGER("NIGER"),
	OGUN("OGUN"),
	ONDO("ONDO"),
	OSUN("OSUN"),
	OYO("OYO"),
	PLATEAU("PLATEAU"),
	RIVERS("RIVERS"),
	SOKOTO("SOKOTO"),
	TARABA("TARABA"),
	YOBE("YOBE"),
	ZAMFARA("ZAMFARA");
	

	private State(String name){
		this.name=name;
	}
	
	public String getName(){
		return name;
	}
	public String toString(){
		return name;
	}
	private String name;

}
