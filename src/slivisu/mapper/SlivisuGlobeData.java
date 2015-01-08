package slivisu.mapper;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import slivisu.data.Data;
import slivisu.data.Sample;
import slivisu.data.selection.Selection;
import slivisu.view.globe.GlobeData;
import slivisu.view.globe.GlobePoint;

public class SlivisuGlobeData implements GlobeData {

	private Data data;
	private Selection<Sample> selectedSamples;
	private Selection<Sample> markedSamples;
	RenderableLayer layerSelected;
	RenderableLayer layerMarked;
	
	/**
	 * @param data Datenobjekt
	 */
	public SlivisuGlobeData(Data data){
		this.data = data;
		updateData();
	}
	
	@Override
	public boolean updateData() {
		layerSelected = new RenderableLayer();
		layerMarked = new RenderableLayer();
		GlobePoint globePoint = null;
		
		this.selectedSamples = data.getSelectedSamples();
		this.markedSamples = data.getMarkedSamples();
		
		for (Sample sample : this.selectedSamples.getAll()) {
			globePoint = new GlobePoint(Position.fromDegrees(sample.getLon(), sample.getLat(), 1e4), sample);
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
