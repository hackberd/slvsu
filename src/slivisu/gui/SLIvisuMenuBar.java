/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import slivisu.data.Data;
import slivisu.data.Wegenetz;
import slivisu.data.Zeitscheibe;
import slivisu.data.io.SettlementDataReader;
import slivisu.data.io.WegenetzDataReader;
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

	private Data data;

	private JMenu menuViews = new JMenu();


	private JMenuItem loadCsv;
	private JMenuItem loadWege;

	public SLIvisuMenuBar(Data data, InteractionController viewControl) {
		this.data = data;
		this.viewControl = viewControl;
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
		loadWege = itemLoadWege();
		menuProgram.add(loadWege);
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

	private JMenuItem itemLoadZeitscheibe() {
		JMenuItem item = new JMenuItem();
		item.setText("Load chronology data");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (new SettlementDataReader(data).loadZeitscheibe()){
					loadCsv.setEnabled(true);
					loadWege.setEnabled(true);
				}
			}
		});
		return item;
	}
	
	private JMenuItem itemLoadCSV() {
		JMenuItem item = new JMenuItem();
		item.setText("Load sample data");
		item.setEnabled(false);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new SettlementDataReader(data).loadCSV();
				viewControl.receive();
			}
		});
		return item;
	}

	
	private JMenuItem itemLoadWege() {
		JMenuItem item = new JMenuItem();
		item.setText("Load way data");
		item.setEnabled(false);
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Map<Zeitscheibe, Wegenetz> wegenetz = new WegenetzDataReader().loadWegenetzFuerZeitscheibe(data.getObservationData().getChronologie());
				data.addWegenetz(wegenetz);				
				viewControl.receive();
			}
		});
		return item;
	}
}
