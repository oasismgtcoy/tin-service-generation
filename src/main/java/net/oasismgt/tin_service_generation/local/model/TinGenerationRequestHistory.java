package net.oasismgt.tin_service_generation.local.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.oasismgt.tin_service_generation.local.backing.MessageStatus;

@Entity
@Table(name="tin_generation_request_history")
public class TinGenerationRequestHistory implements Serializable  {
	
	private static final long serialVersionUID = 1062361297980496659L;
	
	private Long id;
	private String rcno;
	private String mda_transaction_id;
	private String taxservice_transaction_id;
	private String name_of_organization;
	private String tin;
	private String requestSource;
		
	private CorporateTinGenerationRequest corporateTinGenerationRequest;
	private MessageStatus messageStatus;
	
	private String issuanceDate;
	private LocalDateTime tinResponseDate;
	private LocalDateTime dateRequested;
	private LocalDateTime dateOfArrivalFromSourceMda;
	private LocalDateTime notificationDepartureDate;
	private LocalDateTime dateOfDepartureToJtb;
	private LocalDateTime dateOfWriteToJtbFolder;
	private LocalDateTime dateOfReadFromJtbFolder;
	private LocalDateTime dateOfArrivalFromJtb;
	private LocalDateTime lastUpdatedDate;
	
	public TinGenerationRequestHistory(CorporateTinGenerationRequest corporateTinGenerationRequest){
		this.corporateTinGenerationRequest=corporateTinGenerationRequest;
		setRcno(corporateTinGenerationRequest.getRcno());
		setMda_transaction_id(corporateTinGenerationRequest.getMda_transaction_id());
	}
	
	public TinGenerationRequestHistory(){}
	
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
	public LocalDateTime getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(LocalDateTime dateRequested) {
		this.dateRequested = dateRequested;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(this==obj)return true;
		if(!(obj instanceof TinGenerationRequestHistory))
			return false;
		return this.getRcno().equals(((TinGenerationRequestHistory)obj).getRcno());
	}
	@Override
	public int hashCode() {
		return rcno.hashCode();
	}
	@OneToOne(cascade=CascadeType.ALL,optional=false,orphanRemoval=true)
	public CorporateTinGenerationRequest getCorporateTinGenerationRequest() {
		return corporateTinGenerationRequest;
	}
	public void setCorporateTinGenerationRequest(CorporateTinGenerationRequest corporateTinGenerationRequest) {
		this.corporateTinGenerationRequest = corporateTinGenerationRequest;
	}
	public LocalDateTime getTinResponseDate() {
		return tinResponseDate;
	}
	public void setTinResponseDate(LocalDateTime tinResponseDate) {
		this.tinResponseDate = tinResponseDate;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getMda_transaction_id() {
		return mda_transaction_id;
	}
	public void setMda_transaction_id(String mda_transaction_id) {
		this.mda_transaction_id = mda_transaction_id;
	}
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	@Enumerated(EnumType.STRING)
	public MessageStatus getMessageStatus() {
		if(messageStatus==null)
			return MessageStatus.NOT_SENT;
		return messageStatus;
	}
	public void setMessageStatus(MessageStatus processingStatus) {
		this.messageStatus = processingStatus;
	}
	public String getTaxservice_transaction_id() {
		return taxservice_transaction_id;
	}
	public void setTaxservice_transaction_id(String taxservice_transaction_id) {
		this.taxservice_transaction_id = taxservice_transaction_id;
	}
	public String getName_of_organization() {
		return name_of_organization;
	}
	public void setName_of_organization(String name_of_organization) {
		this.name_of_organization = name_of_organization;
	}
	public String getIssuanceDate() {
		return issuanceDate;
	}
	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}
	@Transient
	public String getRequestedWhen(){		
		return dateRequested.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
	}	
	public LocalDateTime getNotificationDepartureDate() {
		if(notificationDepartureDate==null)return tinResponseDate;
		return notificationDepartureDate;
	}
	public void setNotificationDepartureDate(LocalDateTime notificationDepartureDate) {
		this.notificationDepartureDate = notificationDepartureDate;
	}
	public LocalDateTime getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public LocalDateTime getDateOfArrivalFromSourceMda() {
		if(dateOfArrivalFromSourceMda==null)return dateRequested;
		return dateOfArrivalFromSourceMda;
	}
	public void setDateOfArrivalFromSourceMda(LocalDateTime dateOfArrivalFromSourceMda) {
		this.dateOfArrivalFromSourceMda = dateOfArrivalFromSourceMda;
	}
	public LocalDateTime getDateOfDepartureToJtb() {
		if(dateOfDepartureToJtb == null && ( MessageStatus.HAS_TIN.equals(messageStatus) || MessageStatus.RECEPT_ACKNOWLEDGED.equals(messageStatus) ) )			
			return getDateOfArrivalFromSourceMda();		
		return dateOfDepartureToJtb;
	}
	public void setDateOfDepartureToJtb(LocalDateTime dateOfDepartureToJtb) {
		this.dateOfDepartureToJtb = dateOfDepartureToJtb;
	}
	public LocalDateTime getDateOfArrivalFromJtb() {
		if(dateOfArrivalFromJtb==null)return tinResponseDate;
		return dateOfArrivalFromJtb;
	}
	public void setDateOfArrivalFromJtb(LocalDateTime dateOfArrivalFromJtb) {
		this.dateOfArrivalFromJtb = dateOfArrivalFromJtb;
	}

	@Transient
	public String formatDateTime(LocalDateTime dateTime){	
		if(dateTime==null)return "N/A";
		return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
	}
	@Transient
	public String getDifference(LocalDateTime from,LocalDateTime to){	
		if(from==null || to == null)return "N/A";
				
		Period period = Period.between(from.toLocalDate(), to.toLocalDate());
		StringBuilder sb=new StringBuilder();
		
		if(period.getYears()>0)
			sb.append(period.getYears()).append(" Yr(s), ");
		if(period.getMonths()>0)
			sb.append(period.getMonths()).append(" Mth(s), ");
		if(period.getDays()>0)
			sb.append(period.getDays()).append(" Day(s), ");
		
		LocalTime fromTime=from.toLocalTime();
		LocalTime toTime=to.toLocalTime();
		
		int seconds = Math.abs(toTime.getSecond() - fromTime.getSecond());
		int minute = Math.abs(toTime.getMinute() - fromTime.getMinute());
		int hours = Math.abs(toTime.getHour() - fromTime.getHour());
				if(hours>0)
			sb.append(hours).append(" Hr(s), ");
		if(minute>0)
			sb.append(minute).append(" Min(s), ");
		if(seconds>0)
			sb.append(seconds).append(" Sec(s).");
		
		if(sb.toString()==null||sb.toString().trim().isEmpty())
		return "Seamless";
		return sb.toString();
	}
	
	@Transient
	public String getSumDifference(LocalDateTime from,LocalDateTime to){	
		if(from==null || to == null)return "N/A";
				
		Period period = Period.between(from.toLocalDate(), to.toLocalDate());
		StringBuilder sb=new StringBuilder();
		
		if(period.getYears()>0)
			sb.append(period.getYears()).append(" Y,");
		if(period.getMonths()>0)
			sb.append(period.getMonths()).append(" M,");
		if(period.getDays()>0)
			sb.append(period.getDays()).append(" D,");
		
		LocalTime fromTime=from.toLocalTime();
		LocalTime toTime=to.toLocalTime();
		
		int seconds = Math.abs(toTime.getSecond() - fromTime.getSecond());
		int minute = Math.abs(toTime.getMinute() - fromTime.getMinute());
		int hours = Math.abs(toTime.getHour() - fromTime.getHour());
				if(hours>0)
			sb.append(hours).append(" H,");
		if(minute>0)
			sb.append(minute).append(" M,");
		if(seconds>0)
			sb.append(seconds).append(" S");
		
		if(sb.toString()==null||sb.toString().trim().isEmpty())
		return "Seamless";
		return sb.toString();
	}
	
	
	/*@Transient
    public String getDiffInMinutes(LocalDateTime from,LocalDateTime to){		
		Long diffInMinutes = ChronoUnit.MINUTES.between(from, to);		
		return diffInMinutes.toString();
	}
	*/
	
	@Transient
	public Long getDiffInMinutes(LocalDateTime from,LocalDateTime to){	
		//if(from==null || to == null)return "N/A";
				
		Period period = Period.between(from.toLocalDate(), to.toLocalDate());
		LocalTime fromTime=from.toLocalTime();
		LocalTime toTime=to.toLocalTime();
		
		int minute = Math.abs(toTime.getMinute() - fromTime.getMinute());
		int hours = Math.abs(toTime.getHour() - fromTime.getHour());
		int days = period.getDays();
	
		int minutes = (days * 1440) + (hours * 60) + minute ;
		
		return  (long) minutes ;
	}
	
	
	
	@Transient
    public Long getAverageTime(Long sumDuration, Long numberOfRecords){
		Long average = sumDuration/numberOfRecords;
		return average;
	}
	
	@Transient
    public Double getSystemEfficiency(double duration){		    
		
		   Double efficiency	 = (250/duration) * 100;			 	
		
	 	    return efficiency;
	
	}
	
	@Transient
    public Double getOverallEfficiency(double sumEfficiency, double numberOfRecords){
		Double overall =  sumEfficiency/numberOfRecords;
		return overall;
	}
	@Transient
	public String timeConvert(Long time){
		
		   String t = "";

		  Long days = time/(24*60);
		  Long hours = (time%(24*60)) / 60;
		  Long minutes = (time%(24*60)) % 60;



		   t =days + " D " + hours + " H " + minutes  + " M ";
		   return t;
		 }
	@Transient
    public String getSLA(Double efficiency){
		String sla = null;
		if(efficiency > 70){
			sla = "within SLA";
		}
		else if (efficiency < 70){			
			sla = "Out of SLA";
		}
		return sla;
	}
	
	@Transient
	public String getDifference(LocalDate from,LocalDate to){	
		if(from==null || to == null)return "N/A";
		Period period = Period.between(from, to);
		StringBuilder sb=new StringBuilder();
		if(period.getYears()>0)
			sb.append(period.getYears()).append("Year(s), ");
		if(period.getMonths()>0)
			sb.append(period.getMonths()).append("Month(s), ");
		if(period.getDays()>0)
			sb.append(period.getDays()).append("Day(s), ");
		return "";
	}

	public LocalDateTime getDateOfWriteToJtbFolder() {
		if(dateOfWriteToJtbFolder == null && ( MessageStatus.HAS_TIN.equals(messageStatus) || MessageStatus.RECEPT_ACKNOWLEDGED.equals(messageStatus) ) )			
			return getDateOfDepartureToJtb();		
		return dateOfWriteToJtbFolder;
	}

	public void setDateOfWriteToJtbFolder(LocalDateTime dateOfWriteToJtbFolder) {
		this.dateOfWriteToJtbFolder = dateOfWriteToJtbFolder;
	}

	public LocalDateTime getDateOfReadFromJtbFolder() {
		if(dateOfReadFromJtbFolder == null && ( MessageStatus.HAS_TIN.equals(messageStatus)) )			
			return getDateOfArrivalFromJtb();	
		return dateOfReadFromJtbFolder;
	}

	public void setDateOfReadFromJtbFolder(LocalDateTime dateOfReadFromJtbFolder) {
		this.dateOfReadFromJtbFolder = dateOfReadFromJtbFolder;
	}

	
	
}