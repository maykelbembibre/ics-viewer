package ics_viewer.logic.text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class for formatting dates and times using the formats of calendar
 * TXT files.
 */
public class TxtDateFormatter {

	private static final DateTimeFormatter TXT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter TXT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	
	private final ZoneId zoneId;
	
	/**
	 * Creates a new formatter for dates and times of TXT calendar files.
	 * @param zoneId The time zone this formatter will work with.
	 */
	public TxtDateFormatter(ZoneId zoneId) {
		this.zoneId = zoneId;
	}
	
	public ZonedDateTime parseTxtDate(String dateString) {
		ZonedDateTime result = null;
		if (dateString == null) {
			result = null;
		} else {
			try {
				LocalDate localDate = LocalDate.parse(dateString, TXT_DATE_FORMATTER);
				result = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, this.zoneId);
			} catch (DateTimeParseException e) {
				result = null;
			}
		}
		return result;
	}
	
	public String formatTxtDate(ZonedDateTime zonedDateTime) {
		String result;
		if (zonedDateTime == null) {
			result = null;
		} else {
			result = TXT_DATE_FORMATTER.format(zonedDateTime);
		}
		return result;
	}
	
	public ZonedDateTime parseTxtDateTime(String dateTimeString) {
		ZonedDateTime result = null;
		if (dateTimeString == null) {
			result = null;
		} else {
			try {
				LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, TXT_DATE_TIME_FORMATTER);
				result = ZonedDateTime.of(localDateTime, this.zoneId);
			} catch (DateTimeParseException e) {
				result = null;
			}
		}
		return result;
	}
	
	public String formatTxtDateTime(ZonedDateTime zonedDateTime) {
		String result;
		if (zonedDateTime == null) {
			result = null;
		} else {
			result = TXT_DATE_TIME_FORMATTER.format(zonedDateTime);
		}
		return result;
	}
	
	public ZonedDateTime parseTxtDateWithOrWithoutTime(String dateWithOrWithoutTimeString) {
		ZonedDateTime result = parseTxtDate(dateWithOrWithoutTimeString);
		if (result == null) {
			result = parseTxtDateTime(dateWithOrWithoutTimeString);
		}
		return result;
	}
	
	public String formatTxtDateWithOrWithoutTime(ZonedDateTime zonedDateTime) {
		String result;
		if (zonedDateTime == null) {
			result = null;
		} else {
			int hour = zonedDateTime.getHour();
			int minute = zonedDateTime.getMinute();
			int second = zonedDateTime.getSecond();
			int nano = zonedDateTime.getNano();
			if (hour == 0 && minute == 0 && second == 0 && nano == 0) {
				result = formatTxtDate(zonedDateTime);
			} else {
				result = formatTxtDateTime(zonedDateTime);
			}
		}
		return result;
	}
}
