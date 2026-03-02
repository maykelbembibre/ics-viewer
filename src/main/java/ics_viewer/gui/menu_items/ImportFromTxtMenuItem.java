package ics_viewer.gui.menu_items;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ics_viewer.gui.AppWindow;
import ics_viewer.logic.text.TxtCalendarReader;
import ics_viewer.logic.text.converters.TxtToIcs;
import ics_viewer.logic.text.models.TxtCalendarEvent;
import net.fortuna.ical4j.model.Calendar;

/**
 * Menu item for importing calendar events from TXT files.
 */
public class ImportFromTxtMenuItem extends AbstractOpenMenuItem {

	private static final long serialVersionUID = 8168757827396103239L;

	/**
	 * Creates a new menu item for importing calendar events from
	 * TXT files.
	 * @param appWindow The app window.
	 */
	public ImportFromTxtMenuItem(AppWindow appWindow) {
		super(appWindow, "Import from TXT file", "TXT");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Calendar handleFile(AppWindow appWindow, File file) {
		Calendar icsCalendar;
		FileReader fileReader = null;
    	TxtCalendarReader txtCalendarFileReader = null;
    	try {
    		fileReader = new FileReader(file);
    		txtCalendarFileReader = new TxtCalendarReader(fileReader);
    		TxtCalendarEvent event;
    		TxtToIcs txtToIcs = new TxtToIcs();
    		while ((event = txtCalendarFileReader.readEvent()) != null) {
    			System.out.println(event);
    			txtToIcs.addTxtEvent(event);
    		}
    		icsCalendar = txtToIcs.getCalendar();
    	} catch (IOException e) {
    		icsCalendar = null;
			e.printStackTrace();
		} finally {
    		if (txtCalendarFileReader != null) {
    			try {
					txtCalendarFileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		if (fileReader != null) {
    			try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
		return icsCalendar;
	}
}
