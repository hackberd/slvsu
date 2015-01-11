package slivisu.data.datatype;

import java.awt.geom.Rectangle2D;

import slivisu.data.MyZeitscheibe;

/**
 * @author hoffmann
 */

public class Balken {
	
	private MyZeitscheibe relZeitscheibe;
	private int anfang;
	private int ende;
	private int ebene;
	private int sicher;
	private int unsicher;
	private String name;
	private Rectangle2D rect;
	private boolean hit;
	private boolean selected;

	public Balken(MyZeitscheibe relZeitscheibe, int anfang, int ende, int ebene, int sicher, int unsicher, String name) {
		this.relZeitscheibe	= relZeitscheibe;
		this.anfang			= anfang;
		this.ende			= ende;
		this.ebene			= ebene;
		this.sicher			= sicher;
		this.unsicher		= unsicher;
		this.name			= name;
		this.hit			= false;
	}

	public MyZeitscheibe getRelZeitscheibe() {
		return relZeitscheibe;
	}

	public void setRelZeitscheibe(MyZeitscheibe relZeitscheibe) {
		this.relZeitscheibe = relZeitscheibe;
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
	
	public int getEbene() {
		return ebene;
	}
	
	public void setEbene(int ebene) {
		this.ebene = ebene;
	}

	public int getSicher() {
		return sicher;
	}
	
	public void setSicher(int sicher) {
		this.sicher = sicher;
	}
	
	public int getUnsicher() {
		return unsicher;
	}
	
	public void setUnsicher(int unsicher) {
		this.unsicher = unsicher;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Rectangle2D getRect() {
		return rect;
	}
	
	public void setRect(Rectangle2D rect) {
		this.rect = rect;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
