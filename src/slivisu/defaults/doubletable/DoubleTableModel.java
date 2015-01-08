package slivisu.defaults.doubletable;

import java.util.Vector;

import javax.swing.table.TableModel;

public interface DoubleTableModel extends TableModel{

	public Vector<String> getHeaderColumns();
	public Vector<String> getColumns();
}
