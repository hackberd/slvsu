package slivisu.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import slivisu.data.datatype.Bin;

public class ObservationData {

	// auf jeder Chronologie-Ebene gibt es eine sortierte Liste von Zeitscheiben
	List<List<Zeitscheibe>> chronologie;

	// f�r jeden Fund: jeder Zeitscheibe ist ein Wert zwischen 0 und 1 zugeordnet
	Map<Sample, Map<Zeitscheibe, Double>> fundDatierung;

	/**
	 */
	public ObservationData(){
		chronologie = new Vector<List<Zeitscheibe>>();
		fundDatierung = new HashMap<Sample, Map<Zeitscheibe, Double>>();
	}
	
	//############ getter ###############
	
	public Collection<Sample> getSamples(){
		return fundDatierung.keySet();
	}

	public List<List<Zeitscheibe>> getChronologie(){
		return chronologie;
	}

	public List<Zeitscheibe> getZeitscheibenAufEbene(int ebene){
		if (chronologie != null){
			return chronologie.get(ebene);
		}
		return null;
	}

	public Map<Zeitscheibe, Double> getDatierung(Sample fund){
		if (fundDatierung != null){
			return fundDatierung.get(fund);
		}
		return null;
	}

	public Map<Sample, Map<Zeitscheibe, Double>> getDatierungen() {
		return fundDatierung;
	}
	
	//############ getter - ende ###############

	public void addFundDatierungen(Map<Sample, Map<Zeitscheibe, Double>> fundDatierung) {
		for (Sample s : fundDatierung.keySet()){
			this.fundDatierung.put(s, fundDatierung.get(s));
		}
	}

	public void setChronologie(List<List<Zeitscheibe>> chronologie) {
		this.chronologie = chronologie;
	}

	/** 
	 * jene Funde aus einer Menge von Funden herausfiltern, die im angegebenen Zeitbereich liegen 
	 * 
	 * @param selectedSamples - Collection der Siedlungsfunde, die gefiltert werden sollen
	 * @param timeMin - untere Grenze des Zeitintervalls 
	 * @param timeMax - obere Grenze des Zeitintervalls
	 * @return	Collection der Funde innerhalb des Zeitbereichs
	 */
	public Collection<Sample> filterByTimeRange(Collection<Sample> selectedSamples, double timeMin, double timeMax) {
		Collection<Sample> gefilterteListe = new HashSet<Sample>();
		for (Sample sample : selectedSamples) {
			//System.out.println("\n\n"+sample.getGemeinde() + ":");
			Map<Zeitscheibe, Double> alleZeitscheibenVonSample = this.zeitscheibenFuerSample(sample);				
			for (Zeitscheibe zeitscheibe : alleZeitscheibenVonSample.keySet()) {
				
				if (alleZeitscheibenVonSample.get(zeitscheibe) != 0.0) { // Existiert in Zeitscheibe
					
					if (zeitscheibe.anfang >= timeMin && zeitscheibe.ende <= timeMax) { // Liegt im Zeitintervall
						// !!! Falls Sample teilweise in Zeitscheibe liegen kann:
						// if ((zeitscheibe.anfang >= timeMin && zeitscheibe.anfang <= timeMax) || 
						// (zeitscheibe.ende >= timeMin && zeitscheibe.ende <= timeMax) ) { gefilterteListe.add(sample); break; };
					
						gefilterteListe.add(sample);
						break;
					}
				}
			}
				
		}
		System.out.println("Zeitintervall von " + timeMin + " bis " + timeMax + " enthält " + gefilterteListe.size() + " Samples.");
		return gefilterteListe;
	}

	/**
	 * das maximale umschliessende Zeitintervall f�r eine Menge von Funden ermitteln
	 * 
	 * @param selection - Collection der Siedlungsfunde
	 * @return Zeitintervall mit unterer und oberer Grenze
	 */
	public Bin<Integer> getTimeRange(Collection<Sample> selection){
		int minimum = 0;
		int maximum = 1;
		
		for (Sample sample : selection) {
			//System.out.println("\n\nSample: " + sample.getGemeinde());
			// Alle zeitscheiben vom sample
			Map<Zeitscheibe, Double> alleZeitscheibenVonSample = this.zeitscheibenFuerSample(sample);
			for (Zeitscheibe zeitscheibe : alleZeitscheibenVonSample.keySet()) {
				if (alleZeitscheibenVonSample.get(zeitscheibe) != 0.0) { // existiert in zeitscheibe
					//System.out.println(zeitscheibe.name + "  Zeit: " + zeitscheibe.anfang);
					if (zeitscheibe.anfang < minimum) minimum = zeitscheibe.anfang;
					if (zeitscheibe.ende > maximum) maximum = zeitscheibe.ende;
				}
			}
		}
		System.out.println("getTimeRange(Collection<Sample> selection) Zeitinformationen, Min:" + minimum + " Max: " + maximum);
		return new Bin<Integer>(minimum, maximum);
	}
	
	/**
	 * das umschliessende Zeitintervall f�r eine Menge von Funden ermitteln, abh�ngig von der Chronologieebene
	 * 
	 * @param selection - Collection von Siedlungsfunden
	 * @param ebene - Ebene in Zeitchronologie
	 * @return Zeitintervall mit unterer und oberer Grenze
	 */
	public Bin<Integer> getTimeRange(Collection<Sample> selection, int ebene){
		int minimum = 0;
		int maximum = 1;
		
		for (Sample sample : selection) {
			//System.out.println("\n\nSample: " + sample.getGemeinde());
			Map<Zeitscheibe, Double> alleZeitscheibenVonSample = this.zeitscheibenFuerSample(sample);
			List<Zeitscheibe> zeitscheibenFuerEbene = this.zeitscheibenFuerEbene(ebene);
			
			for (Zeitscheibe zeitscheibe : zeitscheibenFuerEbene) {
				if (alleZeitscheibenVonSample.get(zeitscheibe) != 0.0) {
					// existiert in zeitscheibe
					//System.out.println(zeitscheibe.name + "  Zeit: " + zeitscheibe.anfang);
					if (zeitscheibe.anfang < minimum) minimum = zeitscheibe.anfang;
					if (zeitscheibe.ende > maximum) maximum = zeitscheibe.ende;
				}
			}
		}
		System.out.println("getTimeRange(Collection<Sample> selection, int ebene) Zeitinformationen, Min:" + minimum + " Max: " + maximum);
		return new Bin<Integer>(minimum, maximum);
	}
	
	// Helper
	public List<Zeitscheibe> zeitscheibenFuerEbene(int ebene) {
		return this.chronologie.get(ebene);
	}
	
	public Map<Zeitscheibe, Double> zeitscheibenFuerSample(Sample sample) {
		return this.fundDatierung.get(sample);
	}
}
