/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults.mix;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sven
 */
public class UpdateInfoPanel extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JLabel updateP;
    
    
    public UpdateInfoPanel()
    {
        updateP = new JLabel( " update");
        updateP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        
        this.setLayout( new BorderLayout());
        this.add( updateP, BorderLayout.CENTER );
    }
    
    
    public void setUpdatePicon(int iconCount)
    {
        updateP.setIcon( new javax.swing.ImageIcon( getClass().getResource( "/controller/resources/busyicons/busy-icon" + iconCount + ".png" ) ) );
    }
}
