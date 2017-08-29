import java.util.ArrayList;

public class Process {

	private final String ID;
	private int priority;
	private int serviceTime;
	private int arrivalTime;
	private int timeEnteredReadyQueue;
	private int timeCompleted;

	private ArrayList<Integer> timesEnteredCPU;
	private ArrayList<Integer> timesLeftCPU;
	private int totalTimeInCPU;

	private int queueEntryID;

	Process(String id) {
		ID = id;
		serviceTime = 0;
		arrivalTime = 0;
		priority = 5;       // Lowest priority as default.
		totalTimeInCPU = 0;
		timesEnteredCPU = new ArrayList<>();
		timesLeftCPU = new ArrayList<>();
		timeCompleted = -1;
		queueEntryID = -1;
	}

	public void setQueueEntryID(int id) {
		queueEntryID = id;
	} 

	public int getQueueEntryID() {
		return queueEntryID;
	}

	public void setTimeEnteredCPU(int timeEnteredCPU) {
		timesEnteredCPU.add(timeEnteredCPU);
	}

	public int getTimeLastEnteredCPU() {
		return timesEnteredCPU.get(timesEnteredCPU.size()-1);
	}

	public ArrayList<Integer> timesEnteredCPU() {
		return timesEnteredCPU;
	}

	public void setTimeLeftCPU(int timeLeftCPU) {
		timesLeftCPU.add(timeLeftCPU);
	}

	public ArrayList<Integer> timesLeftCPU() {
		return timesLeftCPU;
	}

	public void setTimeEnteredReadyQueue(int time) {
		timeEnteredReadyQueue = time;
	}

	public int getTimeEnteredReadyQueue() {
		return timeEnteredReadyQueue;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setTimeCompleted(int timeCompleted) {
		this.timeCompleted = timeCompleted;
	}

	public int getTimeCompleted() {
		return timeCompleted;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public String getID() {
		return ID;
	}

	public void incrementTotalTimeInCPU(int increment) {
		totalTimeInCPU += increment;
	} 

	public int timeInCPU() {
		return totalTimeInCPU;
	}

	public int compareID(Process process) {
		if(Integer.parseInt(ID.substring(1)) < Integer.parseInt(process.getID().substring(1))) {
			return -1;
		}
		else if(Integer.parseInt(ID.substring(1)) < Integer.parseInt(process.getID().substring(1))) {
			return 1;
		}
		return 0;
	}

}