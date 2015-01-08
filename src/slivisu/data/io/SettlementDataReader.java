package slivisu.data.io;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import slivisu.data.Data;
import slivisu.data.Sample;
import slivisu.data.Zeitscheibe;

public class SettlementDataReader {

	private String separator = ",";

	Data data;
	
	public SettlementDataReader(Data data){
		this.data = data;
	}
	
	private File chooseFile(String folder, String dialogText){
		JFileChooser chooser = new JFileChooser ();
		if (folder != null){
			chooser = new JFileChooser(folder);
		}
		chooser.setFileFilter (new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) { 
					return true; 
				}
				else if (!f.exists()){
					return false;
				}
				return f.getName().toLowerCase().endsWith(".csv");
			}
			@Override
			public String getDescription() {
				return "CSV-File";
			}
		} );
		chooser.showDialog(chooser, dialogText);
		
		return chooser.getSelectedFile();
	}

	public boolean loadCSV() {
				
		File modelEnsembleFile = chooseFile("data", "Load data samples");
		
		List<String> fileContent = DataReader.readData(modelEnsembleFile);
		
		if (fileContent != null){
						
			List<List<Zeitscheibe>> chronologie = data.getObservationData().getChronologie();
			
			if (chronologie != null){
				
				List<Sample> selection = new Vector<Sample>();
				Map<Sample, Map<Zeitscheibe, Double>> fundDatierung = new HashMap<Sample, Map<Zeitscheibe, Double>>();
				
				List<String> header = getHeader(fileContent.get(0));

				int lineNumber = 1;
				
				while (lineNumber < fileContent.size()){
					Sample fund = new Sample();
	
					String[] s = fileContent.get(lineNumber).split(",");
					
					fund.setLon(Double.parseDouble(s[header.indexOf("x")]));
					fund.setLat(Double.parseDouble(s[header.indexOf("y")]));
					fund.setDepartment(s[header.indexOf("DEPARTEMEN")]);
					fund.setFundart(s[header.indexOf("Siedlungss")]);
					fund.setGemeinde(s[header.indexOf("COMMUNE")]);
					fund.setOrt(s[header.indexOf("LIEU_IGN")]);
					fund.setRegion(s[header.indexOf("REGION")]);
									
					Map<Zeitscheibe, Double> zW = new HashMap<Zeitscheibe, Double>();
	
						for(int i = 0 ; i < chronologie.size() ; i++){
						for(int j = 0 ; j< chronologie.get(i).size(); j++){
							Zeitscheibe scheibe =chronologie.get(i).get(j);
							String k = scheibe.getKurzform();
							Double wahrscheinlichkeit = Double.parseDouble(s[header.indexOf(k)]);
							zW.put(scheibe, wahrscheinlichkeit);
						}
					}
				
					selection.add(fund);
					fundDatierung.put(fund, zW);
				
					lineNumber++;
				}
				
				data.addObservationData(fundDatierung);
				
				return true;
			}
		}
		return false;
	}

	public boolean loadZeitscheibe() {
	
		File modelEnsembleFile = chooseFile("data", "Load time slice");
		
		if (modelEnsembleFile != null){
		
			List<String> fileContent = DataReader.readData(modelEnsembleFile);
			
			List<String> header = getHeader(fileContent.get(0));
			//System.out.println(header);
			int lineNumber = 1; //line 0 enth�lt Attributbezeihnungen
			List<Zeitscheibe> zeitscheibe = new Vector<Zeitscheibe>();
			int maxEbene = 0;
			while (lineNumber < fileContent.size()){
				Zeitscheibe scheibe = new Zeitscheibe();
				String[] s = fileContent.get(lineNumber).split(",");
				int ebene = Integer.parseInt(s[header.indexOf("Ebene")]);
				if(ebene>maxEbene){maxEbene=ebene;}
				scheibe.setAnfang(Integer.parseInt(s[header.indexOf("Start")]));
				scheibe.setEnde(Integer.parseInt(s[header.indexOf("Ende")]));
				scheibe.setEbene(ebene);
				scheibe.setKurzform(s[header.indexOf("Kurzform")]);
				scheibe.setName(s[header.indexOf("Name")]);
				zeitscheibe.add(scheibe);
				lineNumber++;
			}
			
			List<List<Zeitscheibe>> chronologie= new Vector<List<Zeitscheibe>>();
			
			for(int i = 1 ; i <= maxEbene; i++){
				List<Zeitscheibe> list = new Vector<Zeitscheibe>();
				for(int j = 0 ; j < zeitscheibe.size() ; j++){
					if(zeitscheibe.get(j).getEbene() == i){
						list.add(zeitscheibe.get(j));
					}
				}
				chronologie.add((i-1),list);
			}
			
			data.getObservationData().setChronologie(chronologie);
			
			return true;
		}	
		return false;
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
