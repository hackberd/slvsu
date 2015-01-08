package slivisu.view.histogram;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import slivisu.data.datatype.Bin;

public interface HistogramData {

	/** 
	 * Methode zum Aktualisieren der Histogrammdaten 
	 * Hier sollen alle Daten, die über getter ausgelesen werden können, anhand des aktuellen Datenobjekts 
	 */
	public void updateData();	

	/** 
	 * @return sortierte Liste der Intervalle des Histogramms entlang der x-Achse
	 */
	public List<Bin<Double>> getBins();
	
	/** 
	 * @return für jedes Intervall: Anzahl der Elemente für verschiedene Kategorien
	 */
	public Map<Bin<Double>, Map<String, Integer>> getData();
	
	/**
	 * @return für jedes Intervall: Anzahl der markierten Elemente für verschiedene Kategorien
	 */
	public Map<Bin<Double>, Map<String, Integer>> getMarkedData();

	/** 
	 * @return unterste Intervallgrenze des Histogramms
	 */
	public double getMin();
	
	/** 
	 * @return oberste Intervallgrenze des Histogramms
	 */
	public double getMax();
	
	/**
	 * @return Anzahl der Intervalle im Histogramm
	 */
	public int getSegments();
	
	/**
	 * Nutzer hat Balken im View markiert, zugehörige Datenpunkte werden programmweit gespeichert
	 * 
	 * @param markedBars - Collection der markierten Rechtecke im Histogramm
	 * @param add2Selection - true: zur bisherigen Selektion hinzufügen, false: bisherige Selektion ersetzen
	 */
	public void markData(Collection<CategoryBar> markedBars, boolean add2Selection);

}
