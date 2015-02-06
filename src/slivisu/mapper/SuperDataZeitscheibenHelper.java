package slivisu.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import slivisu.data.MyZeitscheibe;
import slivisu.data.Zeitscheibe;
import slivisu.data.datatype.Bin;

public class SuperDataZeitscheibenHelper {
		
		static public synchronized LinkedList<MyZeitscheibe> sortiereZeitscheiben (Collection<MyZeitscheibe> zeitscheiben) {
			LinkedList<Bin<Double>> bins = new LinkedList<Bin<Double>>();
			
			for (Zeitscheibe ebene : zeitscheiben) { // Alle relevanten zeitscheiben
				bins.add(ebene.getBin());
			}
			
			// Sortieren
			 LinkedList<MyZeitscheibe> ausgabe = new LinkedList<MyZeitscheibe>();
			 Map<Double, MyZeitscheibe> map = new HashMap<Double, MyZeitscheibe>();
			 List<Double> doublesListe = new LinkedList<Double>();
			 
			 for (MyZeitscheibe zs : zeitscheiben) {
				 double wert = zs.getBin().getMin();
				 doublesListe.add(wert);
				 map.put(wert, zs);
			 }
			 Collections.sort(doublesListe);
			 
			for (Double dbl : doublesListe) {
				if (!ausgabe.contains(map.get(dbl))) {
					ausgabe.add(map.get(dbl));
				}
				//System.out.println(map.get(dbl).getAnfang());
			}
			return ausgabe;
		}
}
