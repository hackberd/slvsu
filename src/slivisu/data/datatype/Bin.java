package slivisu.data.datatype;

import java.util.List;
import java.util.Vector;

/**
 * @author unger
 *
 * speichert ein Zahlenintervall mit unterer und oberer Grenze
 * @param <E> - Zahlentyp
 */
public class Bin<E extends Number> {

	private E min;
	private E max;
	
	/**
	 * @param min
	 * @param max
	 */
	public Bin(E min, E max){
		this.min = min;
		this.max = max;
	}

	/**
	 * @param min
	 */
	public void setMin(E min){
		this.min = min;
	}

	/**
	 * @return
	 */
	public E getMin(){
		return min;
	}

	/**
	 * @param max
	 */
	public void setMax(E max){
		this.max = max;
	}

	/**
	 * @return
	 */
	public E getMax(){
		return max;
	}
	
	/**
	 * unterteilt das Intervall gleichm��ig in eine angegebene Anzahl von Intervallen
	 * 
	 * @param segments - Anzahl der Intervalle
	 * 
	 * @return Liste von Intervallen
	 */
	public List<Bin<E>> getBins(int segments){
		List<Bin<E>> bins = new Vector<Bin<E>>();
		Double segmentSize = (max.doubleValue() - min.doubleValue()) / segments;
		Double minValue = min.doubleValue();
		Double maxValue = min.doubleValue() + segmentSize;
		for (int i = 0; i < segments; i++) {
			bins.add(new Bin<E>((E)minValue, (E)maxValue));
			minValue += segmentSize;
			maxValue += segmentSize;
		}
		return bins;
	}

	public String toString(){
		return super.toString() + "{" + min + "," + max + "}";
	}
}