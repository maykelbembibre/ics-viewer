package ics_viewer.logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Optional;

import net.fortuna.ical4j.model.property.DateProperty;

public class DateOperations {

	public static Optional<ZonedDateTime> getZonedDateTime(DateProperty<Temporal> dateProperty) {
		Optional<ZonedDateTime> result;
		if (dateProperty == null) {
			result = Optional.empty();
		} else {
			Temporal temporal = dateProperty.getDate();
			if (temporal instanceof ZonedDateTime) {
				result = Optional.of((ZonedDateTime) temporal);
			} else if (temporal instanceof LocalDate) {
				LocalDateTime localDateTime = getLocalDateTime((LocalDate) temporal);
				result = Optional.of(getZonedDateTime(localDateTime));
			} else if (temporal instanceof LocalDateTime) {
				result = Optional.of(getZonedDateTime((LocalDateTime) temporal));
			} else if (temporal instanceof OffsetDateTime) {
				OffsetDateTime offsetDateTime = (OffsetDateTime) temporal;
				result = Optional.of(offsetDateTime.atZoneSameInstant(getCurrentZone()));
			} else {
				result = Optional.empty();
			}
		}
		return result;
	}
	
	public static String formatUserDateTime(ZonedDateTime zonedDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return zonedDateTime.format(formatter);
	}
	
	public static ZoneId getCurrentZone() {
		ZonedDateTime now = ZonedDateTime.now();
        return now.getZone();
	}
	
	private static LocalDateTime getLocalDateTime(LocalDate localDate) {
		LocalTime localTime = LocalTime.of(0, 0);
		return LocalDateTime.of(localDate, localTime);
	}
	
	private static ZonedDateTime getZonedDateTime(LocalDateTime localDateTime) {
		ZoneId zoneId = getCurrentZone();
		return ZonedDateTime.of(localDateTime, zoneId);
	}
}
