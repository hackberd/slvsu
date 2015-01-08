package slivisu.gui.globalcontrols.timescale;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TimeScaleListener extends MouseAdapter {

	private TimeScaleData data;
	private TimeScale timeScale;
	private TimeScaleOptions options;

	public TimeScaleListener(TimeScaleData data, TimeScale timescale) {
		this.data = data;
		this.timeScale = timescale;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == 3) {
			showPopup(e);
		} 
		else {
			data.update();
		}
	}

	private void showPopup(MouseEvent e) {
		if (options == null) {
			options = new TimeScaleOptions(null, false, timeScale);
		}
		options.setVisible(true);
	}
}
