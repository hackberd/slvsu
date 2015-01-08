package slivisu.defaults;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter {

	private JPopupMenu popupMenu;

	public PopupListener(JPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 3) {
			if (popupMenu != null){
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		} 
	}
}
