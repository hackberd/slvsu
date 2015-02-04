package slivisu.data.io;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import slivisu.data.Wegenetz;
import slivisu.data.Zeitscheibe;

public class WegenetzDataReader {

	private String separator = ",";

	public Wegenetz loadWegenetz(List<String> content){

		Wegenetz w = null;
		
		List<String> header = getHeader(content.get(0));

		int idIndex = header.indexOf("OBJECTID");
		int lonIndex = header.indexOf("lon");
		int latIndex = header.indexOf("lat");

		if (idIndex != -1 && lonIndex != -1 && latIndex != -1){

			int oldID = -1;

			List<Point2D.Double> linienzug = null;

			// alle Zeilen durchgehen: pro Zeile ein Punkt des Wegenetzes 
			// Punkte eines Linienzug haben gleiche ID
			for (int i = 1; i < content.size(); i++){

				String[] s = content.get(i).split(separator);

				int id = 0;
				try{
					id = Integer.parseInt(s[idIndex]);
				}
				catch (NumberFormatException e){
					e.printStackTrace();
				}

				double lon = 0;
				try{
					lon = Double.parseDouble(s[lonIndex]);
				}
				catch (NumberFormatException e){
					e.printStackTrace();
				}

				double lat = 0;
				try{
					lat = Double.parseDouble(s[latIndex]);
				}
				catch (NumberFormatException e){
					e.printStackTrace();
				}

				Point2D.Double point = new Point2D.Double(lon, lat); // 3 4
				
				if (id != oldID){
					linienzug = new Vector<Point2D.Double>();
					if (w == null){
						w = new Wegenetz();
					}
					w.add(linienzug);
					oldID = id;
				}

				linienzug.add(point);
			}
		}
		return w;
	}

	public Map<Zeitscheibe, Wegenetz> loadWegenetzFuerZeitscheibe(List<List<Zeitscheibe>> chronologie) {

		File modelEnsembleFile = DataReader.chooseData("data", "Load way data"); 

		List<String> fileContent = DataReader.readData(modelEnsembleFile);

		if (fileContent != null && chronologie != null){

			String fileName = modelEnsembleFile.getName();

			int ebene = -1;
			
			int beginIndex = fileName.indexOf("Ebene") + "Ebene".length() + 1;
			int endIndex = fileName.indexOf("Slice") - 1;
			
			if (0 <= beginIndex && beginIndex < endIndex && endIndex < fileName.length()){
				String s = fileName.substring(beginIndex, endIndex);
				try{
					ebene = Integer.parseInt(s) - 1;
				}
				catch (NumberFormatException e){
					e.printStackTrace();
				}
			}

			Zeitscheibe scheibe = null;
			
			beginIndex = fileName.indexOf("Slice") + "Slice".length() + 1;
			endIndex = fileName.indexOf(".csv");

			if (ebene != -1 && 0 <= beginIndex && beginIndex < endIndex && endIndex < fileName.length()){
				
				String zeitScheibe = fileName.substring(beginIndex, endIndex);
				
				for (Zeitscheibe z : chronologie.get(ebene)){
					if (z.getKurzform().equals(zeitScheibe)){
						scheibe = z;
					}
				}
			}
			
			Wegenetz w = loadWegenetz(fileContent);

				
			if (ebene != -1 && scheibe != null && w != null){
							
				Map<Zeitscheibe, Wegenetz> netze = new HashMap<Zeitscheibe, Wegenetz>();
				netze.put(scheibe, w);
				
				return netze;
			}
		}
		return null;
	}

	private List<String> getHeader(String headString){

		String[] head = headString.split(separator);
		List<String> header = new Vector<String>(); // um die Wahrscheinlichkeit anhand der Zeitscheibenkurzform festzulegen

		for(String s : head){
			header.add(s);
		}
		return header;
	}

}
