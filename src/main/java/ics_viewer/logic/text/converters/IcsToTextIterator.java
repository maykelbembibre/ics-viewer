package ics_viewer.logic.text.converters;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import ics_viewer.logic.DateOperations;
import ics_viewer.logic.text.TxtDateFormatter;
import ics_viewer.logic.text.Tools;
import ics_viewer.logic.text.models.TxtCalendarEvent;
import ics_viewer.logic.text.models.TxtEventDateLine;
import ics_viewer.logic.text.models.TxtEventRecurrence;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DateProperty;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.transform.recurrence.Frequency;

public class IcsToTextIterator implements Iterator<TxtCalendarEvent> {

	private static final Map<Frequency, String> FREQUENCY_TO_TXT = Tools.invert(TxtToIcs.TXT_TO_FREQUENCY);
	private static final Set<Frequency> ALWAYS_HAVE_DAY = new HashSet<>();
	static {
		ALWAYS_HAVE_DAY.add(Frequency.MONTHLY);
		ALWAYS_HAVE_DAY.add(Frequency.YEARLY);
	}
	private final VEventIterator vEventIterator;
	private final TxtDateFormatter txtDateFormatter;
	private TxtCalendarEvent nextTxtCalendarEvent;
	
	public IcsToTextIterator(ZoneId zoneId, VEventIterator vEventIterator) {
		this.vEventIterator = vEventIterator;
		this.txtDateFormatter = new TxtDateFormatter(zoneId);
	}

	@Override
	public boolean hasNext() {
		while (this.nextTxtCalendarEvent == null && this.vEventIterator.hasNext()) {
			this.nextTxtCalendarEvent = convert(this.vEventIterator.next());
		}
		return this.nextTxtCalendarEvent != null;
	}

	@Override
	public TxtCalendarEvent next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		TxtCalendarEvent result = this.nextTxtCalendarEvent;
		this.nextTxtCalendarEvent = null;
		return result;
	}
	
	private static ZonedDateTime toZonedDateTime(DateProperty<Temporal> dateProperty) {
		return DateOperations.getZonedDateTime(dateProperty).orElse(null);
	}
	
	private TxtCalendarEvent convert(VEvent vEvent) {
		Optional<Property> opRRuleProp = vEvent.getProperty(Property.RRULE);
		TxtEventRecurrence recurrence;
		if (opRRuleProp.isPresent() && opRRuleProp.get() instanceof RRule) {
			RRule<?> rRule = (RRule<?>) opRRuleProp.get();
			Recur<?> recur = rRule.getRecur();
			int count = recur.getCount();
			Temporal until = recur.getUntil();
			Frequency frequency = recur.getFrequency();
			int interval = recur.getInterval();
			List<WeekDay> dayList = recur.getDayList();
			Integer recurrenceValue;
			String recurrenceUnit;
			String recurrenceDay;
			String recurrenceUntil;
			if (interval > -1) {
				recurrenceValue = interval;
			} else {
				recurrenceValue = null;
			}
			recurrenceUnit = FREQUENCY_TO_TXT.get(frequency);
			if (recurrenceUnit == null) {
				recurrence = null;
			} else {
				if (recurrenceValue != null && recurrenceValue != 1) {
					recurrenceUnit = recurrenceUnit + "s";
				}
				recurrenceDay = Tools.formatDaysOfWeek(dayList);
				if (recurrenceDay == null && ALWAYS_HAVE_DAY.contains(frequency)) {
					recurrenceDay = "on the same day";
				}
				if (count != -1) {
					recurrenceUntil = count + " occurrences";
				} else if (until instanceof ZonedDateTime) {
					ZonedDateTime zonedDateTime = (ZonedDateTime) until;
					recurrenceUntil = this.txtDateFormatter.formatTxtDate(zonedDateTime);
				} else {
					recurrenceUntil = null;
				}
				recurrence = new TxtEventRecurrence(recurrenceValue, recurrenceUnit, recurrenceDay, recurrenceUntil);
			}
		} else {
			recurrence = null;
		}
		DtStart<Temporal> dtStart = vEvent.getDateTimeStart();
		DtEnd<Temporal> dtEnd = vEvent.getDateTimeEnd();
		ZonedDateTime zonedDateTimeFrom, zonedDateTimeTo;
		String from, to;
		zonedDateTimeFrom = toZonedDateTime(dtStart);
		from = this.txtDateFormatter.formatTxtDateWithOrWithoutTime(zonedDateTimeFrom);
		Optional<Property> opSummaryProp = vEvent.getProperty(Property.SUMMARY);
		Property summaryProp;
		String summary;
		if (opSummaryProp.isPresent()) {
			summaryProp = opSummaryProp.get();
			summary = summaryProp.getValue();
		} else {
			summary = null;
		}
		TxtCalendarEvent result;
		if (from == null || summary == null) {
			result = null;
		} else {
			zonedDateTimeTo = toZonedDateTime(dtEnd);
			to = this.txtDateFormatter.formatTxtDateWithOrWithoutTime(zonedDateTimeTo);
			Optional<Property> opDescriptionProp = vEvent.getProperty(Property.DESCRIPTION);
			Property descriptionProp;
			String description;
			if (opDescriptionProp.isPresent()) {
				descriptionProp = opDescriptionProp.get();
				description = descriptionProp.getValue();
			} else {
				description = null;
			}
			TxtEventDateLine dateLine = new TxtEventDateLine(from, to, recurrence);
			result = new TxtCalendarEvent(dateLine, summary, description);
		}
		return result;
	}
}
