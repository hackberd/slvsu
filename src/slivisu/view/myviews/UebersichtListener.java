package slivisu.view.myviews;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JComponent;

import com.sun.org.apache.xpath.internal.axes.SelfIteratorNoPredicate;
import com.sun.tools.javac.comp.Check;

import slivisu.data.datatype.Balken;
import slivisu.defaults.chart.DefaultChart;

public class UebersichtListener extends MouseAdapter {
	
	// TODO einlagern 
	private Uebersicht uebersicht;
	
	public UebersichtListener(Uebersicht uebersicht) {
		// TODO Auto-generated constructor stub
		this.uebersicht	= uebersicht;
	}

	private void checkIfHit(MouseEvent e) {
		if (uebersicht.getBalken() != null) {
			Point mPoint = e.getPoint();
			String text;
			String tooltip = "<html>";
			for (Balken bar : uebersicht.getBalken()) {
				if (bar != null) {
					text = "";
					if (bar != null && bar.getRect() != null && mPoint != null && bar.getRect().contains(mPoint)) {
						// markieren des Balken
						bar.setHit(true);
						//text = "bla";
						
						 tooltip += "<b>ZS:<font size=\"4\"> " + bar.getRelZeitscheibe().getName() + "</font></b> <br>"
								+ "<b>Jahre :</b>  " +  bar.getRelZeitscheibe().getAnfang() + " bis " +  bar.getRelZeitscheibe().getEnde() + "<br>"
								+ "<b>SLIs (sicher):</b>  " +  bar.getRelZeitscheibe().getAnzahlSichereSiedlungen() + " <b>SLIs (unsicher):</b>  " +  bar.getRelZeitscheibe().getAnzahlUnsichereSiedlungen()+ "<br><br>";
					} else if (bar.isHit()) {
						bar.setHit(false);
					}
					uebersicht.repaint();
//					((Uebersicht) e.getSource()).setToolTipText(text);
				}
				
			}
			tooltip+="</html>";
			if (tooltip == "<html></html>") {
				 ((Uebersicht) e.getSource()).tooltip = null;
			} else {
				 ((Uebersicht) e.getSource()).tooltip = tooltip;
			}
			
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.checkIfHit(e);
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
		if (uebersicht.getStartSelection() == uebersicht.getEndSelection()) {
			for (Balken bar : uebersicht.getBalken()) {
				if (bar != null && bar.getRect() != null && bar.getRect().contains(e.getPoint())) {
					bar.setSelected(true);
					break;
				}
				
			}	
			
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		uebersicht.setStartSelection(e.getPoint());
		uebersicht.repaint();
	}
}
