/**
 * 
 */
package slivisu.view.myviews;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import slivisu.data.MyZeitscheibe;
import slivisu.data.datatype.Balken;
import slivisu.gui.controller.InteractionListener;

/**
 * @author immanuelpelzer
 *
 */
public class Uebersicht extends JPanel implements InteractionListener {

	private static final long serialVersionUID = 1L;
	
	// Konstanten
	private final int PADDING = 5;
	
	// Variablen
	private List<Balken> balken;
	private SuperData data;
	private UebersichtListener listener;
	
	private Graphics2D graphic;
	
	private int min			= Integer.MAX_VALUE;
	private int max			= Integer.MIN_VALUE;
	private int rangeTime	= 0;
	private int rangeEbenen	= 0;
	
	private Point startSelection;
	private Point endSelection;
	
	// TODO: Auswahl machen
	
	public Uebersicht(SuperData data){
		this.data = data;
		
		listener = new UebersichtListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
	}
	
	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#updateView()
	 */
	@Override
	public void updateView() {
		if (data != null) {
			this.data.updateDataToSelectedData();
			
			// Variablen initialisieren
			balken = new LinkedList<Balken>();
			
			// über Zeitscheiben der ersten Ebene iterieren und min und max der Zeit ermitteln
			for (MyZeitscheibe scheibe : data.getAllData().get(0)) {
				if (scheibe.getEbene() == 1) {
					if (scheibe.getAnfang() < min) min = scheibe.getAnfang();
					if (scheibe.getEnde() > max) max = scheibe.getEnde();
				}
			}
			rangeTime = max - min;
			rangeEbenen = data.getAllData().size();
			
			// über alle ebenen und zeitscheiben iterieren
			for (int cntEbene = 0; cntEbene < data.getAllData().size(); cntEbene++) {
				for (MyZeitscheibe scheibe : data.getAllData().get(cntEbene)) {
					balken.add(new Balken(	scheibe,
											scheibe.getAnfang(),
											scheibe.getEnde(),
											scheibe.getEbene(),
											scheibe.getAnzahlSichereSiedlungen(),
											scheibe.getAnzahlUnsichereSiedlungen(),
											scheibe.getName()));
				}
			}
		}
	}
	
	public void uebersichtSelection() {
		if (balken != null) {
			List<MyZeitscheibe> selectedZeitscheiben = new LinkedList<MyZeitscheibe>();
			
			// selection ermitteln
			for (Balken bar : balken) {
				if (bar.isSelected()) {
					selectedZeitscheiben.add(bar.getRelZeitscheibe());
				}
			}
			
			startSelection = null;
			endSelection = null;
			
			System.out.println(selectedZeitscheiben.size() + " , " + balken.size());
			
			data.selectZeitscheiben(selectedZeitscheiben);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintUebersicht(g);
	}
	
	public void paintUebersicht(Graphics g) {
		if (data != null) {
			if (balken != null) {
				// Variablen
				int xMin	= 0;
				int xMax	= 0;
				int yMin	= 0;
				int yMax	= 0;
				List<Balken> hitted = new LinkedList<Balken>();
				
				// Rahmen zeichnen
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(PADDING, PADDING, this.getWidth() - 2*PADDING, this.getHeight() - 2*PADDING);
				
				// Balken zeichnen
				g2d.setColor(Color.RED);
				for (Balken bar : balken) {
					// berechne Koordinaten für Balken
					xMin	= (int)	(				  PADDING + ( (double) (bar.getAnfang() - min) / (double) rangeTime) * (this.getWidth() - 2*PADDING));
					xMax	= (int)	(this.getWidth() - PADDING - ( (double) (max - bar.getEnde()) / (double) rangeTime)   * (this.getWidth() - 2*PADDING));
					
					yMin	= (int)	(				   PADDING + ( (double) (bar.getEbene() - 1) / (double) rangeEbenen) * (this.getHeight() - 2*PADDING));
					yMax	= (int)	(this.getHeight() - PADDING - ( (double) (rangeEbenen - bar.getEbene()) / (double) rangeEbenen)  * (this.getHeight() - 2*PADDING));
					
					Rectangle2D rect = new Rectangle(	xMin,
														yMin,
														(xMax - xMin),
														(yMax - yMin));
					bar.setRect(rect);
					if (bar.isHit()) {
						hitted.add(bar);
					}
					g2d.draw(rect);
					
					// header des Balken
					
					int ySplit	= 0;
					
					ySplit	= (int) (yMin + (yMax - yMin) * 0.5);
					
					g2d.setColor(Color.RED);
					g2d.fillRect(	xMin,
									yMin,
									(xMax - xMin),
									(ySplit - yMin));
					g2d.setColor(Color.WHITE);
					g2d.drawString((String.valueOf(bar.getAnfang()) + " - " + String.valueOf(bar.getEnde()) + " | " + bar.getName()), xMin, ySplit);
					
					// sicher & unsicher
					
					int xSplit		= 0;
					int sicher		= bar.getSicher();
					int unsicher	= bar.getUnsicher();
					
					xSplit	= (int) (xMin + (xMax - xMin) * ((double) sicher/ (double) (sicher + unsicher)));
					g2d.setColor(Color.RED);
					g2d.fillRect(	xMin,
							ySplit,
							(xSplit - xMin),
							(yMax - ySplit));
					g2d.setColor(Color.BLACK);
					g2d.drawRect(	xMin,
								ySplit,
								(xSplit - xMin),
								(yMax - ySplit));
					g2d.setColor(Color.WHITE);
					g2d.drawString(String.valueOf(sicher), xMin, yMax);
					
					g2d.setColor(Color.MAGENTA);
					g2d.fillRect(	xSplit,
							ySplit,
							(xMax - xSplit),
							(yMax - ySplit));
					g2d.setColor(Color.BLACK);
					g2d.drawRect(	xSplit,
								ySplit,
								(xMax - xSplit),
								(yMax - ySplit));
					g2d.setColor(Color.WHITE);
					g2d.drawString(String.valueOf(unsicher), xSplit, yMax);
				}
				
				for (Balken hit : hitted) {
					g2d.setColor(Color.YELLOW);
					g2d.draw(hit.getRect());
				}
				
				if ((startSelection != null) && (endSelection != null)) {
					g2d.setColor(Color.BLUE);
					Rectangle2D selRect = new  Rectangle((int) startSelection.getX(),
							(int) startSelection.getY(),
							(int) (endSelection.getX() - startSelection.getX()),
							(int) (endSelection.getY() - startSelection.getY()));
					g2d.draw(selRect);
					for (Balken bar : balken) {
						Rectangle2D rect = bar.getRect();
						if (selRect.contains(rect)) { // nur ganz
							bar.setSelected(true);
							g2d.setColor(Color.YELLOW);
							g2d.draw(bar.getRect());
						} else {
							bar.setSelected(false);
						}
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#getListenerName()
	 */
	@Override
	public String getListenerName() {
		return "UebersichtListenerName";
	}
	
	public List<Balken> getBalken() {
		return balken;
	}
	
	public void setStartSelection(Point point) {
		this.startSelection = point;
	}
	
	public Point getStartSelection() {
		return startSelection;
	}
	
	public void setEndSelection(Point point) {
		this.endSelection = point;
	}
	
	public Point getEndSelection() {
		return endSelection;
	}

}
