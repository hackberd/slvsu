package animation;

public class Animator extends Thread {

	private Animation animation;
	private boolean resume;
	private boolean pause;
	private int framesPerSecond;

	public Animator(Animation animation){
		this.animation = animation;
		pause = false;
		framesPerSecond = 1;
	}

	public synchronized void run(){
		resume = true;
		while (resume){
			animation.next();
			do{
				try{
					int millis = 0;
					if (framesPerSecond > 0){
						millis = 1000/framesPerSecond;
					}
					wait(pause? 0: millis);
				}
				catch (InterruptedException e){
					System.out.println("InterruptedException");
				}
			} 
			while (pause);
		}
	}

	public synchronized void pause(){
		pause = true;
	}

	public synchronized void go(){
		pause = false;
		notify();
	}

	public void finish(){
		go();
		resume = false;
	}
	
	public void setFramesPerSecond(int framesPerSecond){
		this.framesPerSecond = framesPerSecond;
	}
}
