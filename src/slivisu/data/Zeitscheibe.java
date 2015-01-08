package slivisu.data;

import slivisu.data.datatype.Bin;

public class Zeitscheibe {

	String name = "";
	String kurzform = "";
	
	int ebene = -1;
	
	int anfang = 0;
	int ende = 0;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKurzform() {
		return kurzform;
	}
	public void setKurzform(String kurzform) {
		this.kurzform = kurzform;
	}
	public int getEbene() {
		return ebene;
	}
	public void setEbene(int ebene) {
		this.ebene = ebene;
	}
	public int getAnfang() {
		return anfang;
	}
	public void setAnfang(int anfang) {
		this.anfang = anfang;
	}
	public int getEnde() {
		return ende;
	}
	public void setEnde(int ende) {
		this.ende = ende;
	}
	
	public Bin<Double> getBin() {
		return new Bin<Double>((double) anfang, (double) ende);
	}
	
	
	
}
