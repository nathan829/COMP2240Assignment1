public class Time {

	private static Time instance;
	private int currentTime;

	private Time() {
		currentTime = 0;
	}

	public static Time getInstance() {
		if(instance == null) {
			instance = new Time();
		}
		return instance;
	}

	public int getTime() {
		return currentTime;
	}

	public void incrementTime(int increment) {
		currentTime += increment;
	}
 
	public void setTime(int newTime) {
		currentTime = newTime;
	}

	public void resetTime() {
		currentTime = 0;
	}

}