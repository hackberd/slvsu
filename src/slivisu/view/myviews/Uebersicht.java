/**
 * 
 */
package slivisu.view.myviews;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.ToolTipManager;

import slivisu.data.MyZeitscheibe;
import slivisu.data.datatype.Balken;
import slivisu.gui.controller.InteractionListener;

public class Uebersicht extends JPanel implements InteractionListener {

	private static final long serialVersionUID = 1L;
	
	// Konstanten
	private final int PADDING	= 5;
	private final int PTOP		= 50;
	
	// Variablen
	private List<Balken> balken;
	public SuperDataUebersicht data;
	private UebersichtListener listener;
	
	private Graphics2D graphic;
	
	private int min			= Integer.MAX_VALUE;
	private int max			= Integer.MIN_VALUE;
	private int rangeTime	= 0;
	private int rangeEbenen	= 0;
	
	private Point startSelection;
	private Point endSelection;
	
	private UebersichtItemListener itemListener;
	
	private JCheckBox filter1;
	private JCheckBox filter2;
	private JCheckBox filter3;
	private JCheckBox filter4;
	private JCheckBox filter5;
	
	private SpringLayout layout;
	
	private List<Boolean> filter;
	
	public String tooltip;
	
	// TODO: Auswahl machen
	
	public Uebersicht(SuperDataUebersicht data){
		this.data = data;
		
		layout			= new SpringLayout();
		
		filter1			= new JCheckBox("Level 1", true);
		filter2			= new JCheckBox("Level 2", true);
		filter3			= new JCheckBox("Level 3", true);
		filter4			= new JCheckBox("Level 4", true);
		filter5			= new JCheckBox("Level 5", true);
		
		itemListener	= new UebersichtItemListener(this);
		
		filter1.addItemListener(itemListener);
		filter2.addItemListener(itemListener);
		filter3.addItemListener(itemListener);
		filter4.addItemListener(itemListener);
		filter5.addItemListener(itemListener);
		
		listener = new UebersichtListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
		add(filter1);
		add(filter2);
		add(filter3);
		add(filter4);
		add(filter5);
		
		layout.putConstraint(SpringLayout.WEST,		filter1,	PADDING,	SpringLayout.WEST,	this);
		layout.putConstraint(SpringLayout.SOUTH,	filter1,	-PADDING,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter1,	-30,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter1,	75,	SpringLayout.WEST,	filter1);
		
		layout.putConstraint(SpringLayout.WEST,		filter2,	PADDING,	SpringLayout.EAST,	filter1);
		layout.putConstraint(SpringLayout.SOUTH,	filter2,	-PADDING,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter2,	-30,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter2,	75,	SpringLayout.WEST,	filter2);
		
		layout.putConstraint(SpringLayout.WEST,		filter3,	PADDING,	SpringLayout.EAST,	filter2);
		layout.putConstraint(SpringLayout.SOUTH,	filter3,	-PADDING,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter3,	-30,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter3,	75,	SpringLayout.WEST,	filter3);
		
		layout.putConstraint(SpringLayout.WEST,		filter4,	PADDING,	SpringLayout.EAST,	filter3);
		layout.putConstraint(SpringLayout.SOUTH,	filter4,	-PADDING,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter4,	-30,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter4,	75,	SpringLayout.WEST,	filter4);
		
		layout.putConstraint(SpringLayout.WEST,		filter5,	PADDING,	SpringLayout.EAST,	filter4);
		layout.putConstraint(SpringLayout.SOUTH,	filter5,	-PADDING,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.NORTH,	filter5,	-30,	SpringLayout.SOUTH,	this);
		layout.putConstraint(SpringLayout.EAST,		filter5,	75,	SpringLayout.WEST,	filter5);
		
		ToolTipManager.sharedInstance().registerComponent( this);
		ToolTipManager.sharedInstance().setInitialDelay(0) ;
		
	}
	
	public String getToolTipText( MouseEvent e )
    {
		if (tooltip != null) {
			return tooltip;
		}
        return "";
    }

    public Point getToolTipLocation(MouseEvent e)
    {
        return e.getPoint();
    }
	
	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#updateView()
	 */
	@Override
	public void updateView() {
		if (data != null) {
			this.data.updateDataToSelectedData();
			
			List<MyZeitscheibe> selZS = new LinkedList<MyZeitscheibe>();
			
			if (balken != null) {
				for (Balken bar : balken) {
					if (bar.isSelected()) selZS.add(bar.getRelZeitscheibe());
				}
			}
			
			// Variablen initialisieren
			balken	= new LinkedList<Balken>();
			filter	= data.getFilter();
			min		= Integer.MAX_VALUE;
			max		= Integer.MIN_VALUE;
			
			// �ber Zeitscheiben der ersten Ebene iterieren und min und max der Zeit ermitteln
			
			for (int i = 0; i < 5; i++) {
				if (data.getAllData().get(i).size() != 0) {
					for (MyZeitscheibe scheibe : data.getAllData().get(i)) {
						if ((scheibe.getAnfang() < min) && (filter.get(scheibe.getEbene() - 1))) min = scheibe.getAnfang();
						if ((scheibe.getEnde() > max) && (filter.get(scheibe.getEbene() - 1))) max = scheibe.getEnde();
					}
				}
			}
			
			rangeTime = max - min;
			rangeEbenen = data.getAllData().size();
			
			// �ber alle ebenen und zeitscheiben iterieren
			for (int cntEbene = 0; cntEbene < data.getAllData().size(); cntEbene++) {
				for (MyZeitscheibe scheibe : data.getAllData().get(cntEbene)) {
					balken.add(new Balken(	scheibe,
											scheibe.getAnfang(),
											scheibe.getEnde(),
											scheibe.getEbene(),
											scheibe.getAnzahlSichereSiedlungen(),
											scheibe.getAnzahlUnsichereSiedlungen(),
											scheibe.getName()));
					for (MyZeitscheibe myZS : selZS) {
						if (scheibe.equals(myZS)) {
							balken.get(balken.size() - 1).setSelected(true);
						}
					}
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
			
			//System.out.println(selectedZeitscheiben.size() + " , " + balken.size());
			this.data.getData().setCurrentZeitscheibe(selectedZeitscheiben);
			data.selectZeitscheiben(selectedZeitscheiben);
			repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintUebersicht(g);
	}
	
	public void paintUebersicht(Graphics g) {
		if (data != null) {
			if (balken != null && balken.get(0) != null) {
				// Variablen
				int xMin	= 0;
				int xMax	= 0;
				int yMin	= 0;
				int yMax	= 0;
				
				int levelShowed = 0;
				
				List<Balken> hitted = new LinkedList<Balken>();
				List<Integer> levelShowedBefore = new LinkedList<Integer>();
				
				filter				= data.getFilter();
				
				levelShowedBefore.add(new Integer(0));
				for (int i = 0; i < filter.size(); i++) {
					if (filter.get(i)) {
						levelShowed++;
					}
					levelShowedBefore.add(new Integer(levelShowed));
				}
				
				// Rahmen zeichnen
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
				g2d.setColor(Color.BLACK);
				g2d.drawRect(PADDING, PTOP, this.getWidth() - 2*PADDING, this.getHeight() - PADDING - PTOP);
				
				// Balken zeichnen
				g2d.setColor(Color.RED);
				for (Balken bar : balken) {
					if ((bar == null) || (!data.getFilter().get(bar.getEbene() - 1))) return;
					// berechne Koordinaten f�r Balken
					xMin	= (int)	(				  PADDING + ( (double) (bar.getAnfang() - min) / (double) rangeTime) * (this.getWidth() - 2*PADDING));
					xMax	= (int)	(this.getWidth() - PADDING - ( (double) (max - bar.getEnde()) / (double) rangeTime)   * (this.getWidth() - 2*PADDING));
					
					yMin	= (int)	(				   PTOP + ( (double) (levelShowedBefore.get(bar.getEbene() - 1)) / (double) levelShowed) * (this.getHeight() - PADDING - PTOP));
					yMax	= (int)	(this.getHeight() - PADDING - ( (double) (levelShowed - levelShowedBefore.get(bar.getEbene())) / (double) levelShowed)  * (this.getHeight() - PADDING - PTOP));
					
					Rectangle2D rect = new Rectangle(	xMin,
														yMin,
														(xMax - xMin),
														(yMax - yMin));
					bar.setRect(rect);
					if (bar.isHit()) {
						hitted.add(bar);
					}
					g2d.setColor(Color.BLACK);
					
					if (bar.isSelected()) {
						g2d.setStroke(new BasicStroke(3));
						g2d.setColor(Color.BLUE);
					}
					
					g2d.draw(rect);
					g2d.setStroke(new BasicStroke(1));
					
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
					
//					int xSplit		= 0;
					int sicher		= bar.getSicher();
					int unsicher	= bar.getUnsicher();
					
					int y1	= 0;
					int y2	= 0;
					int y3	= 0;
					int y4	= 0;
					
					int squares = sicher + unsicher;
					
					// set y
					if (squares == 1) {
						y1	= yMax;
					} else if (squares == 2){
						y1	= ySplit + (int) ((yMax - ySplit) / 2);
						y2	= yMax;
					} else if (squares == 3) {
						y1	= ySplit + (int) ((yMax - ySplit) / 3);
						y2	= ySplit + (int) (2 * (yMax - ySplit) / 3);
						y3	= yMax;
					} else if (squares == 4) {
						y1	= ySplit + (int) ((yMax - ySplit) / 4);
						y2	= ySplit + (int) (2 * (yMax - ySplit) / 4);
						y3	= ySplit + (int) (3 * (yMax - ySplit) / 4);
						y4	= yMax;
					} else {
						y1	= ySplit + (int) ((yMax - ySplit) / 5);
						y2	= ySplit + (int) (2 * (yMax - ySplit) / 5);
						y3	= ySplit + (int) (3 * (yMax - ySplit) / 5);
						y4	= ySplit + (int) (4 * (yMax - ySplit) / 5);
					}
					
					// set x
					int cols;
					if ((squares % 5) == 0) {
						cols = squares / 5;
					} else {
						cols = (int) (squares / 5) + 1;
					}
					//int xColWidth = (int) ((xMax - xMin) / cols);
					int xColWidth = 5;
					// zeichne
					int xsq		= xMin - xColWidth;
					int xsq2	= xMin;
					int ysq		= 0;
					int ysq2	= 0;
					
					for (int i = 0; i < sicher; i++) {
						if ((i % 5) == 0) {
							xsq = xsq + xColWidth;
							xsq2 = xsq2 + xColWidth;
							ysq = ySplit;
							ysq2 = y1;
						} else if ((i % 5) == 1) {
							ysq = y1;
							ysq2 = y2;
						} else if ((i % 5) == 2) {
							ysq = y2;
							ysq2 = y3;
						} else if ((i % 5) == 3) {
							ysq = y3;
							ysq2 = y4;
						} else { // == 4
							ysq = y4;
							ysq2 = yMax;
						}
						
						g2d.setColor(Color.GREEN);
						g2d.fillRect(	xsq,
								ysq,
								(xsq2 - xsq),
								(ysq2 - ysq));
						g2d.setColor(Color.BLACK);
						g2d.drawRect(	xsq,
								ysq,
								(xsq2 - xsq),
								(ysq2 - ysq));
						
						if ((i % 25 == 0) && (i != 0)) {
							g2d.setColor(Color.BLACK);
							g2d.setStroke(new BasicStroke(2));
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
							g2d.drawLine(xsq, ySplit, xsq, yMax);
							g2d.setStroke(new BasicStroke(1));
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
						}
					}
					
					for (int i = sicher; i < unsicher + sicher; i++) {
						if ((i % 5) == 0) {
							xsq = xsq + xColWidth;
							xsq2 = xsq2 + xColWidth;
							ysq = ySplit;
							ysq2 = y1;
						} else if ((i % 5) == 1) {
							ysq = y1;
							ysq2 = y2;
						} else if ((i % 5) == 2) {
							ysq = y2;
							ysq2 = y3;
						} else if ((i % 5) == 3) {
							ysq = y3;
							ysq2 = y4;
						} else { // == 4
							ysq = y4;
							ysq2 = yMax;
						}
						
						g2d.setColor(Color.RED);
						g2d.fillRect(	xsq,
								ysq,
								(xsq2 - xsq),
								(ysq2 - ysq));
						g2d.setColor(Color.BLACK);
						g2d.drawRect(	xsq,
								ysq,
								(xsq2 - xsq),
								(ysq2 - ysq));
						
						if ((i % 25 == 0) && (i != 0)) {
							g2d.setColor(Color.BLACK);
							g2d.setStroke(new BasicStroke(2));
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
							g2d.drawLine(xsq, ySplit, xsq, yMax);
							g2d.setStroke(new BasicStroke(1));
							g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
						}
					}
					
//					xSplit	= (int) (xMin + (xMax - xMin) * ((double) sicher/ (double) (sicher + unsicher)));
//					g2d.setColor(Color.RED);
//					g2d.fillRect(	xMin,
//							ySplit,
//							(xSplit - xMin),
//							(yMax - ySplit));
//					g2d.setColor(Color.BLACK);
//					g2d.drawRect(	xMin,
//								ySplit,
//								(xSplit - xMin),
//								(yMax - ySplit));
//					g2d.setColor(Color.WHITE);
//					g2d.drawString(String.valueOf(sicher), xMin, yMax);
//					
//					g2d.setColor(Color.MAGENTA);
//					g2d.fillRect(	xSplit,
//							ySplit,
//							(xMax - xSplit),
//							(yMax - ySplit));
//					g2d.setColor(Color.BLACK);
//					g2d.drawRect(	xSplit,
//								ySplit,
//								(xMax - xSplit),
//								(yMax - ySplit));
//					g2d.setColor(Color.WHITE);
//					g2d.drawString(String.valueOf(unsicher), xSplit, yMax);
				}
				
				for (Balken hit : hitted) {
					g2d.setColor(Color.YELLOW);
					g2d.draw(hit.getRect());
				}
				
				if ((startSelection != null) && (endSelection != null)) {
					Point start	= new Point();
					int width 	= 0;
					int height	= 0;
					
					if (startSelection.getX() > endSelection.getX()) {
						width = (int) (startSelection.getX() - endSelection.getX());
						start.setLocation(endSelection.getX(), start.getY());
					} else {
						width = (int) (endSelection.getX() - startSelection.getX());
						start.setLocation(startSelection.getX(), start.getY());
					}
					
					if (startSelection.getY() > endSelection.getY()) {
						height = (int) (startSelection.getY() - endSelection.getY());
						start.setLocation(start.getX(), endSelection.getY());
					} else {
						height = (int) (endSelection.getY() - startSelection.getY());
						start.setLocation(start.getX(), startSelection.getY());
					}
					g2d.setColor(Color.BLUE);
					Rectangle2D selRect = new  Rectangle(
							(int) start.getX(),
							(int) start.getY(),
							width,
							height);
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

	public void setFilter() {
		List<Boolean> settings = new LinkedList<Boolean>();
		
		settings.add(filter1.isSelected());
		settings.add(filter2.isSelected());
		settings.add(filter3.isSelected());
		settings.add(filter4.isSelected());
		settings.add(filter5.isSelected());
		
		data.setFilter(settings);
		updateView();
	}

}
