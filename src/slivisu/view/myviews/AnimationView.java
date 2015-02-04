package slivisu.view.myviews;



import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	private AnimationButton animationControlButton;
	private AnimationFpsPanel fpsPanel;
	private JLabel dateLabel;
	private InteractionListener listener;	
	private Animator animator;
	private SuperDataAnimationImpl data;
	public void updateView() {
		
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
	}
	
	public void init(){
		setLayout(new BorderLayout());
		
		dateLabel = new JLabel("" + timeStep);
		dateLabel.setHorizontalAlignment(JLabel.CENTER);
		dateLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		animationControlButton = new AnimationButton(animator);

		fpsPanel = new AnimationFpsPanel(animator);
		
		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.add(dateLabel, BorderLayout.NORTH);
		controlPanel.add(animationControlButton, BorderLayout.WEST);
		controlPanel.add(fpsPanel, BorderLayout.EAST);
		
		add(controlPanel, BorderLayout.CENTER);
	}
	
	@Override
	public void next() {
		timeStep ++;
		this.dateLabel.setText(""+timeStep);
		this.data.updateData(timeStep);
	}
}
