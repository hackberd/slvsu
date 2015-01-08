package slivisu.data.selection;

import slivisu.gui.controller.InteractionController;


public class SelectionListenerThread<E> extends Thread {

	private InteractionController controller;
	private SelectionListener<E> listener = null;
	private Selection<E> selection;
	
	public SelectionListenerThread(InteractionController controller, Selection<E> selection, SelectionListener<E> listener){
		this.controller = controller;
		this.listener = listener;
		this.selection = selection;
	}
	
	@Override
	public void run(){
		if (controller != null){
			controller.setStatus("Update selection");
		}
		listener.updateData(selection);
		if (controller != null){
			controller.setStatus(null);
		}
	}
}
