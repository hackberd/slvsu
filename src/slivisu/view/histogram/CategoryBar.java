/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.view.histogram;


import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;


/**
 * ein Rechteck im Histogramm
 * speichert Anzahl der Datenpunkte, untere und obere Intervallgrenze entlang der x-Achse sowie Datenkategorie 
 *
 * @author Sven
 * @author unger
 *
 */
public class CategoryBar extends Rectangle {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String category;
    int count;
    double min;
    double max;

    /**
     * @return Anzahl der Datenpunkte
     */
    public int getCount() {
        return count;
    }

    /**
     * @return Datenkategorie
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return obere Intervallgrenze
     */
    public double getMax() {
        return max;
    }

    /**
     * @return untere Intervallgrenze
     */
    public double getMin() {
        return min;
    }
        
    /**
     * Konstruktor
     * 
     * @param category - Datenkategorie
     * @param count - Anzahl der Datenpunkte im Rechteck
     * @param rec - Rechteck als geometrische Form mit Position und Größe
     * @param min - untere Intervallgrenze
     * @param max - obere Intervallgrenze
     */
    public CategoryBar(String category, int count, Rectangle2D rec, double min, double max) {
        
        this.category = category;
        this.count = count;   
        this.setRect(rec);
        this.min = min;
        this.max = max;
    }
}
