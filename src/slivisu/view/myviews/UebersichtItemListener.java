package slivisu.view.myviews;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UebersichtItemListener implements ItemListener {

	private Uebersicht uebersicht;

	public UebersichtItemListener(Uebersicht uebersicht) {
		this.uebersicht	= uebersicht;
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		uebersicht.setFilter();
	}

}
