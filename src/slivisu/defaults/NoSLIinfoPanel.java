/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



/**
 *
 * @author Sven
 */
public class NoSLIinfoPanel extends JPanel 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSLIinfoPanel()
    {
        initial();
    }
    
    private void initial()
    {
        this.add( new JLabel( "no sli selected", null, SwingConstants.CENTER) );
    }
}
