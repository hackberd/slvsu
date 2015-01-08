/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.histogram;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import slivisu.data.datatype.Bin;
import slivisu.defaults.chart.DefaultChart;
import slivisu.defaults.chart.DefaultChartPanel;
import slivisu.defaults.chart.PlotTicks;


/**
 * 
 * @author Sven
 */
public class HistogramChart extends DefaultChart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultChartPanel parent;
	private HistogramData data;
	private List<CategoryBar>	polygonsAll;
	private List<CategoryBar>	polygonsMarked;
	private boolean				totalCountBar			= true;
	private int					bars					= 1;
	private boolean update = true;
	private boolean showSelection = false;

	/**
	 * View-Typen: typ 1: Keine Unterscheidung nach Kategorien; Typ 2: SLI-Kategorien
	 */
	private int	viewTyp = 0;
	public static final int NO_CATEGORIES = 0;
	public static final int CATEGORIES = 1;

	public HistogramChart(DefaultChartPanel parent, HistogramData data) {

		this.parent = parent;
		this.data = data;
		this.polygonsAll = new Vector<CategoryBar>();
		this.polygonsMarked = new Vector<CategoryBar>();

		this.setBackground ( Color.WHITE );

		this.addMouseListener ( new HistogramPopupListener (this, data));
		this.addMouseMotionListener ( new HistogramPopupListener (this, data));
	}

	private int getMaxValueYaxis(Map<Bin<Double>, Map<String, Integer>> valueCount) {
		int max = 0;
		for (Map<String, Integer> histo : valueCount.values()) {
			int sum = 0;
			if (histo != null){
				for (int value : histo.values()){
					sum += value;
				}	
			}
			if (max < sum) {
				max = sum;
			}
		}
		return max;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		refreshTyp1(g2);
	}

	private void refreshTyp1(Graphics2D g2) {

		int segm = data.getSegments();

		// Ermitteln des maximalen Wertes auf der Y-Achse
		List<Bin<Double>> ageH = data.getBins();
		Map<Bin<Double>, Map<String, Integer>> valueCount = data.getData();
		if (ageH != null && valueCount != null){
			double minX = data.getMin();
			double maxX = data.getMax();
			//System.out.println("min " + minX + " max  " + maxX);
			int maxY = getMaxValueYaxis(valueCount);

			Vector<Double> v = PlotTicks.computeTickValues(0, maxY, 5);
			if (v != null){
				maxY = (int) (0 + v.get(v.size()-1));
			}

			if (isUpdate()){

				parent.setSegmentsX(segm);
				parent.setRangeX(-minX, -maxX);
				parent.setRangeY(0, maxY);
				parent.repaint();

				setUpdate(false);
			}

			// Bestimmen der Skalierungsfaktoren
			this.scaleFactors(maxX - minX, maxY);

			// Bestimmen der Skalierungsfaktoren
			double factorX = this.getFactorX();
			double factorY = this.getFactorY();

			int mX = (int) ((0.0 + minX) / factorX);

			polygonsAll = new ArrayList<CategoryBar>();
			polygonsMarked = new ArrayList<CategoryBar>();

			totalBar(g2, mX, factorX, factorY, isTotalCountBar(), showSelection);

			drawSLIbars(g2, mX, factorX, factorY, getViewTyp(), getBars(), showSelection);	
		}
	}

	private void drawSLIbars(Graphics2D g2, int mX, double factorX, double factorY, int viewTyp, int bars, boolean showSelection) {

		// hole alle Kategorien
		g2.setStroke(new BasicStroke(1.0f));
		// hole alle Daten
		Map<Bin<Double>, Map<String, Integer>> valueCount = data.getData();

		if(!showSelection){
			valueCount = data.getData();
		}else{
			valueCount = data.getMarkedData();
		}

		// hole markierte Daten
		Map<Bin<Double>, Map<String, Integer>> valueSelectedCount = data.getMarkedData();
		for (Bin<Double> interval : valueCount.keySet()) {
			int width = (int)((interval.getMax() - interval.getMin()) / factorX );
			int x = this.getWidth() - (int)(interval.getMax() / factorX) + mX - 5;
			int z = 5;
			int zMarked = 5;
			Map<String, Integer> catValues = valueCount.get(interval);
			Map<String, Integer> catSelectedValues = valueSelectedCount.get(interval);
			for (String cat : catValues.keySet()) {
				// Balkenhoehe
				int height = (int) (catValues.get(cat) / factorY); // Gesamtanzahl

				Color color = Color.RED.darker();
				// Balkenfarbe (Gesamtanzahl) festlegen
				if (viewTyp == CATEGORIES) {
					color = barColor(cat);
				}
				g2.setColor(color);

				// Festlegen ob ein oder zwei Balken je Zeitabschnitt
				int w = width;
				int w1 = width;
				int pos = x;
				if (bars == 2) {
					w -= (width / 2);
					w1 -= (width / 2);
					pos += (width / 2);
				}
				// Erzeugen des Balkens für die einzelne Kategorie (gesamt)
				Rectangle2D rect = new Rectangle2D.Double(x, (this.getHeight() - z - height), w, height);
				CategoryBar catBar = new CategoryBar(cat, catValues.get(cat), rect, interval.getMin(), interval.getMax());
				if (height > 0) {
					g2.fill(catBar);
				}
				if (viewTyp != NO_CATEGORIES) {
					polygonsAll.add(catBar);
				}
				// Balkenfarbe (Gesamtanzahl) festlegen
				if (viewTyp == NO_CATEGORIES || (viewTyp == CATEGORIES && bars == 2)) {
					g2.setColor(barColor(cat));
				}
				else {
					//wenn nur markierte SLI zeigen, dann kein einf�rben in Orange
					if(!showSelection){
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
						g2.setColor(Color.ORANGE);
					}else{
						g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
					}
				}
				// Erzeugen des Balkens für die einzelne Kategorie (markierte SLI)
				int yDifsel = 0;
				int count = 0;
				if (catSelectedValues != null && catSelectedValues.get(cat) != null){
					count = catSelectedValues.get(cat);
					yDifsel = (int) (count / factorY); // Markierte
					if (count > 0 && yDifsel == 0) {
						yDifsel = 1;
					}
				}
				rect = new Rectangle2D.Double(pos, (this.getHeight() - zMarked - yDifsel), w1, yDifsel);
				CategoryBar catBarMarked = new CategoryBar (cat, count, rect,interval.getMin(), interval.getMax());
				polygonsMarked.add(catBarMarked);
				if (yDifsel > 0) {
					g2.fill(catBarMarked);
				}

				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
				// Zeichnen eines umschlie�enden Rahmens
				if (height > 0 && viewTyp != NO_CATEGORIES) {
					g2.setColor(Color.BLACK);
					g2.draw(catBar);
				}
				//wenn nur markierte SLI zeigen, dann kein einf�rben in Orange
				if (yDifsel > 0 && !showSelection) {
					g2.setColor(Color.ORANGE);
					g2.draw(catBarMarked);
				}
				if (bars == 1 && viewTyp == NO_CATEGORIES) {
					z += height;
					zMarked += yDifsel;
				}
				if (bars == 1 && viewTyp == CATEGORIES) {
					z += height;
					zMarked = z;
				}
				if (bars == 2) {
					z += height;
					zMarked += yDifsel;
				}
			}
		}
	}

	private Color barColor( String cat ) {
		return Color.red.darker();
	}

	/**
	 * Erzeugt einen Balken je Zeitabschnitt ohne Unterteilung nach Kategorien
	 */
	private void totalBar( Graphics2D g2, int mX, double factorX, double factorY, boolean vis , boolean showSelection) {
		if ( vis) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		else {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f));
		}
		int wPanel = this.getWidth () - 5;


		Map<Bin<Double>, Map<String, Integer>> valueCount = data.getData();
		if(!showSelection){
			valueCount = data.getData();
		}else{
			valueCount = data.getMarkedData();
		}


		for (Bin<Double> interval : valueCount.keySet()) {
			int x0 = wPanel - (int) (interval.getMin() / factorX ) + mX;
			int x2 = wPanel - (int) (interval.getMax() / factorX ) + mX;
			int x1 = x0 - x2;
			int z = 5;
			int yDif = 0;
			Map<String, Integer> catCount = valueCount.get(interval);
			for (int value : catCount.values()) {
				yDif += value;
			}
			Rectangle2D rec = new Rectangle2D.Double(x2, this.getHeight() - z - (yDif/factorY), x1, (yDif/factorY));
			CategoryBar b = new CategoryBar("total", yDif, rec,interval.getMin(), interval.getMax());
			g2.setColor(Color.LIGHT_GRAY);
			g2.fill(b);
			g2.setColor(Color.BLACK);
			g2.draw(b);
			polygonsAll.add(b);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
	}

	public boolean isTotalCountBar() {
		return totalCountBar;
	}

	public void setTotalCountBar(boolean totalCountBar) {
		this.totalCountBar = totalCountBar;
		repaint ();
	}

	public int getBars() {
		return bars;
	}

	public void setBars(int bars) {
		this.bars = bars;
	}

	public int getViewTyp() {
		return viewTyp;
	}

	public void setViewTyp(int viewTyp) {
		this.viewTyp = viewTyp;
		repaint ();
	}

	public List<CategoryBar> getPolygonsAll() {
		return polygonsAll;
	}

	public List<CategoryBar> getPolygonsMarked() {
		return polygonsMarked;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean getShowSelection() {
		return showSelection;
	}

	public void setShowSelection(boolean showSelection) {
		this.showSelection = showSelection;
	}

	public Collection<CategoryBar> getSelectedBars(int x, int y) {

		Collection<CategoryBar> selectedBars = new Vector<CategoryBar>();
		
		for (CategoryBar bar : getPolygonsAll()){
			if (bar.contains(x , y) && viewTyp == 1){
				selectedBars.add(bar);
			}
		}
		return selectedBars;
	}
}
