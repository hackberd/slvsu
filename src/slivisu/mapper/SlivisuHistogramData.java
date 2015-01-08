package slivisu.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import slivisu.data.Data;
import slivisu.data.ObservationData;
import slivisu.data.Sample;
import slivisu.data.Zeitscheibe;
import slivisu.data.datatype.Bin;
import slivisu.data.selection.Selection;
import slivisu.view.histogram.CategoryBar;
import slivisu.view.histogram.HistogramData;

public class SlivisuHistogramData implements HistogramData {
	
	private Data data;
	private Selection<Sample> selectedSamples;
	private Selection<Sample> markedSamples;
	private ObservationData observationData;
	private Bin<Integer> minMax;
	
	// data
	//Set<Bin<Double>> ausgabeListe = new HashSet<Bin<Double>>();
	Map<Bin<Double>, Map<String, Integer>> binsAufKategorien = new HashMap<Bin<Double>, Map<String,Integer>>();
	Map<Bin<Double>, Map<String, Integer>> binsAufMarkedSamples = new HashMap<Bin<Double>, Map<String,Integer>>();
	Set<Zeitscheibe> zs;
	public SlivisuHistogramData(Data data){
		this.data = data;
		updateData();
	}
	
	@Override
	public void updateData() {
		this.selectedSamples = data.getSelectedSamples();
		this.markedSamples = data.getMarkedSamples();
		
		this.observationData = data.getObservationData();
		
		this.minMax = this.observationData.getTimeRange(this.selectedSamples.getAll());
		
		// Mache bins
		//this.ausgabeListe = new HashSet<Bin<Double>>();
		zs = new HashSet<Zeitscheibe>();
		this.binsAufKategorien = new HashMap<Bin<Double>, Map<String, Integer>>();
		
		int ebeneSelektiert = 3; // Seigt nur diese Ebene an
		ebeneSelektiert--; // Arrays fangen bei 0 an
		
		if (this.observationData != null) {
			
			if (this.observationData.getChronologie().size() == 0) return; // Booting
			
			List<Zeitscheibe> ebene = this.observationData.getChronologie().get(ebeneSelektiert);
				
				for (Zeitscheibe zeitscheibe : ebene) { // Alle Zeitscheiben in Ebene
					Map<String, Integer> kategorieInZeitscheibe = new HashMap<String, Integer>();
					Map<String, Integer> kategorieInZeitscheibeMitMarked = new HashMap<String, Integer>();
					int countSicher = 0;
					int countUnsicher = 0;
					
					int countInZeitscheibeMarkiertSicher = 0;
					int countInZeitscheibeMarkiterUnsicher = 0;
					for (Sample sample : this.selectedSamples.getAll()) { // Alle Samples
						
						
						Map<Zeitscheibe, Double> datenFuerSample = this.observationData.getDatierung(sample);
						double wk = datenFuerSample.get(zeitscheibe);
						if (wk != 0.0) { // Sample liegt in Zeitscheibe
							zs.add(zeitscheibe);
							
							if (wk == 1){ // Sicher
								countSicher++;
								if (this.markedSamples.contains(sample)) {
									countInZeitscheibeMarkiertSicher++; // Ist markiert
								} 
							} else { // Unsicher
								countUnsicher++;
								if (this.markedSamples.contains(sample)) { 
									countInZeitscheibeMarkiterUnsicher++; // Ist markiert
								}
							}
							
						}
					}
					if (countSicher != 0 || countUnsicher != 0) {
						kategorieInZeitscheibe.put("sicher",countSicher);
						kategorieInZeitscheibe.put("Unsicher", countUnsicher);
						kategorieInZeitscheibeMitMarked.put("sicher", countInZeitscheibeMarkiertSicher);
						kategorieInZeitscheibeMitMarked.put("unsicher", countInZeitscheibeMarkiterUnsicher);
						binsAufKategorien.put(zeitscheibe.getBin(), kategorieInZeitscheibe);
						binsAufMarkedSamples.put(zeitscheibe.getBin(), kategorieInZeitscheibeMitMarked);
						
						
					}
					
		
					//System.out.println("ebene " + zeitscheibe.getKurzform() + " sicher: " + countSicher + " unsicher " + countUnsicher);
					
					
				}
			
		}

	}
	
	
	
	@Override
	public List<Bin<Double>> getBins() {
		if (zs != null && zs.size() != 0) {
			LinkedList<Bin<Double>> bins = new LinkedList<Bin<Double>>();
			
			for (Zeitscheibe ebene : this.zs) { // Alle relevanten zeitscheiben
				bins.add(ebene.getBin());
			}
			
			// Sortieren
			 LinkedList<Bin<Double>> ausgabe = new LinkedList<Bin<Double>>();
			 Map<Double, Bin<Double>> map = new HashMap<Double, Bin<Double>>();
			 List<Double> doublesListe = new LinkedList<Double>();
			 
			 for (Bin<Double> wert : bins) {
				 doublesListe.add(wert.getMin());
				 map.put(wert.getMin(), wert);
			 }
			 Collections.sort(doublesListe);
			 
			 
			for (Double dbl : doublesListe) {
				ausgabe.add(map.get(dbl));
			}
			
			return ausgabe;
		} 
		return null;
		
		
	}

	@Override
	public Map<Bin<Double>, Map<String, Integer>> getData() {
		if (this.binsAufKategorien != null && this.binsAufKategorien.size() != 0) {
			return this.binsAufKategorien;
		}
		return null;
	}

	@Override
	public Map<Bin<Double>, Map<String, Integer>> getMarkedData() {
		if (this.binsAufMarkedSamples != null && this.binsAufMarkedSamples.size() != 0) {
			return this.binsAufMarkedSamples;
		}
		return null;
	}

	@Override
	public double getMin() {
		//System.out.println("min " + this.minMax.getMin());
		return this.minMax.getMin();
	}

	@Override
	public double getMax() {
		//System.out.println("max " + this.minMax.getMax());
		return this.minMax.getMax();
	}

	@Override
	public int getSegments() {
		if (getBins() != null) {
			int segments = getBins().size();
			//System.out.println("segmente " + segments);
			return segments;
		} 
		return 0;
	}

	@Override
	public void markData(Collection<CategoryBar> markedBars,
		boolean add2Selection) {
		// TODO Auto-generated method stub
		
	}

}
