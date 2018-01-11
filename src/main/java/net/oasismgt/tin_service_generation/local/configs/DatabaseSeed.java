package net.oasismgt.tin_service_generation.local.configs;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import net.oasismgt.tin_service_generation.local.model.Lga;
import net.oasismgt.tin_service_generation.local.model.State;
import net.oasismgt.tin_service_generation.local.service.LgaService;
import net.oasismgt.tin_service_generation.local.service.StateService;
import net.oasismgt.tin_service_generation.local.service.TaxOfficeService;

@Configuration
public class DatabaseSeed  implements CommandLineRunner{
	
	@Autowired private StateService stateService;
	@Autowired private LgaService lgaService;
	@Autowired private TaxOfficeService taxOfficeService;
	
	@Override
	public void run(String... arg0) throws Exception {
		
		Collection<State> states = stateService.getAll();
		if(states==null || states.isEmpty())
			Arrays.asList(States.values()).parallelStream().forEach(s->createState(s));
		if(lgaService.getAll().isEmpty())
			Arrays.asList(StateLga.values()).parallelStream().forEach(l->createLga(l));
		
	}
	
	private void createState(States s){
		State state=new State();
		state.setCode(s.toString());
		state.setName(s.toString());
		state.setTaxOffice(null);
		state.setTaxOfficeName(s.getTaxoffice());
		stateService.add(state);
	}
	private void createLga(StateLga s){
		Lga lga=new Lga();
		lga.setCode(s.getJtbName());
		lga.setName(s.toString());
		lga.setTaxOffice(null);
		lga.setTaxOfficeName(s.getTaxOfficeName());
		lgaService.add(lga);
	}
	
	public enum States{
		
		ABIA("ABIA","MSTO Umuahia"),
		ADAMAWA("ADAMAWA","MSTO Yola"),
		AKWA_IBOM("AKWA IBOM","MSTO Uyo"),
		ANAMBRA("ANAMBRA","MSTO Awka"),
		BAUCHI("BAUCHI","MSTO Bauchi"),
		BAYELSA("BAYELSA","MSTO Yenegoa"),
		BENUE("BENUE","MSTO Makurdi"),
		BORNO("BORNO","MSTO MAIDUGURI"),
		CROSS_RIVER("CROSS RIVER","MSTO Calabar"),
		DELTA("DELTA","MSTO Asaba"),
		EBONYI("EBONYI","MSTO ABAKALIKI"),
		EDO("EDO","GBTO EDO"),
		EKITI("EKITI","MSTO ADO-EKITI"),
		ENUGU("ENUGU","MSTO Enugu"),
		FCT("FCT","MSTO Central Area"),
		GOMBE("GOMBE","MSTO Gombe"),
		IMO("IMO","MSTO Owerri"),
		JIGAWA("JIGAWA","MSTO Dutse"),
		KADUNA("KADUNA","MSTO KADUNA I"),
		KANO("KANO","MSTO KANO I CLUB ROAD"),
		KATSINA("KATSINA","MSTO KATSINA"),
		KEBBI("KEBBI","MSTO Birnin Kebbi"),
		KOGI("KOGI","MSTO LOKOJA"),
		KWARA("KWARA","MSTO ILORIN"),
		LAGOS("LAGOS","MSTO IKEJA"),
		NASSARAWA("NASSARAWA","MSTO LAFIA"),
		NIGER("NIGER","MSTO Minna"),
		OGUN("OGUN","MSTO ABEOKUTA"),
		ONDO("ONDO","MSTO AKURE"),
		OSUN("OSUN","MSTO Oshogbo"),
		OYO("OYO","MTO Ibadan"),
		PLATEAU("PLATEAU","MSTO JOS"),
		RIVERS("RIVERS","MSTO Port Harcourt"),
		SOKOTO("SOKOTO","MSTO Sokoto"),
		TARABA("TARABA","MSTO JALINGO"),
		YOBE("YOBE","MSTO Damaturu"),
		ZAMFARA("ZAMFARA","GBTO ZAMFARA");
		

		private States(String name,String to){
			this.name=name;
			this.taxoffice=to;
		}
		
		public String getName(){
			return name;
		}
		public String getTaxoffice(){
			return taxoffice;
		}
		public String toString(){
			return name;
		}
		private String name;
		private String taxoffice;
	}
	
	public enum StateLga{
		
		ABADAM("ABADAM","","",""),
		ABAJI("ABAJI","","",""),
		ABAK("ABAK","","",""),
		ABAKALIKI("ABAKALIKI","","",""),
		ABA_NORTH("ABA NORTH","","",""),
		ABA_SOUTH("ABA SOUTH","","",""),
		ABEOKUTA_NORTH("ABEOKUTA NORTH","","",""),
		ABEOKUTA_SOUTH("ABEOKUTA SOUTH","","",""),
		ABI("ABI","","",""),
		ABOH_MBAISE("ABOH MBAISE","","",""),
		ABUA_ODUAL("ABUA/ODUAL","","",""),
		ADAVI("ADAVI","","",""),
		ADO_EKITI("ADO EKITI","","",""),
		ADO_ODO_OTA("ADO-ODO/OTA","","",""),
		AFIJIO("AFIJIO","","",""),
		AFIKPO_NORTH("AFIKPO NORTH","","",""),
		AFIKPO_SOUTH_EDDA("AFIKPO SOUTH (EDDA)","AFIKPO SOUTH","",""),
		AGAIE("AGAIE","","",""),
		AGATU("AGATU","","",""),
		AGWARA("AGWARA","","",""),
		AGEGE("AGEGE","","",""),
		AGUATA("AGUATA","","",""),
		AHIAZU_MBAISE("AHIAZU MBAISE","","",""),
		AHOADA_EAST("AHOADA EAST","","",""),
		AHOADA_WEST("AHOADA WEST","","",""),
		AJAOKUTA("AJAOKUTA","","",""),
		AJEROMI_IFELODUN("AJEROMI-IFELODUN","","",""),
		AJINGI("AJINGI","","",""),
		AKAMKPA("AKAMKPA","","",""),
		AKINYELE("AKINYELE","","",""),
		AKKO("AKKO","","",""),
		AKOKO_EDO("AKOKO-EDO","","",""),
		AKOKO_NORTH_EAST("AKOKO NORTH-EAST","","",""),
		AKOKO_NORTH_WEST("AKOKO NORTH-WEST","","",""),
		AKOKO_SOUTH_WEST("AKOKO SOUTH-WEST","","",""),
		AKOKO_SOUTH_EAST("AKOKO SOUTH-EAST","","",""),
		AKPABUYO("AKPABUYO","","",""),
		AKUKU_TORU("AKUKU-TORU","","",""),
		AKURE_NORTH("AKURE NORTH","","",""),
		AKURE_SOUTH("AKURE SOUTH","","",""),
		AKWANGA("AKWANGA","","",""),
		ALBASU("ALBASU","","",""),
		ALEIRO("ALEIRO","","",""),
		ALIMOSHO("ALIMOSHO","","",""),
		ALKALERI("ALKALERI","","",""),
		AMUWO_ODOFIN("AMUWO-ODOFIN","","",""),
		ANAMBRA_EAST("ANAMBRA EAST","","",""),
		ANAMBRA_WEST("ANAMBRA WEST","","",""),
		ANAOCHA("ANAOCHA","","",""),
		ANDONI("ANDONI","","",""),
		ANINRI("ANINRI","","",""),
		ANIOCHA_NORTH("ANIOCHA NORTH","","",""),
		ANIOCHA_SOUTH("ANIOCHA SOUTH","","",""),
		ANKA("ANKA","","",""),
		ANKPA("ANKPA","","",""),
		APA("APA","","",""),
		APAPA("APAPA","","",""),
		ADO("ADO","","",""),
		ARDO_KOLA("ARDO KOLA","","",""),
		AREWA_DANDI("AREWA DANDI","","",""),
		ARGUNGU("ARGUNGU","","",""),
		AROCHUKWU("AROCHUKWU","","",""),
		ASA("ASA","","",""),
		ASARI_TORU("ASARI-TORU","","",""),
		ASKIRA_UBA("ASKIRA/UBA","","",""),
		ATAKUNMOSA_EAST("ATAKUNMOSA EAST","","",""),
		ATAKUNMOSA_WEST("ATAKUNMOSA WEST","","",""),
		ATIBA("ATIBA","","",""),
		ATISBO("ATISBO","","",""),
		AUGIE("AUGIE","","",""),
		AUYO("AUYO","","",""),
		AWE("AWE","","",""),
		AWGU("AWGU","","",""),
		AWKA_NORTH("AWKA NORTH","","",""),
		AWKA_SOUTH("AWKA SOUTH","","",""),
		AYAMELUM("AYAMELUM","","",""),
		AIYEDAADE("AIYEDAADE","","",""),
		AIYEDIRE("AIYEDIRE","","",""),
		BABURA("BABURA","","",""),
		BADAGRY("BADAGRY","","",""),
		BAGUDO("BAGUDO","","",""),
		BAGWAI("BAGWAI","","",""),
		BAKASSI("BAKASSI","","",""),
		BOKKOS("BOKKOS","","",""),
		BAKORI("BAKORI","","",""),
		BAKURA("BAKURA","","",""),
		BALANGA("BALANGA","","",""),
		BALI("BALI","","",""),
		BAMA("BAMA","","",""),
		BADE("BADE","","",""),
		BARKIN_LADI("BARKIN LADI","","",""),
		BARUTEN("BARUTEN","","",""),
		BASSA("BASSA","","",""),
		BATAGARAWA("BATAGARAWA","","",""),
		BATSARI("BATSARI","","",""),
		BAUCHI("BAUCHI","","",""),
		BAURE("BAURE","","",""),
		BAYO("BAYO","","",""),
		BEBEJI("BEBEJI","","",""),
		BEKWARRA("BEKWARRA","","",""),
		BENDE("BENDE","","",""),
		BIASE("BIASE","","",""),
		BICHI("BICHI","","",""),
		BIDA("BIDA","","",""),
		BILLIRI("BILLIRI","","",""),
		BINDAWA("BINDAWA","","",""),
		BINJI("BINJI","","",""),
		BIRINIWA("BIRINIWA","","",""),
		BIRNIN_GWARI("BIRNIN GWARI","","",""),
		BIRNIN_KEBBI("BIRNIN KEBBI","","",""),
		BIRNIN_KUDU("BIRNIN KUDU","","",""),
		BIRNIN_MAGAJI_KIYAW("BIRNIN MAGAJI/KIYAW","","",""),
		BIU("BIU","","",""),
		BODINGA("BODINGA","","",""),
		BOGORO("BOGORO","","",""),
		BOKI("BOKI","","",""),
		BOLUWADURO("BOLUWADURO","","",""),
		BOMADI("BOMADI","","",""),
		BONNY("BONNY","","",""),
		BORGU("BORGU","","",""),
		BORIPE("BORIPE","","",""),
		BURSARI("BURSARI","","",""),
		BOSSO("BOSSO","","",""),
		BRASS("BRASS","","",""),
		BUJI("BUJI","","",""),
		BUKKUYUM("BUKKUYUM","","",""),
		BURUKU("BURUKU","","",""),
		BUNGUDU("BUNGUDU","","",""),
		BUNKURE("BUNKURE","","",""),
		BUNZA("BUNZA","","",""),
		BURUTU("BURUTU","","",""),
		BWARI("BWARI","","",""),
		CALABAR_MUNICIPAL("CALABAR MUNICIPAL","","",""),
		CALABAR_SOUTH("CALABAR SOUTH","","",""),
		CHANCHAGA("CHANCHAGA","","",""),
		CHARANCHI("CHARANCHI","","",""),
		CHIBOK("CHIBOK","","",""),
		CHIKUN("CHIKUN","","",""),
		DALA("DALA","","",""),
		DAMATURU("DAMATURU","","",""),
		DAMBAN("DAMBAN","","",""),
		DAMBATTA("DAMBATTA","","",""),
		DAMBOA("DAMBOA","","",""),
		DANDI("DANDI","","",""),
		DANDUME("DANDUME","","",""),
		DANGE_SHUNI("DANGE SHUNI","","",""),
		DANJA("DANJA","","",""),
		DAN_MUSA("DAN MUSA","","",""),
		DARAZO("DARAZO","","",""),
		DASS("DASS","","",""),
		DAURA("DAURA","","",""),
		DAWAKIN_KUDU("DAWAKIN KUDU","","",""),
		DAWAKIN_TOFA("DAWAKIN TOFA","","",""),
		DEGEMA("DEGEMA","","",""),
		DEKINA("DEKINA","","",""),
		DEMSA("DEMSA","","",""),
		DIKWA("DIKWA","","",""),
		DOGUWA("DOGUWA","","",""),
		DOMA("DOMA","","",""),
		DONGA("DONGA","","",""),
		DUKKU("DUKKU","","",""),
		DUNUKOFIA("DUNUKOFIA","","",""),
		DUTSE("DUTSE","","",""),
		DUTSI("DUTSI","","",""),
		DUTSIN_MA("DUTSIN MA","","",""),
		EASTERN_OBOLO("EASTERN OBOLO","","",""),
		EBONYI("EBONYI","","",""),
		EDATI("EDATI","","",""),
		EDE_NORTH("EDE NORTH","","",""),
		EDE_SOUTH("EDE SOUTH","","",""),
		EDU("EDU","","",""),
		IFE_CENTRAL("IFE CENTRAL","","",""),
		IFE_EAST("IFE EAST","","",""),
		IFE_NORTH("IFE NORTH","","",""),
		IFE_SOUTH("IFE SOUTH","","",""),
		EFON("EFON","","",""),
		YEWA_NORTH("YEWA NORTH","","",""),
		YEWA_SOUTH("YEWA SOUTH","","",""),
		EGBEDA("EGBEDA","","",""),
		EGBEDORE("EGBEDORE","","",""),
		EGOR("EGOR","","",""),
		EHIME_MBANO("EHIME MBANO","","",""),
		EJIGBO("EJIGBO","","",""),
		EKEREMOR("EKEREMOR","","",""),
		EKET("EKET","","",""),
		EKITI("EKITI","","",""),
		EKITI_EAST("EKITI EAST","","",""),
		EKITI_SOUTH_WEST("EKITI SOUTH-WEST","","",""),
		EKITI_WEST("EKITI WEST","","",""),
		EKWUSIGO("EKWUSIGO","","",""),
		ELEME("ELEME","","",""),
		EMUOHA("EMUOHA","","",""),
		EMURE("EMURE","","",""),
		ENUGU_EAST("ENUGU EAST","","",""),
		ENUGU_NORTH("ENUGU NORTH","","",""),
		ENUGU_SOUTH("ENUGU SOUTH","","",""),
		EPE("EPE","","",""),
		ESAN_CENTRAL("ESAN CENTRAL","","",""),
		ESAN_NORTH_EAST("ESAN NORTH-EAST","","",""),
		ESAN_SOUTH_EAST("ESAN SOUTH-EAST","","",""),
		ESAN_WEST("ESAN WEST","","",""),
		ESE_ODO("ESE ODO","","",""),
		ESIT_EKET("ESIT EKET","","",""),
		ESSIEN_UDIM("ESSIEN UDIM","","",""),
		ETCHE("ETCHE","","",""),
		ETHIOPE_EAST("ETHIOPE EAST","","",""),
		ETHIOPE_WEST("ETHIOPE WEST","","",""),
		ETIM_EKPO("ETIM EKPO","","",""),
		ETINAN("ETINAN","","",""),
		ETI_OSA("ETI OSA","ETI-OSA","",""),
		ETSAKO_CENTRAL("ETSAKO CENTRAL","","",""),
		ETSAKO_EAST("ETSAKO EAST","","",""),
		ETSAKO_WEST("ETSAKO WEST","","",""),
		ETUNG("ETUNG","","",""),
		EWEKORO("EWEKORO","","",""),
		EZEAGU("EZEAGU","","",""),
		EZINIHITTE("EZINIHITTE","","",""),
		EZZA_NORTH("EZZA NORTH","","",""),
		EZZA_SOUTH("EZZA SOUTH","","",""),
		FAGGE("FAGGE","","",""),
		FAKAI("FAKAI","","",""),
		FASKARI("FASKARI","","",""),
		FIKA("FIKA","","",""),
		FUFURE("FUFURE","","",""),
		FUNAKAYE("FUNAKAYE","","",""),
		FUNE("FUNE","","",""),
		FUNTUA("FUNTUA","","",""),
		GABASAWA("GABASAWA","","",""),
		GADA("GADA","","",""),
		GAGARAWA("GAGARAWA","","",""),
		GAMAWA("GAMAWA","","",""),
		GANJUWA("GANJUWA","","",""),
		GANYE("GANYE","","",""),
		GARKI("GARKI","","",""),
		GARKO("GARKO","","",""),
		GARUN_MALLAM("GARUN MALLAM","","",""),
		GASHAKA("GASHAKA","","",""),
		GASSOL("GASSOL","","",""),
		GAYA("GAYA","","",""),
		GAYUK("GAYUK","","",""),
		GEZAWA("GEZAWA","","",""),
		GBAKO("GBAKO","","",""),
		GBOKO("GBOKO","","",""),
		GBONYIN("GBONYIN","","",""),
		GEIDAM("GEIDAM","","",""),
		GIADE("GIADE","","",""),
		GIWA("GIWA","","",""),
		GOKANA("GOKANA","","",""),
		GOMBE("GOMBE","","",""),
		GOMBI("GOMBI","","",""),
		GORONYO("GORONYO","","",""),
		GRIE("GRIE","","",""),
		GUBIO("GUBIO","","",""),
		GUDU("GUDU","","",""),
		GUJBA("GUJBA","","",""),
		GULANI("GULANI","","",""),
		GUMA("GUMA","","",""),
		GUMEL("GUMEL","","",""),
		GUMMI("GUMMI","","",""),
		GURARA("GURARA","","",""),
		GURI("GURI","","",""),
		GUSAU("GUSAU","","",""),
		GUZAMALA("GUZAMALA","","",""),
		GWADABAWA("GWADABAWA","","",""),
		GWAGWALADA("GWAGWALADA","","",""),
		GWALE("GWALE","","",""),
		GWANDU("GWANDU","","",""),
		GWARAM("GWARAM","","",""),
		GWARZO("GWARZO","","",""),
		GWER_EAST("GWER EAST","","",""),
		GWER_WEST("GWER WEST","","",""),
		GWIWA("GWIWA","","",""),
		GWOZA("GWOZA","","",""),
		HADEJIA("HADEJIA","","",""),
		HAWUL("HAWUL","","",""),
		HONG("HONG","","",""),
		IBADAN_NORTH("IBADAN NORTH","","",""),
		IBADAN_NORTH_EAST("IBADAN NORTH-EAST","","",""),
		IBADAN_NORTH_WEST("IBADAN NORTH-WEST","","",""),
		IBADAN_SOUTH_EAST("IBADAN SOUTH-EAST","","",""),
		IBADAN_SOUTH_WEST("IBADAN SOUTH-WEST","","",""),
		IBAJI("IBAJI","","",""),
		IBARAPA_CENTRAL("IBARAPA CENTRAL","","",""),
		IBARAPA_EAST("IBARAPA EAST","","",""),
		IBARAPA_NORTH("IBARAPA NORTH","","",""),
		IBEJU_LEKKI("IBEJU-LEKKI","","",""),
		IBENO("IBENO","","",""),
		IBESIKPO_ASUTAN("IBESIKPO ASUTAN","","",""),
		IBI("IBI","","",""),
		IBIONO_IBOM("IBIONO-IBOM","","",""),
		IDAH("IDAH","","",""),
		IDANRE("IDANRE","","",""),
		IDEATO_NORTH("IDEATO NORTH","","",""),
		IDEATO_SOUTH("IDEATO SOUTH","","",""),
		IDEMILI_NORTH("IDEMILI NORTH","","",""),
		IDEMILI_SOUTH("IDEMILI SOUTH","","",""),
		IDO("IDO","","",""),
		IDO_OSI("IDO OSI","","",""),
		IFAKO_IJAIYE("IFAKO-IJAIYE","","",""),
		IFEDAYO("IFEDAYO","","",""),
		IFEDORE("IFEDORE","","",""),
		IFELODUN("IFELODUN","","",""),
		IFO("IFO","","",""),
		IGABI("IGABI","","",""),
		IGALAMELA_ODOLU("IGALAMELA ODOLU","","",""),
		IGBO_ETITI("IGBO ETITI","","",""),
		IGBO_EZE_NORTH("IGBO EZE NORTH","","",""),
		IGBO_EZE_SOUTH("IGBO EZE SOUTH","","",""),
		IGUEBEN("IGUEBEN","","",""),
		IHIALA("IHIALA","","",""),
		IHITTE_UBOMA("IHITTE/UBOMA","","",""),
		ILAJE("ILAJE","","",""),
		IJEBU_EAST("IJEBU EAST","","",""),
		IJEBU_NORTH("IJEBU NORTH","","",""),
		IJEBU_NORTH_EAST("IJEBU NORTH EAST","","",""),
		IJEB_ODE("IJEBU ODE","","",""),
		IJERO("IJERO","","",""),
		IJUMU("IJUMU","","",""),
		IKA("IKA","","",""),
		IKA_NORTH_EAST("IKA NORTH EAST","","",""),
		IKARA("IKARA","","",""),
		IKA_SOUTH("IKA SOUTH","","",""),
		IKEDURU("IKEDURU","","",""),
		IKEJA("IKEJA","","",""),
		IKENNE("IKENNE","","",""),
		IKERE("IKERE","","",""),
		IKOLE("IKOLE","","",""),
		IKOM("IKOM","","",""),
		IKONO("IKONO","","",""),
		IKORODU("IKORODU","","",""),
		IKOT_ABASI("IKOT ABASI","","",""),
		IKOT_EKPENE("IKOT EKPENE","","",""),
		IKPOBA_OKHA("IKPOBA OKHA","","",""),
		IKWERRE("IKWERRE","","",""),
		IKWO("IKWO","","",""),
		IKWUANO("IKWUANO","","",""),
		ILA("ILA","","",""),
		ILEJEMEJE("ILEJEMEJE","","",""),
		ILE_OLUJI_OKEIGBO("ILE OLUJI/OKEIGBO","","",""),
		ILESA_EAST("ILESA EAST","","",""),
		ILESA_WEST("ILESA WEST","","",""),
		ILLELA("ILLELA","","",""),
		ILORIN_EAST("ILORIN EAST","","",""),
		ILORIN_SOUTH("ILORIN SOUTH","","",""),
		ILORIN_WEST("ILORIN WEST","","",""),
		IMEKO_AFON("IMEKO AFON","","",""),
		INGAWA("INGAWA","","",""),
		INI("INI","","",""),
		IPOKIA("IPOKIA","","",""),
		IRELE("IRELE","","",""),
		IREPO("IREPO","","",""),
		IREPODUN("IREPODUN","","",""),
		IREPODUN_IFELODUN("IREPODUN/IFELODUN","","",""),
		IREWOLE("IREWOLE","","",""),
		ISA("ISA","","",""),
		ISE_ORUN("ISE/ORUN","","",""),
		ISEYIN("ISEYIN","","",""),
		ISHIELU("ISHIELU","","",""),
		ISIALA_MBANO("ISIALA MBANO","","",""),
		ISIALA_NGWA_NORTH("ISIALA NGWA NORTH","","",""),
		ISIALA_NGWA_SOUTH("ISIALA NGWA SOUTH","","",""),
		ISIN("ISIN","","",""),
		ISI_UZO("ISI UZO","","",""),
		ISOKAN("ISOKAN","","",""),
		ISOKO_NORTH("ISOKO NORTH","","",""),
		ISOKO_SOUTH("ISOKO SOUTH","","",""),
		ISU("ISU","","",""),
		ISUIKWUATO("ISUIKWUATO","","",""),
		ITAS_GADAU("ITAS/GADAU","","",""),
		ITESIWAJU("ITESIWAJU","","",""),
		ITU("ITU","","",""),
		IVO("IVO","","",""),
		IWAJOWA("IWAJOWA","","",""),
		IWO("IWO","","",""),
		IZZI("IZZI","","",""),
		JABA("JABA","","",""),
		JADA("JADA","","",""),
		JAHUN("JAHUN","","",""),
		JAKUSKO("JAKUSKO","","",""),
		JALINGO("JALINGO","","",""),
		JAMA_ARE("JAMA'ARE","","",""),
		JEGA("JEGA","","",""),
		JEMA_A("JEMA'A","","",""),
		JERE("JERE","","",""),
		JIBIA("JIBIA","","",""),
		JOS_EAST("JOS EAST","","",""),
		JOS_NORTH("JOS NORTH","","",""),
		JOS_SOUTH("JOS SOUTH","","",""),
		KABBA_BUNU("KABBA/BUNU","","",""),
		KABO("KABO","","",""),
		KACHIA("KACHIA","","",""),
		KADUNA_NORTH("KADUNA NORTH","","",""),
		KADUNA_SOUTH("KADUNA SOUTH","","",""),
		KAFIN_HAUSA("KAFIN HAUSA","","",""),
		KAFUR("KAFUR","","",""),
		KAGA("KAGA","","",""),
		KAGARKO("KAGARKO","","",""),
		KAIAMA("KAIAMA","","",""),
		KAITA("KAITA","","",""),
		KAJOLA("KAJOLA","","",""),
		KAJURU("KAJURU","","",""),
		KALA_BALGE("KALA/BALGE","","",""),
		KALGO("KALGO","","",""),
		KALTUNGO("KALTUNGO","","",""),
		KANAM("KANAM","","",""),
		KANKARA("KANKARA","","",""),
		KANKE("KANKE","","",""),
		KANKIA("KANKIA","","",""),
		KANO_MUNICIPAL("KANO MUNICIPAL","","",""),
		KARASUWA("KARASUWA","","",""),
		KARAYE("KARAYE","","",""),
		KARIM_LAMIDO("KARIM LAMIDO","","",""),
		KARU("KARU","","",""),
		KATAGUM("KATAGUM","","",""),
		KATCHA("KATCHA","","",""),
		KATSINA("KATSINA","","",""),
		KATSINA_ALA("KATSINA-ALA","","",""),
		KAURA("KAURA","","",""),
		KAURA_NAMODA("KAURA NAMODA","","",""),
		KAURU("KAURU","","",""),
		KAZAURE("KAZAURE","","",""),
		KEANA("KEANA","","",""),
		KEBBE("KEBBE","","",""),
		KEFFI("KEFFI","","",""),
		KHANA("KHANA","","",""),
		KIBIYA("KIBIYA","","",""),
		KIRFI("KIRFI","","",""),
		KIRI_KASAMA("KIRI KASAMA","","",""),
		KIRU("KIRU","","",""),
		KIYAWA("KIYAWA","","",""),
		KOGI("KOGI","","",""),
		KOKO_BESSE("KOKO/BESSE","","",""),
		KOKONA("KOKONA","","",""),
		KOLOKUMA_OPOKUMA("KOLOKUMA/OPOKUMA","","",""),
		KONDUGA("KONDUGA","","",""),
		KONSHISHA("KONSHISHA","","",""),
		KONTAGORA("KONTAGORA","","",""),
		KOSOFE("KOSOFE","","",""),
		KAUGAMA("KAUGAMA","","",""),
		KUBAU("KUBAU","","",""),
		KUDAN("KUDAN","","",""),
		KUJE("KUJE","","",""),
		KUKAWA("KUKAWA","","",""),
		KUMBOTSO("KUMBOTSO","","",""),
		KUMI("KUMI","","",""),
		KUNCHI("KUNCHI","","",""),
		KURA("KURA","","",""),
		KURFI("KURFI","","",""),
		KUSADA("KUSADA","","",""),
		KWALI("KWALI","","",""),
		KWANDE("KWANDE","","",""),
		KWAMI("KWAMI","","",""),
		KWARE("KWARE","","",""),
		KWAYA_KUSAR("KWAYA KUSAR","","",""),
		LAFIA("LAFIA","","",""),
		LAGELU("LAGELU","","",""),
		LAGOS_ISLAND("LAGOS ISLAND","","",""),
		LAGOS_MAINLAND("LAGOS MAINLAND","","",""),
		LANGTANG_SOUTH("LANGTANG SOUTH","","",""),
		LANGTANG_NORTH("LANGTANG NORTH","","",""),
		LAPAI("LAPAI","","",""),
		LAMURDE("LAMURDE","","",""),
		LAU("LAU","","",""),
		LAVUN("LAVUN","","",""),
		LERE("LERE","","",""),
		LOGO("LOGO","","",""),
		LOKOJA("LOKOJA","","",""),
		MACHINA("MACHINA","","",""),
		MADAGALI("MADAGALI","","",""),
		MADOBI("MADOBI","","",""),
		MAFA("MAFA","","",""),
		MAGAMA("MAGAMA","","",""),
		MAGUMERI("MAGUMERI","","",""),
		MAI_ADUA("MAI'ADUA","","",""),
		MAIDUGURI("MAIDUGURI","","",""),
		MAIGATARI("MAIGATARI","","",""),
		MAIHA("MAIHA","","",""),
		MAIYAMA("MAIYAMA","","",""),
		MAKARFI("MAKARFI","","",""),
		MAKODA("MAKODA","","",""),
		MALAM_MADORI("MALAM MADORI","","",""),
		MALUMFASHI("MALUMFASHI","","",""),
		MANGU("MANGU","","",""),
		MANI("MANI","","",""),
		MARADUN("MARADUN","","",""),
		MARIGA("MARIGA","","",""),
		MAKURDI("MAKURDI","","",""),
		MARTE("MARTE","","",""),
		MARU("MARU","","",""),
		MASHEGU("MASHEGU","","",""),
		MASHI("MASHI","","",""),
		MATAZU("MATAZU","","",""),
		MAYO_BELWA("MAYO BELWA","","",""),
		MBAITOLI("MBAITOLI","","",""),
		MBO("MBO","","",""),
		MICHIKA("MICHIKA","","",""),
		MIGA("MIGA","","",""),
		MIKANG("MIKANG","","",""),
		MINJIBIR("MINJIBIR","","",""),
		MISAU("MISAU","","",""),
		MOBA("MOBA","","",""),
		MOBBAR("MOBBAR","","",""),
		MUBI_NORTH("MUBI NORTH","","",""),
		MUBI_SOUTH("MUBI SOUTH","","",""),
		MOKWA("MOKWA","","",""),
		MONGUNO("MONGUNO","","",""),
		MOPA_MURO("MOPA MURO","","",""),
		MORO("MORO","","",""),
		MOYA("MOYA","","",""),
		MKPAT_ENIN("MKPAT-ENIN","","",""),
		MUNICIPAL_AREA_COUNCIL("MUNICIPAL AREA COUNCIL","AMAC","",""),
		MUSAWA("MUSAWA","","",""),
		MUSHIN("MUSHIN","","",""),
		NAFADA("NAFADA","","",""),
		NANGERE("NANGERE","","",""),
		NASARAWA("NASARAWA","","",""),
		NASARAWA_EGON("NASARAWA EGON","","",""),
		NDOKWA_EAST("NDOKWA EAST","","",""),
		NDOKWA_WEST("NDOKWA WEST","","",""),
		NEMBE("NEMBE","","",""),
		NGALA("NGALA","","",""),
		NGANZAI("NGANZAI","","",""),
		NGASKI("NGASKI","","",""),
		NGOR_OKPALA("NGOR OKPALA","","",""),
		NGURU("NGURU","","",""),
		NINGI("NINGI","","",""),
		NJABA("NJABA","","",""),
		NJIKOKA("NJIKOKA","","",""),
		NKANU_EAST("NKANU EAST","","",""),
		NKANU_WEST("NKANU WEST","","",""),
		NKWERRE("NKWERRE","","",""),
		NNEWI_NORTH("NNEWI NORTH","","",""),
		NNEWI_SOUTH("NNEWI SOUTH","","",""),
		NSIT_ATAI("NSIT-ATAI","","",""),
		NSIT_IBOM("NSIT-IBOM","","",""),
		NSIT_UBIUM("NSIT-UBIUM","","",""),
		NSUKKA("NSUKKA","","",""),
		NUMAN("NUMAN","","",""),
		NWANGELE("NWANGELE","","",""),
		OBAFEMI_OWODE("OBAFEMI OWODE","","",""),
		OBANLIKU("OBANLIKU","","",""),
		OBI("OBI","","",""),
		OBI_NGWA("OBI NGWA","","",""),
		OBIO_AKPOR("OBIO/AKPOR","","",""),
		OBOKUN("OBOKUN","","",""),
		OBOT_AKARA("OBOT AKARA","","",""),
		OBOWO("OBOWO","","",""),
		OBUBRA("OBUBRA","","",""),
		OBUDU("OBUDU","","",""),
		ODEDA("ODEDA","","",""),
		ODIGBO("ODIGBO","","",""),
		ODOGBOLU("ODOGBOLU","","",""),
		ODO_OTIN("ODO OTIN","","",""),
		ODUKPANI("ODUKPANI","","",""),
		OFFA("OFFA","","",""),
		OFU("OFU","","",""),
		OGBA_EGBEMA_NDONI("OGBA/EGBEMA/NDONI","","",""),
		OGBADIBO("OGBADIBO","","",""),
		OGBARU("OGBARU","","",""),
		OGBIA("OGBIA","","",""),
		OGBOMOSHO_NORTH("OGBOMOSHO NORTH","","",""),
		OGBOMOSHO_SOUTH("OGBOMOSHO SOUTH","","",""),
		OGU_BOLO("OGU/BOLO","","",""),
		OGOJA("OGOJA","","",""),
		OGO_OLUWA("OGO OLUWA","","",""),
		OGORI_MAGONGO("OGORI/MAGONGO","","",""),
		OGUN_WATERSIDE("OGUN WATERSIDE","","",""),
		OGUTA("OGUTA","","",""),
		OHAFIA("OHAFIA","","",""),
		OHAJI_EGBEMA("OHAJI/EGBEMA","","",""),
		OHAOZARA("OHAOZARA","","",""),
		OHAUKWU("OHAUKWU","","",""),
		OHIMINI("OHIMINI","","",""),
		ORHIONMWON("ORHIONMWON","","",""),
		OJI_RIVER("OJI RIVER","","",""),
		OJO("OJO","","",""),
		OJU("OJU","","",""),
		OKEHI("OKEHI","","",""),
		OKENE("OKENE","","",""),
		OKE_ERO("OKE ERO","","",""),
		OKIGWE("OKIGWE","","",""),
		OKITIPUPA("OKITIPUPA","","",""),
		OKOBO("OKOBO","","",""),
		OKPE("OKPE","","",""),
		OKRIKA("OKRIKA","","",""),
		OLAMABORO("OLAMABORO","","",""),
		OLA_OLUWA("OLA OLUWA","","",""),
		OLORUNDA("OLORUNDA","","",""),
		OLORUNSOGO("OLORUNSOGO","","",""),
		OLUYOLE("OLUYOLE","","",""),
		OMALA("OMALA","","",""),
		OMUMA("OMUMA","","",""),
		ONA_ARA("ONA ARA","","",""),
		ONDO_EAST("ONDO EAST","","",""),
		ONDO_WEST("ONDO WEST","","",""),
		ONICHA("ONICHA","","",""),
		ONITSHA_NORTH("ONITSHA NORTH","","",""),
		ONITSHA_SOUTH("ONITSHA SOUTH","","",""),
		ONNA("ONNA","","",""),
		OKPOKWU("OKPOKWU","","",""),
		OPOBO_NKORO("OPOBO/NKORO","","",""),
		OREDO("OREDO","","",""),
		ORELOPE("ORELOPE","","",""),
		ORIADE("ORIADE","","",""),
		ORI_IRE("ORI IRE","","",""),
		ORLU("ORLU","","",""),
		OROLU("OROLU","","",""),
		ORON("ORON","","",""),
		ORSU("ORSU","","",""),
		ORU_EAST("ORU EAST","","",""),
		ORUK_ANAM("ORUK ANAM","","",""),
		ORUMBA_NORTH("ORUMBA NORTH","","",""),
		ORUMBA_SOUTH("ORUMBA SOUTH","","",""),
		ORU_WEST("ORU WEST","","",""),
		OSE("OSE","","",""),
		OSHIMILI_NORTH("OSHIMILI NORTH","","",""),
		OSHIMILI_SOUTH("OSHIMILI SOUTH","","",""),
		OSHODI_ISOLO("OSHODI-ISOLO","","",""),
		OSISIOMA("OSISIOMA","","",""),
		OSOGBO("OSOGBO","","",""),
		OTURKPO("OTURKPO","","",""),
		OVIA_NORTH_EAST("OVIA NORTH-EAST","","",""),
		OVIA_SOUTH_WEST("OVIA SOUTH-WEST","","",""),
		OWAN_EAST("OWAN EAST","","",""),
		OWAN_WEST("OWAN WEST","","",""),
		OWERRI_MUNICIPAL("OWERRI MUNICIPAL","","",""),
		OWERRI_NORTH("OWERRI NORTH","","",""),
		OWERRI_WEST("OWERRI WEST","","",""),
		OWO("OWO","","",""),
		OYE("OYE","","",""),
		OYI("OYI","","",""),
		OYIGBO("OYIGBO","","",""),
		OYO("OYO","","",""),
		OYO_EAST("OYO EAST","","",""),
		OYUN("OYUN","","",""),
		PAIKORO("PAIKORO","","",""),
		PANKSHIN("PANKSHIN","","",""),
		PATANI("PATANI","","",""),
		PATEGI("PATEGI","","",""),
		PORT_HARCOURT("PORT HARCOURT","","",""),
		POTISKUM("POTISKUM","","",""),
		QUA_AN_PAN("QUA'AN PAN","","",""),
		RABAH("RABAH","","",""),
		RAFI("RAFI","","",""),
		RANO("RANO","","",""),
		REMO_NORTH("REMO NORTH","","",""),
		RIJAU("RIJAU","","",""),
		RIMI("RIMI","","",""),
		RIMIN_GADO("RIMIN GADO","","",""),
		RINGIM("RINGIM","","",""),
		RIYOM("RIYOM","","",""),
		ROGO("ROGO","","",""),
		RONI("RONI","","",""),
		SABON_BIRNI("SABON BIRNI","","",""),
		SABON_GARI("SABON GARI","","",""),
		SABUWA("SABUWA","","",""),
		SAFANA("SAFANA","","",""),
		SAGBAMA("SAGBAMA","","",""),
		SAKABA("SAKABA","","",""),
		SAKI_EAST("SAKI EAST","","",""),
		SAKI_WEST("SAKI WEST","","",""),
		SANDAMU("SANDAMU","","",""),
		SANGA("SANGA","","",""),
		SAPELE("SAPELE","","",""),
		SARDAUNA("SARDAUNA","","",""),
		SHAGAMU("SHAGAMU","SAGAMU","",""),
		SHAGARI("SHAGARI","","",""),
		SHANGA("SHANGA","","",""),
		SHANI("SHANI","","",""),
		SHANONO("SHANONO","","",""),
		SHELLENG("SHELLENG","","",""),
		SHENDAM("SHENDAM","","",""),
		SHINKAFI("SHINKAFI","","",""),
		SHIRA("SHIRA","","",""),
		SHIRORO("SHIRORO","","",""),
		SHONGOM("SHONGOM","","",""),
		SHOMOLU("SHOMOLU","SOMOLU","",""),
		SILAME("SILAME","","",""),
		SOBA("SOBA","","",""),
		SOKOTO_NORTH("SOKOTO NORTH","","",""),
		SOKOTO_SOUTH("SOKOTO SOUTH","","",""),
		SONG("SONG","","",""),
		SOUTHERN_IJAW("SOUTHERN IJAW","","",""),
		SULEJA("SULEJA","","",""),
		SULE_TANKARKAR("SULE TANKARKAR","","",""),
		SUMAILA("SUMAILA","","",""),
		SURU("SURU","","",""),
		SURULERE("SURULERE","","",""),
		TAFA("TAFA","","",""),
		TAFAWA_BALEWA("TAFAWA BALEWA","","",""),
		TAI("TAI","","",""),
		TAKAI("TAKAI","","",""),
		TAKUM("TAKUM","","",""),
		TALATA_MAFARA("TALATA MAFARA","","",""),
		TAMBUWAL("TAMBUWAL","","",""),
		TANGAZA("TANGAZA","","",""),
		TARAUNI("TARAUNI","","",""),
		TARKA("TARKA","","",""),
		TARMUWA("TARMUWA","","",""),
		TAURA("TAURA","","",""),
		TOUNGO("TOUNGO","","",""),
		TOFA("TOFA","","",""),
		TORO("TORO","","",""),
		TOTO("TOTO","","",""),
		CHAFE("CHAFE","","",""),
		TSANYAWA("TSANYAWA","","",""),
		TUDUN_WADA("TUDUN WADA","","",""),
		TURETA("TURETA","","",""),
		UDENU("UDENU","","",""),
		UDI("UDI","","",""),
		UDU("UDU","","",""),
		UDUNG_UKO("UDUNG-UKO","","",""),
		UGHELLI_NORTH("UGHELLI NORTH","","",""),
		UGHELLI_SOUTH("UGHELLI SOUTH","","",""),
		UGWUNAGBO("UGWUNAGBO","","",""),
		UHUNMWONDE("UHUNMWONDE","","",""),
		UKANAFUN("UKANAFUN","","",""),
		UKUM("UKUM","","",""),
		UKWA_EAST("UKWA EAST","","",""),
		UKWA_WEST("UKWA WEST","","",""),
		UKWUANI("UKWUANI","","",""),
		UMUAHIA_NORTH("UMUAHIA NORTH","","",""),
		UMUAHIA_SOUTH("UMUAHIA SOUTH","","",""),
		UMU_NNEOCHI("UMU NNEOCHI","","",""),
		UNGOGO("UNGOGO","","",""),
		UNUIMO("UNUIMO","","",""),
		URUAN("URUAN","","",""),
		URUE_OFFONG_ORUKO("URUE-OFFONG/ORUKO","","",""),
		USHONGO("USHONGO","","",""),
		USSA("USSA","","",""),
		UVWIE("UVWIE","","",""),
		UYO("UYO","","",""),
		UZO_UWANI("UZO UWANI","","",""),
		VANDEIKYA("VANDEIKYA","","",""),
		WAMAKO("WAMAKO","","",""),
		WAMBA("WAMBA","","",""),
		WARAWA("WARAWA","","",""),
		WARJI("WARJI","","",""),
		WARRI_NORTH("WARRI NORTH","","",""),
		WARRI_SOUTH("WARRI SOUTH","","",""),
		WARRI_SOUTH_WEST("WARRI SOUTH WEST","","",""),
		WASAGU_DANKO("WASAGU/DANKO","","",""),
		WASE("WASE","","",""),
		WUDIL("WUDIL","","",""),
		WUKARI("WUKARI","","",""),
		WURNO("WURNO","","",""),
		WUSHISHI("WUSHISHI","","",""),
		YABO("YABO","","",""),
		YAGBA_EAST("YAGBA EAST","","",""),
		YAGBA_WEST("YAGBA WEST","","",""),
		YAKUUR("YAKUUR","","",""),
		YALA("YALA","","",""),
		YAMALTU_DEBA("YAMALTU/DEBA","","",""),
		YANKWASHI("YANKWASHI","","",""),
		YAURI("YAURI","","",""),
		YENAGOA("YENAGOA","","",""),
		YOLA_NORTH("YOLA NORTH","","",""),
		YOLA_SOUTH("YOLA SOUTH","","",""),
		YORRO("YORRO","","",""),
		YUNUSARI("YUNUSARI","","",""),
		YUSUFARI("YUSUFARI","","",""),
		ZAKI("ZAKI","","",""),
		ZANGO("ZANGO","","",""),
		ZANGON_KATAF("ZANGON KATAF","","",""),
		ZARIA("ZARIA","","",""),
		ZING("ZING","","",""),
		ZURMI("ZURMI","","",""),
		ZURU("ZURU","","","");

		private StateLga(String name,String jtbName,String stateName,String taxOffice){
			this.name=name;
			this.jtbName=jtbName;
			this.stateName=stateName;
			this.taxOfficeName=taxOffice;
		}
		
		public String getName(){
			return name;
		}		
		public String getJtbName(){
			return jtbName;
		}
		public String getStateName(){
			return stateName;
		}
		public String getTaxOfficeName(){
			return taxOfficeName;
		}
		public String toString(){
			return name;
		}
		private String name;
		private String jtbName;
		private String stateName;
		private String taxOfficeName;
	}
	
	/*private enum TaxOffices{
		MSTO ABA
		MSTO ABAKALIKI
		MSTO ABEOKUTA
		MSTO ADO-EKITI
		Agege MSTO
		MSTO AJAH
		MSTO AKURE
		MSTO Alaba
		MSTO Alimosho
		MSTO Apapa
		MSTO Asaba
		MSTO Auchi
		MSTO Awka
		MSTO Azare
		MSTO Bauchi
		MSTO Benin
		MSTO Birnin Kebbi
		MSTO BROAD STREET
		MSTO Calabar
		MSTO Central Area
		MSTO Damaturu
		MSTO Dutse
		MSTO Enugu
		FCT/Special Tax Office
		MSTO Festac
		FUNTUA MSTO
		MSTO Garki
		MSTO Gombe
		MSTO Gusau
		MSTO Gwagwalada
		MSTO IKEJA
		MSTO IKOM
		MSTO IKORODU
		MSTO IKOYI
		MSTO ILORIN
		MSTO ILUPEJU
		MSTO ISOLO
		MSTO JALINGO
		MSTO JOS
		MSTO KADUNA I
		MSTO KADUNA II
		MSTO KANO I CLUB ROAD
		MSTO KANO II ZARIA ROAD
		MSTO KATSINA
		MSTO LAFIA
		MSTO LOKOJA
		LTO (Non-Oil) Lagos
		LTO (Oil & Gas) Lagos
		LTO Abuja
		LTO Kano
		LTO Port-Harcourt
		MSTO MAIDUGURI
		MSTO Makurdi
		MSTO Minna
		MSTO Nnewi
		MSTO Onikan
		MSTO Onitsha
		MSTO Ore
		MSTO Orlu
		MSTO Oshogbo
		MSTO Osisioma
		MSTO Otta
		MSTO Owerri
		MSTO Port Harcourt
		MSTO Rumuokwurushi
		MSTO Shagamu
		MSTO Sapele
		MSTO Sokoto
		MSTO Suleja
		MSTO Surulere
		MSTO Umuahia
		MSTO Utako
		MSTO Uyo
		MSTO Victoria Island
		MSTO Warri
		MSTO Wuse
		MSTO Yaba
		MSTO Yenegoa
		MSTO Yola
		MSTO Zaria
		LTO Lagos Oil And Gas (Upstream)
		LTO Lagos Oil And Gas (Downstream)
		LTO Lagos Oil And Gas (Services)
		LTO Lagos Non-Oil (Financial)
		LTO LAGOS NON-OIL (MANUFACTURING & OTHERS)
		GBTO KWARA
		GBTO NASARAWA
		GBTO PLATEAU
		GBTO KADUNA
		GBTO KANO
		GBTO KATSINA
		GBTO KEBBI
		GBTO SOKOTO
		MSTO Oyo
		MSTO ADEOYO
		MSTO IWO ROAD
		MSTO IKARE-AKOKO
		GBTO KOGI
		GBTO AKWA IBOM
		GBTO ZAMFARA
		GBTO ADAMAWA
		GBTO BAUCHI
		GBTO BORNO
		GBTO GOMBE
		GBTO YOBE
		GBTO LAGOS
		GBTO OGUN
		GBTO ONDO
		GBTO OYO
		GBTO ABIA
		GBTO ANAMBRA
		GBTO EBONYI
		GBTO ENUGU
		GBTO IMO
		STAMP DUTIES ABUJA
		STAMP DUTIES KADUNA
		STAMP DUTIES KANO
		STAMP DUTIES SOKOTO
		STAMP DUTIES YOLA
		STAMP DUTIES ABEOKUTA
		STAMP DUTIES ILORIN
		STAMP DUTIES UMUAHIA
		MSTO AGUDA
		IETO ASOKORO
		MSTO ASOKORO
		MSTO BAR-BEACH
		IETO CENTRAL
		IETO GARKI
		GBTO Bayelsa
		GBTO Benue
		GBTO Cross-Rivers
		GBTO Ekiti
		GBTO Jigawa
		GBTO Niger
		GBTO Osun
		GBTO Taraba
		IETO Gwagwalada
		GBTO DELTA
		STAMP DUTY ILORIN
		ILUPEJU 2
		MSTO KAFANCHAN
		IETO MAITAMA
		MSTO Maitama
		MTO Abuja
		MTO Enugu
		MTO Ibadan
		MTO Kano
		MTO Lagos Island
		MTO Lagos Mainland
		MTO Portharcourt
		MSTO MUBI
		GBTO EDO
		MSTO Onigbongbo
		MSTO Orile-Iganmu
		STAMP DUTIES Ado Ekiti
		STAMP DUTIES Akure
		STAMP DUTIES Asaba
		STAMP DUTIES Bauchi
		STAMP DUTIES Benin
		STAMP DUTIES Calabar
		STAMP DUTIES Enugu
		STAMP DUTIES Ibadan
		STAMP DUTIES Ikeja
		STAMP DUTIES Katsina
		STAMP DUTIES Lagos
		STAMP DUTIES Lokoja
		STAMP DUTIES Maiduguri
		STAMP DUTIES Makurdi
		STAMP DUTIES Owerri
		STAMP DUTIES Port Harcourt
		IETO Utako
		IETO Wuse
		GBTO RIVERS
		GBTO FCT
		LTO IBADAN
		MTO LAGOS MAINLAND WEST
		MSTO LUGBE
		MTO WARRI
		MSTO KATSINA ROAD
		MSTO RUMUEME

	}*/
	
	
}
