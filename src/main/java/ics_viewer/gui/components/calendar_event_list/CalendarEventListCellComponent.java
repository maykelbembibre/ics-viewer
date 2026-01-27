package ics_viewer.gui.components.calendar_event_list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * The {@link Component} that goes inside of every cell of the {@link JList}.
 */
public class CalendarEventListCellComponent extends JPanel {

	private static final long serialVersionUID = -6496941466814065971L;
	
	/**
	 * Creates an adaptable list cell component.
	 * @param list The list for which this method returns cell components.
	 * @param background The background for this component.
	 * @param ordinal A number that will show inside this component to the left
	 * of the text.
	 * @param text The text.
	 */
	public CalendarEventListCellComponent(JList<?> list, int ordinal, String text) {
		this.setLayout(new BorderLayout());
		Component ordinalLabel = createJLabel(Integer.valueOf(ordinal).toString());
		JPanel ordinalPanel = createListCellPanel();
		ordinalPanel.add(ordinalLabel);
		ordinalPanel.add(Box.createVerticalGlue());
		
		this.add(ordinalPanel, BorderLayout.WEST);
		
		JPanel linesPanel = createListCellPanel();
    	
		JTextArea jTextArea = new JTextArea(text);
    	linesPanel.add(jTextArea);
    	linesPanel.setBorder(
			BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(0, 5, 0, 0),
	    		BorderFactory.createCompoundBorder(
	    			BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray),
	    			BorderFactory.createEmptyBorder(0, 5, 0, 0)
	    		)
    		)
    	);
        
        this.add(linesPanel, BorderLayout.CENTER);
        
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
		
		jTextArea.setOpaque(false);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		
		// What all the borders combined measure.
		Insets linesPanelInsets = linesPanel.getBorder().getBorderInsets(linesPanel);
		
        /*
         * This is just to lure the text area's internal sizing mechanism into action
         * It's needed for the text area to be the correct height to accomodate the text.
         * ordinalPanel has no size yet because it hasn't been laid out. But it does
         * have preferred size (how it wants to be). The text area's width calculation
         * then is:
         * list width - left panel width - right panel borders width
         */
		int width = list.getWidth() - ordinalPanel.getPreferredSize().width - linesPanelInsets.left - linesPanelInsets.right;
        if (width > 0) {
        	jTextArea.setSize(width, Short.MAX_VALUE);
        }
	}
	
	private static JLabel createJLabel(String text) {
		JLabel jLabel = new JLabel(text);
		jLabel.setOpaque(false);
		return jLabel;
	}
	
	private static JPanel createListCellPanel() {
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.PAGE_AXIS));
		result.setOpaque(false);
		return result;
	}
}
