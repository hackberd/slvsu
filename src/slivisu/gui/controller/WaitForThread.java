package slivisu.gui.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Wartet für eine Liste von Threads auf deren Ende 
 * 
 * @author unger
 *
 */
public class WaitForThread implements Runnable {

	private ThreadGenerator	controller;
	private List<Thread> threads;
	
	
	/**
	 * @param controller - Erzeuger der Threads
	 */
	public WaitForThread(ThreadGenerator controller){
		this.controller = controller;
		this.threads = new ArrayList<Thread>();
	}
	
	/**
	 * @param index
	 * @param t
	 */
	public void add(Thread t){
		threads.add(t);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public synchronized void run() {

		int size = threads.size();
		
		for (int i=0; i < size; i++){
			Thread t = threads.get(i);
			if (t != null){
				try{
					t.join();
				}
				catch (InterruptedException e){
				}
			}
		}

		threads.clear();
		controller.finish();
	}
}