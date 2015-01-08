/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.table;

import java.util.List;

import javax.swing.RowFilter;

/**
 * 
 * @author Sven
 */
public class SelectionFilter extends RowFilter<SelectionTableModel, Integer> {

	@Override
	public boolean include(Entry<? extends SelectionTableModel, ? extends Integer> entry) {
		int id = entry.getIdentifier();
		List<Integer> selection = entry.getModel().getSelectedRows();
		for (int selectedRow : selection){
			if (id == selectedRow){
				return true;
			}
		}
		return false;
	}
}
