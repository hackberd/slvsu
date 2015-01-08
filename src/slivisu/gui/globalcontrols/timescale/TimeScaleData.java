package slivisu.gui.globalcontrols.timescale;

public interface TimeScaleData {

	public void setGlobalMin(Integer globalMin);
	public Integer getGlobalMin();
	
	public void setGlobalMax(Integer globalMax);
	public Integer getGlobalMax();
	
	public void setMin(Integer min);
	public Integer getMin();
	
	public void setMax(Integer max);
	public Integer getMax();
	public void update();
	public void setFreezeRange(boolean selected);
	public boolean isFreezeRange();
}
