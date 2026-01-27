package ics_viewer.gui.components.calendar_event_list;

import javax.swing.ListModel;

import ics_viewer.gui.components.UnselectableJList;

/**
 * A list specialised for displaying calendar events in its cells.
 * 
 * @param <E> the type of the elements of this list.
 */
public class CalendarEventJList<E extends Displayable> extends UnselectableJList<E> {

	private static final long serialVersionUID = -2247323102055375094L;

	/**
	 * Creates a calendar event list.
	 * 
	 * @param dataModel The model that contains the data of the elements of this
	 *                  list.
	 */
	public CalendarEventJList(ListModel<E> dataModel) {
		super(dataModel);
		this.setCellRenderer(new CalendarEventListCellRenderer<E>(this));
	}

	/**
	 * Makes the list always as wide as the viewport. <br/>
	 * <br/>
	 * {@link javax.swing.JList JList}'s Scrollable implementation of
	 * tracksViewportWidth contains logic which stands in the way (leads to looping
	 * stretch-out of the area until it's a single line), so it must be subclassed
	 * to return true. <br/>
	 * <br/>
	 * 
	 * @see {@link javax.swing.JList#getScrollableTracksViewportWidth()
	 *      JList.getScrollableTracksViewportWidth()}
	 * @return Whether or not an enclosing viewport should force the list's width to
	 *         match its own.
	 */
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return true;
	}
}
