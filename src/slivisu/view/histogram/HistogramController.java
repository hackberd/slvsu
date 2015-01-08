/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.histogram;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import slivisu.defaults.chart.DefaultChartPanel;
import slivisu.gui.controller.InteractionListener;


/**
 * 
 * @author Sven
 */
public class HistogramController extends JPanel implements InteractionListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HistogramData							data;
	private DefaultChartPanel						histogram;
	private HistogramChart 							histogramChart;

	// ########################################

	public HistogramController(HistogramData data) {

		this.data = data;
		createHistograms();
		layoutPanel();
	}

	private DefaultChartPanel createHistograms() {

		histogram = new DefaultChartPanel();
		histogram.setDistances( 50, 10, 30, 20);
		histogram.setAxisLabelingLeftRight( false);
		histogram.setAxisLabelingTopDown( true);
		histogram.setSegmentsX ( 10 );
		histogram.setSegmentsY ( 10 );
		histogram.setAxisTitelX( "Age (yr)");
		histogram.setAxisTitelY( "Number of SLIs");
		histogram.setChartTitel( "");
		histogram.setShowAxisTitelX( true );
		histogram.setShowAxisTitelY( true );
		histogram.setShowChartTitel( true );
		histogram.setTickChangerX ( true );
		histogram.setTickChangerY ( true );

		histogramChart = new HistogramChart(histogram, data);
		histogram.setChart(histogramChart);
		
		return histogram;
	}

	private void layoutPanel() {

		this.setLayout(new BorderLayout());
		
		JPanel dummy = new JPanel();
		dummy.setPreferredSize(new Dimension(50, 1));

		this.add(histogram, BorderLayout.CENTER);
		this.add(dummy, BorderLayout.EAST);
	}

	@Override
	public void updateView() {
		histogramChart.setUpdate(true);
		data.updateData();
		repaint();
	}

	@Override
	public String getListenerName() {
		return "Histogram";
	}
}