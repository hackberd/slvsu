package slivisu.view.myviews;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

//import com.sun.javafx.geom.Dimension2D;

import slivisu.data.MyZeitscheibe;
import slivisu.data.Sample;
import slivisu.data.datatype.Balken;

public class DetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	// Konstanten
	private final int PADDING			= 5;
	//private final int PADDING_SHORT		= 2;
	private final int OFFSET			= 5;
	private final int HEIGHTVISIBLE		= 10;
	private final int HEIGHTINVISIBLE	= 2;
	private final int WIDTHNAME			= 200;
	
	// Variablen
	private SuperDataDetail data;
	private DetailListener listener;
	
	private Map<Sample, List<Balken>> balkenForSample;
	HashMap<Sample, List<List<Boolean>>> ebenenShowForSample;
	
	private int min;
	private int max;
	private int rangeTime	= 0;
	private int rangeEbenen	= 0;

	public String tooltip;

	private List<Boolean> filter;
	
	public DetailPanel(SuperDataDetail data) {
		this.data = data;
		
		listener = new DetailListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		
		// Tooltip
		ToolTipManager.sharedInstance().registerComponent(this);
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
	
	public void updateView() {
		if (data != null) {
			this.data.updateDataToSelectedSamples();
			
			// Variablen
			List<Balken> balken;
			Map<MyZeitscheibe, Boolean> map;
			List<MyZeitscheibe> allZeitscheiben;
			
			int sicher;
			int unsicher;
			
			allZeitscheiben	= data.getAllMyZeitscheiben();
			balkenForSample	= new HashMap<Sample, List<Balken>>();
			ebenenShowForSample = new HashMap<Sample, List<List<Boolean>>>();
			
			
			
			filter			= data.getFilter();
			
			min	= Integer.MAX_VALUE;
			max	= Integer.MIN_VALUE;
			
			for (Sample sample : data.getAllSelectedSamples()) {
				balken	= new LinkedList<Balken>();
				ebenenShowForSample.put(sample, new LinkedList<List<Boolean>>());
				for (int i = 1; i <= 5; i++) {
					ebenenShowForSample.get(sample).add(new LinkedList<Boolean>());
					ebenenShowForSample.get(sample).get(i -1).add(false);
					ebenenShowForSample.get(sample).get(i -1).add(false);
				}
				
				
				for (int cntEbene = 1; cntEbene < 6; cntEbene++) {
					map = data.zsForSampleWithSicher(sample, cntEbene);
					// set show ebene
					
					
					for (MyZeitscheibe myZeitscheibe : allZeitscheiben) {
						if (map.get(myZeitscheibe) != null) {
							
							
							sicher		= 0;
							unsicher	= 0;
							
							if (map.get(myZeitscheibe) ) {
								ebenenShowForSample.get(sample).get(cntEbene-1).set(0, true);
								sicher++;
							} else {
								ebenenShowForSample.get(sample).get(cntEbene-1).set(1, true);
								unsicher++;
							}
							
							balken.add(new Balken(	myZeitscheibe,
									myZeitscheibe.getAnfang(),
									myZeitscheibe.getEnde(),
									myZeitscheibe.getEbene(),
									sicher,
									unsicher,
									myZeitscheibe.getName()));
							
							if (rangeEbenen < myZeitscheibe.getEbene())	rangeEbenen	= myZeitscheibe.getEbene();
							// filter if-anweisung
							if (filter.get(myZeitscheibe.getEbene() - 1)) {
								if (myZeitscheibe.getAnfang() < min) min = myZeitscheibe.getAnfang();
								if (myZeitscheibe.getEnde() > max) max = myZeitscheibe.getEnde();
							}
							// ebenen
							
						}
					}
				}
				balkenForSample.put(sample, balken);
			}
			rangeTime	= max - min;
			System.out.println("range:" + rangeTime);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDetail(g);
	}
	
	public void paintDetail(Graphics g) {
		if (data != null) {
			if (balkenForSample != null) {
				// Variablen
				int xMin	= 0;
				int xMax	= 0;
				int yMin	= 0;
				int lastDetailY	= 0;
				int lastBarY	= 0;
				int curLevel	= 0;
				
				List<Balken> hitted;
				
//				filter = data.getFilter();
				
				Map<MyZeitscheibe, Boolean> mapSicherheit;
				
				Graphics2D g2d = (Graphics2D) g;
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
				
				// auszugehen, dass alle samples zeitscheiben haben
				for (Sample sample : data.getAllSelectedSamples()) {
//					Sample sample = data.getAllSelectedSamples().get(0);
					// TODO: f�r sortieren eine eigene Liste mit Samples
					
					lastBarY	= lastDetailY + PADDING;
					curLevel	= 1;
					mapSicherheit	= data.zsForSampleWithSicher(sample, curLevel);
					
					hitted = new LinkedList<Balken>();
					
					
					// Balken zeichnen
					if (!balkenForSample.containsKey(sample)) break;
					for (Balken bar : balkenForSample.get(sample)) {
						if (curLevel < bar.getEbene()) {
							if (filter.get(curLevel - 1)) {
								lastBarY		= lastBarY + 2 * HEIGHTVISIBLE + PADDING;
							} else {
								lastBarY		= lastBarY + 2 * HEIGHTINVISIBLE + PADDING;
							}
							curLevel		= bar.getEbene();
							mapSicherheit	= data.zsForSampleWithSicher(sample, curLevel);
						}
						
						// berechne Koordinaten f�r Balken
						xMin	= (int)	(					WIDTHNAME + PADDING + ( (double) (bar.getAnfang() - min) / (double) rangeTime) * (this.getWidth() - WIDTHNAME - 2*PADDING));
						xMax	= (int)	(this.getWidth() - PADDING - ( (double) (max - bar.getEnde()) / (double) rangeTime)   * (this.getWidth() - WIDTHNAME - 2*PADDING));
						
						// Filter ber�cksichtigen
						if (xMin < WIDTHNAME + PADDING)			xMin = WIDTHNAME + PADDING;
						if (xMin > this.getWidth() - PADDING)	xMin = this.getWidth() - PADDING;
						
						if (xMax < WIDTHNAME + PADDING)			xMax = WIDTHNAME + PADDING;
						if (xMax > this.getWidth() - PADDING)	xMax = this.getWidth() - PADDING;
						
						// unsicher / sicher -> Offset
						yMin	= PADDING + lastBarY;
						if (!mapSicherheit.get(bar.getRelZeitscheibe())) {
							if (filter.get(curLevel - 1)) {
								yMin += HEIGHTVISIBLE;
							} else {
								yMin += HEIGHTINVISIBLE;
							}
						}
						
						// Filter ber�cksichtigen f�r H�he
						Rectangle2D rect;
						if (filter.get(curLevel - 1)) {
							rect = new Rectangle(	xMin,
									yMin,
									(xMax - xMin),
									HEIGHTVISIBLE);
						} else {
							rect = new Rectangle(	xMin,
									yMin,
									(xMax - xMin),
									HEIGHTINVISIBLE);
						}
						
						bar.setRect(rect);
						
						if (bar.isHit()) {
							hitted.add(bar);
						}
						
						// Farbe sicher oder unsicher?
						if (mapSicherheit.get(bar.getRelZeitscheibe())) {
							g2d.setColor(Color.GREEN);
						} else {
							g2d.setColor(Color.RED);
						}
						
						g2d.fill(rect);
						
						g2d.setColor(Color.BLACK);
						g2d.draw(rect);
						
					}
					
					for (Balken hit : hitted) {
						g2d.setColor(Color.YELLOW);
						g2d.draw(hit.getRect());
					}
					
					lastBarY	= lastBarY + HEIGHTVISIBLE + PADDING;

					// Name vorne weg
					g2d.setColor(Color.BLACK);
					g2d.drawRect(
							PADDING,
							lastDetailY + PADDING,
							WIDTHNAME,
							lastBarY - lastDetailY + PADDING);
					g2d.drawString(
							sample.getGemeinde(),
							2 * PADDING,
							lastDetailY + 5 * PADDING);
					
					// Rahmen zeichnen (nach Balken, damit H�he bekannt)
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
					g2d.setColor(Color.BLACK);
					g2d.drawRect(
							PADDING,
							lastDetailY + PADDING,
							this.getWidth() - 2*PADDING,
							lastBarY - lastDetailY + PADDING);
					
					
					
					// Linien zwischen ebenen
					int height = lastDetailY + PADDING;
					g2d.setColor(Color.GRAY);
					for (int ebene = 1; ebene < 5;ebene++) {
						if (ebenenShowForSample.get(sample).get(ebene -1).get(0) || ebenenShowForSample.get(sample).get(ebene -1).get(1)) {
							//System.out.println(ebenenShowForSample.get(sample));						
							int ch =0;
							if (filter.get(ebene - 1)) { // Show
								if (ebenenShowForSample.get(sample).get(ebene -1).get(0) || ebenenShowForSample.get(sample).get(ebene -1).get(1)) {
									height		= height + HEIGHTVISIBLE * 2;
								}
								ch = PADDING + PADDING /2;
								
							} else {
								if (ebenenShowForSample.get(sample).get(ebene -1).get(0) || ebenenShowForSample.get(sample).get(ebene -1).get(1)) {
									height		= height + HEIGHTINVISIBLE *2;
								}
								ch =  PADDING  + PADDING /2;
							}
							height = height + ch;
							g2d.drawLine( WIDTHNAME+ PADDING, height, this.getWidth() - 2*PADDING, height);
							
							if (filter.get(ebene - 1)) {
								g2d.drawString(
										ebene+"",
										WIDTHNAME - 2 * PADDING,
										height - ch + ch / 2);
							} 
							
							
						} else {
							height = height +PADDING +  PADDING /2;
						}
						height = height -  PADDING /2;
						
					}
					
					lastDetailY = lastBarY + PADDING;
				}
				
				
				setSize(this.getWidth(), lastDetailY + PADDING);
				setPreferredSize(new Dimension(this.getWidth(), lastDetailY + PADDING));
				setMinimumSize(new Dimension(this.getWidth(), lastDetailY + PADDING));
			}
		}
	}
	
	public Map<Sample, List<Balken>> getBalken() {
		return balkenForSample;
	}

	public void setFilter(List<Boolean> settings) {
		data.setFilter(settings);
		updateView();
	}
}
