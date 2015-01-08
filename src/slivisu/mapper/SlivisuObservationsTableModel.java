package slivisu.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import slivisu.data.Data;
import slivisu.data.Sample;
import slivisu.defaults.doubletable.DoubleTableModel;
import slivisu.view.table.SelectionTableModel;

public class SlivisuObservationsTableModel extends AbstractTableModel implements SelectionTableModel, DoubleTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Data data;
	private String tableName;

	private Vector<String> headerColumns;
	private Vector<String> attributeColumns;
	private Map<String, Class<?>> columnTypes;
	private Vector<Vector<?>> tableData = null;
	private List<Sample> rows = null;

	public SlivisuObservationsTableModel(String tableName, Data data){
		this.tableName = tableName;
		this.data = data;
		this.tableData = null;
		this.headerColumns = new Vector<String>();

		this.attributeColumns = new Vector<String>();
		this.attributeColumns.addAll(data.getObservationTableData().getTableColumns(tableName));

		this.columnTypes = new HashMap<String, Class<?>>();
		this.columnTypes.putAll(data.getObservationTableData().getTableColumnTypes(tableName));
		
		this.rows = new Vector<Sample>();

		updateData();
	}

	public String getColumnName(int columnIndex){
		return attributeColumns.get(columnIndex);
	}

	@Override
	public int getColumnCount() {
		return attributeColumns.size();
	}

	@Override
	public int getRowCount() {
		return tableData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return tableData.get(rowIndex).get(columnIndex);
	}

	public void updateData(){
		if (tableData == null || data.getSelectedSamples().isChanged()){
			tableData = new Vector<Vector<?>>();
			
			rows.clear();
			
			for (Sample sample : data.getSelectedSamples().getAll()){
				rows.add(sample);
				Vector<?> rowData = data.getObservationTableData().getTableRow(tableName, sample);
				if (rowData != null){
					Vector<Object> row = new Vector<Object>();
					row.addAll(rowData);
					tableData.add(row);
				}
			}
			fireTableDataChanged();
		}
	}

	private Sample getSample(int rowIndex){
		return rows.get(rowIndex);
	}

	
	public void setSelectedRows(List<Integer> rows){
		List<Sample> marked = new Vector<Sample>();
		for (int row : rows){
			marked.add(getSample(row));
		}
		data.getMarkedSamples().set(marked);
	}

	public Vector<Integer> getSelectedRows(){

		Vector<Integer> selectedRows = new Vector<Integer>();
		Collection<Sample> ids = data.getMarkedSamples().getAll();

		if (ids != null) {
			for (int k = 0; k < getRowCount(); k++) {
				if (ids.contains(getSample(k).toString())) {
					selectedRows.add(k);
				}
			}
		}
		return selectedRows;
	}

	public Vector<String> getHeaderColumns(){
		return headerColumns;
	}

	@Override
	public Vector<String> getColumns() {
		return attributeColumns;
	}

	public Class<?> getColumnClass(int columnIndex){
		return columnTypes.get(getColumnName(columnIndex));
	}

	public Data getData() {
		return data;
	}

	public List<Sample> getSelectedSamples(List<Integer> rows) {
		List<Sample> marked = new Vector<Sample>();
		for (int row : rows){
			marked.add(getSample(row));
		}
		return marked;
	}
}