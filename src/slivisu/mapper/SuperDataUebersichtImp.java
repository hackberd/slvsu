package slivisu.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

import slivisu.data.Data;
import slivisu.data.MyZeitscheibe;
import slivisu.data.ObservationData;
import slivisu.data.Sample;
import slivisu.data.Zeitscheibe;
import slivisu.data.datatype.Bin;
import slivisu.view.myviews.SuperDataUebersicht;

public class SuperDataUebersichtImp implements SuperDataUebersicht {
	// Internal
	Data data;
	ObservationData observationData;
	private List<Set<MyZeitscheibe>> allEbenenAufZeitscheibenListeInternal;
	private List<MyZeitscheibe> allZeitscheibenInternal;
	int lastAllCount = 0;
	
	List<Boolean> filter;
	
	// Ausgaben
	List<List<MyZeitscheibe>> allData;	
	List<List<MyZeitscheibe>> lastFocus;
	
	
	public SuperDataUebersichtImp(Data data) {
		this.data = data;
		this.observationData = data.getObservationData();
		getFilter();
		
		this.updateDataToSelectedData();
	}
	
	public void updateDataToSelectedData() {
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
					if (wert != 0  && this.getFilter().get(zeitscheibe.getEbene() - 1)) {// ist drin und soll angezeigt werden
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
		
			// darüberliegende zeitebenen berechnen, falls diese nich existieren
			if(obersteEbene != 1) {
//				System.out.println("NOT TESTED");
//				// reset zeitscheiben
//				for (int i = 1; i < obersteEbene; i++) {
//						for (MyZeitscheibe zeitscheibeInDerLeerenEbene : helper.allEbenenAufZeitscheibenListeInternal.get(i - 1)) {
//							zeitscheibeInDerLeerenEbene.resetAllCounters();
//						}
//				}
//				// daten füllen
//				for (int i = 1; i < obersteEbene; i++) {
//					List<MyZeitscheibe> obersteZeitscheiben = allDataTemp.get(obersteEbene - 1);
//					for (MyZeitscheibe zs : obersteZeitscheiben) {
//						for (MyZeitscheibe zeitscheibeInDerLeerenEbene : helper.allEbenenAufZeitscheibenListeInternal.get(i - 1)) {
//							if (zeitscheibeInDerLeerenEbene.getAnfang() >= zs.getAnfang() && zeitscheibeInDerLeerenEbene.getEbene() <= zs.getEnde()) { // Liegt im Zeitintervall
//								// einsetzen
//								if (!allDataTemp.get(zeitscheibeInDerLeerenEbene.getEbene() -1).contains(zeitscheibeInDerLeerenEbene)) {
//									allDataTemp.get(i).add(zeitscheibeInDerLeerenEbene);
//								}
//								zeitscheibeInDerLeerenEbene.setAnzahlSichereSiedlungen(zeitscheibeInDerLeerenEbene.getAnzahlSichereSiedlungen() + zs.getAnzahlSichereSiedlungen());
//								zeitscheibeInDerLeerenEbene.setAnzahlUnsichereSiedlungen(zeitscheibeInDerLeerenEbene.getAnzahlUnsichereSiedlungen() + zs.getAnzahlUnsichereSiedlungen());
//							}	
//						}
//					}
//				}
				
			}
		
			// Sortieren
			for (int i = 0; i < 5;i++) {
				allDataTemp.set(i, new LinkedList<MyZeitscheibe>(SuperDataZeitscheibenHelper.sortiereZeitscheiben(allDataTemp.get(i))));
			}
		
			this.allData = allDataTemp;
			//this.lastFocus = this.allData;
		
			 //DEBUG
//					int ebene = 0;
//					for (List<MyZeitscheibe> eben: allDataTemp) {
//						ebene++;
//						System.out.println("\nEbene: " + ebene + " \n");
//						for (MyZeitscheibe zsMyZeitscheibe : eben) {
//							System.out.print(" " + zsMyZeitscheibe.getAnfang() + " "  + "S" + zsMyZeitscheibe.getAlleSamplesInZeitscheibe().size());
//						}
//						
//					}
//		
		
		}
	}

	@Override
	public List<List<MyZeitscheibe>> getAllData() {
		return this.allData;
	}

	@Override
	public void selectZeitscheiben(List<MyZeitscheibe> selectedZeitscheiben) {
		if (selectedZeitscheiben.size() > 0) {
			Collection<Sample> selectedSamples = new LinkedList<Sample>();
			for (MyZeitscheibe zeitscheibe : selectedZeitscheiben) {
				selectedSamples.addAll(zeitscheibe.getAlleSamplesInZeitscheibe());
				//System.out.println("anz zs " + selectedZeitscheiben.size() +" s in zs " + zeitscheibe.getAlleSamplesInZeitscheibe().size() + " ausgabe s " +  selectedSamples.size());
			}
			if (selectedSamples.size() > 0) {  this.data.getSelectedSamples().set(selectedSamples);}
			
			
			
		}
		
		//updateDataToSelectedData();
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
	
	@Override
	public void resetSelectionToLastFocus() {
		this.data.setCurrentZeitscheibe(null);
		this.data.getSelectedSamples().set(this.data.getAllSamples().getAll());
	}

	@Override
	public List<List<MyZeitscheibe>> getDataForFocusWhileFocussing() {
		this.lastFocus = this.allData;
		// Update
		this.updateDataToSelectedData();
		
		return null;
	}

	@Override
	public List<Boolean> getFilter() {
		if (this.filter == null) {
			this.filter = new LinkedList<Boolean>();
			this.filter.add(true); 
			this.filter.add(true); 
			this.filter.add(true); 
			this.filter.add(true); 
			this.filter.add(true); 
		}
		return this.filter;
	}

	@Override
	public void setFilter(List<Boolean> settings) {
		this.filter = settings;
	}

	@Override
	public Data getData() {
		// TODO Auto-generated method stub
		return this.data;
	}
	

}
