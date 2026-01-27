package ics_viewer.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import ics_viewer.exceptions.GeneralException;
import ics_viewer.gui.AppWindow;
import ics_viewer.gui.Tools;
import ics_viewer.gui.components.AppWindowContentPane;
import ics_viewer.logic.FileOperations;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

public class OpenMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	public OpenMenuItem(AppWindow appWindow) {
		super("Open");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = appWindow.getFileChooser();
				int returnVal = fileChooser.showOpenDialog(OpenMenuItem.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            Calendar calendar;
		            if (file == null) {
		            	calendar = null;
		            } else {
		            	try {
		            		calendar = FileOperations.getCalendar(file);
						} catch (GeneralException e1) {
							calendar = null;
						}
		            }
		            if (calendar == null) {
		            	Tools.printMessage(OpenMenuItem.this, "That's not a valid ICS file.");
		            } else {
		            	appWindow.setFile(file);
		            	AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
		            	List<VEvent> events = calendar.getComponents(Component.VEVENT);
		            	appWindowContentPane.setCalendarEvents(events);
		            }
		        }
			}}
		);
	}
}
