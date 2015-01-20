package slivisu.view.myviews;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import slivisu.data.datatype.Balken;

public class UebersichtListener extends MouseAdapter {
	
	// TODO einlagern 
	private Uebersicht uebersicht;
	
	public UebersichtListener(Uebersicht uebersicht) {
		// TODO Auto-generated constructor stub
		this.uebersicht	= uebersicht;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (uebersicht.getBalken() != null) {
			Point mPoint = e.getPoint();
			String text;
			for (Balken bar : uebersicht.getBalken()) {
				text = "";
				if (bar != null && bar.getRect() != null && mPoint != null && bar.getRect().contains(mPoint)) {
					// markieren des Balken
					bar.setHit(true);
					text = "bla";
					
					// tooltip
				} else if (bar.isHit()) {
					bar.setHit(false);
				}
				uebersicht.repaint();
//				((Uebersicht) e.getSource()).setToolTipText(text);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		uebersicht.setEndSelection(e.getPoint());
		uebersicht.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		uebersicht.setEndSelection(e.getPoint());
		uebersicht.uebersichtSelection();
		uebersicht.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		uebersicht.setStartSelection(e.getPoint());
		uebersicht.repaint();
	}
}
