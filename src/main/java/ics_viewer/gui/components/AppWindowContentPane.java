package ics_viewer.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import ics_viewer.gui.AppWindow;
import ics_viewer.gui.components.calendar_event_list.CalendarEventJList;
import ics_viewer.gui.models.EventListModel;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

public class AppWindowContentPane extends JPanel {

	private static final long serialVersionUID = 6828491862536000990L;
	
	static final Color TRANSPARENT = new Color(255, 255, 255, 0);
	private static final int GAP = 5;
	
	/**
	 * This is the iCal calendar whose events are currently displayed in the
	 * app window.
	 */
	private Calendar calendar;
	
	/**
	 * This is the collection of calendar events that is currently open and
	 * visible in the app window.
	 */
	private List<VEvent> events;
	
	/**
	 * The underlying model of the calendar event list that is in this content
	 * pane.
	 */
	private final DefaultListModel<EventListModel> listModel;
	
	private final ListSelectionModel listSelectionModel;
	private final JScrollPane listScrollPane;
	private final JLabel noEventsJLabel;
	private final JTextComponent details;
	
	public AppWindowContentPane(AppWindow appWindow) {
		super(new BorderLayout());
		this.listModel = new DefaultListModel<>();        
		JList<EventListModel> list = new CalendarEventJList<>(this.listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listSelectionModel = list.getSelectionModel();
        list.setVisibleRowCount(5);
        this.listScrollPane = new JScrollPane(list);
        this.listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(listScrollPane, BorderLayout.CENTER);

        this.noEventsJLabel = new JLabel("No events.");
        this.noEventsJLabel.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        this.add(noEventsJLabel, BorderLayout.NORTH);
        
        this.details = new CopyableTextComponent(this);
        JScrollPane detailsScrollPane = new JScrollPane(this.details, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        detailsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        detailsScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        detailsScrollPane.setBorder(BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP));
        this.add(detailsScrollPane, BorderLayout.SOUTH);
        
        this.setCalendarEvents(null);
	}
	
	/**
	 * Returns whether this instance has calendar events currently.
	 * @return <code>true</code> or <code>false</code>.
	 */
	public boolean hasCalendarEvents() {
		return this.events != null && !this.events.isEmpty();
	}
	
	/**
	 * Returns the currently open calendar in this instance.
	 * @return The currently open calendar in this instance.
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Sets the currently open calendar in this instance and displays its
	 * events.
	 * @param calendar The calendar.
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		List<VEvent> events;
		if (calendar == null) {
			events = null;
		} else {
			events = calendar.getComponents(Component.VEVENT);
		}
		this.setCalendarEvents(events);
	}

	/**
	 * Sets the collection of calendar events of the app, making it visible
	 * in the app window.
	 * @param contactIterable The collection of calendar events.
	 */
	private void setCalendarEvents(List<VEvent> events) {
		this.events = events;
		this.reloadList();
		this.updateDetails();
	}
	
	private void reloadList() {
		this.listModel.clear();
		
		// Not resetting the selection model before repopulating the list model
		// would make it so slow it looks as if the program had crashed.
		this.listSelectionModel.clearSelection();
		this.listSelectionModel.setAnchorSelectionIndex(-1);
		this.listSelectionModel.setLeadSelectionIndex(-1);
		
		boolean empty = true;
		if (this.events != null) {
			List<EventListModel> eventListModels = new ArrayList<>();
			Optional<EventListModel> optionalEventListModel;
			for (VEvent event : this.events) {
				empty = false;
				optionalEventListModel = EventListModel.of(event);
				if (optionalEventListModel.isPresent()) {
					eventListModels.add(optionalEventListModel.get());
				}
			}
			Collections.sort(eventListModels);
			this.listModel.addAll(eventListModels);
		}
		this.noEventsJLabel.setVisible(empty);
		this.listScrollPane.setVisible(!empty);
	}
	
	private void updateDetails() {
		List<String> details;
		if (this.events == null) {
			details = null;
		} else {
			details = new ArrayList<>();
			details.add("Events: " + this.listModel.getSize() + ".");
		}
		
		StringBuilder newText = new StringBuilder();
		if (details != null) {
			boolean first = true;
			for (String detail : details) {
				if (!first) {
					newText.append("\n");
				}
				newText.append(detail);
				first = false;
			}
		}
		this.details.setVisible(!newText.isEmpty());
		this.details.setText(newText.toString());
	}
}
