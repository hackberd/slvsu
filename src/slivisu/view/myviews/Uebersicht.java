/**
 * 
 */
package slivisu.view.myviews;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.ToolTipManager;

import slivisu.data.MyZeitscheibe;
import slivisu.data.datatype.Balken;
import slivisu.gui.controller.InteractionListener;

public class Uebersicht extends JPanel implements InteractionListener {

	private static final long serialVersionUID = 1L;
	
	// Konstanten
	private final int PADDING	= 5;
	
	// Variablen
	public SuperDataUebersicht data;
	
	private UebersichtPanel uebersichtPanel;
	private JScrollPane scrollPane;
	private SpringLayout layout;
	
	private UebersichtListener listener;
	
	private UebersichtItemListener itemListener;
	
	private JCheckBox filter1;
	private JCheckBox filter2;
	private JCheckBox filter3;
	private JCheckBox filter4;
	private JCheckBox filter5;
	
	public Uebersicht(SuperDataUebersicht data){
		this.data = data;
		
		layout			= new SpringLayout();
		uebersichtPanel	= new UebersichtPanel(data);
		scrollPane		= new JScrollPane(uebersichtPanel);
		
		filter1			= new JCheckBox("Level 1", true);
		filter2			= new JCheckBox("Level 2", true);
		filter3			= new JCheckBox("Level 3", true);
		filter4			= new JCheckBox("Level 4", true);
		filter5			= new JCheckBox("Level 5", true);
		
		itemListener	= new UebersichtItemListener(this);
		
		filter1.addItemListener(itemListener);
		filter2.addItemListener(itemListener);
		filter3.addItemListener(itemListener);
		filter4.addItemListener(itemListener);
		filter5.addItemListener(itemListener);
		
		add(scrollPane);
		
		add(filter1);
		add(filter2);
		add(filter3);
		add(filter4);
		add(filter5);
		
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		layout.putConstraint(SpringLayout.WEST,		scrollPane,	5,		SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	scrollPane,	35,		SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		scrollPane,	-5,		SpringLayout.EAST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	scrollPane,	-5,		SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		filter1,	5,	SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter1,	5,	SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter1,	75,	SpringLayout.WEST,	filter1);
		layout.putConstraint(SpringLayout.SOUTH,	filter1,	-5,	SpringLayout.NORTH,	scrollPane);
		
		layout.putConstraint(SpringLayout.WEST,		filter2,	5,	SpringLayout.EAST,	filter1);
		layout.putConstraint(SpringLayout.NORTH,	filter2,	5,	SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter2,	75,	SpringLayout.WEST,	filter2);
		layout.putConstraint(SpringLayout.SOUTH,	filter2,	-5,	SpringLayout.NORTH,	scrollPane);
		
		layout.putConstraint(SpringLayout.WEST,		filter3,	5,	SpringLayout.EAST,	filter2);
		layout.putConstraint(SpringLayout.NORTH,	filter3,	5,	SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter3,	75,	SpringLayout.WEST,	filter3);
		layout.putConstraint(SpringLayout.SOUTH,	filter3,	-5,	SpringLayout.NORTH,	scrollPane);
		
		layout.putConstraint(SpringLayout.WEST,		filter4,	5,	SpringLayout.EAST,	filter3);
		layout.putConstraint(SpringLayout.NORTH,	filter4,	5,	SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter4,	75,	SpringLayout.WEST,	filter4);
		layout.putConstraint(SpringLayout.SOUTH,	filter4,	-5,	SpringLayout.NORTH,	scrollPane);
		
		layout.putConstraint(SpringLayout.WEST,		filter5,	5,	SpringLayout.EAST,	filter4);
		layout.putConstraint(SpringLayout.NORTH,	filter5,	5,	SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter5,	75,	SpringLayout.WEST,	filter5);
		layout.putConstraint(SpringLayout.SOUTH,	filter5,	-5,	SpringLayout.NORTH,	scrollPane);
		
		this.setLayout(layout);
	}
	
	@Override
	public void updateView() {
		uebersichtPanel.updateView();
	}
	
	@Override
	public String getListenerName() {
		return "UebersichtListenerName";
	}

	public void setFilter() {
		List<Boolean> settings = new LinkedList<Boolean>();
		
		settings.add(filter1.isSelected());
		settings.add(filter2.isSelected());
		settings.add(filter3.isSelected());
		settings.add(filter4.isSelected());
		settings.add(filter5.isSelected());
		
		uebersichtPanel.setFilter(settings);
	}

}
