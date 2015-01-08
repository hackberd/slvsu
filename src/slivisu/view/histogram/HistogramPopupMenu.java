/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.histogram;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


/**
 * 
 * @author Sven
 */
public class HistogramPopupMenu extends JPopupMenu {


	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private HistogramChart			histogramChart;
	private JMenuItem					itemHistTyp;
	private JCheckBoxMenuItem			itemHistTyp1;
	
	public HistogramPopupMenu( HistogramChart histogramChart) {
		this.histogramChart = histogramChart;
		initialMapPopUpMenu ();
	}

	private void initialMapPopUpMenu() {
		this.add ( itemOnlyMarked () );
		this.addSeparator ();
		itemHistTyp = itemHistTyp ();		
		itemHistTyp1 = itemHistTyp1 ();
		this.add ( itemHistTyp );
		this.add ( itemHistTyp1 );
		this.addSeparator ();
		this.add ( itemTotal () );
	}

	private JCheckBoxMenuItem itemTotal() {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem ( "show total count bar" );
		item.setSelected ( true );
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				histogramChart.setTotalCountBar(item.isSelected());
			}
		} );
		return item;
	}

//	private JCheckBoxMenuItem itemHistTyp0() {
//
//		final JCheckBoxMenuItem item = new JCheckBoxMenuItem ( "SLI-categorie visualMapping" );
//		item.setIcon ( new ImageIcon ( getClass ().getResource ( "/controller/resources/view-refresh_16x16.png" ) ) );
//		item.setSelected ( true );
//		item.addActionListener ( new ActionListener () {
//			@Override
//			public void actionPerformed( ActionEvent e ) {
//				if( item.isSelected () == true ) {
//					histoContr.setViewTyp ( 0 );
//				}
//			}
//		} );
//		return item;
//	}

	private JCheckBoxMenuItem itemHistTyp1() {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem ( "SLI-categorie differentiation" );
		item.setSelected(true);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				if (item.isSelected()) {
					histogramChart.setViewTyp(1);
				}
				else {
					histogramChart.setViewTyp(0);
				}
			}
		} );
		return item;
	}

	private JCheckBoxMenuItem itemHistTyp() {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem ( "marked SLI in separate bar" );
		item.setSelected ( false );
		item.addActionListener ( new ActionListener () {
			@Override
			public void actionPerformed( ActionEvent e ) {
				if( item.isSelected ()) {					
					histogramChart.setBars (2 );
				}
				else {					
					histogramChart.setBars (1 );
				}
			}
		} );
		return item;
	}
	
	private JCheckBoxMenuItem itemOnlyMarked() {
		final JCheckBoxMenuItem item = new JCheckBoxMenuItem ( "Only selection" );
		item.setSelected (histogramChart.getShowSelection());
		//item.setEnabled ( false );
		item.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(item.isSelected()){
					histogramChart.setShowSelection(true);
				}else{
					histogramChart.setShowSelection(false);
				}
				
				
//				if( item.isSelected() == true ) {
//					item.setText ( "2 bars" );
//					histoContr.setBars ( 2 );
//				}
//				else {
//					item.setText ( "1 bar" );
//					histoContr.setBars ( 1 );
//				}
			}
		} );
		return item;
	}
	
}
