package slivisu.defaults.chart;


import java.util.Vector;

public class PlotTicks{

	static double [] steps = {1, 2, 5};
	static double [] stepsBackward = {5, 2, 1};
	static double factor = 10;
	
	public static int getNumberOfTicks(int pixels, int pixelBetweenTicks){
		
		if (pixelBetweenTicks == 0){
			return 1;
		}
		return pixels / pixelBetweenTicks;
	}
	
	public static Vector<Double> computeTickValues(double min, double max, double ticks){
		
		if (min < max){
			Vector<Double> result = new Vector<Double>();
			
			double diff = max - min;
			
			double computedIncrement = diff / ticks;
			
			double increment = getIncrement(computedIncrement, ticks);
	
	//		System.out.println("computedIncrement: "+computedIncrement+", increment: "+increment);
			
			double minTick = Math.floor(min / increment);
			double maxTick = Math.ceil(max / increment);
			
			for (double d = minTick; d <= maxTick; d++){
				result.add(d * increment);
			}
		
			return result;
		}
		return null;
	}
	
	public static double getIncrement(double computedIncrement, double ticks){
		
		double increment = computedIncrement;
		
		if (computedIncrement < 1){
			
			double currentFactor = 0.1;
			double nextIncrement = 1;
			
			boolean nextIsSet = false;
			
			do{				
				for (double step : stepsBackward){
					nextIncrement = step * currentFactor; 
					if (nextIncrement > computedIncrement || ! nextIsSet){
						increment = nextIncrement;
						
						if (nextIncrement <= computedIncrement){
							nextIsSet = true;
						}
					}
				}
				currentFactor /= factor;
			}
			while (nextIncrement > computedIncrement);
		}
		else{
			double currentFactor = 1;
			double nextIncrement = 1;
			
			boolean nextIsSet = false;
			
			do{				
				for (double step : steps){
					nextIncrement = step * currentFactor; 
					if (nextIncrement < computedIncrement || ! nextIsSet){
						increment = nextIncrement;
						
						if (nextIncrement >= computedIncrement){
							nextIsSet = true;
						}
					}
				}
				currentFactor *= factor;
			}
			while (nextIncrement < computedIncrement);
		}
		
		return increment;
	}

	public static void print(Vector<Double> v){
		System.out.println("Vector mit "+v.size()+" Elementen");
		for (double d : v){
			System.out.println(d);
		}		
	}
	
	public static void main(String [] args){
		
		double ticks = getNumberOfTicks(960, 100);
		
		print(computeTickValues(0, 18, ticks));
		print(computeTickValues(350, 4729, ticks));
		print(computeTickValues(-204838, -24729, ticks));
		print(computeTickValues(0.02, 0.048, ticks));
	}
}