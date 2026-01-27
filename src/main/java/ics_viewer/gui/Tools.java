package ics_viewer.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Tools {

	public static void printMessage(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message);
	}
	
	public static boolean showConfirmDialog(Component parentComponent, String message, String title) {
		Object[] options = {"Yes", "No"};
		int result = JOptionPane.showOptionDialog(
			parentComponent, message, title,
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			
			// It's safer if the No option is selected initially.
			options[1]
		);
		return result == JOptionPane.YES_OPTION;
	}
}
