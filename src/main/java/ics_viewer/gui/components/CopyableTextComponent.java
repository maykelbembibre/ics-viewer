package ics_viewer.gui.components;

import java.awt.Component;

import javax.swing.JTextArea;

/**
 * A component that has text that you can copy by selecting it
 * with the mouse.
 */
public class CopyableTextComponent extends JTextArea {

	private static final long serialVersionUID = 793798810159898558L;
	private static final int INITIAL_ROWS = 1;
	private static final int INITIAL_COLUMNS = 20;

	public CopyableTextComponent(Component parentComponent) {
		super(INITIAL_ROWS, INITIAL_COLUMNS);
		this.initialise(parentComponent);
	}
	
	/**
	 * Creates a copyable text component.
	 * @param parentComponent The parent component.
	 * @param text 
	 */
	public CopyableTextComponent(Component parentComponent, String text) {
//		super(INITIAL_ROWS, INITIAL_COLUMNS);
		super.setText(text);
		this.initialise(parentComponent);
	}
	
    /**
     * Hides the text cursor by making it transparent.
     */
    private void hideInsertCaret() {
    	this.setCaretColor(AppWindowContentPane.TRANSPARENT);
    }
    
    private void initialise(Component parentComponent) {
        this.setBackground(parentComponent.getBackground());
        this.setEditable(false);
        this.setWrapStyleWord(true);
        this.setLineWrap(true);
        this.hideInsertCaret();
    }
}