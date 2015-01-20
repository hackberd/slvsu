package slivisu.data;

import java.util.HashSet;
import java.util.Set;

public class MyZeitscheibe extends Zeitscheibe {
	Set<Sample> sichereSiedlungen;
	Set<Sample> unsichereSiedlungen;
	Zeitscheibe originalZeitscheibe;
	Set<Sample> alleSamplesInZeitscheibe;

	
	public MyZeitscheibe(Zeitscheibe zs) {
		this.setAnfang(zs.getAnfang());
		this.setEbene(zs.getEbene());
		this.setEnde(zs.getEnde());
		this.setKurzform(zs.getKurzform());
		this.setName(zs.getName());
		originalZeitscheibe = zs;
		alleSamplesInZeitscheibe = new HashSet<Sample>();
		sichereSiedlungen = new HashSet<Sample>();
		unsichereSiedlungen = new HashSet<Sample>();
	}
	
	public synchronized void addSample(Sample sample) {
		this.alleSamplesInZeitscheibe.add(sample);
	}
	
	public int getAnzahlSichereSiedlungen() {
		return this.sichereSiedlungen.size();
	}
	
	
	public void addSichereSiedlung(Sample sample) {
		this.sichereSiedlungen.add(sample);
	}
	
	public int getAnzahlUnsichereSiedlungen() {
		return this.unsichereSiedlungen.size();
	}
	public void addUnsichereSiedlung(Sample sample) {
		this.unsichereSiedlungen.add(sample);
	}

	
	public Zeitscheibe getOriginalZeitscheibe() {
		return originalZeitscheibe;
	}
	public void setOriginalZeitscheibe(Zeitscheibe originalZeitscheibe) {
		this.originalZeitscheibe = originalZeitscheibe;
	}

	public Set<Sample> getAlleSamplesInZeitscheibe() {
		return alleSamplesInZeitscheibe;
	}
	
	public void resetAllCounters() {
		sichereSiedlungen = new HashSet<Sample>();
		unsichereSiedlungen = new HashSet<Sample>();
	}

	

//	public void setAlleSamplesInZeitscheibe(List<Sample> alleSamplesInZeitscheibe) {
//		this.alleSamplesInZeitscheibe = alleSamplesInZeitscheibe;
//	}
	
	
}
