package slivisu.view.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Sven
 */
public class AttributeTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AttributeTables attribController;

	public AttributeTableCellRenderer(AttributeTables attribController) {
		this.attribController = attribController;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color color = table.getBackground();
		Object val = table.getValueAt(row, 1);

		switch (attribController.getViewMode()) {
		case TYPE1: // Kategoriespalte in entsprechender Farbe, Selektion einheitlich
			if (column == 0) {
			} 
			else if (isSelected) {
				color = table.getSelectionBackground();
			}
			break;

		case TYPE2: // alle Zeilen in Kategoriefarben, markierte SLIs in Markierungsfarbe
			if (isSelected) {
				color = table.getSelectionBackground();
			}
			// setFontSize( table , cell , row , isSelected );
			break;

		case TYPE3: // alle Zeilen in weiﬂ, markierte Zeilen in Kategoriefarbe
			if (isSelected) {
			}
			break;
		}
		cell.setBackground(color);
		return cell;
	}

//	/**
//	 * Anpassung der Schriftgroesse und Zeilenhoehe !!! Bei Aktivierung einfrieren des WorldWind Globes !!!
//	 * 
//	 * @param table
//	 * @param cell
//	 * @param row
//	 * @param selected
//	 */
//	private void setFontSize(JTable table, Component cell, int row, boolean selected) {
//
//		if (selected) {
//			cell.setFont(new Font("SansSerif", Font.BOLD, 11));
//			table.setRowHeight(row, 13);
//		} else {
//			cell.setFont(new Font("SansSerif", Font.PLAIN, 6));
//			table.setRowHeight(row, 6);
//		}
//	}
}
