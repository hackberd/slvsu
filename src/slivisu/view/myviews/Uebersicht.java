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

	SuperData data;
	public Uebersicht(SuperData data){
		this.data = data;
	}
	
	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#updateView()
	 */
	@Override
	public void updateView() {
		this.data.updateDataToSelectedData();
	}

	/* (non-Javadoc)
	 * @see slivisu.gui.controller.InteractionListener#getListenerName()
	 */
	@Override
	public String getListenerName() {
		return "UebersichtListenerName";
	}

}
