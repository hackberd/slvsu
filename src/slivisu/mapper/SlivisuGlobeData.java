package slivisu.mapper;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.Polyline;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import slivisu.data.Data;
import slivisu.data.Sample;
import slivisu.data.selection.Selection;
import slivisu.view.globe.GlobeData;
import slivisu.view.globe.GlobePoint;

public class SlivisuGlobeData implements GlobeData {

	private Data data;
	private SuperDataAnimationImpl animationData;
	private Selection<Sample> selectedSamples;
	private Selection<Sample> markedSamples;
	private List<Vector<List<Point2D.Double>>> wegnetz;
	
	RenderableLayer layerSelected;
	RenderableLayer layerMarked;
	RenderableLayer layerWegnetz;
	
	List<RenderableLayer> renderList;
	
	/**
	 * @param data Datenobjekt
	 */
	public SlivisuGlobeData(Data data, SuperDataAnimationImpl animationData){
		this.data = data;
		this.animationData = animationData;
		updateData();
	}
	
	@Override
	public boolean updateData() {
		layerSelected = new RenderableLayer();
		layerSelected.setName("Selected Samples");
		layerMarked = new RenderableLayer();
		layerMarked.setName("Marked Samples");
		layerWegnetz = new RenderableLayer();
		layerWegnetz.setName("Wegnetz");
		layerWegnetz.setMaxActiveAltitude(10000); // Erst anzeigen ab dieser Höhe, sonst unbenutzbar
		GlobePoint globePoint = null;
		
		this.selectedSamples = data.getSelectedSamples();
		this.markedSamples = data.getMarkedSamples();
		this.wegnetz = animationData.getWegnetz();
		
		//if (this.wegnetz != null) System.out.println("WEGNETZ" + this.wegnetz.size());
		//if (this.wegnetz != null) {
		if (this.data.getWegenetz().size() != 0) {
			for (int i = 0; i < this.wegnetz.size();i++) { // TODO: was soll mit den happen passieren? viel zu groß!!
				List<List<Point2D.Double>> einHappen = this.wegnetz.get(i);
				for (List<Point2D.Double> einzelneLinie : einHappen) {
					LinkedList<Position> positions = new LinkedList<Position>();
					for (Point2D.Double p : einzelneLinie) {
						positions.add(Position.fromDegrees(p.getY(), p.getX(), 1000));
					}
					
					Polyline path = new Polyline(positions);
				
					//path.setShowPositions(true);
					//System.out.println(pos1.getLongitude() +" " +  pos1.getLatitude());
					//path.setPathType( AVKey.RHUMB_LINE);
					//path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
					layerWegnetz.addRenderable(path);
				}
			}
		}
		
		
		for (Sample sample : this.selectedSamples.getAll()) {
			globePoint = new GlobePoint(Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4), sample);
			//System.out.println("p" + Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4));
			layerSelected.addRenderable(globePoint);
		}
		
		for (Sample sample : this.markedSamples.getAll()) {
			globePoint = new GlobePoint(Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4), sample);
			globePoint.setColor(Color.YELLOW);
			layerMarked.addRenderable(globePoint);
		}
		
		return true;
	}

	public void replaceMarkedWithSamples(Collection<Sample> samples) {
		layerMarked = new RenderableLayer();
		layerMarked.setName("Moving Samples");
		for (Sample sample : samples) {
			GlobePoint globePoint = new GlobePoint(Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4), sample);
			globePoint.setColor(Color.YELLOW);
			layerMarked.addRenderable(globePoint);
		}
	}
	
	@Override
	public List<RenderableLayer> getLayers() {
		renderList = new ArrayList<RenderableLayer>();
		renderList.add(layerSelected);
		renderList.add(layerMarked);
		
		//layerWegnetz.setEnabled(false);
		
		renderList.add(layerWegnetz);
		return renderList;
	}

	@Override
	public void markData(Collection<GlobePoint> o, boolean add2Selection) {
		if (!add2Selection) {
			data.getMarkedSamples().clear();
			for (GlobePoint globePoint : o) {
				data.getMarkedSamples().add(globePoint.getSample());
			}
		} else {
			for (GlobePoint globePoint : o) {
				if (!data.getMarkedSamples().contains(globePoint.getSample())) {
					data.getMarkedSamples().add(globePoint.getSample());
				}
			}
		}
	}

}
