package slivisu.data.datatype;

public class NumberRange <E extends Number>{

	E globalMin = null;
	E globalMax = null;
	E min = null;
	E max = null;
	
	boolean fixedGlobalMin = false;
	boolean fixedGlobalMax = false;
	
	public NumberRange(E globalMin, E globalMax){
		setGlobalMin(globalMin);
		setGlobalMax(globalMax);
		setMin(globalMin);
		setMax(globalMax);
	}
	
	public E getGlobalMin() {
		return globalMin;
	}
	
	public void setGlobalMin(E globalMin) {
		if (! fixedGlobalMin){
			this.globalMin = globalMin;
			if (min != null && min.doubleValue() < globalMin.doubleValue()){
				min = globalMin;
			}
		}
	}
	
	public E getGlobalMax() {
		return globalMax;
	}
	
	public void setGlobalMax(E globalMax) {
		if (! fixedGlobalMax){
			this.globalMax = globalMax;
			if (min != null && max.doubleValue() > globalMax.doubleValue()){
				max = globalMax;
			}
		}
	}
	
	public E getMin() {
		return min;
	}
	
	public void setMin(E min) {
		if (globalMin != null && min.doubleValue() < globalMin.doubleValue()){
			this.min = globalMin;
		}
		else{
			this.min = min;
		}
	}
	
	public E getMax() {
		return max;
	}
	
	public void setMax(E max) {
		if (globalMax != null && max.doubleValue() > globalMax.doubleValue()){
			this.max = globalMax;
		}
		else{
			this.max = max;
		}
	}
	
	public boolean isFixedGlobalMin() {
		return fixedGlobalMin;
	}

	public void setFixedGlobalMin(boolean fixedGlobalMin) {
		this.fixedGlobalMin = fixedGlobalMin;
	}

	public boolean isFixedGlobalMax() {
		return fixedGlobalMax;
	}

	public void setFixedGlobalMax(boolean fixedGlobalMax) {
		this.fixedGlobalMax = fixedGlobalMax;
	}

}
