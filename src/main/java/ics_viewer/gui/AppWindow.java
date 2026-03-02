package ics_viewer.gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ics_viewer.gui.components.AppWindowContentPane;
import ics_viewer.gui.components.PropertyBackedJFileChooser;
import ics_viewer.gui.menu_items.CloseMenuItem;
import ics_viewer.gui.menu_items.ExportToTxtMenuItem;
import ics_viewer.gui.menu_items.ImportFromTxtMenuItem;
import ics_viewer.gui.menu_items.OpenMenuItem;
import ics_viewer.gui.menu_items.SaveToIcsMenuItem;
import ics_viewer.logic.PropertyManager;

public class AppWindow extends JFrame {

	private static final long serialVersionUID = -706300805865875624L;
	private static final String APP_NAME = "ICS viewer";
	private final AppWindowContentPane contentPane;
	private final PropertyManager propertyManager = new PropertyManager(APP_NAME);
	
	/**
	 * An instance of a {@link JFileChooser to be re-used across the app.
	 */
	private final JFileChooser fileChooser = new PropertyBackedJFileChooser(this.propertyManager, "last.directory");
	
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
		JMenuItem saveMenuItem = new SaveToIcsMenuItem(this);
		fileMenu.add(saveMenuItem);
		JMenuItem closeMenuItem = new CloseMenuItem(this);
		fileMenu.add(closeMenuItem);
		menuBar.add(fileMenu);
		JMenu importExportMenu = new JMenu("Import/export");
		JMenuItem importFromTxtMenuItem = new ImportFromTxtMenuItem(this);
		importExportMenu.add(importFromTxtMenuItem);
		JMenuItem exportToTxtMenuItem = new ExportToTxtMenuItem(this);
		importExportMenu.add(exportToTxtMenuItem);
		menuBar.add(importExportMenu);
		this.setJMenuBar(menuBar);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	@Override
	public void setTitle(String title) {
		StringBuilder titleBuilder = new StringBuilder(APP_NAME);
		if (title != null && !title.isEmpty()) {
			titleBuilder.append(" - " + title);
		}
		String newTitle = titleBuilder.toString();
		super.setTitle(newTitle);
	}

	public AppWindowContentPane getAppWindowContentPane() {
		return this.contentPane;
	}

	/**
	 * Returns the {@link JFileChooser} instance of this app window.
	 * @return The {@link JFileChooser} instance.
	 */
	public JFileChooser getFileChooser() {
		return fileChooser;
	}
}
