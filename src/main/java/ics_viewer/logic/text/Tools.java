package ics_viewer.logic.text;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ibm.icu.text.RuleBasedNumberFormat;

import net.fortuna.ical4j.model.WeekDay;

public class Tools {

	public static final Locale TXT_FILE_LOCALE = Locale.ENGLISH;
	private static final RuleBasedNumberFormat ORDINAL_STRING_FORMATTER = new RuleBasedNumberFormat(TXT_FILE_LOCALE, RuleBasedNumberFormat.SPELLOUT );
	
	public static Integer toInteger(String string) {
		Integer result;
		if (string == null) {
			result = null;
		} else {
			try {
				result = Integer.valueOf(string);
			} catch (NumberFormatException e) {
				result = null;
			}
		}
		return result;
	}
	
	public static <V, K> Map<V, K> invert(Map<K, V> map) {
	    Map<V, K> inversedMap = new HashMap<V, K>();
	    for (Entry<K, V> entry : map.entrySet()) {
	    	if (!inversedMap.containsKey(entry.getValue())) {
	    		inversedMap.put(entry.getValue(), entry.getKey());
	    	}
	    }
	    return inversedMap;
	}
	
	public static Date parseDate(String dateAsString, DateFormat dateFormat) {
		Date date;
		if (dateAsString == null) {
			date = null;
		} else {
			try {
				date = dateFormat.parse(dateAsString);
			} catch (ParseException e) {
				date = null;
			}
		}
		return date;
	}
	
	public static DayOfWeek parseDayOfWeek(String input) {
		DayOfWeek day;
		if (input == null) {
			day = null;
		} else {
			Set<DayOfWeek> matches = new HashSet<>();
			String name;
			String lowerCaseInput = input.toLowerCase();
			for (DayOfWeek value : DayOfWeek.values()) {
				name = value.getDisplayName(TextStyle.FULL, TXT_FILE_LOCALE).toLowerCase();
	    		if (name.startsWith(lowerCaseInput)) {
	    			matches.add(value);
	    		}
	    	}
			if (matches.size() == 1) {
				day = matches.iterator().next();
			} else {
				day = null;
			}
		}
		return day;
	}
	
	public static String formatDaysOfWeek(Collection<WeekDay> weekDays) {
		StringBuilder resultStringBuilder = new StringBuilder();
		if (weekDays != null && !weekDays.isEmpty()) {
			String dayOfWeekName;
			DayOfWeek dayOfWeek;
			int offset;
			List<String> daysWithoutOffset = new ArrayList<>();
			List<String> daysWithOffset = new ArrayList<>();
			for (WeekDay weekDay : weekDays) {
				dayOfWeek = icsToDayOfWeek(weekDay);
				dayOfWeekName = dayOfWeek.getDisplayName(TextStyle.FULL, TXT_FILE_LOCALE);
				offset = weekDay.getOffset();
				if (offset == 0) {
					daysWithoutOffset.add(dayOfWeekName);
				} else {
					daysWithOffset.add("every " + formatOrdinalString(offset) + " " + dayOfWeekName);
				}
			}
			if (!daysWithoutOffset.isEmpty()) {
				resultStringBuilder.append("on " + String.join(", ", daysWithoutOffset));
			} else {
				resultStringBuilder.append(String.join(", ", daysWithOffset));
			}
		}
		String result;
		if (resultStringBuilder.isEmpty()) {
			result = null;
		} else {
			result = resultStringBuilder.toString();
		}
		return result;
	}
	
	public static Integer parseOrdinalString(String ordinalString) {
		Integer result;
		if (ordinalString == null) {
			result = null;
		} else {
			try {
			    result = ORDINAL_STRING_FORMATTER.parse(ordinalString.toLowerCase(Locale.ROOT)).intValue();
			} catch (ParseException e) {
			    result = null;
			}
		}
		return result;
	}
	
	public static String formatOrdinalString(Integer number) {
		String result;
		if (number == null) {
			result = null;
		} else {
		    result = ORDINAL_STRING_FORMATTER.format(number, "%spellout-ordinal");
		}
		return result;
	}
	
	private static DayOfWeek icsToDayOfWeek(WeekDay weekDay) {
		DayOfWeek dayOfWeek;
		if (weekDay == null) {
			dayOfWeek = null;
		} else {
			dayOfWeek = WeekDay.getDayOfWeek(weekDay);
		}
		return dayOfWeek;
	}
}
