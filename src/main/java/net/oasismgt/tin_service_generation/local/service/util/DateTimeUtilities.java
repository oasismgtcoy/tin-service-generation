package net.oasismgt.tin_service_generation.local.service.util;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public interface DateTimeUtilities extends Serializable  {

	public ZoneOffset getTimeZone();
	public LocalDateTime getDateTime();
	public LocalDate getDate();
	public String getDateTime(LocalDateTime dt);
	public String getDate(LocalDate d);
	public LocalDateTime getDateTime(String dt);
	public LocalDate getDate(String d);
	public DateTimeFormatter getDateTimeFormatter();
	public DateTimeFormatter getDateFormatter();
	
}
