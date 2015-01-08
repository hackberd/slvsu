package slivisu.defaults.doubletable;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class DoubleTable extends JScrollPane implements TableModelListener{

	/**
	 * 
	 */
	private static final long 						serialVersionUID = 1L;
	private DoubleTableModel						tableModel;
	private JTable 									leftTable;
	private JTable 									table;

	public DoubleTable(DoubleTableModel tableModel, TableCellRenderer renderer, JPopupMenu menu, RowSorter<? extends TableModel> sorter) {
		
		table = new JTable();
		table.setRowSorter(sorter);
		table.setDefaultRenderer(Object.class, renderer);
		table.setUpdateSelectionOnSort(true);
		table.setComponentPopupMenu(menu);

		leftTable = new JTable();
		leftTable.setRowSorter(sorter);
		leftTable.setDefaultRenderer(Object.class, renderer);
		leftTable.setSelectionModel(table.getSelectionModel());
		leftTable.setUpdateSelectionOnSort(false);
		leftTable.setComponentPopupMenu(menu);
		
		setTableModel(tableModel);

		setComponentPopupMenu(menu);
		
		initial();
	}

	private void initial() {
		setRowHeaderView(leftTable);
		setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, leftTable.getTableHeader());
	}

	private void setColumnWidths() {
		for (String column : tableModel.getColumns()) {
			JTable invisibleTable = leftTable;
			if (tableModel.getHeaderColumns().contains(column)){
				invisibleTable = table;
			}
			invisibleTable.getColumn(column).setPreferredWidth(0);
			invisibleTable.getColumn(column).setMinWidth(0);
			invisibleTable.getColumn(column).setMaxWidth(0);
		}
	}

	public void setColumnModel(TableColumnModel columnModel) {
		table.setColumnModel(columnModel);
		leftTable.setColumnModel(columnModel);
	}

	public void setTableHeader(JTableHeader tableHeader) {
		table.setTableHeader(tableHeader);
		leftTable.setTableHeader(tableHeader);
	}

	public void setSelectionMode(int selectionMode) {
		table.getSelectionModel().setSelectionMode(selectionMode);
	}

	public DoubleTableModel getTableModel() {
		return tableModel;
	}
	
	public void setTableModel(DoubleTableModel tableModel){
		this.tableModel = tableModel;
		this.tableModel.addTableModelListener(this);
		this.table.setModel(this.tableModel);
		this.leftTable.setModel(this.tableModel);
		this.setColumnWidths();
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		if (e.getFirstRow() == TableModelEvent.HEADER_ROW){
			this.setColumnWidths();
		}
		JViewport viewport = getRowHeader();
		Dimension dim = leftTable.getPreferredSize();
		int d = 0;
		for (String s : tableModel.getHeaderColumns()) {
			d += leftTable.getColumn(s).getPreferredWidth();
		}
		dim.setSize(d, dim.getHeight());
		viewport.setPreferredSize(dim);
	}

	public void setTableSelectionForeground(Color color) {
		table.setSelectionForeground(color);
		leftTable.setSelectionForeground(color);
	}
	
	public void setTableForeground(Color color){
		table.setForeground(color);
		leftTable.setForeground(color);		
	}

	public void setTableSelectionBackground(Color color) {
		table.setSelectionBackground(color);
		leftTable.setSelectionBackground(color);
	}
	
	public void setTableBackground(Color color){
		table.setBackground(color);
		leftTable.setBackground(color);		
	}

	public JTable getTable() {
		return table;
	}

	public JTable getLeftTable() {
		return leftTable;
	}
}
