package slivisu.gui.controller;

public class InteractionListenerThread extends Thread {

	InteractionListener listener = null;
	
	public InteractionListenerThread(InteractionListener listener){
		this.listener = listener;
	}
	
	@Override
	public void run(){
		listener.updateView();
	}
}
