package slivisu.view.myviews;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import slivisu.data.datatype.Balken;

public class DetailListener extends MouseAdapter {
	
	// TODO einlagern 
	private DetailPanel detail;
	
	public DetailListener(DetailPanel detail) {
		// TODO Auto-generated constructor stub
		this.detail	= detail;
	}

	private void checkIfHit(MouseEvent e) {
		if (detail.getBalken() != null) {
			Point mPoint = e.getPoint();
			String text;
			boolean hit = false;
			
			String tooltip = "<html>";
			
			for (List<Balken> listOfBars : detail.getBalken().values()) {
				for (Balken bar : listOfBars) {
					if (bar != null) {
						text = "";
						if (bar != null && bar.getRect() != null && mPoint != null && bar.getRect().contains(mPoint)) {
							// markieren des Balken
							bar.setHit(true);
							hit = true;
							
							tooltip += "<b>ZS:<font size=\"4\"> " + bar.getRelZeitscheibe().getName() + "</font></b> <br>"
									+ "<b>Jahre :</b>  " +  bar.getRelZeitscheibe().getAnfang() + " bis " +  bar.getRelZeitscheibe().getEnde() + "<br>";
							if (bar.getSicher() > 0) {
								tooltip += "<b>sicher</b> <br>";
							} else {
								tooltip += "<b>unsicher</b> <br>";
							}
							
						} else if (bar.isHit()) {
							bar.setHit(false);
						}
					}
				}
				detail.repaint();
				if (hit) break;
			}
			
			tooltip+="</html>";
			
			if (tooltip == "<html></html>") {
				 ((DetailPanel) e.getSource()).tooltip = null;
			} else {
				 ((DetailPanel) e.getSource()).tooltip = tooltip;
			}
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.checkIfHit(e);
	}
}
