package slivisu.mapper;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import slivisu.data.Data;
import slivisu.data.Wegenetz;
import slivisu.data.Zeitscheibe;
import slivisu.view.globe.SliGlobe;

public class SuperDataAnimationImpl {
	Data data;
	Vector<List<Point2D.Double>> currentDisplayWegnetz;
	private Map<Zeitscheibe, Wegenetz> wegenetz;
	SliGlobe globe;
	
	public SuperDataAnimationImpl (Data data) {
		this.data = data;
		this.wegenetz = data.getWegenetz();
		this.currentDisplayWegnetz = new Vector<List<Point2D.Double>>();
	}
	
	public void setGlobe (SliGlobe globe) {
		this.globe = globe;
	}
	
	public void updateData(int i) {
		// Was ist i??  (id?)
		
		this.currentDisplayWegnetz = new Vector<List<Point2D.Double>>();
		for (Zeitscheibe zs : this.wegenetz.keySet()) {
			Wegenetz netz = this.wegenetz.get(zs);
			List<Point2D.Double> pDoubles = netz.get(i); // TODO: Ändern
			this.currentDisplayWegnetz.add(pDoubles);
		}
		
		if (this.currentDisplayWegnetz.size() != 0)this.globe.updateView();
		
		
	} 
	
	public Vector<List<Point2D.Double>> getWegnetz() {
		return this.currentDisplayWegnetz;
	}
}
