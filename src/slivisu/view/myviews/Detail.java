package slivisu.view.myviews;

import javax.swing.JPanel;

import slivisu.gui.controller.InteractionListener;

public class Detail extends JPanel implements InteractionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Detail(SuperDataDetail data) {
	}
	
	@Override
	public void updateView() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getListenerName() {
		return "DetailListenerName";
	}

}
