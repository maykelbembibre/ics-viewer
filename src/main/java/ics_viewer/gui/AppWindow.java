package ics_viewer.gui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ics_viewer.gui.components.AppWindowContentPane;
import ics_viewer.gui.menu_items.CloseMenuItem;
import ics_viewer.gui.menu_items.OpenMenuItem;

public class AppWindow extends JFrame {

	private static final long serialVersionUID = -706300805865875624L;

	private final AppWindowContentPane contentPane;
	
	/**
	 * An instance of a {@link JFileChooser to be re-used across the app.
	 */
	private final JFileChooser fileChooser = new JFileChooser();
	
	private File file;
	
	/**
	 * Creates the GUI and shows it. For thread safety, this constructor
	 * should be invoked from the event-dispatching thread.
	 */
	public AppWindow() {
		this.setTitle(null);
		
		// Create and set up the content pane.
		this.contentPane = new AppWindowContentPane(this);
		this.contentPane.setOpaque(true); //content panes must be opaque
        this.setContentPane(this.contentPane);
		
        // Create menu bar.
        JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openMenuItem = new OpenMenuItem(this);
		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);
		JMenuItem closeMenuItem = new CloseMenuItem(this);
		fileMenu.add(closeMenuItem);
		this.setJMenuBar(menuBar);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	@Override
	public void setTitle(String title) {
		StringBuilder titleBuilder = new StringBuilder("ICS viewer");
		if (title != null && !title.isEmpty()) {
			titleBuilder.append(" - " + title);
		}
		String newTitle = titleBuilder.toString();
		super.setTitle(newTitle);
	}

	public AppWindowContentPane getAppWindowContentPane() {
		return this.contentPane;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		if (file != null) {
			this.setTitle(file.getName());
		}
	}
	
	/**
	 * Returns the {@link JFileChooser} instance of this app window.
	 * @return The {@link JFileChooser} instance.
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
}
