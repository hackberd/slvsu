package slivisu.gui.controller;

import slivisu.data.Sample;
import slivisu.data.selection.Selection;

public interface DataInterface {

	public void wrapUp();

	void updateData(Selection<Sample> selection);
}
