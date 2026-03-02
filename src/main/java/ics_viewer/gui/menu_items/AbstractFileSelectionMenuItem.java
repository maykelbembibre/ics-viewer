package ics_viewer.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import ics_viewer.gui.AppWindow;

/**
 * Base class for all menu items that begin with the user
 * selecting a file.
 */
public abstract class AbstractFileSelectionMenuItem extends JMenuItem {

	private static final long serialVersionUID = 259664020863767979L;

	/**
	 * Creates a new menu item that begins with the user selecting a file.
	 * @param appWindow The app window.
	 * @param title The title of this menu item.
	 * @param fileType The file type that this menu item opens or
	 * <code>null</code> if this menu item is for saving to a file instead.
	 */
	public AbstractFileSelectionMenuItem(AppWindow appWindow, String title, String fileType) {
		super(title);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = appWindow.getFileChooser();
				int returnVal = fileChooser.showOpenDialog(AbstractFileSelectionMenuItem.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            fileSelected(appWindow, file, fileType);
		        }
			}}
		);
	}
	
	/**
	 * Called when a file has been selected for opening.
	 * @param appWindow The appWindow.
	 * @param file A file.
	 */
	protected abstract void fileSelected(AppWindow appWindow, File file, String fileType);
}
