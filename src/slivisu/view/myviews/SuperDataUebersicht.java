package slivisu.view.myviews;

import java.util.List;

import slivisu.data.MyZeitscheibe;

public interface SuperDataUebersicht {
	
	public void updateDataToSelectedData();
	
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
	
	/***
	 * Liste ob bestimmte ebenen angezeigt werden sollen
	 * @return
	 */
	public List<Boolean> getFilter();
	
	/***
	 * Settings
	 * @param settings Liste von ebenen (0 = 1, 1 =2, usw) auf Anzeige
	 */
	public void setFilter(List<Boolean> settings);
}
