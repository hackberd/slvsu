/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.gui;


import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import slivisu.data.Data;


/**
 *
 * @author Sven
 */
public class InformationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String lastSLI = "";

	private JEditorPane pan;
	//    JLabel pan;

	public String getLastSLI() {
		return lastSLI;
	}


	public void setLastSLI( String lastSLI ) {
		this.lastSLI = lastSLI;
	}


	public InformationPanel( Data data ) {

		initial();

	}


	private void initial() {

		pan = new JEditorPane();
		pan.setEditable( false );
		//        pan = new JLabel();


		// add an html editor kit
		HTMLEditorKit kit = new HTMLEditorKit();
		pan.setEditorKit( kit );

		// add some styles to the html
		StyleSheet styleSheet = kit.getStyleSheet();
		//        styleSheet.addRule( "tr { background: #2C5755; text-align: center;}" );
		//        styleSheet.addRule( "tr.odd { background-color: #6E8D88;}" );
		//        styleSheet.addRule( "tr.head { background-color: #333333; }" );
		//        styleSheet.addRule( "th { background-color: #DBE6DD }" );



		styleSheet.addRule( "table { border-collapse: collapse; border: 1px solid #839E99; background: #f1f8ee; font: 1.em sans serif; color: #111111; }" );

		styleSheet.addRule( "th.head { background-color: #2c5755; font: 1.em sans serif; color: #dddddd; font-weight: bold; text-align: center; padding-right: .5em; vertical-align: top; }" );
		styleSheet.addRule( "th.odd { background: #839e99; font: 0.9em sans serif; color: #dddddd; font-weight: plain; text-align: center; padding-right: .5em; vertical-align: top; }" );
		styleSheet.addRule( "th.o { background: #6e8d88; font: 0.9em sans serif; color: #dddddd; font-weight: plain; text-align: center; padding-right: .5em; vertical-align: top; }" );

		styleSheet.addRule( "td.odd { background: #f1f8ee; font: 0.9em sans serif; color: #111111;  font-weight: plain; text-align: center; padding-right: .5em; vertical-align: top; }" );
		styleSheet.addRule( "td.o { background: #dbe6dd; font: 0.9em sans serif; color: #111111;  font-weight: plain; text-align: center; padding-right: .5em; vertical-align: top; }" );


		// create a document, set it on the jeditorpane, then add the html
		Document doc = kit.createDefaultDocument();
		pan.setDocument( doc );
		//        pan.setText(htmlString);




		this.setLayout( new BorderLayout() );
		this.add( new JScrollPane( pan ) , BorderLayout.CENTER );


	}

	public void updateInfoPan( String sliName ) {
		if (! sliName.equals( this.lastSLI ) ) {

			this.lastSLI = sliName;

			// Abfrage aller Informationen aus der jeweiligen DB-Tabelle
//			pan.setText(m.getObservations().observationSource.getSLIinfos(m.getObservations(), m, sliName ));
		}
	}
}
