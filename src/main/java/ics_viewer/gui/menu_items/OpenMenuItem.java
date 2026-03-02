package ics_viewer.gui.menu_items;

import java.io.File;

import ics_viewer.exceptions.GeneralException;
import ics_viewer.gui.AppWindow;
import ics_viewer.logic.FileOperations;
import net.fortuna.ical4j.model.Calendar;

public class OpenMenuItem extends AbstractOpenMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	public OpenMenuItem(AppWindow appWindow) {
		super(appWindow, "Open", "ICS");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Calendar handleFile(AppWindow appWindow, File file) {
		Calendar calendar;
		try {
    		calendar = FileOperations.getCalendar(file);
		} catch (GeneralException e1) {
			calendar = null;
		}
		return calendar;
	}
}
