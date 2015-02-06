package slivisu.view.myviews;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class AnimationActionListener implements ActionListener {
	private int setting;
	private AnimationView view;
	
	public AnimationActionListener(AnimationView animationView) {
		view = animationView;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		  JComboBox cb = (JComboBox)arg0.getSource();
		  setting = (int)cb.getSelectedIndex() ;
	      view.updateZS();
	}
	public int getSetting() {
		return setting;
	}
	
}
