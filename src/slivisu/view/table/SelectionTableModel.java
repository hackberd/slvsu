package slivisu.view.table;

import java.util.List;

import javax.swing.table.TableModel;

public interface SelectionTableModel extends TableModel {

	public void setSelectedRows(List<Integer> rows);
	public List<Integer> getSelectedRows();
}
