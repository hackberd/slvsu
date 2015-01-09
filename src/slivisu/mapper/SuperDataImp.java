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
import slivisu.view.myviews.SuperData;

public class SuperDataImp implements SuperData {
	// Internal
	Data data;
	ObservationData observationData;
	
	List<Set<MyZeitscheibe>> allEbenenAufZeitscheibenListeInternal;
	List<MyZeitscheibe> allZeitscheibenInternal;
	
	int lastAllCount = 0;
	
	// Ausgaben
	List<List<MyZeitscheibe>> allData;	
	List<List<MyZeitscheibe>> lastFocus;
	
	
	public SuperDataImp(Data data) {
		this.data = data;
		this.observationData = data.getObservationData();
		
		this.updateDataToSelectedData();
	}
	
	// lazy init alle zeitscheiben
	private List<MyZeitscheibe> getAlleZeitscheiben() {
		// Zeitscheiven
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
					this.allEbenenAufZeitscheibenListeInternal.set(i, new HashSet<MyZeitscheibe>(this.sortiereZeitscheiben(this.allEbenenAufZeitscheibenListeInternal.get(i))));
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

	private Collection<MyZeitscheibe> sortiereZeitscheiben (Collection<MyZeitscheibe> zeitscheiben) {
		LinkedList<Bin<Double>> bins = new LinkedList<Bin<Double>>();
		
		for (Zeitscheibe ebene : zeitscheiben) { // Alle relevanten zeitscheiben
			bins.add(ebene.getBin());
		}
		
		// Sortieren
		 Set<MyZeitscheibe> ausgabe = new HashSet<MyZeitscheibe>();
		 Map<Double, MyZeitscheibe> map = new HashMap<Double, MyZeitscheibe>();
		 List<Double> doublesListe = new LinkedList<Double>();
		 
		 for (MyZeitscheibe zs : zeitscheiben) {
			 double wert = zs.getBin().getMin();
			 doublesListe.add(wert);
			 map.put(wert, zs);
		 }
		 Collections.sort(doublesListe);
		 
		 
		for (Double dbl : doublesListe) {
			ausgabe.add(map.get(dbl));
		}
		return ausgabe;
	}
	
	
	
	public void updateDataToSelectedData() {
		// Daten aufbereiten (myzeitscheibe) + Samples den Zeitscheiben zuordnen
		List<MyZeitscheibe> alleZeitscheiben = this.getAlleZeitscheiben();
		
		if (alleZeitscheiben != null) {
			// Alle Counter zurücksetzen
			for (MyZeitscheibe zs : this.allZeitscheibenInternal) {
				zs.resetAllCounters();
			}
		
			// Sichere/Unsichere Siedlungen berechnen
			List<List<MyZeitscheibe>> allDataTemp = new LinkedList<List<MyZeitscheibe>>(); 
			
			int obersteEbene = 10000;
			int untersteEbene = 0;
			
			// make ebenen
			for (int i = 0; i < 5;i++) {
				allDataTemp.add(new LinkedList<MyZeitscheibe>());
			}
		
			// Sample data auf die zeitscheiben übertragen
			for(Sample sample : data.getSelectedSamples().getAll()) {
				Map<Zeitscheibe, Double> map = observationData.getDatierung(sample);
				for (MyZeitscheibe zeitscheibe : alleZeitscheiben) {
					Double wert = map.get(zeitscheibe.getOriginalZeitscheibe());
					if (wert != 0) {// ist drin
						// einsetzen
						if (!allDataTemp.get(zeitscheibe.getEbene() -1).contains(zeitscheibe)) {
							allDataTemp.get(zeitscheibe.getEbene() -1).add(zeitscheibe);
						}
						
						// ebenen finden
						if (zeitscheibe.getEbene() < obersteEbene) obersteEbene = zeitscheibe.getEbene();
						if (zeitscheibe.getEbene() > untersteEbene) untersteEbene = zeitscheibe.getEbene();
						
						if (wert == 1) {
							zeitscheibe.increaseAnzahlSichereSiedlungen();
						} else if (wert < 1 && wert > 0) {
							zeitscheibe.increaseAnzahlUnsichereSiedlungen();
						}
					}
					
				}
			}
		
			// darüberliegende zeitebenen berechnen, falls diese nich existieren
			if(obersteEbene != 1) {
				System.out.println("NOT TESTED");
				// reset zeitscheiben
				for (int i = 1; i < obersteEbene; i++) {
						for (MyZeitscheibe zeitscheibeInDerLeerenEbene : allEbenenAufZeitscheibenListeInternal.get(i - 1)) {
							zeitscheibeInDerLeerenEbene.resetAllCounters();
						}
				}
				// daten füllen
				for (int i = 1; i < obersteEbene; i++) {
					List<MyZeitscheibe> obersteZeitscheiben = allDataTemp.get(obersteEbene);
					for (MyZeitscheibe zs : obersteZeitscheiben) {
						for (MyZeitscheibe zeitscheibeInDerLeerenEbene : allEbenenAufZeitscheibenListeInternal.get(i - 1)) {
							if (zeitscheibeInDerLeerenEbene.getAnfang() >= zs.getAnfang() && zeitscheibeInDerLeerenEbene.getEbene() <= zs.getEnde()) { // Liegt im Zeitintervall
								// einsetzen
								if (!allDataTemp.get(zeitscheibeInDerLeerenEbene.getEbene() -1).contains(zeitscheibeInDerLeerenEbene)) {
									allDataTemp.get(i).add(zeitscheibeInDerLeerenEbene);
								}
								zeitscheibeInDerLeerenEbene.setAnzahlSichereSiedlungen(zeitscheibeInDerLeerenEbene.getAnzahlSichereSiedlungen() + zs.getAnzahlSichereSiedlungen());
								zeitscheibeInDerLeerenEbene.setAnzahlUnsichereSiedlungen(zeitscheibeInDerLeerenEbene.getAnzahlUnsichereSiedlungen() + zs.getAnzahlUnsichereSiedlungen());
							}	
						}
					}
				}
				
			}
		
			// Sortieren
			for (int i = 0; i < 5;i++) {
				allDataTemp.set(i, new LinkedList<MyZeitscheibe>(this.sortiereZeitscheiben(allDataTemp.get(i))));
			}
		
			this.allData = allDataTemp;
			//this.lastFocus = this.allData;
		
			// DEBUG
			//		int ebene = 0;
			//		for (List<MyZeitscheibe> eben: allDataTemp) {
			//			ebene++;
			//			System.out.println("\nEbene: " + ebene + " \n");
			//			for (MyZeitscheibe zsMyZeitscheibe : eben) {
			//				System.out.print(" " + zsMyZeitscheibe.getAnfang() + " ");
			//			}
			//			
			//		}
		
		
		}
	}

	@Override
	public List<List<MyZeitscheibe>> getAllData() {
		return this.allData;
	}

	@Override
	public void selectZeitscheiben(List<MyZeitscheibe> selectedZeitscheiben) {
		Collection<Sample> selectedSamples = new LinkedList<Sample>();
		for (MyZeitscheibe zeitscheibe : selectedZeitscheiben) {
			selectedSamples.addAll(zeitscheibe.getAlleSamplesInZeitscheibe());
		}
		this.data.getSelectedSamples().set(selectedSamples);
		
		//updateDataToSelectedData();
	}

	@Override
	public void resetSelectionToLastFocus() {
		if (this.lastFocus != null) {
			this.allData = this.lastFocus;
		}
	}

	@Override
	public List<List<MyZeitscheibe>> getDataForFocusWhileFocussing() {
		this.lastFocus = this.allData;
		// Update
		this.updateDataToSelectedData();
		
		return null;
	}
	

}
