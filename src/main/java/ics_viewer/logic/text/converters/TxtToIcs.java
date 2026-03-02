package ics_viewer.logic.text.converters;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ics_viewer.logic.text.DateTools;
import ics_viewer.logic.text.Tools;
import ics_viewer.logic.text.models.TxtCalendarEvent;
import ics_viewer.logic.text.models.TxtEventDateLine;
import ics_viewer.logic.text.models.TxtEventRecurrence;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Month;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.immutable.ImmutableCalScale;
import net.fortuna.ical4j.model.property.immutable.ImmutableVersion;
import net.fortuna.ical4j.transform.recurrence.Frequency;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

public class TxtToIcs {

	static final Map<String, Frequency> TXT_TO_FREQUENCY = new LinkedHashMap<>();
	static {
		TXT_TO_FREQUENCY.put("day", Frequency.DAILY);
		TXT_TO_FREQUENCY.put("days", Frequency.DAILY);
		TXT_TO_FREQUENCY.put("week", Frequency.WEEKLY);
		TXT_TO_FREQUENCY.put("weeks", Frequency.WEEKLY);
		TXT_TO_FREQUENCY.put("month", Frequency.MONTHLY);
		TXT_TO_FREQUENCY.put("months", Frequency.MONTHLY);
		TXT_TO_FREQUENCY.put("year", Frequency.YEARLY);
		TXT_TO_FREQUENCY.put("years", Frequency.YEARLY);
	}
	private static final Pattern TXT_OCCURRENCES_PATTERN = Pattern.compile("(\\d+)\\s+\\w+");
	private static final Pattern TXT_DAY_PATTERN = Pattern.compile("(?:every\\s+(\\w+)\\s+(\\w+)|on\\s(\\w+)).*");
	private static final TimeZoneRegistry REGISTRY = TimeZoneRegistryFactory.getInstance().createRegistry();
	private static final TimeZone TIMEZONE = REGISTRY.getTimeZone(DateTools.MY_TIME_ZONE_NAME);
	private static final VTimeZone V_TIME_ZONE = TIMEZONE.getVTimeZone();
	private static final UidGenerator UID_GENERATOR = new RandomUidGenerator();
	
	private final Calendar calendar = createCalendar();

	public void addTxtEvent(TxtCalendarEvent txtEvent) {
		TxtEventDateLine dateLine = txtEvent.getDateLine();
		
		// Create the event
		String eventName = txtEvent.getTitle();
		ZonedDateTime start = DateTools.parseTxtDateWithOrWithoutTime(dateLine.getFrom());
		if (start != null) {
			ZonedDateTime end;
			String to = dateLine.getTo();
			if (to == null) {
				end = null;
			} else {
				end = DateTools.parseTxtDateWithOrWithoutTime(to);
			}
			VEvent meeting;
			if (end == null) {
				meeting = new VEvent(start, eventName);
			} else {
				meeting = new VEvent(start, end, eventName);
			}
			
			String notes = txtEvent.getNotes();
			if (notes != null) {
				Description description = new Description(notes);
				meeting.add(description);
			}
			
			TxtEventRecurrence recurrence = dateLine.getRecurrence();
			if (recurrence != null) {
				addRecurrence(start, meeting, recurrence);
			}
	
			// add timezone info..
			meeting.add(V_TIME_ZONE.getTimeZoneId());
	
			// generate unique identifier..
			Uid uid = UID_GENERATOR.generateUid();
			meeting.add(uid);
			
			this.calendar.add(meeting);
		}
	}
	
	public Calendar getCalendar() {
		return calendar;
	}

	private static Calendar createCalendar() {
		Calendar calendar = new Calendar();
		calendar.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		calendar.add(ImmutableVersion.VERSION_2_0);
		calendar.add(ImmutableCalScale.GREGORIAN);
		return calendar;
	}
	
	private static WeekDay dayOfWeekToIcs(DayOfWeek dayOfWeek) {
		WeekDay result;
		if (dayOfWeek == null) {
			result = null;
		} else {
			result = WeekDay.getWeekDay(dayOfWeek);
		}
		return result;
	}
	
	private static Month monthToIcs(java.time.Month javaMonth) {
		int monthNumber = javaMonth.getValue();
		return new Month(monthNumber);
	}
	
	private static void addRecurrence(ZonedDateTime start, VEvent meeting, TxtEventRecurrence txtRecurrence) {
		Integer txtValue = txtRecurrence.getRecurrenceValue();
		String txtUnit = txtRecurrence.getRecurrenceUnit();
		String txtDay = txtRecurrence.getRecurrenceDay();
		String txtUntil = txtRecurrence.getRecurrenceUntil();
		Frequency frequency = TXT_TO_FREQUENCY.get(txtUnit);
		RRule<Temporal> rRule;
		if (frequency == null) {
			rRule = null;
		} else {
			Recur.Builder<Temporal> builder = new Recur.Builder<Temporal>();
			builder.frequency(frequency);
			if (txtValue != null) {
				builder.interval(txtValue);
			}
			Matcher occurrencesMatcher;
			ZonedDateTime untilDate = DateTools.parseTxtDate(txtUntil);
			if (txtUntil == null) {
				occurrencesMatcher = null;
			} else {
				occurrencesMatcher = TXT_OCCURRENCES_PATTERN.matcher(txtUntil);
			}
			if (occurrencesMatcher != null && occurrencesMatcher.matches()) {
				Integer occurrencesValue = Tools.toInteger(occurrencesMatcher.group(1));
				builder.count(occurrencesValue);
			} else if (untilDate != null) {
				builder.until(untilDate);
			}
			if (txtDay != null) {
				Matcher dayMatcher = TXT_DAY_PATTERN.matcher(txtDay);
				Integer everyOrdinal;
				WeekDay everyDayOfWeek, onDayOfWeek;
				if (dayMatcher.matches()) {
					everyOrdinal = Tools.parseOrdinalString(dayMatcher.group(1));
					everyDayOfWeek = dayOfWeekToIcs(Tools.parseDayOfWeek(dayMatcher.group(2)));
					onDayOfWeek = dayOfWeekToIcs(Tools.parseDayOfWeek(dayMatcher.group(3)));
				} else {
					everyOrdinal = null;
					everyDayOfWeek = null;
					onDayOfWeek = null;
				}
				if (everyOrdinal != null && everyDayOfWeek != null) {
					WeekDay weekDayWithOffset = new WeekDay(everyDayOfWeek, everyOrdinal);
					builder.dayList(weekDayWithOffset);
				} else if (onDayOfWeek != null) {
					builder.dayList(onDayOfWeek);
				} else if (frequency == Frequency.YEARLY) {
					// Events created on the calendar app as yearly for the same day are
					// interpreted in ICS as recurring yearly by month.
					builder.monthList(Collections.singletonList(monthToIcs(start.getMonth())));
				}
			}
			Recur<Temporal> recur = builder.build();
			rRule = new RRule<>(recur);
		}
		if (rRule != null) {
			meeting.add(rRule);
		}
	}
}
