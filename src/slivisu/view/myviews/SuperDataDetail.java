package slivisu.view.myviews;

import java.util.List;
import java.util.Map;

import slivisu.data.Data;
import slivisu.data.MyZeitscheibe;
import slivisu.data.Sample;
import slivisu.mapper.SuperDataDetailImp.SamplesSorting;

public interface SuperDataDetail {
	
	public Data getData() ;
	
	/***
	 * Update Data to selected Data
	 */
	public void updateDataToSelectedSamples();
	
	/***
	 * Alle Selected Samples
	 * @return List<Sample>  alle Samples
	 */
	public List<Sample> getAllSelectedSamples(SamplesSorting sortingMode);
	
	/***
	 * Gibt alle Zeitscheiben des Samples mit Sicher
	 * @param sample 
	 * @param ebene
	 * @return Map<MyZeitscheibe, Boolean> Zeitscheibe auf Sicher (True für ja, false für nein)
	 */
	public Map<MyZeitscheibe, Boolean> zsForSampleWithSicher(Sample sample, int ebene);

	/***
	 * Alle MyZeitscheiben
	 * @return List<MyZeitscheibe>  alle MyZeitscheiben
	 */
	public List<MyZeitscheibe> getAllMyZeitscheiben();
	
	/***
	 * Ist ein Sample sicher in einer ZS
	 * @param s Sample
	 * @param zs Zeitscheibe 
	 * @return
	 */
	public List<Boolean> getFilter();
	
	/***
	 * Settings
	 * @param settings Liste von ebenen (0 = 1, 1 =2, usw) auf Anzeige
	 */
	public void setFilter(List<Boolean> settings);
	
}
