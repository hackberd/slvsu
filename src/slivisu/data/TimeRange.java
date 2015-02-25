package slivisu.data;

import java.util.List;

import slivisu.data.datatype.NumberRange;
import slivisu.gui.globalcontrols.timescale.TimeScaleData;

public class TimeRange extends NumberRange<Integer> implements TimeScaleData {

	private Data data;
	private boolean freezeRange;
	
	public TimeRange(Data data, Integer globalMin, Integer globalMax) {
		super(globalMin, globalMax);
		this.data = data;
		this.setFixedGlobalMin(false);
		this.freezeRange = false;

	}

	
	
	@Override
	public void update() {
		data.updateSelections();
	}

	@Override
	public void setFreezeRange(boolean freezeRange) {
		this.freezeRange = freezeRange;
	}

	@Override
	public boolean isFreezeRange() {
		return freezeRange;
	}



	@Override
	public List<MyZeitscheibe> selectedZeitscheibene() {
		return this.data.getCurrentZeitscheibe();
	}
}
