package slivisu.data;

import java.util.HashSet;
import java.util.Set;

public class MyZeitscheibe extends Zeitscheibe {
	int anzahlSichereSiedlungen = 0;
	int anzahlUnsichereSiedlungen = 0;
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
	}
	
	public void addSample(Sample sample) {
		this.alleSamplesInZeitscheibe.add(sample);
	}
	
	public int getAnzahlSichereSiedlungen() {
		return anzahlSichereSiedlungen;
	}
	public void setAnzahlSichereSiedlungen(int anzahlSichereSiedlungen) {
		this.anzahlSichereSiedlungen = anzahlSichereSiedlungen;
	}
	
	public void increaseAnzahlSichereSiedlungen() {
		this.anzahlSichereSiedlungen++;
	}
	
	public int getAnzahlUnsichereSiedlungen() {
		return anzahlUnsichereSiedlungen;
	}
	public void setAnzahlUnsichereSiedlungen(int anzahlUnsichereSiedlungen) {
		this.anzahlUnsichereSiedlungen = anzahlUnsichereSiedlungen;
	}
	
	public void increaseAnzahlUnsichereSiedlungen() {
		this.anzahlUnsichereSiedlungen++;
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
		this.anzahlSichereSiedlungen = 0;
		this.anzahlUnsichereSiedlungen = 0;
	}

	

//	public void setAlleSamplesInZeitscheibe(List<Sample> alleSamplesInZeitscheibe) {
//		this.alleSamplesInZeitscheibe = alleSamplesInZeitscheibe;
//	}
	
	
}
