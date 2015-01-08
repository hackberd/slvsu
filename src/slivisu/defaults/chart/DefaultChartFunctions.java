/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults.chart;


/**
 *
 * @author Sven
 */
public class DefaultChartFunctions {
    
    
    /**
     * Bestimmt für den Datenpunkt den entsprechenden Pixelwert.
     * @param dataV - Datenwert
     * @param factor - Skalierungsfaktor
     * @return Pixelwert
     */
    public static int data2Px( double dataV , double factor ) {

        return (( int ) ((0.0 + dataV) * factor));
    }
    
     /**
     * Bestimmt das Verhältnis von Pixeln zur originalen Datenausdehnung
     * @param pxW - Breite der Panels
     * @param range - Wertebereich
     * @return
     */
    public static double calculateFactor( int pxW , double range ) {
        return (range /  pxW);
    }
    
    
    
}
