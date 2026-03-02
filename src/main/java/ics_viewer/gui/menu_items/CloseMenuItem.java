package ics_viewer.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import ics_viewer.gui.AppWindow;
import ics_viewer.gui.components.AppWindowContentPane;

/**
 * Menu item that closes the currently opened iCal calendar.
 */
public class CloseMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	/**
	 * Creates a new menu item that closes the currently opened iCal
	 * calendar.
	 * @param appWindow The app window.
	 */
	public CloseMenuItem(AppWindow appWindow) {
		super("Close");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				appWindow.setTitle(null);
				AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
				appWindowContentPane.setCalendar(null);
			}}
		);
	}
}
