package ics_viewer.gui.components;

import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;

import ics_viewer.logic.PropertyManager;

public class PropertyBackedJFileChooser extends JFileChooser {

	private static final long serialVersionUID = -4504187360240448884L;
	
	private final PropertyManager propertyManager;
	private final String propertyKey;
	
	public PropertyBackedJFileChooser(PropertyManager propertyManager, String propertyKey) {
		this.propertyManager = propertyManager;
		this.propertyKey = propertyKey;
		Optional<String> value = propertyManager.getProperty(propertyKey);
		if (value.isPresent()) {
			File currentDirectory = new File(value.get());
			if (currentDirectory.isDirectory()) {
				this.setCurrentDirectory(currentDirectory);
			}
		}
	}
	
	@Override
	public void setSelectedFile(File file) {
		super.setSelectedFile(file);
		if (file != null) {
			this.propertyManager.setProperty(this.propertyKey, file.getParentFile().getAbsolutePath());
		}
	}
}
