package ics_viewer.gui.components;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import ics_viewer.gui.models.ToggleSelectionModel;

/**
 * A {@link JList} that allows a selected item to be unselected.
 * @param <E> the type of the elements of this list.
 */
public class UnselectableJList<E> extends JList<E> {

	private static final long serialVersionUID = 7827729024344513963L;
	
	/**
	 * Creates a list that allows a selected item to be unselected.
	 * @param dataModel The data model.
	 */
	public UnselectableJList(ListModel<E> dataModel) {
		super(dataModel);
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected ListSelectionModel createSelectionModel() {
        return new ToggleSelectionModel<>();
    }
}