package ics_viewer.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import ics_viewer.gui.components.ReadOnlyJTextArea;


public class Tools {

	private static final Color TRANSPARENT = new Color(255, 255, 255, 0);
	
	public static void printMessage(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message);
	}
	
	/**
	 * Shows a confirmation dialog.
	 * @param parentComponent The parent component.
	 * @param question The question to ask the user.
	 * @return Whether the user has confirmed or not.
	 */
	public static boolean showConfirmationDialog(Component parentComponent, String question) {
		Object[] options = {"Yes", "No"};
		JTextArea textArea = new ReadOnlyJTextArea(question);
		textArea.setBackground(TRANSPARENT);
		int n = JOptionPane.showOptionDialog(
			parentComponent, textArea, "Confirmation",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[1]
		);
		return n == 0;
	}
}
