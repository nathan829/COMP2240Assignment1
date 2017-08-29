import java.util.ArrayList;

public class FirstComeFirstServed implements SchedulingAlgorithm {

	FirstComeFirstServed() {}

	public void orderProcesses(ArrayList<Process> processes) {}

	public boolean preemptive() {
		return false;
	}

	public int getTimeQuantum(Process process) {
		return process.getServiceTime();
	}

	public String getAlgorithmName() {
		return "FCFS";
	}

}