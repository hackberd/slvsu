package slivisu.view.myviews;



import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;

import slivisu.data.MyZeitscheibe;
import slivisu.data.Sample;
import slivisu.gui.controller.InteractionListener;
import slivisu.mapper.SuperDataAnimationImpl;
import animation.Animation;
import animation.AnimationButton;
import animation.AnimationFpsPanel;
import animation.Animator;

public class AnimationView extends JPanel implements Animation  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int timeStep = 1;
	private int currentSliderLength = 0;
	private AnimationButton animationControlButton;
	private AnimationFpsPanel fpsPanel;
	private JLabel dateLabel;
	private JLabel currentYearLable;
	private InteractionListener listener;	
	private Animator animator;
	private SuperDataAnimationImpl data;
	private JComboBox checkBox;
	//private JPanel contentView;
	private JSlider slider;
	private int currentZsIndex = 0;
	
	//listener
	private AnimationActionListener actionListener;
	private AnimationsChangeListener changeListener;
	
	private List<MyZeitscheibe> aktuelleEbene;
	
	public void updateView() {
		this.data.updateData();
		//updateZS();
		this.data.updateWegnetzData();
	}
	public InteractionListener getListener() {
		return this.listener;
	}
	public AnimationView(SuperDataAnimationImpl data){
		this.data = data;
		this.animator = new Animator(this);
		init();
		this.animator.pause();
		this.animator.start();
		this.listener = new AnimationInteractionListener(this);
		this.animator.setFramesPerSecond(1);
	}
	
	public void init(){
		SpringLayout layout = new SpringLayout();
		
		
		String[] comboBoxStrings = { "Keine", "Ebene 1", "Ebene 2", "Ebene 3", "Ebene 4", "Ebene 5" };
		
		actionListener = new AnimationActionListener(this);
		
		// Top Half: Lable: CheckBox
		
		// Label
		dateLabel = new JLabel("Ebene: ");
//		dateLabel.setHorizontalAlignment(JLabel.LEFT);
//		dateLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		this.add(dateLabel);
		
		// Checkbox
		checkBox = new JComboBox(comboBoxStrings);
		//checkBox.setSelectedIndex(3);
		checkBox.addActionListener(actionListener);
	
		this.add(checkBox);
		
		// CONTROL BUTTON	
		animationControlButton = new AnimationButton(animator);
		animationControlButton.setText("Play");
		this.add(animationControlButton);
		
		final int heightTop = 25;
		final int heightBottom = 75;
		// Constaints lable
		layout.putConstraint(SpringLayout.WEST,		dateLabel,	5,			SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	dateLabel,	5,			SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		dateLabel,	60,	SpringLayout.WEST,	dateLabel);
		layout.putConstraint(SpringLayout.SOUTH,	dateLabel,	heightTop,	SpringLayout.NORTH,	this);
		// Constaints checkbox
		layout.putConstraint(SpringLayout.WEST,		checkBox,	5,			SpringLayout.EAST,	dateLabel);
		layout.putConstraint(SpringLayout.NORTH,	checkBox,	5,			SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		checkBox,	75,	SpringLayout.WEST,	checkBox);
		layout.putConstraint(SpringLayout.SOUTH,	checkBox,	heightTop,	SpringLayout.NORTH,	this);
		// Constaints button
		layout.putConstraint(SpringLayout.WEST,		animationControlButton,	5,			SpringLayout.EAST,	checkBox);
		layout.putConstraint(SpringLayout.NORTH,	animationControlButton,	5,			SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		animationControlButton,	75,	SpringLayout.WEST,	animationControlButton);
		layout.putConstraint(SpringLayout.SOUTH,	animationControlButton,	heightTop,	SpringLayout.NORTH,	this);
				
		currentYearLable = new JLabel("0");
		
		this.add(currentYearLable);
		// Constaints checkbox
		layout.putConstraint(SpringLayout.WEST,		currentYearLable,	5,			SpringLayout.EAST,	animationControlButton);
		layout.putConstraint(SpringLayout.NORTH,	currentYearLable,	5,			SpringLayout.NORTH,	this);
		layout.putConstraint(SpringLayout.EAST,		currentYearLable,	75,	SpringLayout.EAST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	currentYearLable,	heightTop,	SpringLayout.NORTH,	this);
		
		
		
//		contentView = new JPanel();
		
		//this.add(contentView);
//		layout.putConstraint(SpringLayout.WEST,		contentView,	5,			SpringLayout.WEST,	this);
//		layout.putConstraint(SpringLayout.NORTH,	contentView,	5,			SpringLayout.SOUTH,	dateLabel);
//		layout.putConstraint(SpringLayout.EAST,		contentView,	5,			SpringLayout.EAST,	this);
//		layout.putConstraint(SpringLayout.SOUTH,	contentView,	heightBottom,			SpringLayout.SOUTH,	dateLabel);
		
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0); 
		changeListener = new AnimationsChangeListener(this);
		slider.addChangeListener(changeListener);

	
		slider.setMajorTickSpacing(200); 
		//slider.setMinorTickSpacing(20);
		slider.setPaintTicks(true);
		//slider.setPaintLabels(true);
		
		this.add(slider);
		layout.putConstraint(SpringLayout.WEST,		slider,	5,			SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	slider,	heightBottom,SpringLayout.SOUTH,	dateLabel);
		layout.putConstraint(SpringLayout.EAST,		slider,	5,			SpringLayout.EAST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	slider,	heightBottom + 40,			SpringLayout.SOUTH,	dateLabel);
		
//		animationControlButton = new AnimationButton(animator);
//
//		fpsPanel = new AnimationFpsPanel(animator);
//		
//		
//		
//		
//		JPanel controlPanel = new JPanel(new BorderLayout());
//		controlPanel.add(dateLabel, BorderLayout.NORTH);
//		controlPanel.add(animationControlButton, BorderLayout.WEST);
//		controlPanel.add(fpsPanel, BorderLayout.EAST);
//		
//		add(controlPanel, BorderLayout.CENTER);
		setLayout(layout);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDetail(g);
	}
	
	public void paintDetail(Graphics g) {
		if ((data != null) && (aktuelleEbene != null)) {
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
			
			final int PADDING	= 5;
			
			int xMin;
			int xMax;
			int yMin2		= 28;
			int yMin		= 53;
			int yMax		= 78;
			int min			= slider.getMinimum();
			int max			= slider.getMaximum();
			int rangeTime	= max- min;
			
			for (MyZeitscheibe zs : aktuelleEbene) {
				xMin	= (int)	(				 + PADDING + ( (double) (zs.getAnfang() - min) / (double) rangeTime) * (this.getWidth() - 2*PADDING));
				xMax	= (int)	(this.getWidth() - PADDING - ( (double) (max - zs.getEnde()) / (double) rangeTime)   * (this.getWidth() - 2*PADDING));
				
				Rectangle2D rect;
				rect = new Rectangle(	xMin,
						yMin,
						(xMax - xMin),
						(yMax - yMin));
				
				g2d.setColor(Color.WHITE);
				g2d.fill(rect);
				
				g2d.setColor(Color.BLACK);
				g2d.draw(rect);
			}
			
			xMin	= (int)	(				 + PADDING + ( (double) (aktuelleEbene.get(currentZsIndex).getAnfang() - min) / (double) rangeTime) * (this.getWidth() - 2*PADDING));
			xMax	= (int)	(this.getWidth() - PADDING - ( (double) (max - aktuelleEbene.get(currentZsIndex).getEnde()) / (double) rangeTime)   * (this.getWidth() - 2*PADDING));
			
			Rectangle2D rect;
			rect = new Rectangle(	xMin,
					yMin2,
					(xMax - xMin),
					(yMin - yMin2 - PADDING));
			
			String name = aktuelleEbene.get(currentZsIndex).getName();
			
			g2d.setColor(Color.WHITE);
			g2d.fill(rect);
			
			g2d.setColor(Color.BLACK);
			g2d.drawString(name, xMin + PADDING, yMin - PADDING);
			g2d.draw(rect);
		}
	}
	
	public void updateZS() {
		int setting = this.actionListener.getSetting() ;
		if (setting != 0 && this.data.getDataForEbene(setting) != null) {
			this.aktuelleEbene = this.data.getDataForEbene(setting);
			MyZeitscheibe first = this.aktuelleEbene.get(0);
			
			
			
			MyZeitscheibe last = this.aktuelleEbene.get(this.aktuelleEbene.size() -1);
			int min = first.getAnfang();
			int max = last.getEnde();
			
			
			slider.setMinimum(min);
			slider.setMaximum(max);
			
			slider.setValue(min);
			currentYearLable.setText(" "+min);
			currentSliderLength = Math.abs(max - min);
			int mt = currentSliderLength / 6;
			//System.out.println("mt" + mt);
			slider.setMajorTickSpacing(mt - 2);
			//slider.setMinorTickSpacing((mt - 2) / 2);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			
			//
			
			// TODO Male die Zeitscheiben in contentView (copy paste aus Übersicht oder Detail?)
		}
	}

	public void sliderChangedTo(int year) {
		if (this.aktuelleEbene != null) {
			Set<Sample> markSamples = new HashSet<Sample>();
			LinkedList<MyZeitscheibe> currZs = new LinkedList<MyZeitscheibe>();
			
			int i = 0;
			for (MyZeitscheibe zeitscheibe : this.aktuelleEbene) {
				
				if (zeitscheibe.getAnfang() <= year && zeitscheibe.getEnde() >= year) { 
					markSamples.addAll(zeitscheibe.getAlleSamplesInZeitscheibe());
					data.setYear(year);
					currZs.add(zeitscheibe);
					this.currentZsIndex = i;
				};
				i++;
			}	
			if (year != 0) {
				this.data.data.setCurrentZeitscheibe(currZs);
			}
			
			this.data.data.getSelectedSamples().set(markSamples);
			
			
		
			//for (int i = 0; i< this.aktuelleEbene.size())
			repaint();
		}
		
	}
	
	@Override
	public void next() {
		if (this.aktuelleEbene != null) {
			//repaint();
			currentZsIndex++;
			
			if (this.aktuelleEbene.size() > currentZsIndex ) {
				slider.setValue(this.aktuelleEbene.get(currentZsIndex).getAnfang());
				
			} else {
				currentZsIndex = 0;
				slider.setValue(this.aktuelleEbene.get(currentZsIndex).getAnfang());
			}
			
			currentYearLable.setText("Y: "+slider.getValue());
			//this.data.updateData(timeStep);
		}
		
	}
	
	public void paused() {
		this.data.data.setCurrentZeitscheibe(null);
	}
	
}
