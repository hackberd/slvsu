package slivisu.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import slivisu.data.Data;
import slivisu.data.MyZeitscheibe;
import slivisu.data.ObservationData;
import slivisu.data.Sample;
import slivisu.data.Zeitscheibe;
import slivisu.view.myviews.SuperDataDetail;

public class SuperDataDetailImp implements SuperDataDetail {
	// Internal
	Data data;
	ObservationData observationData;

	private List<Set<MyZeitscheibe>> allEbenenAufZeitscheibenListeInternal;
	private List<MyZeitscheibe> allZeitscheibenInternal;
	int lastAllCount = 0;
	
	
	// Return stuff
	//Map<MyZeitscheibe, Boolean> zsAufSicherheitMap;
	Map<Sample, LinkedList<HashMap<MyZeitscheibe, Boolean>>> sampleAufListeVonEbenenMitZeitscheibenAufSicher;
	List<Boolean> filter;
	
	public SuperDataDetailImp(Data data) {
		this.data = data;
		this.observationData = data.getObservationData();

	
		this.updateDataToSelectedSamples();
	}
	
	public void updateDataToSelectedSamples() {
		this.sampleAufListeVonEbenenMitZeitscheibenAufSicher = new HashMap<Sample, LinkedList<HashMap<MyZeitscheibe, Boolean>>>();
		
		// Sample data auf die zeitscheiben übertragen
		for(Sample sample : data.getSelectedSamples().getAll()) {
					
			// make ebenen, Ebene -> Map(ZS,Sicher)
			LinkedList<HashMap<MyZeitscheibe, Boolean>> ebenenAufZeitscheibenInEbene = new LinkedList<HashMap<MyZeitscheibe, Boolean>>();
			for (int i = 0; i < 5;i++) {
					ebenenAufZeitscheibenInEbene.add(new HashMap<MyZeitscheibe, Boolean>());
			}
					
			Map<Zeitscheibe, Double> map = observationData.getDatierung(sample);
			for (MyZeitscheibe zeitscheibe : this.getAlleZeitscheiben()) {
					Double wert = map.get(zeitscheibe.getOriginalZeitscheibe());
					if (wert != 0) {// ist drin
						// einsetzen
						if (wert == 1) {
							ebenenAufZeitscheibenInEbene.get(zeitscheibe.getEbene() -1).put(zeitscheibe, true);
						} else if (wert < 1 && wert > 0) {
							ebenenAufZeitscheibenInEbene.get(zeitscheibe.getEbene() -1).put(zeitscheibe, false);
						}
					} // wert != 0
				} // for zs
			this.sampleAufListeVonEbenenMitZeitscheibenAufSicher.put(sample, ebenenAufZeitscheibenInEbene);
		} //for sample
		
//		for (Sample sample: this.sampleAufListeVonEbenenMitZeitscheibenAufSicher.keySet()) {
//			LinkedList<HashMap<MyZeitscheibe, Boolean>> list = this.sampleAufListeVonEbenenMitZeitscheibenAufSicher.get(sample);
//			System.out.println("Sample: " + sample);
//			for (int i = 0; i < list.size(); i++) {
//				System.out.println("Ebene: " + i  + " ");
//				for (MyZeitscheibe zs: list.get(i).keySet()) {
//					System.out.print(zs.getAnfang() + " " + list.get(i).get(zs));
//					System.out.println();
//				}
//			}
//		}
	}
	
	// lazy init alle zeitscheiben
	public synchronized List<MyZeitscheibe> getAlleZeitscheiben() {
				// Zeitscheiben
				if (this.allZeitscheibenInternal == null) {
					if (observationData.getChronologie().size() != 0) {
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

	@Override
	public List<Sample> getAllSelectedSamples() {
		return new LinkedList<Sample>(this.data.getSelectedSamples().getAll());
	}

	@Override
	public Map<MyZeitscheibe, Boolean> zsForSampleWithSicher(Sample sample, int ebene) {
		return this.sampleAufListeVonEbenenMitZeitscheibenAufSicher.get(sample).get(ebene - 1);
	}

	@Override
	public List<Boolean> getFilter() {
		if (this.filter == null) {
			this.filter = new LinkedList<Boolean>();
			for (int i = 0; i < 5; i++) { this.filter.add(true); }
		}
		return this.filter;
	}

	@Override
	public void setFilter(List<Boolean> settings) {
		this.filter = settings;
	}

}
