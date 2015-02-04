package animation;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnimationFpsPanel extends JPanel implements ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7918511771383525783L;
	
	private Animator animator;
	
	private JLabel speedLabel;
	private JSlider speedSlider;
	
	private int framesPerSecond = 10;
	private int minFramesPerSecond = 0;
	private int maxFramesPerSecond = 100;
	
	public AnimationFpsPanel(Animator animator){
		this.animator = animator;
		this.animator.setFramesPerSecond(framesPerSecond);
		init();
	}
	
	private void init(){
		
		speedLabel = new JLabel("" + framesPerSecond + " frames/second");
		
		speedSlider = new JSlider(minFramesPerSecond, maxFramesPerSecond, framesPerSecond);
		speedSlider.setPaintTicks(true);
		speedSlider.setMajorTickSpacing(20);
		speedSlider.setMinorTickSpacing(5);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(this);

		setLayout(new BorderLayout());
		add(speedLabel, BorderLayout.WEST);		
		add(speedSlider, BorderLayout.EAST);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == speedSlider && ! speedSlider.getValueIsAdjusting()){
			framesPerSecond = Math.max(1, speedSlider.getValue());
			animator.setFramesPerSecond(framesPerSecond);
			speedLabel.setText("" + framesPerSecond + " frames/second");
		}
	}
}
