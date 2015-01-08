/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import slivisu.data.Data;
import slivisu.data.io.SettlementDataReader;
import slivisu.gui.controller.InteractionController;

/**
 * 
 * @author Sven
 */
public class SLIvisuMenuBar extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private InteractionController viewControl;

	private SettlementDataReader reader;

	private JMenu menuViews = new JMenu();


	private JMenuItem loadCsv;

	public SLIvisuMenuBar(Data data, InteractionController viewControl) {
		this.viewControl = viewControl;
		this.reader = new SettlementDataReader(data);
		this.setBackground(new Color(202, 203, 188));
		this.add(initialMenuProgram());
		initialMenuViews();
	}

	private JMenu initialMenuProgram() {
		JMenu menuProgram = new JMenu();
		menuProgram.setText("Program");
		menuProgram.setFont(new Font("SansSerif", Font.PLAIN, 10));
		menuProgram.add(itemLoadZeitscheibe());
		loadCsv = itemLoadCSV();
		menuProgram.add(loadCsv);
		menuProgram.add(itemExit());
		return menuProgram;
	}

	private void initialMenuViews() {
		menuViews = new JMenu();
		menuViews.setText("views");
	}

	/**
	 * @return MenuItem zum Beenden des Programms
	 */
	private JMenuItem itemExit() {
		JMenuItem item = new JMenuItem();
		item.setText("Exit");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				exitSLIVISU();
			}
		});
		return item;
	}

	/**
	 * Beendet das Programm und sichert die Einstellungen
	 */
	public void exitSLIVISU() {
		System.exit(0);
	}

	private JMenuItem itemLoadCSV() {
		JMenuItem item = new JMenuItem();
		item.setText("Load sample data");
		item.setEnabled(false);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				reader.loadCSV();
				viewControl.receive();
			}
		});
		return item;
	}


	private JMenuItem itemLoadZeitscheibe() {
		JMenuItem item = new JMenuItem();
		item.setText("Load chronology data");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (reader.loadZeitscheibe()){
					loadCsv.setEnabled(true);
				}
			}
		});
		return item;
	}
}
