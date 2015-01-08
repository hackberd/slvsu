package slivisu.view.globe;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.globes.Earth;
import gov.nasa.worldwind.globes.EarthFlat;
import gov.nasa.worldwind.globes.FlatGlobe;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.LatLonGraticuleLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.ScalebarLayer;
import gov.nasa.worldwind.layers.WorldMapLayer;
import gov.nasa.worldwind.layers.Earth.BMNGWMSLayer;
import gov.nasa.worldwind.layers.Earth.CountryBoundariesLayer;
import gov.nasa.worldwind.util.StatusBar;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.FlatOrbitView;
import gov.nasa.worldwindx.examples.util.LayerManagerLayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JPanel;

import slivisu.gui.controller.InteractionListener;

/**
 * @author Sven
 */
public class SliGlobe extends JPanel implements InteractionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GlobeData 					data;
	private StatusBar 					statusBar;
	private LayerManagerLayer 			layerManager;
	private WorldWindowGLCanvas 		wwd;
	private Globe 						globe;
	private Globe 						roundGlobe;
	private FlatGlobe 					flatGlobe;
	private LayerList 					layerList;
	private LayerList					addedLayers;
	private String 						projection;
	private SliGlobeListener 			listener;
	private SliGlobePopupMenu 			popupMenu 			= null;
	
	//private boolean showSLI = true;
	//private boolean onlySelection = false;	

	public SliGlobe(GlobeData data) {

		this.data = data;
		this.popupMenu = new SliGlobePopupMenu(this);
		this.setComponentPopupMenu(popupMenu);
		listener = new SliGlobeListener(popupMenu, data); 
		init();
	}

	private void init() {

		this.setLayout(new BorderLayout());

		this.wwd = new WorldWindowGLCanvas();
		this.wwd.setPreferredSize(new java.awt.Dimension(300, 300));
		this.wwd.addMouseListener(listener);
		this.wwd.addSelectListener(listener);

		this.flatGlobe = new EarthFlat();
		this.roundGlobe = new Earth();
		this.globe = roundGlobe;
		this.layerList = new LayerList();
		this.addedLayers = new LayerList();

		this.layerList.add(new BMNGWMSLayer());
		// Koordinatenraster - Layer
		this.layerList.add(new LatLonGraticuleLayer());
		this.layerList.add(new WorldMapLayer());
		this.layerList.add(new ScalebarLayer());
		this.layerList.add(new CompassLayer());
		this.layerList.add(new CountryBoundariesLayer());

		this.wwd.setModel(new BasicModel(globe, layerList));

		// Layermanager als Layer
		this.layerManager = new LayerManagerLayer(wwd);
		this.layerManager.setName("Layer");
		this.layerManager.setFont(new Font("SansSerif", Font.PLAIN, 11));
		this.layerList.add(layerManager);

		// Deaktivieren einiger Standard-Layer
		//wwd.getModel().getLayers().getLayerByName("i-cubed Landsat").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("USDA NAIP").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("USDA NAIP USGS").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("USGS Topographic Maps 1:250K").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("USGS Topographic Maps 1:100K").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("USGS Topographic Maps 1:24K").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("USGS Urban Area Ortho").setEnabled(false);
		wwd.getModel().getLayers().getLayerByName("Political Boundaries").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("Place Names").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("World Map").setEnabled(false);
		//wwd.getModel().getLayers().getLayerByName("Scale bar").setEnabled(false);
		wwd.getModel().getLayers().getLayerByName("Compass").setEnabled(false);
		wwd.getModel().getLayers().getLayerByName("Lat-Lon Graticule").setEnabled(false);
		wwd.getModel().getLayers().getLayerByName("Layer").setEnabled(false);

		this.add(wwd, BorderLayout.CENTER);

		// Erzeugen und Anpassen der StatusBar
		this.statusBar = new StatusBar();
		this.statusBar.setEventSource(wwd);
		this.statusBar.getComponent(0).setFont(new Font("SansSerif", Font.BOLD, 10));
		this.statusBar.getComponent(1).setFont(new Font("SansSerif", Font.BOLD, 10));
		this.statusBar.getComponent(2).setFont(new Font("SansSerif", Font.BOLD, 10));
		this.statusBar.getComponent(3).setFont(new Font("SansSerif", Font.BOLD, 10));

		this.add(statusBar, BorderLayout.SOUTH);
	}


	@Override
	public void updateView() {

		if (data.updateData()){
			for (Layer layer : addedLayers){
				layerList.remove(layer);
			}
			addedLayers.removeAll();
			for (RenderableLayer layer : data.getLayers()){
//				if(showSLI){
//					if(layer.getName()!="Marked SLIs Bunt"){
						addedLayers.add(layer);
						layerList.add(layerList.size() - 1, layer);
						/*
					}
				}else if(onlySelection){
					if(layer.getName()!="SLIs" && layer.getName()!="Marked SLIs"){
						addedLayers.add(layer);
						layerList.add(layerList.size() - 1, layer);
					}
					
				*/
			}
		}
		wwd.redraw();
		repaint();
	}

	@Override
	public String getListenerName() {
		return "SliGlobe";
	}

	private String getProjection() {

		if (projection == null) {
			return FlatGlobe.PROJECTION_MERCATOR;
		}

		if (projection.equals("Mercator"))
			return FlatGlobe.PROJECTION_MERCATOR;
		else if (projection.equals("Sinusoidal"))
			return FlatGlobe.PROJECTION_SINUSOIDAL;
		else if (projection.equals("Modified Sin."))
			return FlatGlobe.PROJECTION_MODIFIED_SINUSOIDAL;
		// Default to lat-lon
		return FlatGlobe.PROJECTION_LAT_LON;
	}

	public boolean isFlatGlobe() {
		return globe == flatGlobe;
	}

	public void setGlobe(boolean flat) {

		if (isFlatGlobe() != flat){

			if (!flat) {

				globe = roundGlobe;
				// Switch to round globe
				wwd.getModel().setGlobe(roundGlobe);
				// Switch to orbit view and update with current position
				FlatOrbitView flatOrbitView = (FlatOrbitView) wwd.getView();
				BasicOrbitView orbitView = new BasicOrbitView();
				orbitView.setCenterPosition(flatOrbitView.getCenterPosition());
				orbitView.setZoom(flatOrbitView.getZoom());
				orbitView.setHeading(flatOrbitView.getHeading());
				orbitView.setPitch(flatOrbitView.getPitch());
				wwd.setView(orbitView);
				// Change sky layer
				// LayerList layers = wwd.getModel().getLayers();
				// for(int i = 0; i < layers.size(); i++)
				// {
				// if(layers.get(i) instanceof SkyColorLayer)
				// layers.set(i, new SkyGradientLayer());
				// }
			} 
			else {
				globe = flatGlobe;
				// Switch to flat globe
				wwd.getModel().setGlobe(flatGlobe);
				flatGlobe.setProjection(this.getProjection());
				// Switch to flat view and update with current position
				BasicOrbitView orbitView = (BasicOrbitView) wwd.getView();
				FlatOrbitView flatOrbitView = new FlatOrbitView();
				flatOrbitView.setCenterPosition(orbitView.getCenterPosition());
				flatOrbitView.setZoom(orbitView.getZoom());
				flatOrbitView.setHeading(orbitView.getHeading());
				flatOrbitView.setPitch(orbitView.getPitch());
				wwd.setView(flatOrbitView);
				// Change sky layer
				// LayerList layers = wwd.getModel().getLayers();
				// for(int i = 0; i < layers.size(); i++)
				// {
				// if(layers.get(i) instanceof SkyGradientLayer)
				// layers.set(i, new SkyColorLayer());
				// }
			}

			wwd.redraw();
		}
	}

	// Update flat globe projection
	protected void updateProjection() {

		this.flatGlobe.setProjection(this.getProjection());
		if (isFlatGlobe()){

			// Update flat globe projection
			this.wwd.redraw();
		}
	}

	public void showPopup(Component comp, int x, int y) {		
		popupMenu.show(comp, x, y);
	}

	// ########## Getter und Setter ##########

	public void setProjection(String projection) {
		this.projection = projection;
	}

	public void setPopupMenu(SliGlobePopupMenu popupMenu){
		this.popupMenu = popupMenu;
		this.setComponentPopupMenu(popupMenu);
		this.listener.setPopupMenu(popupMenu);
	}

	/*
	public boolean isShowSLI() {
		return showSLI;
	}

	public void setShowSLI(boolean showSLI) {
		this.showSLI = showSLI;
	}

	public boolean isOnlySelection() {
		return onlySelection;
	}

	public void setOnlySelection(boolean onlySelection) {
		this.onlySelection = onlySelection;
		
		for (Layer layer : addedLayers){
			layerList.remove(layer);
		}
		addedLayers.removeAll();
		for (RenderableLayer layer : data.getLayers()){
			if(showSLI){
				if(layer.getName()!="Marked SLIs Bunt"){
					addedLayers.add(layer);
					layerList.add(layerList.size() - 1, layer);
				}
			}else if(onlySelection){
				if(layer.getName()!="SLIs" && layer.getName()!="Marked SLIs"){
					addedLayers.add(layer);
					layerList.add(layerList.size() - 1, layer);
				}
			}
		}
		wwd.redraw();
	}
	*/
	// ########## ##########
}
