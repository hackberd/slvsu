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
import slivisu.data.MyZeitscheibe;
import slivisu.data.ObservationData;
import slivisu.data.Sample;
import slivisu.data.Zeitscheibe;
import slivisu.data.datatype.Bin;
import sun.tools.tree.ThisExpression;

public class SuperDataZeitscheibenHelper {
		
		static public synchronized Collection<MyZeitscheibe> sortiereZeitscheiben (Collection<MyZeitscheibe> zeitscheiben) {
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
}
