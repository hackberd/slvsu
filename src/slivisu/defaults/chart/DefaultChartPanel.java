/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults.chart;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * 
 * @author Sven
 */
public class DefaultChartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int tickFontSize = 9;
	private int labelFontSize = 10;
	/** Wertebereich der X- und Y-Achse */
	double minXvis, maxXvis, minYvis, maxYvis;
	/**
	 * Abstaende vom Panelrand fuer die Platzierung des innenliegenden Diagramms
	 */
	int distL, distR, distB, distT;
	/** Anzahl der Unterteilungen auf der X- bzw. Y-Achse */
	int segmentsX, segmentsY;
	/** innenliegendes Diagramm */
	DefaultChart chart;
	/** Beschriftung von Links nach Rechts aufsteigend */
	boolean axisLabelingLeftRight;
	/** Beschriftung von Unten nach Oben */
	boolean axisLabelingTopDown;
	/** nutzerdefinierte Beschriftungstexte */
	List<String> textX, textY;
	/** nutzerdefinierte Beschriftungstexte verwenden */
	private boolean usertextX;
	private boolean usertextY;
	String axisTitelX, axisTitelY, chartTitel;
	boolean showAxisTitelX, showAxisTitelY, showChartTitel;
	boolean tickChangerY, tickChangerX;
	/** Positionen der Hilfslinen auf der X-Achse */
	private List<Integer> segPX;
	// ------------
	DecimalFormat df;
	FontMetrics fm;
	// ##########

	public boolean isShowAxisTitelX() {
		return showAxisTitelX;
	}

	public void setSegPX(List<Integer> segPX) {
		this.segPX = segPX;
	}

	public List<Integer> getSegPX() {
		return segPX;
	}

	public void setTickChangerY(boolean tickChangerY) {
		this.tickChangerY = tickChangerY;
	}

	public void setTickChangerX(boolean tickChangerX) {
		this.tickChangerX = tickChangerX;
	}

	public void setShowAxisTitelX(boolean showAxisTitelX) {
		this.showAxisTitelX = showAxisTitelX;
	}

	public boolean isShowAxisTitelY() {
		return showAxisTitelY;
	}

	public void setShowAxisTitelY(boolean showAxisTitelY) {
		this.showAxisTitelY = showAxisTitelY;
	}

	public boolean isShowChartTitel() {
		return showChartTitel;
	}

	public void setShowChartTitel(boolean showChartTitel) {
		this.showChartTitel = showChartTitel;
	}

	public String getAxisTitelX() {
		return axisTitelX;
	}

	public void setAxisTitelX(String axisTitelX) {
		this.axisTitelX = axisTitelX;
	}

	public String getAxisTitelY() {
		return axisTitelY;
	}

	public void setAxisTitelY(String axisTitelY) {
		this.axisTitelY = axisTitelY;
	}

	public String getChartTitel() {
		return chartTitel;
	}

	public void setChartTitel(String chartTitel) {
		this.chartTitel = chartTitel;
	}

	public boolean isUsertextX() {
		return usertextX;
	}

	public void setUsertextX(boolean usertextX) {
		this.usertextX = usertextX;
	}

	public boolean isUsertextY() {
		return usertextY;
	}

	public void setUsertextY(boolean usertextY) {
		this.usertextY = usertextY;
	}

	public void setSegmentsX(int i) {
		segmentsX = i;
	}

	public void setSegmentsY(int i) {
		segmentsY = i;
	}

	public boolean isAxisLabelingLeftRight() {
		return axisLabelingLeftRight;
	}

	public void setAxisLabelingLeftRight(boolean axisLabelingLeftRight) {
		this.axisLabelingLeftRight = axisLabelingLeftRight;
	}

	public boolean isAxisLabelingTopDown() {
		return axisLabelingTopDown;
	}

	public void setAxisLabelingTopDown(boolean axisLabelingTopDown) {
		this.axisLabelingTopDown = axisLabelingTopDown;
	}

	// ########################
	public DefaultChartPanel() {
		this.setLayout(null);
		// this.setBackground( Color.WHITE );
	}

	public void setChart(DefaultChart pan) {
		if (chart != null) {
			this.remove(chart);
		}
		chart = pan;
		setChartSize();
		this.add(chart);
	}

	public JPanel getChart() {
		return chart;
	}

	/**
	 * Position des innenliegenden Diagramms festlegen
	 */
	public void setChartSize() {
		int x = getWidth() - distL - distR;
		int y = getHeight() - distT - distB;
		chart.setPreferredSize(new Dimension(x, y));
		chart.setSize(x, y);
		chart.setBounds(distL, distT, x, y);
	}

	/**
	 * Festlegen der Wertebereiche der Y-Achse
	 * 
	 * @param minY1
	 *            - kleinster Wert der Y-Achse
	 * @param maxY1
	 *            - maximaler Wert der Y-Achse
	 */
	public void setRangeY(double minY1, double maxY1) {
		minYvis = minY1;
		maxYvis = maxY1;
		// repaint();
	}

	/**
	 * Festlegen der Wertebereiche der X-Achse
	 * 
	 * @param minX1
	 *            - kleinster Wert der X-Achse
	 * @param maxX1
	 *            - maximaler Wert der X-Achse
	 */
	public void setRangeX(double minX1, double maxX1) {
		minXvis = minX1;
		maxXvis = maxX1;
		// repaint();
	}

	/**
	 * Abstand des internen Diagramms vom Diagrammrahmen
	 * 
	 * @param distL1
	 *            - linker Abstand
	 * @param distR1
	 *            - rechter Abstand
	 * @param distB1
	 *            - unterer Abstand
	 * @param distT1
	 *            - oberer Abstand
	 */
	public void setDistances(int distL1, int distR1, int distB1, int distT1) {
		this.distL = distL1;
		this.distR = distR1;
		this.distB = distB1;
		this.distT = distT1;
	}

	public List<String> getTextX() {
		return textX;
	}

	public void setTextX(List<String> textX) {
		this.textX = textX;
	}

	public List<String> getTextY() {
		return textY;
	}

	public void setTextY(List<String> textY) {
		this.textY = textY;
	}

	/**
	 * Erzeugt die X- und Y-Achse mit dazugehoeriger Beschriftung
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(1.0f));
		chart.repaint();
		setChartSize();
		if (tickChangerX) {
			xAxisTicks(g2);
		} else {
			xAxis(g2);
		}
		if (tickChangerY) {
			yAxisTicks(g2);
		} else {
			yAxis(g2);
		}
		chart.repaint();
		initialDiagramTitels(g2, distL, distR, distT, distB);
	}

	/**
	 * Erzeugen der Linien und Beschriftungselemente der X-Achse
	 * 
	 * @param g2
	 */
	private void xAxisTicks(Graphics2D g2) {
		fm = g2.getFontMetrics();
		df = new DecimalFormat("#0");
		// Breite des inneren Panels (Pixel)
		int wPan = chart.getWidth() - 10;
		// Festlegen der Schriftgröße
		g2.setFont(new Font("SansSerif", 0, tickFontSize));
		// Abstandsmaß (Beginn der Beschriftung)
		int versatz = distL + 5;
		// Bestimmen des Abstands der Ticks in Pixeln
		double ticks = PlotTicks.getNumberOfTicks(wPan, (wPan / segmentsX));
		// Errechnen der Werte für die Ticks
		Vector<Double> tickArray;
		if (axisLabelingLeftRight) {
			tickArray = PlotTicks.computeTickValues(minXvis, maxXvis, ticks);
		} 
		else {
			tickArray = PlotTicks.computeTickValues(maxXvis, minXvis, ticks);
		}

		if (tickArray == null || tickArray.isEmpty()) {
			return;
		}
		// Skalierungsfaktor bestimmen
		double fX = DefaultChartFunctions.calculateFactor(wPan, (maxXvis - minXvis));
		double mXl = (int) (0.0 + (minXvis) / fX);
		// Durchlaufen aller Ticks
		int pxM = -999;
		segPX = new ArrayList<Integer>();
		if (axisLabelingLeftRight == true) {
			for (int i = 0; i < tickArray.size(); i++) {
				double tick = tickArray.get(i);
				pxM = genTickX(g2, tick, versatz, mXl, fX, pxM, segPX);
			}
		} else {
			for (int i = tickArray.size() - 1; i > -1; i--) {
				double tick = tickArray.get(i);
				pxM = genTickX(g2, tick, versatz, mXl, fX, pxM, segPX);
			}
		}
		// Einzeichnen der Hilfslinien in das innere Diagram
		chart.setRasterX(segPX);
	}

	private int genTickX(Graphics2D g2, double tick, int versatz, double mXl, double fX, int pxM, List<Integer> segPX) {
		String bez = "" + df.format(tick);
		int f;
		if (axisLabelingLeftRight) {
			f = (int) Math.round(versatz - mXl + (tick / fX));
		} 
		else {
			double d = getWidth() + mXl - distR - 5 - ((tick / fX));
			f = (int) Math.round(d);
		}
		// Zeichnen der Achsenunterteilungen
		Point lineS = new Point(f, distT - 2);
		Point lineE = new Point(f, this.getHeight() - distB + 2);
		Line2D line2 = new Line2D.Double(lineS, lineE);
		// Setzen der Beschriftung
		int w = fm.stringWidth(bez);
		int px = f - (w / 2);
		if (pxM == -999) {
			pxM = f;
		} 
		else if (axisLabelingLeftRight) {
			if (pxM < px - w) {
				pxM = f;
			}
		} 
		else {
			if (pxM > px + w + 10) {
				pxM = f;
			}
		}

		// Prüfen ob Tick innerhalb des sichtbaren Diagrambereichs liegt
		if (f > distL && f <= (this.getWidth() - distR)) {
			g2.draw(line2);
			if (pxM == f) {
				g2.drawString(bez, px, this.getHeight() - distB + 12);
			}
		}
		segPX.add(f - distL);
		return pxM;
	}

	/**
	 * Zeichnen und Beschriften der Y-Achse
	 * 
	 * @param g2
	 */
	private void yAxisTicks(Graphics2D g2) {
		fm = g2.getFontMetrics();
		df = new DecimalFormat("#0.#");
		// Breite des inneren Panels (Pixel)
		int hPan = chart.getHeight() - 10;
		// Festlegen der Schriftgröße
		g2.setFont(new java.awt.Font("SansSerif", 0, tickFontSize));
		// Abstandsmaß (Beginn der Beschriftung)
		int versatz = distT + 5;
		// Bestimmen des Abstands der Ticks in Pixeln
		double ticks = PlotTicks.getNumberOfTicks(hPan, 20);
		// Errechnen der Werte für die Ticks
		Vector<Double> tickArray = PlotTicks.computeTickValues(minYvis, maxYvis, ticks);
		if (tickArray == null || tickArray.isEmpty()) {
			return;
		}
		// Skalierungsfaktor bestimmen
		double fY = DefaultChartFunctions.calculateFactor(hPan, (maxYvis - minYvis));
		// double mXl = ( 0.0 + ( minY - minYvis ) / fY );
		double mXl = (0.0 + (minYvis) / fY);
		// Durchlaufen aller Ticks
		int pxM = -999;
		ArrayList<Integer> segPX = new ArrayList<Integer>();
		if (axisLabelingTopDown) {
			for (int i = 0; i < tickArray.size(); i++) {
				double tick = tickArray.get(i);
				pxM = genTickY(g2, tick, versatz, mXl, fY, pxM);
				segPX.add(pxM - distT);
			}
		} else {
			for (int i = tickArray.size() - 1; i > -1; i--) {

				double tick = tickArray.get(i);
				pxM = genTickY(g2, tick, versatz, mXl, fY, pxM);
				segPX.add(pxM - distT);
			}
		}
		// Einzeichnen der Hilfslinien in das innere Diagram
		chart.setRasterY(segPX);
	}

	private int genTickY(Graphics2D g2, double tick, int versatz, double mXl, double fY, int pxM) {
		String bez = "" + df.format(tick);
		int f;
		if (! axisLabelingTopDown) {
			f = (int) (versatz - mXl + ((tick / fY)));
		} else {
			f = (int) (this.getHeight() + mXl - distB - 5 - ((tick / fY)));
		}
		// Zeichnen der Achsenunterteilungen
		Point lineS = new Point(distL - 2, f);
		Point lineE = new Point(getWidth() - distR + 2, f);
		Line2D line2 = new Line2D.Double(lineS, lineE);
		// Setzen der Beschriftung
		int w = fm.stringWidth(bez);
		pxM = f;
		// Prüfen ob Tick innerhalb des sichtbaren Diagrambereichs liegt
		if (pxM > distT && pxM < this.getHeight() - distB) {
			g2.draw(line2);
			g2.drawString(bez, distL - w - 2, pxM);
		}
		return pxM;
	}

	/**
	 * Einfügen der optionalen Beschriftungsachsen
	 * 
	 * @param g2
	 * @param distL
	 * @param distR
	 * @param distT
	 * @param distB
	 */
	private void initialDiagramTitels(Graphics2D g2, int distL, int distR, int distT, int distB) {
		int w;
		FontMetrics fm = g2.getFontMetrics();
		g2.setFont(new Font("Sans Serif", Font.BOLD, labelFontSize));
		if (showChartTitel) {
			w = fm.stringWidth(chartTitel);
			int p = distL + (this.getWidth() - distR - distL) / 2;
			g2.drawString(chartTitel, p - (w / 2), 15);
		}
		if (showAxisTitelX) {
			w = fm.stringWidth(axisTitelX);
			int p = distL + (this.getWidth() - distR - distL) / 2;
			g2.drawString(axisTitelX, p - (w / 2), this.getHeight() - (distB - 25));
		}
		if (showAxisTitelY) {
			w = fm.stringWidth(axisTitelY);
			int f = distT + (this.getHeight() - (distB + distT)) / 2 + w / 2;
			g2.translate(15, f);
			g2.rotate(-(Math.PI / 2));
			g2.drawString(axisTitelY, 0, 0);
			g2.rotate((Math.PI / 2));
			g2.translate(-15, -f);
		}
	}

	/**
	 * Erzeugen der Linien und Beschriftungselemente der X-Achse
	 * 
	 * @param g2
	 */
	private void xAxis(Graphics2D g2) {
		// Breite des inneren Panels (Pixel)
		int wPan = chart.getWidth() - 10;
		// Festlegen der Schriftgröße
		g2.setFont(new Font("SansSerif", Font.PLAIN, tickFontSize));
		Point lineS, lineE;
		Line2D line2;
		int versatz = distL + 5;
		if (segmentsX == 0) {
			segmentsX = 10;
		}
		FontMetrics fm = g2.getFontMetrics();
		DecimalFormat df = new DecimalFormat("#0.##");
		double min = 0.0;
		if (! axisLabelingLeftRight) {
			min = maxXvis;
		} else {
			min = minXvis;
		}
		// Chart typ
		int count;
		double wP;
		if (usertextX) {
			count = segmentsX;
			wP = (0.0 + wPan) / (count - 2);
		} else {
			count = segmentsX + 1;
			// Segmentabstand
			wP = (0.0 + wPan) / (count - 1);
		}
		// Bestimmen der Spannweite (Wertebereich)
		double hX = maxXvis - minXvis;
		// Segmentwert
		double wPvalue = hX / (count - 1);
		// Schleife für alle Segmente
		ArrayList<Integer> segPX = new ArrayList<Integer>();
		int pxM = -999;
		for (int i = 0; i < count; i++) {
			// Bestimmen der Pixellänge des Beschriftungselements
			int w;
			String bez;
			if (! usertextX) {
				bez = "" + df.format(min - (i * wPvalue) * -1);
			} else {
				bez = "" + textX.get(i);
			}
			int f;
			if (! usertextX) {
				f = (int) (versatz + (i * wP));
			} else {
				f = (int) (versatz + (i * wP) - (wP / 2));
			}
			// Zeichnen der Achsenunterteilungen
			lineS = new Point(f, distT - 2);
			lineE = new Point(f, this.getHeight() - distB + 2);
			line2 = new Line2D.Double(lineS, lineE);
			// Setzen der Beschriftung
			w = fm.stringWidth(bez);
			int px = f - (w / 2);
			if (pxM == -999) {
				pxM = px;
				g2.drawString(bez, px, this.getHeight() - distB + 12);
			} else {
				if (pxM < px - w) {
					pxM = px;
					g2.drawString(bez, px, this.getHeight() - distB + 12);
				}
			}
			// Zwischenspeichern der Positionen der Hilfslinien
			if (usertextX == false) {
				g2.draw(line2);
				segPX.add(f - versatz + 5);
			} else {
				if (i != 0 && i != count - 1) {
					g2.draw(line2);
					segPX.add(f - versatz + 5);
				}
			}
		}
		// Einzeichnen der Hilfslinien in das innere Diagram
		chart.setRasterX(segPX);
	}

	/**
	 * Zeichnen und Beschriften der Y-Achse
	 * 
	 * @param g2
	 */
	private void yAxis(Graphics2D g2) {
		// Breite des inneren Panels (Pixel)
		int hPan = chart.getHeight() - 10;
		// Festlegen der Schriftgröße
		g2.setFont(new Font("Sans Serif", 0, tickFontSize));
		Point lineS, lineE;
		Line2D line2;
		int versatz = distT + 5;
		if (segmentsY == 0) {
			segmentsY = 10;
		}
		FontMetrics fm = g2.getFontMetrics();
		DecimalFormat df = new DecimalFormat("#0.##");
		double min = minYvis;
		int count;
		double wP;
		if (! usertextY) {
			count = segmentsY + 1;
			// Segmentabstand
			wP = (0.0 + hPan) / (count - 1);
		} 
		else {
			count = segmentsY;
			wP = (0.0 + hPan) / (count - 2);
		}
		// Bestimmen der Spannweite (Wertebereich)
		double hY = maxYvis - minYvis;
		// Segmentwert
		double wPvalue = hY / (count - 1);
		// Schleife für alle Segmente
		ArrayList<Integer> segPY = new ArrayList<Integer>();
		for (int i = 0; i < count; i++) {
			// Bestimmen der Pixellänge des Beschriftungselements
			String bez;
			int w;
			if (! usertextY) {
				bez = "" + df.format((min + (i * wPvalue)));
			} 
			else {
				bez = "" + textY.get(i);
			}

			int f;
			if (! usertextY) {
				if (! axisLabelingTopDown) {
					f = (int) (versatz + (i * wP));
				} 
				else {
					f = (int) (this.getHeight() - 5 - distB - (i * wP));
				}
			} 
			else {
				if (! axisLabelingTopDown) {
					f = (int) (versatz + (i * (int) wP) - (wP / 2));
				} 
				else {
					f = (int) (this.getHeight() - 5 - distB - (i * wP) - (wP / 2));
				}
				f = (int) (versatz + (i * wP) - (wP / 2));
			}
			// Zeichnen der Achsenunterteilungen
			lineS = new Point(distL - 2, f);
			lineE = new Point(getWidth() - distR + 2, f);
			line2 = new Line2D.Double(lineS, lineE);
			// Setzen der Beschriftung
			w = fm.stringWidth(bez);
			g2.drawString(bez, distL - w - 2, f);
			// Zwischenspeichern der Positionen der Hilfslinien
			if (! usertextY) {
				g2.draw(line2);
				segPY.add(f - versatz + 5);
			} 
			else {
				if (i != 0 && i != count - 1) {
					g2.draw(line2);
					segPY.add(f - versatz + 5);
				}
			}
		}
		chart.setRasterY(segPY);
	}
}