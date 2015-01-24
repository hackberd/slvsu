package slivisu.view.myviews;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import slivisu.gui.controller.InteractionListener;

public class Detail extends JPanel implements InteractionListener{

	private static final long serialVersionUID = 1L;
	
	// Variablen
	private SuperDataDetail data;
	
	private DetailPanel detail;
	private JScrollPane scrollPane;
	private SpringLayout layout;
	
	private DetailItemListener itemListener;
	
	private JCheckBox filter1;
	private JCheckBox filter2;
	private JCheckBox filter3;
	private JCheckBox filter4;
	private JCheckBox filter5;
	
	public Detail(SuperDataDetail data) {
		this.data	= data;
		
		layout			= new SpringLayout();
		detail			= new DetailPanel(data);
		scrollPane		= new JScrollPane(detail);
		
		filter1			= new JCheckBox("Level 1", true);
		filter2			= new JCheckBox("Level 2", true);
		filter3			= new JCheckBox("Level 3", true);
		filter4			= new JCheckBox("Level 4", true);
		filter5			= new JCheckBox("Level 5", true);
		
		itemListener	= new DetailItemListener(this);
		
		filter1.addItemListener(itemListener);
		filter2.addItemListener(itemListener);
		filter3.addItemListener(itemListener);
		filter4.addItemListener(itemListener);
		filter5.addItemListener(itemListener);
		
		// TODO: adden
		add(scrollPane);
		add(filter1);
		add(filter2);
		add(filter3);
		add(filter4);
		add(filter5);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		layout.putConstraint(SpringLayout.WEST,		scrollPane,	5,		SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	scrollPane,	5,		SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		scrollPane,	-5,		SpringLayout.EAST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	scrollPane,	-35,	SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		filter1,	5,	SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter1,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter1,	75,	SpringLayout.WEST,	filter1);
		layout.putConstraint(SpringLayout.SOUTH,	filter1,	-5,	SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		filter2,	5,	SpringLayout.EAST,	filter1);
		layout.putConstraint(SpringLayout.NORTH,	filter2,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter2,	75,	SpringLayout.WEST,	filter2);
		layout.putConstraint(SpringLayout.SOUTH,	filter2,	-5,	SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		filter3,	5,	SpringLayout.EAST,	filter2);
		layout.putConstraint(SpringLayout.NORTH,	filter3,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter3,	75,	SpringLayout.WEST,	filter3);
		layout.putConstraint(SpringLayout.SOUTH,	filter3,	-5,	SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		filter4,	5,	SpringLayout.EAST,	filter3);
		layout.putConstraint(SpringLayout.NORTH,	filter4,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter4,	75,	SpringLayout.WEST,	filter4);
		layout.putConstraint(SpringLayout.SOUTH,	filter4,	-5,	SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		filter5,	5,	SpringLayout.EAST,	filter4);
		layout.putConstraint(SpringLayout.NORTH,	filter5,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter5,	75,	SpringLayout.WEST,	filter5);
		layout.putConstraint(SpringLayout.SOUTH,	filter5,	-5,	SpringLayout.SOUTH,	this);
		
		this.setLayout(layout);
		
		setFilter();
	}
	
	@Override
	public void updateView() {
		detail.updateView();
	}

	@Override
	public String getListenerName() {
		return "DetailListenerName";
	}

	public void setFilter() {
		List<Boolean> settings = new LinkedList<Boolean>();
		
		settings.add(filter1.isSelected());
		settings.add(filter2.isSelected());
		settings.add(filter3.isSelected());
		settings.add(filter4.isSelected());
		settings.add(filter5.isSelected());
		
		detail.setFilter(settings);
	}
}
