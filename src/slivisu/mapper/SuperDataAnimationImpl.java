package slivisu.mapper;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import slivisu.data.Data;
import slivisu.data.Wegenetz;
import slivisu.data.Zeitscheibe;
import slivisu.view.globe.SliGlobe;

public class SuperDataAnimationImpl {
	Data data;
	List<Vector<List<Point2D.Double>>> currentDisplayWegnetz;
	//private Map<Zeitscheibe, Wegenetz> wegenetz;
	SliGlobe globe;
	
	public SuperDataAnimationImpl (Data data) {
		this.data = data;
		//this.wegenetz = data.getWegenetz();
		this.currentDisplayWegnetz = new LinkedList<Vector<List<Point2D.Double>>>();
	}
	
	public void setGlobe (SliGlobe globe) {
		this.globe = globe;
	}
	
	public int happenGroesse = 3000;
	
	public void updateData() {
		this.currentDisplayWegnetz = new LinkedList<Vector<List<Point2D.Double>>>();
		for (Zeitscheibe zs : this.data.getWegenetz().keySet()) {
			Wegenetz netz = this.data.getWegenetz().get(zs);
			
			Vector<List<Point2D.Double>> einHappen = null;
			for (int i = 0; i < netz.size(); i++) {
				if (einHappen == null) einHappen = new Vector<List<Point2D.Double>>();
				List<Point2D.Double> d = netz.get(i);
				einHappen.add(d);
				//System.out.println(" " + i);
				if (i != 0 && i % happenGroesse == 0) {
					this.currentDisplayWegnetz.add(einHappen);
					einHappen = null;
				}
			}
		}
		
		if (this.currentDisplayWegnetz.size() != 0) this.globe.updateView();
	}
	
	public void updateData(int i) {
		// Was ist i??  (id?)
		
//		this.currentDisplayWegnetz = new Vector<List<Point2D.Double>>();
//		for (Zeitscheibe zs : this.data.getWegenetz().keySet()) {
//			Wegenetz netz = this.data.getWegenetz().get(zs);
//			List<Point2D.Double> pDoubles = netz.get(i); // TODO: Ändern
//			this.currentDisplayWegnetz.add(pDoubles);
//		}
//		
//		if (this.currentDisplayWegnetz.size() != 0)this.globe.updateView();
		
		
	} 
	
	public List<Vector<List<Point2D.Double>>> getWegnetz() {
		return this.currentDisplayWegnetz;
	}
}
