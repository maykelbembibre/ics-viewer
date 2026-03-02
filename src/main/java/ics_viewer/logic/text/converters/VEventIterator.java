package ics_viewer.logic.text.converters;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;

public class VEventIterator implements Iterator<VEvent> {
	
	private final Iterator<CalendarComponent> componentIterator;
	private CalendarComponent nextCalendarComponent;
	
	public VEventIterator(Calendar icsCalendar) {
		this.componentIterator = icsCalendar.getComponentList().getAll().iterator();
	}
	
	@Override
	public boolean hasNext() {
		while (!(this.nextCalendarComponent instanceof VEvent) && this.componentIterator.hasNext()) {
			this.nextCalendarComponent = this.componentIterator.next();
		}
		return this.nextCalendarComponent instanceof VEvent;
	}

	@Override
	public VEvent next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}
		VEvent result = (VEvent) this.nextCalendarComponent;
		this.nextCalendarComponent = null;
		return result;
	}
}
