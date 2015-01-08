package slivisu.view.myviews;

import java.util.List;

import slivisu.data.MyZeitscheibe;

public interface SuperData {
	// Sinnlos?
	//public void updateData();
	
	/***
	 * Gibt eine Liste mit allen Ebenen zurück
	 * In diesen Ebenen sind alle Zeitscheiben dieser Ebene sortiert enthalten 
	 * 
	 */
	public List<List<MyZeitscheibe>> getAllData();
	
	// public List<List<MyZeitscheibe>> getAllData(boolean mitSicherenSiedlungen, boolean mitUnsicherenSiedlungen, int vonEbene, int bisEbene, int vonZeit, int bisZeit);

	/***
	 * Selektiert die Zeitscheiben + Slider aktuallisieren
	 * @param selectedZeitscheiben
	 */
	public void selectZeitscheiben(List<MyZeitscheibe> selectedZeitscheiben);
	
	/***
	 * Selektiert alte fokussierte Daten
	 */
	public void resetSelectionToLastFocus();
	
	/***
	 * Gibt Die zeischeiben in Ebenen sortiert zurück,
	 * rechnet darüber liegende Ebenen um, um nurnoch diese Siedlungen anzeigen
	 */
	public List<List<MyZeitscheibe>> getDataForFocusWhileFocussing();
	
}
