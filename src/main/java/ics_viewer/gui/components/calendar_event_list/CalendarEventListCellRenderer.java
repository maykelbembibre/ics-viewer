package ics_viewer.gui.components.calendar_event_list;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * The {@link ListCellRenderer} that makes the components that go inside a
 * calendar event list cells.
 * 
 * @param <E> the type of the elements of the list.
 */
public class CalendarEventListCellRenderer<E extends Displayable> implements ListCellRenderer<E> {

	/**
	 * Creates the calendar event list cell renderer.
	 * 
	 * @param jList The {@link JList}.
	 */
	public CalendarEventListCellRenderer(JList<E> jList) {
		ComponentListener l = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// next line possible if list is of type JXList
				// list.invalidateCellSizeCache();
				// for core: force cache invalidation by temporarily setting fixed height
				//
				// A JList only calculates the height of all the cells once and then assumes
				// it'll always stay the same. That's why with every resize event, we make that
				// cache be invalidated.
				jList.setFixedCellHeight(10);
				jList.setFixedCellHeight(-1);
			}
		};
		jList.addComponentListener(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
			boolean cellHasFocus) {
		Component component = new CalendarEventListCellComponent(list, index + 1, value.displayAsString());
		if (isSelected) {
			component.setBackground(list.getSelectionBackground());
			component.setForeground(list.getSelectionForeground());
		} else {
			component.setBackground(list.getBackground());
			component.setForeground(list.getForeground());
		}
		return component;
	}
}
