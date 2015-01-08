package slivisu.view.globe;

import gov.nasa.worldwind.layers.RenderableLayer;

import java.util.Collection;
import java.util.List;

public interface GlobeData{

	/**
	 * Aktualisieren aller Daten, die ueber getter ausgelesen werden koennen
	 * @return true Daten sind aktualisert worden, false Daten wurden nicht aktualisiert, weil z.B. keine Aenderungen an den Selektionen vorgenommen wurden
	 */
	public boolean updateData();
	
	/**
	 * Gibt alle Geometrielayer zurueck, die auf Karte angezeigt werden sollen 
	 * @return Liste der Geometrielayer
	 */
	
	public List<RenderableLayer> getLayers();
	
	/**
	 * aktualisiert die Datenselektion markedSamples anhand der vom Nutzer geklickten Datenpunkte
	 * @param o Liste der 
	 * @param add2Selection
	 */
	public void markData(Collection<GlobePoint> o, boolean add2Selection);	
}
