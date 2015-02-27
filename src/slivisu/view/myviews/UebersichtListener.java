package slivisu.view.myviews;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import slivisu.data.datatype.Balken;

public class UebersichtListener extends MouseAdapter {
	
	// TODO einlagern 
	private UebersichtPanel uebersichtPanel;
	
	public UebersichtListener(UebersichtPanel uebersichtPanel) {
		// TODO Auto-generated constructor stub
		this.uebersichtPanel	= uebersichtPanel;
	}

	private void checkIfHit(MouseEvent e) {
		if (uebersichtPanel.getBalken() != null) {
			Point mPoint = e.getPoint();
			String text;
			String tooltip = "<html>";
			for (Balken bar : uebersichtPanel.getBalken()) {
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
					uebersichtPanel.repaint();
//					((Uebersicht) e.getSource()).setToolTipText(text);
				}
				
			}
			tooltip+="</html>";
			if (tooltip == "<html></html>") {
				 ((UebersichtPanel) e.getSource()).tooltip = null;
			} else {
				 ((UebersichtPanel) e.getSource()).tooltip = tooltip;
			}
			
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.checkIfHit(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		uebersichtPanel.setEndSelection(e.getPoint());
		uebersichtPanel.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		uebersichtPanel.setEndSelection(e.getPoint());
		
		if (uebersichtPanel.getStartSelection().getX() == uebersichtPanel.getEndSelection().getX() && uebersichtPanel.getStartSelection().getY() == uebersichtPanel.getEndSelection().getY()) {
			for (Balken bar : uebersichtPanel.getBalken()) {
				System.out.println(bar.getRect());
				if (bar != null && bar.getRect() != null && bar.getRect().contains(e.getPoint())) {
					bar.setSelected(true);
					break;
				}		
			}		
		}
		uebersichtPanel.uebersichtSelection();
		uebersichtPanel.repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		uebersichtPanel.setStartSelection(e.getPoint());
		uebersichtPanel.repaint();
	}
}
