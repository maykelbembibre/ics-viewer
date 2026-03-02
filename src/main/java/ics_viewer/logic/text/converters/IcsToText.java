package ics_viewer.logic.text.converters;

import java.util.Iterator;

import ics_viewer.logic.text.models.TxtCalendarEvent;
import net.fortuna.ical4j.model.Calendar;

public class IcsToText implements Iterable<TxtCalendarEvent> {

	private final Calendar calendar;
	
	public IcsToText(Calendar calendar) {
		this.calendar = calendar;
	}
	
	@Override
	public Iterator<TxtCalendarEvent> iterator() {
		VEventIterator vEventIterator = new VEventIterator(this.calendar);
		return new IcsToTextIterator(vEventIterator);
	}
}
