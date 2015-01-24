package slivisu.view.myviews;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DetailItemListener implements ItemListener {

	private Detail detail;

	public DetailItemListener(Detail detail) {
		this.detail	= detail;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		detail.setFilter();
	}

}
