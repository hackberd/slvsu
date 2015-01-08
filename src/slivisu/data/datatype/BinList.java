package slivisu.data.datatype;

import java.util.List;
import java.util.Vector;

public class BinList {

	List<Double> min = null;
	List<Double> max = null;

	public BinList(){
		this.min = new Vector<Double>();
		this.max = new Vector<Double>();
	}

	public void add(double min, double max){
		this.min.add(min);
		this.max.add(max);
	}
	
	public double getMin(int index){
		return this.min.get(index);
	}
	
	public double getMax(int index){
		return this.max.get(index);
	}
	
	public List<Double> getMin(){
		return this.min;
	}
	
	public List<Double> getMax(){
		return this.max;
	}
	
	public int size(){
		return this.min.size();
	}

}