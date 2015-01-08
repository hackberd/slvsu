/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.globe;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;

import java.awt.Color;

import slivisu.data.Sample;

/**
 * 
 * @author Sven
 */
public class GlobePoint extends PointPlacemark {

	private Sample id;

	public GlobePoint(Position position, Sample id) {
		super(position);
		this.id = id;
		this.setValue(AVKey.DISPLAY_NAME, "SLI " + id);
		this.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
		PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
		attrs.setLabelColor("ffffffff");
		attrs.setUsePointAsDefaultImage(true);
		attrs.setScale(6d);
		this.setAttributes(attrs);

		PointPlacemarkAttributes highAttrs = new PointPlacemarkAttributes();
		Material m = new Material(Color.ORANGE);
		highAttrs.setLineMaterial(m);
		highAttrs.setLineWidth(10d);
		highAttrs.setUsePointAsDefaultImage(true);
		highAttrs.setScale(8d);
		//		highAttrs.setImageColor(catColor);
		this.setHighlightAttributes(highAttrs);
	}

	public Sample getSample() {
		return id;
	}
	
	public void setColor(Color color){
		this.getAttributes().setImageColor(color);
		this.getAttributes().setLineMaterial(new Material(color));
	}
}