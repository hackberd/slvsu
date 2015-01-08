package slivisu.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ObservationTableData{

	// mehrere Datentabellen möglich
	private List<String> tableNames;
	/** für jede Datentabelle Liste mit allen Attributen*/
	private Map<String, Vector<String>> tableColumns;
	/** für jede Datentabelle Map für Attribute mit allen Attribut-Typen*/
	private Map<String, Map<String, Class<?>>> tableColumnTypes;

	private String name = "Siedlungsfunde";

	public ObservationTableData(){
		this.init();
	}

	private void init(){

		tableNames = new Vector<String>();
		tableNames.add(name);
		
		tableColumns = new HashMap<String, Vector<String>>();
		tableColumnTypes = new HashMap<String, Map<String, Class<?>>>();

		Vector<String> columnNames = new Vector<String>();
		Map<String, Class<?>> columns = new HashMap<String, Class<?>>();

		columnNames.add("Gemeinde");
		columns.put("Gemeinde", String.class);

		columnNames.add("Ort");
		columns.put("Ort", String.class);

		columnNames.add("Fundart");
		columns.put("Fundart", String.class);

		columnNames.add("Region");
		columns.put("Region", String.class);

		columnNames.add("Department");
		columns.put("Department", String.class);

		columnNames.add("Longitude");
		columns.put("Longitude", Double.class);

		columnNames.add("Latitude");
		columns.put("Latitude", Double.class);

			
		tableColumns.put(name, columnNames);
		tableColumnTypes.put(name, columns);
	}

	/**
	 * @param table
	 * @param sample
	 * @return
	 */
	public Vector<?> getTableRow(String table, Sample sample) {

		Vector<Object> tableRow = new Vector<Object>();
		tableRow.add(sample.getGemeinde());
		tableRow.add(sample.getOrt());
		tableRow.add(sample.getFundart());
		tableRow.add(sample.getRegion());
		tableRow.add(sample.getDepartment());
		tableRow.add(sample.getLon());
		tableRow.add(sample.getLat());
		
		return tableRow;
	}

	public Vector<String> getTableColumns(String tableName) {
		return tableColumns.get(tableName);
	}

	public Map<String, Class<?>> getTableColumnTypes(String tableName) {
		return tableColumnTypes.get(tableName);
	}

	public List<String> getTableNames(){
		return tableNames;
	}
}
