package ics_viewer.gui.models;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import ics_viewer.gui.components.calendar_event_list.Displayable;
import ics_viewer.logic.DateOperations;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Summary;

/**
 * Represents a model of a calendar event that can be displayed in a list
 * on the app window content pane. These event list models are comparable
 * by their start date and time so they can be chronologically sorted.
 */
public class EventListModel implements Comparable<EventListModel>, Displayable {

	private final String summary;
	private final ZonedDateTime dateTimeStart;
	
	private EventListModel(@NotNull String summary, @NotNull ZonedDateTime dateTimeStart) {
		Objects.requireNonNull(summary);
		Objects.requireNonNull(dateTimeStart);
		this.summary = summary;
		this.dateTimeStart = dateTimeStart;
	}

	/**
	 * Creates an event list model from an iCal event.
	 * @param event An iCal event.
	 * @return An {@link Optional} wrapping the event list model or an empty {@link Optional}
	 * if this event list model lacks the logic to represent the given iCal event.
	 */
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

	/**
	 * Returns the summary of this event list model.
	 * @return The summary of this event list model.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Returns the start date and time of this event list model.
	 * @return The start date and time of this event list model.
	 */
	public ZonedDateTime getDateTimeStart() {
		return dateTimeStart;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String displayAsString() {
		return DateOperations.formatDateTime(this.getDateTimeStart())
    	+ ": " + this.getSummary();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(EventListModel o) {
		return this.dateTimeStart.compareTo(o.dateTimeStart);
	}
}
