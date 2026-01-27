package ics_viewer.gui.models;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;

/**
 * Class taken from the Sun documentation that allows to unselect items
 * from a {@link JList}.
 * @param <E> The type of the elements on the list.
 */
public class ToggleSelectionModel<E> extends DefaultListSelectionModel {

	private static final long serialVersionUID = 1234049410693542353L;
	
	private boolean gestureStarted = false;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectionInterval(int index0, int index1) {
		if (isSelectedIndex(index0) && !gestureStarted) {
			super.removeSelectionInterval(index0, index1);
		} else {
			super.setSelectionInterval(index0, index1);
		}
		this.gestureStarted = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValueIsAdjusting(boolean isAdjusting) {
		if (isAdjusting == false) {
			this.gestureStarted = false;
		}
	}
}