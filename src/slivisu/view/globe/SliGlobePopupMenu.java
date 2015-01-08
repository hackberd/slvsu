package slivisu.view.globe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;


public class SliGlobePopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SliGlobe sliglobe;

	public SliGlobePopupMenu(SliGlobe sliglobe) {
		this.sliglobe = sliglobe;
		initial();
	}

	public void addMenuItems(List<JMenuItem> items, int position){
		this.insert(new JSeparator(), position++);
		for (JMenuItem item : items){
			this.insert(item, position++);
		}
	}
	
	private void initial() {
		this.add(menuShape());
		//ButtonGroup gr = new ButtonGroup();
		//this.add(itemShowSLI(gr));
		//this.add(itemOnlySelection(gr));
	}

	private JMenu menuShape() {
		JMenu menu = new JMenu("projection");
		JCheckBoxMenuItem itemRound = new JCheckBoxMenuItem("Globe");
		itemRound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sliglobe.setGlobe(false);
			}
		});
		JCheckBoxMenuItem itemFlat = new JCheckBoxMenuItem("Map");
		itemFlat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sliglobe.setGlobe(true);
			}
		});
		menu.add(itemRound);
		menu.add(itemFlat);
		ButtonGroup gr = new ButtonGroup();
		gr.add(itemRound);
		gr.add(itemFlat);
		this.addSeparator();
		String[] proj = new String[] { "Mercator", "Lat-Lon", "Modified Sin.", "Sinusoidal" };
		ButtonGroup gr2 = new ButtonGroup();
		for (String s : proj) {
			JCheckBoxMenuItem item = cbItem(s);
			menu.add(item);
			gr2.add(item);
		}
		return menu;
	}

	private JCheckBoxMenuItem cbItem(final String itemName) {
		JCheckBoxMenuItem cbItem = new JCheckBoxMenuItem(itemName);
		cbItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sliglobe.setProjection(itemName);
				sliglobe.updateProjection();
			}
		});
		return cbItem;
	}
	
	/*
	private JCheckBoxMenuItem itemShowSLI(ButtonGroup gr) {
		JCheckBoxMenuItem itemShowSLI = new JCheckBoxMenuItem("Show SLI");
		gr.add(itemShowSLI);
		itemShowSLI.setFont(new Font("SansSerif", Font.PLAIN, 11));
		itemShowSLI.setSelected(sliglobe.isShowSLI());
		itemShowSLI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem item = (JCheckBoxMenuItem)(e.getSource());
				sliglobe.setShowSLI(item.isSelected());
				sliglobe.setOnlySelection(!item.isSelected());
			}
		});
		return itemShowSLI;
	}
	
	private JCheckBoxMenuItem itemOnlySelection(ButtonGroup gr) {
		JCheckBoxMenuItem itemOnlySelection = new JCheckBoxMenuItem("Only selection");
		gr.add(itemOnlySelection);
		itemOnlySelection.setFont(new Font("SansSerif", Font.PLAIN, 11));
		itemOnlySelection.setSelected(sliglobe.isOnlySelection());
		itemOnlySelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem item = (JCheckBoxMenuItem)(e.getSource());
				sliglobe.setShowSLI(!item.isSelected());
				sliglobe.setOnlySelection(item.isSelected());
			}
		});
		return itemOnlySelection;
	}
	*/
}
