package slivisu.gui.globalcontrols.timescale;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import slivisu.data.MyZeitscheibe;
import slivisu.data.Zeitscheibe;
import slivisu.data.datatype.Balken;
import slivisu.gui.controller.InteractionListener;
import slivisu.mapper.SlivisuColors;

import com.visutools.nav.bislider.BiSlider;
import com.visutools.nav.bislider.BiSliderEvent;
import com.visutools.nav.bislider.BiSliderListener;
import com.visutools.nav.bislider.UnitPainterEvent;
import com.visutools.nav.bislider.UnitPainterListener;

/**
 * @author Sven
 */
public class TimeScale extends JPanel implements InteractionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimeScaleData data = null;
	private BiSlider timeslider = null;
	private int lastPosi;
	private boolean bol = false;
	private int segments = 10;

	public TimeScale(TimeScaleData data) {
		this.data = data;
		initial();
		updateView();
	}

	public void updateView() {
		changeTimeSlider();
		repaint();
	}

	public BiSlider getTimeslider() {
		return timeslider;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.paintZS(g);;
	}
	
	
	
	public void paintZS(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				
				
				List<MyZeitscheibe> zsList = this.data.selectedZeitscheibene();
				
				if (zsList != null) {
					double tsWidth = timeslider.getBounds().getWidth();
					double oneYear = tsWidth / (this.data.getGlobalMax() - this.data.getGlobalMin());
				
					
					double smallest = Math.abs(this.data.getGlobalMax() - this.data.getGlobalMin()) * oneYear;
					
					for (MyZeitscheibe zs : zsList) {
						
						if (zs.getAnfang() >= this.data.getGlobalMin() && zs.getEnde() <= this.data.getGlobalMax()) {
							double posx = 0;
							double posy = 0;
							
							g2d.setColor(SlivisuColors.neutral());
							posx = Math.abs(this.data.getGlobalMin() - zs.getAnfang());
							posx = oneYear * posx;
							
							if (posx < smallest) smallest = posx;
							posy = Math.abs(zs.getEnde() - zs.getAnfang());
							//System.out.println(posy);
							posy = oneYear * posy;
							//System.out.println("camp : " + posx + " " + 0 + " " + posy + " "+  0);
							g2d.fillRect( (int)posx,(int) timeslider.getBounds().getY() , (int)posy, (int)timeslider.getBounds().getHeight() / 2);
							
							
							
						}
					}
					if (smallest != Math.abs(this.data.getGlobalMax() - this.data.getGlobalMin()) * oneYear) {
						g2d.setColor(Color.black);
						g2d.drawString("Sicherheiten",(int) smallest,(int) timeslider.getBounds().getY());
					}
					
				}
			
	}
	


	private void initial() {
		this.setLayout(new BorderLayout());
		TitledBorder t = new TitledBorder("Time scale");
		t.setTitleFont(new Font("Sans Serif", Font.PLAIN, 9));
		t.setBorder(new LineBorder(Color.GRAY, 1, true));
		this.setBorder(t);
		timeslider = initTimeSlider(this);
		this.add(freeze(), BorderLayout.EAST);
		this.add(timeslider, BorderLayout.CENTER);
		timeslider.addMouseListener(new TimeScaleListener(data, this));
	}

	private JPanel freeze() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		final JCheckBox cb = new JCheckBox("freeze");
		cb.setSelected(data.isFreezeRange());
		cb.setFont(new Font("SansSerif", Font.PLAIN, 8));
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				data.setFreezeRange(cb.isSelected());
			}
		});
		p.add(cb, BorderLayout.CENTER);
		return p;
	}

	private BiSlider initTimeSlider(TimeScale timeScale) {
		final BiSlider bislider = new BiSlider();
		// Festlegen einer einheitlichen Farbe
		bislider.setInterpolationMode(BiSlider.RGB);
		bislider.setMinimumColor(Color.GRAY);
		bislider.setMaximumColor(Color.GRAY);
		bislider.setMiddleColor(Color.GRAY);
		bislider.setArcSize(5);
		bislider.setInheritsPopupMenu(true);
		bislider.setMinimumColoredSize(0.1f);
		bislider.addBiSliderListener(new BiSliderListener() {
			public void newValues(BiSliderEvent BiSliderEvent_Arg) {
				if (!bol) {
					int minA = (int) BiSliderEvent_Arg.getMinimumColored();
					int maxA = (int) BiSliderEvent_Arg.getMaximumColored();
					data.setMin(minA);
					data.setMax(maxA);
				}
			}
			public void newMin(BiSliderEvent BiSliderEvent_Arg) {
				if (!bol) {
					int min = (int) (bislider.getMinimumValue());
					if (min > data.getMin()){
						data.setMin(min);
					}
					data.setGlobalMin(min);
					updateSegments();
				}
			}
			public void newMax(BiSliderEvent BiSliderEvent_Arg) {
				if (!bol) {
					int max = (int) (bislider.getMaximumValue());
					if (max < data.getMax()){
						data.setMax(max);
					}
					data.setGlobalMax(max);
					updateSegments();
				}
			}
			public void newColors(BiSliderEvent BiSliderEvent_Arg) {}
			public void prepareJumpValues(BiSliderEvent BiSliderEvent_Arg) {}
			public void notifyJumpValues(BiSliderEvent BiSliderEvent_Arg) {}
			public void newMinValue(BiSliderEvent BiSliderEvent_Arg) {}
			public void newMaxValue(BiSliderEvent BiSliderEvent_Arg) {}
			public void newSegments(BiSliderEvent BiSliderEvent_Arg) {}
			public void newGlobalRange(BiSliderEvent BiSliderEvent_Arg) {}
		});

		bislider.addUnitPainterListener(new UnitPainterListener() {
			final DecimalFormat decimalFormater = new DecimalFormat("0.##");

			public void paintTick(UnitPainterEvent event) {
				Graphics2D g2 = (Graphics2D) event.getGraphics();
				g2.setFont(new Font("Sans Serif", Font.BOLD, 10));
				g2.setColor(bislider.getForeground());
				Rectangle rect2 = event.getRectangle();
				FontMetrics fontMetrics0 = g2.getFontMetrics();
				double val = event.getValue();
				String text = "" + decimalFormater.format(val);
				int pos = rect2.x + rect2.width / 2 - fontMetrics0.stringWidth(text) / 2;
				if (val == bislider.getMinimumValue()) {
					pos = 10;
					lastPosi = pos;
				}
				if (val == bislider.getMaximumValue()) {
					pos = rect2.x + rect2.width / 2 - fontMetrics0.stringWidth(text);
				}
				if ((pos - fontMetrics0.stringWidth(text)) >= lastPosi || val == bislider.getMinimumValue()) {
					lastPosi = pos;
					g2.drawString(text, pos, rect2.y + rect2.height - 2);
				}
			}

			public void paintValue(UnitPainterEvent event) {
				Graphics2D g2 = (Graphics2D) event.getGraphics();
				g2.setColor(bislider.getForeground());
				Rectangle rect2 = event.getRectangle();
				double val = event.getValue();
				FontMetrics fontMetrics0 = g2.getFontMetrics();
				String text = "" + decimalFormater.format(val);
				if (event.getValue() == bislider.getMinimumColoredValue())
					g2.drawString(text, rect2.x + rect2.width - fontMetrics0.stringWidth(text), rect2.y + rect2.height - 2);
				else
					g2.drawString(text, rect2.x, rect2.y + rect2.height - 2);
			}
		});
		return bislider;
	}

	private void changeTimeSlider() {
		bol = true;
		// Zeitbereich festlegen
		timeslider.setValues(data.getGlobalMin(), data.getGlobalMax());
		timeslider.setColoredValues(data.getMin(), data.getMax());
		updateSegments();
		bol = false;
	}

	private void updateSegments() {
		double max = timeslider.getMaximumValue();
		double min = timeslider.getMinimumValue();
		timeslider.setSegmentSize((max - min) / segments);
	}

	public int getSegments() {
		return segments;
	}

	protected void setSegments(int segments) {
		this.segments = segments;
		updateSegments();
	}

	@Override
	public String getListenerName() {
		return "TimeRange";
	}
}
