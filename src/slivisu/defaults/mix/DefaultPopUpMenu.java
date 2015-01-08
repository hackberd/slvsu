/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package slivisu.defaults.mix;


import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;


/**
 *
 * @author Sven
 */
public class DefaultPopUpMenu extends JPopupMenu {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JCheckBoxMenuItem itemShowALL;

    JCheckBoxMenuItem itemShowCategoriesAlways;


//    SLIvisuFunctions f;
    public DefaultPopUpMenu() {

        initial();
    }


    public void openRightClickMenu( MouseEvent event ) {
    }


    private void initial() {
        this.setBorder( tBorder() );
    }


    /**
     * @return Rahmen für das PopupMenu
     */
    private TitledBorder tBorder() {
        TitledBorder tB = new TitledBorder( "menu" );
        tB.setTitlePosition( TitledBorder.CENTER );
        tB.setTitleFont( new Font( "SansSerif" , Font.PLAIN , 10 ) );

        return tB;
    }


//
//
//    private JCheckBoxMenuItem itemShowCategoriesAlways() {
//        itemShowCategoriesAlways = new JCheckBoxMenuItem( "Show Categories Always" );
////        itemShowCategoriesAlways.setIcon( new ImageIcon( getClass().getResource( "/controller/resources/preferences-desktop.png" ) ) );
//        itemShowCategoriesAlways.setSelected( false );
//
//        itemShowCategoriesAlways.addActionListener( new ActionListener() {
//
//            public void actionPerformed( ActionEvent e ) {
//                checkViewMode();
//            }
//
//
//        } );
//
//        return itemShowCategoriesAlways;
//    }
//
//
//    private void checkViewMode() {
//        if ( itemShowALL.isSelected() == true ) {
//            if ( itemShowCategoriesAlways.isSelected() == true ) {
//                setViewMode( 1 );
//            }
//            else {
//                setViewMode( 0 );
//            }
//        }
//        else {
//            itemShowCategoriesAlways.setSelected( true );
//            setViewMode( 2 );
//        }
//    }
//
//
//    /**
//     * Setzen der Markierungsharken
//     */
//    public void setSelections( int mode ) {
////        switch(mode)
////        {
//////            case 0:
//////                itemShowCategoriesAlways.setSelected( false );
//////                break;
//////            case 1:
//////                itemShowCategoriesAlways.setSelected( true );
//////                break;
////            case 2:
////                itemShowCategoriesAlways.setSelected( true );
////                break;
//////            
////        }
//    }
//
//
//    public void setViewMode( int data ) {
//        try {
//
//            System.out.println( "itemShowALL  " + itemShowALL.isSelected() );
//
//            Slivisu.viewMode = data;
//            System.out.println( "ViewMode:  " + Slivisu.viewMode );
//            Slivisu.startController( 4 );
//
//
//        }
//        catch ( Exception ex ) {
//            System.err.println( "error in SLIvisu_ControlCenter - setViewMode  " + ex.getMessage() );
//        }
//    }
//
    public class SLIMenuItem extends JMenuItem {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public void setFont( Font font ) {
            super.setFont( new Font( "SansSerif" , Font.PLAIN , 11 ) );
        }

    }
}
