package ics_viewer.logic.text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTools {

	public static final String MY_TIME_ZONE_NAME = "Europe/Madrid";
	public static final ZoneId MY_ZONE_ID = ZoneId.of(MY_TIME_ZONE_NAME);
	private static final DateTimeFormatter TXT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter TXT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	public static ZonedDateTime parseTxtDate(String dateString) {
		ZonedDateTime result = null;
		if (dateString == null) {
			result = null;
		} else {
			try {
				LocalDate localDate = LocalDate.parse(dateString, TXT_DATE_FORMATTER);
				result = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, MY_ZONE_ID);
			} catch (DateTimeParseException e) {
				result = null;
			}
		}
		return result;
	}
	
	public static String formatTxtDate(ZonedDateTime zonedDateTime) {
		return TXT_DATE_FORMATTER.format(zonedDateTime);
	}
	
	public static ZonedDateTime parseTxtDateTime(String dateTimeString) {
		ZonedDateTime result = null;
		if (dateTimeString == null) {
			result = null;
		} else {
			try {
				LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, TXT_DATE_TIME_FORMATTER);
				result = ZonedDateTime.of(localDateTime, MY_ZONE_ID);
			} catch (DateTimeParseException e) {
				result = null;
			}
		}
		return result;
	}
	
	public static String formatTxtDateTime(ZonedDateTime zonedDateTime) {
		return TXT_DATE_TIME_FORMATTER.format(zonedDateTime);
	}
	
	public static ZonedDateTime parseTxtDateWithOrWithoutTime(String dateWithOrWithoutTimeString) {
		ZonedDateTime result = parseTxtDate(dateWithOrWithoutTimeString);
		if (result == null) {
			result = parseTxtDateTime(dateWithOrWithoutTimeString);
		}
		return result;
	}
	
	public static String formatTxtDateWithOrWithoutTime(ZonedDateTime zonedDateTime) {
		String result;
		int hour = zonedDateTime.getHour();
		int minute = zonedDateTime.getMinute();
		int second = zonedDateTime.getSecond();
		int nano = zonedDateTime.getNano();
		if (hour == 0 && minute == 0 && second == 0 && nano == 0) {
			result = formatTxtDate(zonedDateTime);
		} else {
			result = formatTxtDateTime(zonedDateTime);
		}
		return result;
	}
}
