package slivisu.data;






/**
 * 
 * @author Andrea
 */
public class Sample {
	
	String gemeinde = "";
	String ort = "";
	String fundart = "";
	String region = "";
	String department = "";
	
	double lat = 0;
	double lon = 0;

	public String getGemeinde() {
		return gemeinde;
	}

	public void setGemeinde(String gemeinde) {
		this.gemeinde = gemeinde;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getFundart() {
		return fundart;
	}

	public void setFundart(String fundart) {
		this.fundart = fundart;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
}
