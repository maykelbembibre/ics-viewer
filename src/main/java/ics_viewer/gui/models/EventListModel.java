package ics_viewer.gui.models;

import java.time.ZonedDateTime;
import java.util.Optional;

import ics_viewer.gui.components.calendar_event_list.Displayable;
import ics_viewer.logic.DateOperations;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;

public class EventListModel implements Displayable {

	private final String summary;
	private final ZonedDateTime dateTimeStart;
	
	private EventListModel(String summary, ZonedDateTime dateTimeStart) {
		this.summary = summary;
		this.dateTimeStart = dateTimeStart;
	}

	public static Optional<EventListModel> of(VEvent event) {
		Optional<EventListModel> result;
		Optional<ZonedDateTime> optionalDateTimeStart = DateOperations.getZonedDateTime(event.getDateTimeStart());
		Summary summary = event.getSummary();
		if (optionalDateTimeStart.isPresent() && summary != null) {
			ZonedDateTime dateTimeStart = optionalDateTimeStart.get();
			result = Optional.of(new EventListModel(summary.getValue(), dateTimeStart));
		} else {
			result = Optional.empty();
		}
		return result;
	}

	public String getSummary() {
		return summary;
	}

	public ZonedDateTime getDateTimeStart() {
		return dateTimeStart;
	}

	@Override
	public String displayAsString() {
		return DateOperations.formatDateTime(this.getDateTimeStart())
    	+ ": " + this.getSummary();
	}
}
