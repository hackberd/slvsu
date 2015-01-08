package slivisu.view.globe;

import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwind.render.PointPlacemark;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;


public class SliGlobeListener extends MouseAdapter implements SelectListener{

	private SliGlobePopupMenu popupMenu;
	private GlobeData data;
	

	public SliGlobeListener(SliGlobePopupMenu popupMenu, GlobeData data) {
		this.popupMenu = popupMenu;
		this.data = data;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {
			popupMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

	@Override
	public void selected(SelectEvent event) {
		
		Object o = event.getTopObject();
		if (o != null){
			if (o instanceof PointPlacemark){
				if (event.getEventAction().equals(SelectEvent.LEFT_CLICK)) {
					
					List<GlobePoint> pList = new Vector<GlobePoint>();
					List<PickedObject> objects = event.getObjects();
					for (PickedObject po : objects){
						if (po.getObject() instanceof GlobePoint){
							pList.add((GlobePoint)(po.getObject()));
						}
					}
					
					data.markData(pList, event.getMouseEvent().isControlDown());
				}
			}
		}
	}
	
	public void setPopupMenu(SliGlobePopupMenu popupMenu){
		this.popupMenu = popupMenu;
	}
}
