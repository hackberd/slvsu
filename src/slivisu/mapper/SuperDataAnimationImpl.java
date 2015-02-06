package slivisu.mapper;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import slivisu.data.Data;
import slivisu.data.MyZeitscheibe;
import slivisu.data.ObservationData;
import slivisu.data.Sample;
import slivisu.data.Wegenetz;
import slivisu.data.Zeitscheibe;
import slivisu.view.globe.SliGlobe;

public class SuperDataAnimationImpl {
	public Data data;
	public ObservationData observationData;
	List<Vector<List<Point2D.Double>>> currentDisplayWegnetz;
	//private Map<Zeitscheibe, Wegenetz> wegenetz;
	public SliGlobe globe;
	public SlivisuGlobeData globeData;
	
	List<List<MyZeitscheibe>> allData;	
	
	int lastAllCount = 0;
	
	private List<Set<MyZeitscheibe>> allEbenenAufZeitscheibenListeInternal;
	private List<MyZeitscheibe> allZeitscheibenInternal;
	
	public SuperDataAnimationImpl (Data data) {
		this.data = data;
		this.observationData = data.getObservationData();
		//this.wegenetz = data.getWegenetz();
		this.currentDisplayWegnetz = new LinkedList<Vector<List<Point2D.Double>>>();
	}
	
	public void setGlobe (SliGlobe globe) {
		this.globe = globe;
	}
	
	public int happenGroesse = 3000;
	
	
	public void updateWegnetzData() {
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
	
	public List<MyZeitscheibe> getDataForEbene(int i) {
		if (this.allData == null) {
			this.updateData();
		}
		
		if (this.allData == null) {
			return null;
		}
		
		return this.allData.get(i -1);
	} 
	
	public void updateData() {
		// Daten aufbereiten (myzeitscheibe) + Samples den Zeitscheiben zuordnen
		List<MyZeitscheibe> alleZeitscheiben = this.getAlleZeitscheiben();
		
		if (alleZeitscheiben != null) {
			// Alle Counter zurücksetzen
			for (MyZeitscheibe zs : alleZeitscheiben) {
				zs.resetAllCounters();
			}
		
			// Sichere/Unsichere Siedlungen berechnen
			List<List<MyZeitscheibe>> allDataTemp = new LinkedList<List<MyZeitscheibe>>(); 
			
			int obersteEbene = 5;
			int untersteEbene = 1;
			
			// make ebenen
			for (int i = 0; i < 5;i++) {
				allDataTemp.add(new LinkedList<MyZeitscheibe>());
			}
			
			// Sample data auf die zeitscheiben übertragen
			for(Sample sample : data.getSelectedSamples().getAll()) {
				Map<Zeitscheibe, Double> map = observationData.getDatierung(sample);
				for (MyZeitscheibe zeitscheibe : alleZeitscheiben) {
					Double wert = map.get(zeitscheibe.getOriginalZeitscheibe());
					//System.out.println("draw ebene " + zeitscheibe.getEbene() + " " + this.getFilter().get(zeitscheibe.getEbene() - 1));
					if (wert != 0 ) {// ist drin und soll angezeigt werden
						// einsetzen
						if (!allDataTemp.get(zeitscheibe.getEbene() -1).contains(zeitscheibe)) {
							allDataTemp.get(zeitscheibe.getEbene() -1).add(zeitscheibe);
						}
						//System.out.println("draw ebene " + zeitscheibe.getEbene() + " " + this.getFilter().get(zeitscheibe.getEbene() - 1));
						// ebenen finden
						if (zeitscheibe.getEbene() < obersteEbene) obersteEbene = zeitscheibe.getEbene();
						if (zeitscheibe.getEbene() > untersteEbene) untersteEbene = zeitscheibe.getEbene();
						
						if (wert == 1) {
							zeitscheibe.addSichereSiedlung(sample);;
						} else if (wert < 1 && wert > 0) {
							zeitscheibe.addUnsichereSiedlung(sample);
						}
					}
					
				}
			}
		
			// Sortieren
			
			List<List<MyZeitscheibe>> allDataTempNew = new LinkedList<List<MyZeitscheibe>>(); 
			for (int i = 0; i < 5;i++) {
				allDataTempNew.add(new LinkedList<MyZeitscheibe>(SuperDataZeitscheibenHelper.sortiereZeitscheiben(allDataTemp.get(i))));

			}
			
		
			this.allData = allDataTempNew;
		
		}
	}

	public List<List<MyZeitscheibe>> getAllData() {
		return this.allData;
	}
	
	public List<Vector<List<Point2D.Double>>> getWegnetz() {
		return this.currentDisplayWegnetz;
	}
	
	// lazy init alle zeitscheiben
				public synchronized List<MyZeitscheibe> getAlleZeitscheiben() {
						// Zeitscheiben
						if (observationData.getChronologie().size() != 0 && this.allZeitscheibenInternal == null) {
								this.allEbenenAufZeitscheibenListeInternal = new LinkedList<Set<MyZeitscheibe>>();
								for (int i = 0; i < 5;i++) {
									this.allEbenenAufZeitscheibenListeInternal.add(new HashSet<MyZeitscheibe>());
								}
								
								// Alle zeitscheiben auf MyZeitscheibe ändern
								LinkedList<MyZeitscheibe> alleZeitscheiben = new LinkedList<MyZeitscheibe>();
								for (List<Zeitscheibe> ebene : observationData.getChronologie()) {
										LinkedList<MyZeitscheibe> allZeitscheibenInEbene = new LinkedList<MyZeitscheibe>();
										for(Zeitscheibe zeitscheibe : ebene) {
												MyZeitscheibe eineZeitscheibe = new MyZeitscheibe(zeitscheibe);
												allZeitscheibenInEbene.add(eineZeitscheibe);
												alleZeitscheiben.add(eineZeitscheibe);
												this.allEbenenAufZeitscheibenListeInternal.get(eineZeitscheibe.getEbene() -1).add(eineZeitscheibe);
										}
								}
								
								for (int i = 0; i < 5;i++) {
									this.allEbenenAufZeitscheibenListeInternal.set(i, new HashSet<MyZeitscheibe>(SuperDataZeitscheibenHelper.sortiereZeitscheiben(this.allEbenenAufZeitscheibenListeInternal.get(i))));
								}
								this.allZeitscheibenInternal = alleZeitscheiben;
							
						}
						
						if (this.allZeitscheibenInternal != null) {
							// alle samples den zeitscheiben zuordnen
							if (this.lastAllCount != this.data.getAllSamples().getAll().size()) { // nur falls sich was geändert hat
								this.lastAllCount = this.data.getAllSamples().getAll().size(); 
								for(Sample sample : data.getAllSamples().getAll()) {
									Map<Zeitscheibe, Double> map = observationData.getDatierung(sample);
									for (MyZeitscheibe zeitscheibe : this.allZeitscheibenInternal) {
										Double wert = map.get(zeitscheibe.getOriginalZeitscheibe());
										if (wert != 0) {
											 zeitscheibe.addSample(sample); 
										}
									}
								}
							}					
						}
						
						return this.allZeitscheibenInternal;
					}

				public void setGlobeData(SlivisuGlobeData globeData) {
					this.globeData = globeData;
				}
}
