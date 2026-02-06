package threadfool.op.engine.core;

public class Time
{
	private static double lastTIme;
	private static float delta;
	public static void init(){
		lastTIme = now();
	}

	public static void update(){
		double current = now();
		delta = (float)(current - lastTIme);
		lastTIme = current;
	}

	public static float delta(){
		return delta;
	}

	private static double now(){
		return System.nanoTime() / 1_000_000_000.0;
	}
}

