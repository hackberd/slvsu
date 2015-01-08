/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.histogram;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import slivisu.defaults.chart.DefaultChart;

/**
 *
 * @author Sven
 */
public class HistogramPopupListener extends MouseAdapter {

	private HistogramChart chart;
	private HistogramData histogramData;
	private HistogramPopupMenu popupMenu;

	public HistogramPopupListener(HistogramChart chart, HistogramData histogramData) {
		this.histogramData = histogramData;
		this.chart = chart;

		popupMenu = new HistogramPopupMenu(chart);
	}

	@Override
	public void mouseReleased( MouseEvent e ) {

		if ( e.getButton() == 3 ) {
			showPopup( e );
		}
		else {
			Collection<CategoryBar> selectedBars = chart.getSelectedBars(e.getX(), e.getY()); 
			histogramData.markData(selectedBars, e.isControlDown());
		}
	}

	private void showPopup( MouseEvent e ) {
		popupMenu.show( e.getComponent() , e.getX() , e.getY() );

	}

	@Override
	public void mouseMoved( MouseEvent e ) {

		double x = (e.getX());
		double y = (e.getY());


		List<CategoryBar> map = chart.getPolygonsAll();
		List<CategoryBar> mapMarked = chart.getPolygonsMarked();

		if (map == null)
		{
			return;
		}

		Iterator<CategoryBar> iter = map.iterator();
		Iterator<CategoryBar> iterMarked = mapMarked.iterator();

		String tip = "<html>";
		while ( iter.hasNext() ) {

			CategoryBar bar = iter.next();

			if ( bar.contains( x , y ) ) {
				tip = tip + "<b>categorie:<font size=\"4\"> " + bar.getCategory() + "</font></b> <br>"
						+ "<b>age range (yr) :</b>  " + bar.getMin() + " - " + bar.getMax() + "<br>"
						+ "<b>SLIs (total):</b>  " + bar.getCount() + "<br>";

				if ( bar.getCategory().equals( "total" ) ) {

					String s = "<br> <u> marked SLI</u><br>";
					while ( iterMarked.hasNext() ) {
						CategoryBar barMarked = iterMarked.next();

						if ( bar.contains( barMarked ) ) {
							s = s + "categorie: " + barMarked.getCategory() + " (" + barMarked.getCount() + ")" + "<br>";
						}
					}

					tip = tip + s + "<br><hr>";

				}
				else
				{
					iterMarked = mapMarked.iterator();
					while ( iterMarked.hasNext() ) {
						CategoryBar barM = iterMarked.next();
						if ( bar.contains( barM ) ) {
							tip = tip + "<b>SLIs (marked):</b>  " + barM.getCount() + "<br>";
						}
					}
				}
			}
		}

		tip = tip + "</html>";

		// Information als Tooltip ausgeben
		(( DefaultChart ) e.getSource()).setToolTipText( tip );

	}
}