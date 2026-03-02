package ics_viewer.gui.menu_items;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ics_viewer.gui.AppWindow;
import ics_viewer.gui.Tools;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.validate.ValidationException;

/**
 * Menu item that saves the currently opened iCal calendar
 * to an ICS file.
 */
public class SaveToIcsMenuItem extends CalendarSaveMenuItem {

	private static final long serialVersionUID = 8005153330080329628L;

	/**
	 * Creates a new menu item that saves the currently opened iCal
	 * calendar to an ICS file.
	 * @param appWindow The app window.
	 */
	public SaveToIcsMenuItem(AppWindow appWindow) {
		super(appWindow, "Save");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveCalendarToFile(AppWindow appWindow, Calendar calendar, File file) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			CalendarOutputter outputter = new CalendarOutputter();
			outputter.output(calendar, fout);
		} catch (IOException | ValidationException e) {
			String message = e.getMessage();
			if (message != null) {
				Tools.printMessage(appWindow, message);
			} else {
				Tools.printMessage(appWindow, "Unknown error: " + e.getClass().getName() + ".");
			}
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
