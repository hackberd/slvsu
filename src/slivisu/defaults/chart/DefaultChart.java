/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;


/**
 *
 * @author Sven
 */
public class DefaultChart extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Rahmen des Diagramms */
    private LineBorder border;

    /** Punkte an denen eine Linie des Hilfsgitters verläuft */
    private List<Integer> rasterX, rasterY;

    /** Bezeichner der Parameterachsen */
    private String paraX, paraY;

    /** Umrechnung von Daten- in Pixelkoordinaten */
    private double factorX, factorY;

    /** kleinster sichtbarer Punkt auf x- und y-Achse */
    private double minX = 0, minY = 0; 

    public double getFactorX() {
        return factorX;
    }


    public void setFactorX( double factorX ) {
        this.factorX = factorX;
    }


    public double getFactorY() {
        return factorY;
    }


    public void setFactorY( double factorY ) {
        this.factorY = factorY;
    }


    public List<Integer> getRasterX() {
        return rasterX;
    }


    public void setRasterX(List<Integer> rasterX ) {
        this.rasterX = rasterX;
    }


    public List<Integer> getRasterY() {
        return rasterY;
    }


    public void setRasterY(List<Integer> rasterY ) {
        this.rasterY = rasterY;
    }


    public String getParaX() {
        return paraX;
    }


    public void setParaX( String paraX ) {
        this.paraX = paraX;
    }


    public String getParaY() {
        return paraY;
    }


    public void setParaY( String paraY ) {
        this.paraY = paraY;
    }


//    public String getParaZ() {
//        return paraZ;
//    }
//
//
//    public void setParaZ( String paraZ ) {
//        this.paraZ = paraZ;
//    }


    public DefaultChart() {
        border = new LineBorder( Color.BLACK , 1 );
        this.setBorder( border );
        this.setBackground( Color.WHITE );
    }


    /**
     * Erzeugt X- und Y-Gitter
     */
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
        Graphics2D g2 = ( Graphics2D ) g;
        g2.setColor( Color.LIGHT_GRAY );
        g2.setStroke( new BasicStroke( 1f , BasicStroke.JOIN_BEVEL , BasicStroke.JOIN_BEVEL , 1f , new float[] { 5f } , 5f ) );
        if (rasterX != null) {
            for (int i : rasterX) {
                g2.drawLine( i , 0 , i , getHeight() );
            }
        }

        if ( rasterY != null ) {
        	for (int i : rasterY) {
                g2.drawLine( 0 , i , getWidth() , i );
            }
        }
        g2.setStroke( new BasicStroke( 1f ) );
    }

    /**
     * Bestimmen des Sklalierungsfaktors
     */
    public void scaleFactors( double xRange , double yRange ) {
        setFactorX( DefaultChartFunctions.calculateFactor( getWidth() - 10 , xRange ) );
        setFactorY( DefaultChartFunctions.calculateFactor( getHeight() - 10 , yRange ) );
    }

	/**
	 * Rechne Datenkoordinate in Pixelkoordinate um
	 */
	public Point2D getPosition(double x, double y) {
		return new Point2D.Double(5 + ((x - minX) / getFactorX()), getHeight() - 5 - ((y - minY) / getFactorY()));
	}


	protected double getMinX() {
		return minX;
	}


	protected void setMinX(double minX) {
		this.minX = minX;
	}


	protected double getMinY() {
		return minY;
	}


	protected void setMinY(double minY) {
		this.minY = minY;
	}

}
