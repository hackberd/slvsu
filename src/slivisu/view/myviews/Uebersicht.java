/**
 * 
 */
package slivisu.view.myviews;

import javax.swing.JPanel;

import slivisu.gui.controller.InteractionListener;

/**
 * @author immanuelpelzer
 *
 */
public class Uebersicht extends JPanel implements InteractionListener {

	private static final long serialVersionUID = 1L;

	
	public Uebersicht(SuperData data){
	
	}
	
	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#updateView()
	 */
	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#getListenerName()
	 */
	@Override
	public String getListenerName() {
		return "UebersichtListenerName";
	}

}
