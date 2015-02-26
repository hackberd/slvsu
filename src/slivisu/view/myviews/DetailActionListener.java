package slivisu.view.myviews;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailActionListener implements ActionListener {

	private Detail detail;
	
	public DetailActionListener(Detail detail) {
		this.detail = detail;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		detail.buttonClicked(e.getActionCommand());
	}

}
