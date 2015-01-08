/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import slivisu.data.Data;
import slivisu.defaults.SortedTabbedPane;
import slivisu.defaults.doubletable.DoubleTable;
import slivisu.gui.controller.InteractionController;
import slivisu.gui.controller.InteractionListener;
import slivisu.mapper.SlivisuObservationsTableModel;

/**
 *
 * @author Sven
 */
public class AttributeTables extends SortedTabbedPane<DoubleTable> implements InteractionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, SlivisuObservationsTableModel> tableModels;
	private Map<String, TableSelectionController> selectionController;
	private Map<String, TableRowSorter<SelectionTableModel>> sorters;
	private Map<String, JTextField> selected;
	
	private ViewMode viewMode;
	
	protected enum ViewMode{
		TYPE1("Marked Sli in Orange, other Sli in white"),
		TYPE2("Marked SLI (orange) other SLI (categorie color)"),
		TYPE3("Marked SLI (categorie color) other SLI (white)");

		private String s;
		private ViewMode(String s){
			this.s = s;
		}
		public String toString(){
			return s;
		}
	};

	public AttributeTables(InteractionController contr, Map<String, SlivisuObservationsTableModel> data) {
		this.tableModels = data;
		this.selectionController = new HashMap<String, TableSelectionController>();
		this.sorters = new HashMap<String, TableRowSorter<SelectionTableModel>>();
		this.selected = new HashMap<String, JTextField>();
		this.viewMode = ViewMode.TYPE1;
		
		TableCellRenderer renderer = new AttributeTableCellRenderer(this);
		
		JPopupMenu menu = new AttributeTablePopupMenu(this, false, null, viewMode);
		this.setComponentPopupMenu(menu);
		
		for (String tableName : data.keySet()){
			
			sorters.put(tableName, new TableRowSorter<SelectionTableModel>(data.get(tableName))); 
			DoubleTable table = new DoubleTable(data.get(tableName), renderer, menu, sorters.get(tableName));
			selectionController.put(tableName, new TableSelectionController(table.getTable(), table.getLeftTable(), tableModels.get(tableName)));
			table.setTableSelectionForeground(Color.BLACK);
			table.setTableForeground(Color.BLACK);
			table.setTableSelectionBackground(Color.ORANGE);
			table.setTableBackground(Color.WHITE);
			JTextField field = new JTextField(20);
			selected.put(tableName, field);
			
			addComponent(tableName, table, createTableTabPanel(data.get(tableName).getData(), table, field, tableName));
		}
	}
	
	public JPanel createTableTabPanel(Data data, DoubleTable table, JTextField field, final String tableName){
		
		JPanel tab = new JPanel();
		tab.setName(tableName);
		tab.setLayout(new BorderLayout());
		tab.add(table, BorderLayout.CENTER);
		return tab;
	}

	public void updateView(){
		// iterate through all selected tables
		for (String tableName : getComps().keySet()){
			SlivisuObservationsTableModel tableModel = tableModels.get(tableName);
			tableModel.updateData();
			// if marked data has changed
			TableRowSorter<? extends TableModel> sorter = sorters.get(tableName);
			RowFilter filter = sorter.getRowFilter();
			sorter.setRowFilter(null);
			selectionController.get(tableName).setSelectedRowsToView(tableModel.getSelectedRows());
			sorter.setRowFilter(filter);
			setComponentVisible(tableName, tableModel.getRowCount() != 0);
			getComponent(tableName).repaint();
		}
		updateTabbedPane();
	}

	public void invertSelection(String tableName) {	
		SlivisuObservationsTableModel tableModel = tableModels.get(tableName);
		List<Integer> rows = new ArrayList<Integer>();
		List<Integer> selectedRows = tableModel.getSelectedRows();
		for( int i = 0 ; i < tableModel.getRowCount() ; i++){
			if(!selectedRows.contains(i)){
				rows.add(i);
			}
		}
		tableModel.setSelectedRows(rows);
	}

	// Getter und Setter ###########################    

	protected void setFilterMarked(boolean filterMarked) {
		for (String tableName : getComps().keySet()){
			SelectionFilter filter = null;
			if (filterMarked){
				filter = new SelectionFilter();
			}
			sorters.get(tableName).setRowFilter(filter);
		}
		updateView();
	}

	protected ViewMode getViewMode() {
		return viewMode;
	}

	protected void setViewMode(ViewMode viewMode) {
		this.viewMode = viewMode;
		updateView();
	}

	@Override
	public String getListenerName() {
		return "Listing";
	}

}
