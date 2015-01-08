/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.gui;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JToolBar;

import slivisu.gui.controller.InteractionController;


/**
 *
 * @author Sven
 */
public class StatusPanel extends JToolBar implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InteractionController controller;
	private JLabel processStatus;
	private int iconCount = 0;
	private boolean stopThread = false;

	public StatusPanel(InteractionController controller) {
		this.controller = controller;
		this.setLayout(new BorderLayout());
		this.add(initialProcessStatus(), BorderLayout.CENTER);
	}

	private JLabel initialProcessStatus() {
		processStatus = new JLabel("");
		processStatus.setMinimumSize(new Dimension(150, 20));
		return processStatus;
	}

	public void startStatusThread() {
		iconCount = 0;
		stopThread = false;
		Thread t = new Thread(this);
		t.start();
	}

	public void stopStatusThread() {
		stopThread = true;
	}

	@Override
	public void run() {
		while (! stopThread) {
			try {
				count();
				Thread.sleep(100);
			}
			catch (InterruptedException ex) {
				System.out.println("" + ex.getMessage());
			}
		}
	}

	/**
	 * Methode verändert das Statusicon und die Statustext
	 * Blendet die einzelnen Views während Aktualisierungsvorgängen aus
	 */
	public void count() {
		String s = controller.getStatus();
		if (s == null){
			s = "";
		}
		processStatus.setText(s);
	}

	public void stopStatusPanelThread() {
		stopThread = true;
	}
}
