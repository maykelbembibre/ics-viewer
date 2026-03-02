package ics_viewer.gui.menu_items;

import java.io.File;

import ics_viewer.gui.AppWindow;
import ics_viewer.gui.Tools;
import ics_viewer.gui.components.AppWindowContentPane;
import net.fortuna.ical4j.model.Calendar;

/**
 * Abstract class for menu items that open files to display
 * their contents as an iCal calendar.
 */
public abstract class AbstractOpenMenuItem extends AbstractFileSelectionMenuItem {

	private static final long serialVersionUID = -7920117758868359963L;

	/**
	 * Creates a new abstract class for a menu item that opens files
	 * to display their contents as an iCal calendar.
	 * @param appWindow The app window.
	 * @param title The title of this menu item.
	 * @param fileType The file type this menu item opens: ICS, TXT.
	 */
	public AbstractOpenMenuItem(AppWindow appWindow, String title, String fileType) {
		super(appWindow, title, fileType);
	}
	
	/**
	 * Called when a valid file has been selected for opening.
	 * @param appWindow The appWindow.
	 * @param file A valid file.
	 * @return The iCal calendar from the file or <code>null</code>
	 * if the file hasn't got the expected format.
	 */
	protected abstract Calendar handleFile(AppWindow appWindow, File file);
	
	/**
	 * {@inheritDoc}
	 */
	protected void fileSelected(AppWindow appWindow, File file, String fileType) {
		Calendar calendar;
        if (file == null || !file.isFile()) {
        	calendar = null;
        } else {
        	calendar = handleFile(appWindow, file);
        }
        if (calendar == null) {
        	Tools.printMessage(AbstractOpenMenuItem.this, "That's not a valid " + fileType + " file.");
        } else {
        	AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
        	appWindowContentPane.setCalendar(calendar);
        }
	}
}
