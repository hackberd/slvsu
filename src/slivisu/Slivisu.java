/**
 * 
 */
package slivisu;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import slivisu.data.Data;
import slivisu.gui.SLIvisuGUI;
import slivisu.gui.controller.InteractionController;


/**
 * @author Sven
 *
 */
public class Slivisu extends InteractionController{

	private SLIvisuGUI gui;
	private Data data = null;
	public static boolean showGlobe = true;

	// ##########  ##########  ##########  ##########

	public Slivisu(){

		try {
			// Set cross-platform Java L&F (also called "Metal")
			String lf = "";
			lf = UIManager.getCrossPlatformLookAndFeelClassName();
//			lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}

		data = new Data(this);
		gui = new SLIvisuGUI(data, this);
		// Bestimmen des aktuellen Speicherorts
		System.setProperty("org.geotools.referencing.forceXY", "true");
		
		setStatus(" Build views");
		// Initialisert die oberste View-Ebene (SLI- und Modellansicht)
		gui.showMainViews();
		setStatus(null);	
	}

	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		for (String s : args){
			if (s.equals("noglobe")){
				showGlobe = false;
			}
		}
		new Slivisu();
	}
}
