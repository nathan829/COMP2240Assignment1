/*
  Time.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

	Description: 	A singleton class which allows for several classes to subscribe to the same
								time unit. This is essentially a global time reference which is a good tool
								for simulations involving multiple classes without having to pass around
								references.
*/

public class Time {

	private static Time instance;
	private int currentTime;

	// Private constructor following the singleton pattern.
	private Time() {
		currentTime = 0;
	}

	// Only allows for a single Time instance to be given out.
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