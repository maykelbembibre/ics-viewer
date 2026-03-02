package ics_viewer.gui.menu_items;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ics_viewer.gui.AppWindow;
import ics_viewer.gui.Tools;
import ics_viewer.logic.DateOperations;
import ics_viewer.logic.text.TxtCalendarWriter;
import ics_viewer.logic.text.converters.IcsToText;
import ics_viewer.logic.text.models.TxtCalendarEvent;
import net.fortuna.ical4j.model.Calendar;

/**
 * Menu item that exports the currently opened iCal calendar
 * to a TXT file.
 */
public class ExportToTxtMenuItem extends CalendarSaveMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	/**
	 * Creates a new menu item that exports the currently opened iCal
	 * calendar to a TXT file.
	 * @param appWindow The app window.
	 */
	public ExportToTxtMenuItem(AppWindow appWindow) {
		super(appWindow, "Export to TXT");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void saveCalendarToFile(AppWindow appWindow, Calendar calendar, File file) {
    	FileWriter fileWriter = null;
    	TxtCalendarWriter txtCalendarWriter = null;
    	try {
    		IcsToText icsToText = new IcsToText(DateOperations.getCurrentZone(), calendar);
    		fileWriter = new FileWriter(file);
    		txtCalendarWriter = new TxtCalendarWriter(fileWriter);
    		for (TxtCalendarEvent txtCalendarEvent : icsToText) {
    			txtCalendarWriter.writeEvent(txtCalendarEvent);
    		}
    		Tools.printMessage(appWindow, "Export finished successfully. Check the file " + file.getName() + ".");
    	} catch (IOException ex) {
    		String exMessage = ex.getMessage();
    		String message;
    		if (exMessage == null || exMessage.isEmpty()) {
    			message = "unknown error: " + ex.getClass().getName();
    		} else {
    			message = ex.getMessage();
    		}
    		Tools.printMessage(appWindow, "There was an error during export: " + message + ".");
    		ex.printStackTrace();
		} finally {
			if (txtCalendarWriter != null) {
				txtCalendarWriter.close();
			}
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
    	}
	}
}
