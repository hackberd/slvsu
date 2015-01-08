package slivisu.gui.controller;

import java.util.ArrayList;
import java.util.List;

public class InteractionController implements ThreadGenerator{

	/** aktueller Programmstatus (z.B. Check Database Connection) */
	private String status = null;
	protected List<InteractionListener> listener = null;
	private DataInterface dataInterface = null;

	public InteractionController(){
		listener = new ArrayList<InteractionListener>();
	}
	
	int counter = 0;

	public synchronized void receive() {
		setStatus("Update views.." + counter ++);
		WaitForThread waitForThread = new WaitForThread(this);
		for (int i=0; i < listener.size(); i++){
			InteractionListener l = listener.get(i);
			Thread t = new InteractionListenerThread(l);
			t.start();
			waitForThread.add(t);
		}
		Thread t = new Thread(waitForThread);
		t.start();
	}

	public void addInteractionListener(InteractionListener listener){
		this.listener.add(listener);
	}
	
	public void setDataInterface(DataInterface dataInterface){
		this.dataInterface = dataInterface;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void addStatus(String status){
		this.status += status;
	}

	public void removeStatus(String status){
		this.status.replace(status, "");
	}
	
	public void finish(){
		if (dataInterface != null){
			dataInterface.wrapUp();
		}
		this.status = "";
	}
}