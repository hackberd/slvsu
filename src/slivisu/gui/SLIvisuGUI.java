/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.View;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.theme.ShapedGradientDockingTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;
import slivisu.data.Data;
import slivisu.gui.controller.InteractionController;
import slivisu.gui.globalcontrols.timescale.TimeScale;
import slivisu.mapper.SlivisuGlobeData;
import slivisu.mapper.SlivisuHistogramData;
import slivisu.mapper.SlivisuObservationsTableModel;
import slivisu.mapper.SuperDataAnimationImpl;
import slivisu.mapper.SuperDataDetailImp;
import slivisu.mapper.SuperDataUebersichtImp;
import slivisu.view.globe.SliGlobe;
import slivisu.view.globe.SliGlobePopupMenu;
import slivisu.view.histogram.HistogramController;
import slivisu.view.myviews.AnimationView;
import slivisu.view.myviews.Detail;
import slivisu.view.myviews.Uebersicht;
import slivisu.view.table.AttributeTables;


/**
 *
 * @author Sven
 */
public class SLIvisuGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Data data;
	private InteractionController viewControl;
	private JPanel mainPanel;

	private RootWindow rootWindow;
	private boolean showListing = true;

	public SLIvisuGUI(Data data, InteractionController viewControl) {
		super("Slivisu");
		this.data = data;
		this.viewControl = viewControl;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add MenuBar
		JMenuBar sliMenuBar =  new SLIvisuMenuBar(data, viewControl);
		setJMenuBar(sliMenuBar);

		// add MainPanel (mit Startbildschirm)
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		StatusPanel statusPan = new StatusPanel(viewControl);
		statusPan.startStatusThread(); 
		mainPanel.add(statusPan, BorderLayout.SOUTH);
		
		add(mainPanel, BorderLayout.CENTER);

		setSize(1000, 700);
		setPreferredSize(new Dimension(1200, 700));
		centerFrame();

		pack();
		setVisible(true);
	}

	/**
	 * Zentriert das UI auf dem Bildschirm
	 */
	public void centerFrame() {
		try {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x = dim.width - getWidth();
			int y = dim.height - getHeight();
			x = x / 2;
			y = y / 2;
			setLocation(x , y);
		}
		catch (HeadlessException ex ) {
			System.err.println( "error in SLIvisuGUI - centerFrame  " + ex.getMessage() );
		}
	}

	private JToolBar initControlBar() {

		JToolBar toolBar = new JToolBar();
		// Zeitschieber
		TimeScale timeScale = new TimeScale(data.getTimeRange());
		viewControl.addInteractionListener(timeScale);
		toolBar.add(timeScale);
		toolBar.addSeparator();
		return toolBar;
	}

	/**
	 * Oeffnet die Viewzusammenstellung des Analysetools
	 */
	public void showMainViews() {
		
		viewControl.setStatus(" Create control bar");
		// Erzeugen der ToolBar und der Hauptansicht (SLI und Data views)
		JToolBar contrBar = initControlBar();
		viewControl.setStatus(" Create main view");

		Map<String, View> viewPanel = initialViews(data);
		rootWindow = initialRootWindow();
		rootWindow.setWindow(getDefaultLayout(viewPanel));
		viewControl.setStatus(" Layout");
		mainPanel.add(contrBar, BorderLayout.NORTH);
		mainPanel.add(rootWindow, BorderLayout.CENTER);
		
		setSize(1024, 780);
		pack();
		centerFrame();
	}

	/**
	 * Initiziert zwei Hauptviews (SLI- und Data-View Zusammenstellungen)
	 */
	private Map<String, View> initialViews(Data data) {
		Map<String, View> viewPanel = new HashMap<String, View>();
		SuperDataAnimationImpl animationData = new SuperDataAnimationImpl(data);
		// Map
		viewControl.setStatus(" Build views .. globe");
		SlivisuGlobeData globeData = new SlivisuGlobeData(data,animationData);
		SliGlobe globe = new SliGlobe(globeData);
		animationData.setGlobe(globe);
		animationData.setGlobeData(globeData);
		globe.setPopupMenu(new SliGlobePopupMenu(globe));
		viewPanel.put("globe", new View("Geographic distribution (Globe)", null, globe));		
		viewControl.addInteractionListener(globe);
		// Histrogramm
		viewControl.setStatus(" Build views .. histogram ");
		HistogramController histogram = new HistogramController(new SlivisuHistogramData(data));
		viewPanel.put("histogram", new View("Time histogram", null, histogram));
		viewControl.addInteractionListener(histogram);
		// Tabelle
		if (showListing){
			viewControl.setStatus(" Build views .. SLI tables");
			Map<String, SlivisuObservationsTableModel> attributeData = new HashMap<String, SlivisuObservationsTableModel>();
			attributeData.put("Siedlungsfunde", new SlivisuObservationsTableModel("Siedlungsfunde", data));
			AttributeTables attributeTable = new AttributeTables(viewControl, attributeData);
			viewControl.addInteractionListener(attributeTable);
			viewPanel.put("listing", new View("Listing", null, attributeTable));
		}
		
		// Uebersicht
		Uebersicht uebersichtView = new Uebersicht(new SuperDataUebersichtImp(data));
		// ODER EINFACHE VARIANTE: MeineVis vis = new MeineVis(data);
		viewPanel.put("UebersichtView",new View("Übersicht", null, uebersichtView));
		viewControl.addInteractionListener(uebersichtView);
		// Details
		Detail detailView = new Detail(new SuperDataDetailImp(data));
		// ODER EINFACHE VARIANTE: MeineVis vis = new MeineVis(data);
		viewPanel.put("DetailView",new View("Detail", null, detailView));
		viewControl.addInteractionListener(detailView);
		// Animation
		
		
		AnimationView animationView = new AnimationView(animationData);
		viewPanel.put("AnimationView",new View("Animation", null, animationView));
		viewControl.addInteractionListener(animationView.getListener());
		
		// End adding
		return viewPanel;
	}

	private DockingWindow getDefaultLayout(Map<String, View> viewPanel) {
		
		// Sea Level Curve
		DockingWindow histogramm = viewPanel.get("histogram");
	
		DockingWindow globe = viewPanel.get("globe");
		
		DockingWindow animationView = viewPanel.get("AnimationView");
		
		globe = new SplitWindow(false, 0.35f, animationView, globe);
		
		histogramm = new SplitWindow(false, 0.15f, histogramm, globe);
		
		
		
		
		// FALSE = untereinander
		// TRUE NEBEN
		
		
		
		// Detail view
		DockingWindow detailViewDockingWindow = viewPanel.get("DetailView");
		
		// Tabelle und Datenbankhierarchie f�r Beobachtungsdaten horizontal gruppieren
		DockingWindow completeRightView = viewPanel.get("UebersichtView");
		
		
		DockingWindow listing = viewPanel.get("listing");
		DockingWindow listingAndDetail = new SplitWindow(false, 0.5f, detailViewDockingWindow, listing);
		
		// get uebersicht
		completeRightView = new SplitWindow(false, 0.5f, completeRightView, listingAndDetail);
		
		
		// Gruppe 1 und Gruppe 2 horizontal
		DockingWindow s2 = new SplitWindow(true, 0.3f, histogramm , completeRightView);

		
		
		return s2;
	}

	private RootWindow initialRootWindow() {
		RootWindow rootWindow = DockingUtil.createRootWindow(new ViewMap(), false);
		rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);
		RootWindowProperties properties = new RootWindowProperties();
		properties.getDockingWindowProperties().setCloseEnabled(false);
		properties.getDockingWindowProperties().getTabProperties().getHighlightedButtonProperties().getUndockButtonProperties().setVisible(false);
		properties.getDockingWindowProperties().getTabProperties().getHighlightedButtonProperties().getMinimizeButtonProperties().setVisible(false);
		properties.getDockingWindowProperties().getTabProperties().getHighlightedButtonProperties().getUndockButtonProperties().setVisible(false);
		DockingWindowsTheme theme = new ShapedGradientDockingTheme();
		properties.addSuperObject(theme.getRootWindowProperties());
		properties.replaceSuperObject(theme.getRootWindowProperties(), theme.getRootWindowProperties());
		// Einstellungen fuer den ausgewaehlten Tab
		properties.getTabWindowProperties().getTabProperties().getTitledTabProperties().getHighlightedProperties().setTextTitleComponentGap(50);
		properties.getTabWindowProperties().getTabProperties().getTitledTabProperties().getHighlightedProperties().getComponentProperties().setFont(new Font("Sans Serif", Font.BOLD, 9));
		properties.getTabWindowProperties().getTabProperties().getTitledTabProperties().getHighlightedProperties().getComponentProperties().setBackgroundColor(new Color(239, 228, 176));
		// Einstellungen fuer normale Tabs
		properties.getTabWindowProperties().getTabProperties().getTitledTabProperties().getNormalProperties().getComponentProperties().setFont(new Font("Sans Serif", Font.PLAIN, 9));
		rootWindow.getRootWindowProperties().addSuperObject( properties );
		return rootWindow;
	}

	public RootWindow getRootWindow(){
		return rootWindow;
	}
}
