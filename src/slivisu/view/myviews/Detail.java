package slivisu.view.myviews;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;

import slivisu.data.Sample;
import slivisu.data.datatype.Balken;
import slivisu.data.selection.Selection;
import slivisu.gui.controller.InteractionListener;

public class Detail extends JPanel implements InteractionListener{

	private static final long serialVersionUID = 1L;
	
	// Variablen
	private SuperDataDetail data;
	
	private DetailPanel detail;
	private JScrollPane scrollPane;
	private SpringLayout layout;
	
	private DetailItemListener itemListener;
	
	private DetailActionListener actionListener;
	
	private JCheckBox filter1;
	private JCheckBox filter2;
	private JCheckBox filter3;
	private JCheckBox filter4;
	private JCheckBox filter5;
	private JCheckBox filter6;
	private JCheckBox filter7;
	public JButton first;
	public JButton oneHigher;
	
	
	public Detail(SuperDataDetail data) {
		this.data	= data;
		
		layout			= new SpringLayout();
		detail			= new DetailPanel(data, this);
		scrollPane		= new JScrollPane(detail);
		
		filter1			= new JCheckBox("Level 1", true);
		filter2			= new JCheckBox("Level 2", true);
		filter3			= new JCheckBox("Level 3", true);
		filter4			= new JCheckBox("Level 4", true);
		filter5			= new JCheckBox("Level 5", true);
		filter6			= new JCheckBox("Sicher", true);
		filter7			= new JCheckBox("Unsicher", true);
		
		first			= new JButton("Region");
		oneHigher		= new JButton("Name Z-A");
		
		itemListener	= new DetailItemListener(this);
		actionListener	= new DetailActionListener(this);
		
		
		detail.addMouseListener(actionListener);
		detail.addMouseMotionListener(actionListener);
		
		
		filter1.addItemListener(itemListener);
		filter2.addItemListener(itemListener);
		filter3.addItemListener(itemListener);
		filter4.addItemListener(itemListener);
		filter5.addItemListener(itemListener);
		filter6.addItemListener(itemListener);
		filter7.addItemListener(itemListener);
		
		first.addActionListener(actionListener);
		oneHigher.addActionListener(actionListener);
		
		first.setActionCommand("none");
		oneHigher.setActionCommand("name-asc");
		
		// TODO: adden
		add(scrollPane);
		add(filter1);
		add(filter2);
		add(filter3);
		add(filter4);
		add(filter5);
		add(filter6);
		add(filter7);
		
		add(first);
		add(oneHigher);
	
		
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
		
		
		layout.putConstraint(SpringLayout.WEST,		filter6,	5,	SpringLayout.EAST,	filter5);
		layout.putConstraint(SpringLayout.NORTH,	filter6,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter6,	75,	SpringLayout.WEST,	filter6);
		layout.putConstraint(SpringLayout.SOUTH,	filter6,	-5,	SpringLayout.SOUTH,	this);
		
		
		layout.putConstraint(SpringLayout.WEST,		filter7,	5,	SpringLayout.EAST,	filter6);
		layout.putConstraint(SpringLayout.NORTH,	filter7,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		filter7,	100,SpringLayout.WEST,	filter7);
		layout.putConstraint(SpringLayout.SOUTH,	filter7,	-5,	SpringLayout.SOUTH,	this);
		
		
		layout.putConstraint(SpringLayout.WEST,		first,	5,	SpringLayout.EAST,	filter7);
		layout.putConstraint(SpringLayout.NORTH,	first,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		first,	100,SpringLayout.WEST,	first);
		layout.putConstraint(SpringLayout.SOUTH,	first,	-5,	SpringLayout.SOUTH,	this);
		
		layout.putConstraint(SpringLayout.WEST,		oneHigher,	5,	SpringLayout.EAST,	first);
		layout.putConstraint(SpringLayout.NORTH,	oneHigher,	5,	SpringLayout.SOUTH,	scrollPane);
		layout.putConstraint(SpringLayout.EAST,		oneHigher,	100,SpringLayout.WEST,	oneHigher);
		layout.putConstraint(SpringLayout.SOUTH,	oneHigher,	-5,	SpringLayout.SOUTH,	this);
		
//		layout.putConstraint(SpringLayout.WEST,		oneLower,	5,	SpringLayout.EAST,	oneHigher);
//		layout.putConstraint(SpringLayout.NORTH,	oneLower,	5,	SpringLayout.SOUTH,	scrollPane);
//		layout.putConstraint(SpringLayout.EAST,		oneLower,	100,SpringLayout.WEST,	oneLower);
//		layout.putConstraint(SpringLayout.SOUTH,	oneLower,	-5,	SpringLayout.SOUTH,	this);
//		
//		layout.putConstraint(SpringLayout.WEST,		last,	5,	SpringLayout.EAST,	oneLower);
//		layout.putConstraint(SpringLayout.NORTH,	last,	5,	SpringLayout.SOUTH,	scrollPane);
//		layout.putConstraint(SpringLayout.EAST,		last,	100,SpringLayout.WEST,	last);
//		layout.putConstraint(SpringLayout.SOUTH,	last,	-5,	SpringLayout.SOUTH,	this);
		
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
		settings.add(filter6.isSelected());
		settings.add(filter7.isSelected());
		
		detail.setFilter(settings);
	}

	public void buttonClicked(String actionCommand) {
		detail.buttonClicked(actionCommand);
	}

	public void isHit(MouseEvent arg0, boolean b) {
		
		if (this.detail.rectsAufSamples != null){
			
			for (Rectangle rect : this.detail.rectsAufSamples.keySet()) {
					if (rect != null && rect.contains(arg0.getPoint())) {
						LinkedList<Sample> col = new LinkedList<Sample>();
						col.add(this.detail.rectsAufSamples.get(rect));
						//System.out.println("selecte" +  this.detail.rectsAufSamples.get(rect));
						if (!b) {
							if (this.data.getData().getMarkedSamples().getAll().size() == 1 && this.data.getData().getMarkedSamples().contains(this.detail.rectsAufSamples.get(rect))) {
								
							} else {this.data.getData().getMarkedSamples().set(col);}
							
						} else {
							if (this.data.getData().getSelectedSamples().getAll().size() == 1 && this.data.getData().getSelectedSamples().contains(this.detail.rectsAufSamples.get(rect))) {
								
							} else {this.data.getData().getSelectedSamples().set(col);}
							
						}
						
						//this.data.getData().controller.receive();
					}
				
			}
		}
	}
}
