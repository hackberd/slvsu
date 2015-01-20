package slivisu.view.myviews;

import javax.swing.JPanel;

import slivisu.gui.controller.InteractionListener;

public class Detail extends JPanel implements InteractionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SuperDataDetail data;
	public Detail(SuperDataDetail data) {
		this.data = data;
	}
	
	@Override
	public void updateView() {
		if (data != null) {
			this.data.updateDataToSelectedSamples();;
		}
	}

	@Override
	public String getListenerName() {
		return "DetailListenerName";
	}

}
