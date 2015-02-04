package slivisu.mapper;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Path;

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
	private Vector<List<Point2D.Double>> wegnetz;
	
	RenderableLayer layerSelected;
	RenderableLayer layerMarked;
	RenderableLayer layerWegnetz;
	
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
		GlobePoint globePoint = null;
		
		this.selectedSamples = data.getSelectedSamples();
		this.markedSamples = data.getMarkedSamples();
		this.wegnetz = animationData.getWegnetz();
		
		//if (this.wegnetz != null) System.out.println("WEGNETZ" + this.wegnetz.size());
		//if (this.wegnetz != null) {
			for (List<Point2D.Double> einzelneLinie : this.wegnetz) {
				LinkedList<Position> positions = new LinkedList<Position>();
				for (Point2D.Double p : einzelneLinie) {
					positions.add(Position.fromDegrees(p.getX(), p.getY(), 1e4));
				}
				
				Path path = new Path(positions);
				
				//System.out.println(pos1.getLongitude() +" " +  pos1.getLatitude());
				//path.setPathType( AVKey.RHUMB_LINE);
				//path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
				layerWegnetz.addRenderable(path);
			}
		//}
		
		
		for (Sample sample : this.selectedSamples.getAll()) {
			globePoint = new GlobePoint(Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4), sample);
			//System.out.println("p" + Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4));
			layerSelected.addRenderable(globePoint);
		}
		
		for (Sample sample : this.markedSamples.getAll()) {
			globePoint = new GlobePoint(Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4), sample);
			layerMarked.addRenderable(globePoint);
		}
		
		return true;
	}

	@Override
	public List<RenderableLayer> getLayers() {
		List<RenderableLayer> renderList = new ArrayList<RenderableLayer>();
		renderList.add(layerSelected);
		renderList.add(layerMarked);
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
