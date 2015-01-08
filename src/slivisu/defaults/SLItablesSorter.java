/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults;

import java.util.Comparator;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * 
 * @author Sven
 */
public class SLItablesSorter extends TableRowSorter<TableModel> {

	/**
	 * Erzeugen eines TableRowSorter fuer die Spalten der Tabelle
	 * 
	 * @param tModel
	 */
	public SLItablesSorter(TableModel tModel) {

		this.setModel(tModel);

		for (int i = 0; i < tModel.getColumnCount(); i++) {
			Object value = tModel.getValueAt(0, i);

			//				System.out.println("--->  " + value);

			if (value instanceof Double) {
				this.setComparator(i, new Comparator<Double>() {

					@Override
					public int compare(Double o1, Double o2) {
						if (o1 < o2) {
							return -1;
						}
						if (o1 > o2) {
							return 1;
						}
						return 0;
					}

				});
			} 
			else if (value instanceof Integer) {

				this.setComparator(i, new Comparator<Integer>() {

					@Override
					public int compare(Integer o1, Integer o2) {
						if (o1 < o2) {
							return -1;
						}
						if (o1 > o2) {
							return 1;
						}
						return 0;
					}

				});
			}
		}
	}

}
