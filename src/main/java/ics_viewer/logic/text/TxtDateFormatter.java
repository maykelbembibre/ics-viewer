package ics_viewer.logic.text;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;

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
	
	public LocalDate parseTxtDate(String dateString) {
		LocalDate result = null;
		if (dateString == null) {
			result = null;
		} else {
			try {
				result = LocalDate.parse(dateString, TXT_DATE_FORMATTER);
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
	
	public Temporal parseTxtDateWithOrWithoutTime(String dateWithOrWithoutTimeString) {
		Temporal result = parseTxtDate(dateWithOrWithoutTimeString);
		if (result == null) {
			result = parseTxtDateTime(dateWithOrWithoutTimeString);
		}
		return result;
	}
	
	public boolean hasTime(ZonedDateTime zonedDateTime) {
		int hour = zonedDateTime.getHour();
		int minute = zonedDateTime.getMinute();
		int second = zonedDateTime.getSecond();
		int nano = zonedDateTime.getNano();
		return hour != 0 || minute != 0 || second != 0 || nano != 0;
	}
	
	public String formatTxtDateWithOrWithoutTime(ZonedDateTime zonedDateTime) {
		String result;
		if (zonedDateTime == null) {
			result = null;
		} else {
			if (this.hasTime(zonedDateTime)) {
				result = formatTxtDateTime(zonedDateTime);
			} else {
				result = formatTxtDate(zonedDateTime);
			}
		}
		return result;
	}
}
