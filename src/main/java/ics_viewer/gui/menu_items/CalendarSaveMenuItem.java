package ics_viewer.gui.menu_items;

import java.io.File;
import java.util.List;

import ics_viewer.gui.AppWindow;
import ics_viewer.gui.Tools;
import ics_viewer.gui.components.AppWindowContentPane;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

/**
 * Menu item that saves the currently opened iCal calendar
 * to a file in a way that is defined by the subclasses.
 */
public abstract class CalendarSaveMenuItem extends AbstractFileSelectionMenuItem {


	private static final long serialVersionUID = -5705085818713814846L;

	/**
	 * Creates a new menu item that saves the currently opened iCal
	 * calendar to an ICS file.
	 * @param appWindow The app window.
	 */
	public CalendarSaveMenuItem(AppWindow appWindow, String title) {
		super(appWindow, title, null);
	}

	/**
	 * Called when the app window has a calendar with events opened.
	 * @param appWindow The app window.
	 * @param calendar The calendar that is currently opened.
	 * @param file The file to which to save the calendar to.
	 */
	protected abstract void saveCalendarToFile(AppWindow appWindow, Calendar calendar, File file);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void fileSelected(AppWindow appWindow, File file, String fileType) {
		if (file != null) {
			AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
			Calendar calendar = appWindowContentPane.getCalendar();
			List<VEvent> events;
			if (calendar == null) {
				events = null;
			} else {
				events = calendar.getComponents(Component.VEVENT);
			}
			if (events == null || events.isEmpty()) {
				Tools.printMessage(appWindow, "You have to open a calendar with events first.");
			} else if (file.isFile()) {
				if (Tools.showConfirmationDialog(appWindow, "The file " + file.getName() + " already exists. Do you wish to replace it?")) {
					this.saveCalendarToFile(appWindow, calendar, file);
				}
			} else if (file.isDirectory()) {
				Tools.printMessage(appWindow, "You have to select a file, not a directory.");
			} else {
				this.saveCalendarToFile(appWindow, calendar, file);
			}
		}
	}
}
