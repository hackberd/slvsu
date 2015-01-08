package slivisu.view.table;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

import slivisu.view.table.AttributeTables.ViewMode;

/**
 * 
 * @author Sven
 */
public class AttributeTablePopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AttributeTables contr;

	public AttributeTablePopupMenu(AttributeTables contr, boolean showAll, ViewMode[] viewTypes, ViewMode currentViewType) {
		super();
		this.contr = contr;
		init(showAll, viewTypes, currentViewType);
	}

	private void init(boolean showAll, ViewMode[] viewTypes, ViewMode currentViewType) {
		this.add(itemShowAll(showAll));
		if (viewTypes != null){
			this.addSeparator();
			ButtonGroup gr = new ButtonGroup();
			for (ViewMode viewType : viewTypes){
				JCheckBoxMenuItem itemViewType = itemViewType(viewType, viewType == currentViewType);
				gr.add(itemViewType);
				this.add(itemViewType);
			}
		}
		this.add(invertSelection());
	}

	/**
	 * Umschalten zwischen der gesamten Datenauswahl und den markierten SLIs
	 * 
	 * @return
	 */
	private JCheckBoxMenuItem itemShowAll(boolean selected) {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Only selection");
		item.setFont(new Font("SansSerif", Font.PLAIN, 11));
		item.setSelected(selected);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (item.isSelected()) {
					//item.setText("Show Only Marked SLI");
					contr.setFilterMarked(true);
				} 
				else {
					//item.setText("Show All SLI");
					contr.setFilterMarked(false);
				}
			}
		});
		return item;
	}
	
	private JCheckBoxMenuItem invertSelection() {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Invert Selection");
		item.setFont(new Font("SansSerif", Font.PLAIN, 11));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tableName = contr.getSelectedComponent().getName();
				item.setSelected(false);
				contr.invertSelection(tableName);
			}
		});
		return item;
	}

	private JCheckBoxMenuItem itemViewType(final ViewMode viewType, boolean selected) {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem(viewType.toString());
		item.setFont(new Font("SansSerif", Font.PLAIN, 11));
		item.setSelected(selected);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (item.isSelected()) {
					contr.setViewMode(viewType);
				}
			}
		});
		return item;
	}
}
