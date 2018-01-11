package net.oasismgt.tin_service_generation.local.service.util.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.oasismgt.tin_service_generation.local.service.util.DateTimeUtilities;

@Service
@Transactional
public class DateTimeUtilitiesImpl implements DateTimeUtilities  {

	private static final long serialVersionUID = 5286775477864943164L;
	
	@Override
	public LocalDateTime getDateTime() {
		return LocalDateTime.now(getTimeZone());
	}
	@Override
	public LocalDate getDate() {
		return  LocalDate.now(getTimeZone());
	}
	@Override
	public String getDateTime(LocalDateTime dt) {		
		return  dt.format(getDateTimeFormatter());
	}
	@Override
	public String getDate(LocalDate d) {
		return d.format(getDateFormatter());
	}
	@Override
	public LocalDateTime getDateTime(String dt) {
		return LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
	}
	@Override
	public LocalDate getDate(String d) {
		return LocalDate.parse(d, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	@Override
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
	}
	@Override
	public DateTimeFormatter getDateFormatter() {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}
	@Override
	public ZoneOffset getTimeZone() {
		return ZoneOffset.of("+1");
	}
	
	
}
