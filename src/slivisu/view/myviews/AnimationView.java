package slivisu.view.myviews;



import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import slivisu.data.MyZeitscheibe;
import slivisu.data.Sample;
import slivisu.data.Zeitscheibe;
import slivisu.data.selection.Selection;
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
	private JPanel contentView;
	private JSlider slider;
	private int currentZsIndex = 0;
	
	//listener
	private AnimationActionListener actionListener;
	private AnimationsChangeListener changeListener;
	
	public void updateView() {
		this.data.updateData();
		//updateZS();
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
		final int heightBottom = 50;
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
		
		
		
		contentView = new JPanel();
		
		this.add(contentView);
		layout.putConstraint(SpringLayout.WEST,		contentView,	5,			SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	contentView,	5,			SpringLayout.SOUTH,	dateLabel);
		layout.putConstraint(SpringLayout.EAST,		contentView,	5,			SpringLayout.EAST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	contentView,	heightBottom,			SpringLayout.SOUTH,	dateLabel);
		
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0); 
		changeListener = new AnimationsChangeListener(this);
		slider.addChangeListener(changeListener);

		// TODO set this
		slider.setMajorTickSpacing(200); 
		//slider.setMinorTickSpacing(20);
		slider.setPaintTicks(true);
		//slider.setPaintLabels(true);
		
		this.add(slider);
		layout.putConstraint(SpringLayout.WEST,		slider,	5,			SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.NORTH,	slider,	5,			SpringLayout.SOUTH,	contentView);
		layout.putConstraint(SpringLayout.EAST,		slider,	5,			SpringLayout.EAST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	slider,	75,			SpringLayout.SOUTH,	contentView);
		
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
	private List<MyZeitscheibe> aktuelleEbene;
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
		}
	}

	public void sliderChangedTo(int year) {
		if (this.aktuelleEbene != null) {
			Set<Sample> markSamples = new HashSet<Sample>();
			for (MyZeitscheibe zeitscheibe : this.aktuelleEbene) {
					if (zeitscheibe.getAnfang() <= year && zeitscheibe.getEnde() >= year) { 
						markSamples.addAll(zeitscheibe.getAlleSamplesInZeitscheibe());
					};
			}	
			this.data.data.getMarkedSamples().set(markSamples);
		}
		
	}
	
	@Override
	public void next() {
		if (this.aktuelleEbene != null) {
			
			if (this.aktuelleEbene.size() > currentZsIndex) {
				slider.setValue(this.aktuelleEbene.get(currentZsIndex).getAnfang());
				currentZsIndex++;
			} else {
				currentZsIndex = 0;
			}
			
			currentYearLable.setText("Y: "+slider.getValue());
			//this.data.updateData(timeStep);
		}
		
	}
	
}
