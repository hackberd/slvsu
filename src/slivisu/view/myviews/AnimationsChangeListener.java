package slivisu.view.myviews;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AnimationsChangeListener implements ChangeListener {
	public int year;
	AnimationView animationView;
	
	public AnimationsChangeListener(AnimationView animationView) {
		this.animationView = animationView;
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		   JSlider source = (JSlider)arg0.getSource();
		    if (!source.getValueIsAdjusting()) {
		    	year = (int)source.getValue();
		        this.animationView.sliderChangedTo(year);
		        
		        
		    }
	}

	
	
}
