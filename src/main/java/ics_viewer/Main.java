package ics_viewer;

import ics_viewer.gui.AppWindow;

public class Main {

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new AppWindow();
            }
        });
	}
}
