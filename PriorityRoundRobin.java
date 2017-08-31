/*
  PriorityRoundRobin.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

	Description: 	An implementation of a Scheduling Algorithm which represents a Round Robin algorithm
								where the time quantum is determined by the priority of the process.
*/

import java.util.ArrayList;

public class PriorityRoundRobin implements SchedulingAlgorithm {

	PriorityRoundRobin() {}	

	public String getAlgorithmName() {
		return "PRR";
	}

	public void orderProcesses(ArrayList<Process> processes) {
		// No ordering needed, as it is a first come first served algorithm with time quantums.
	}

	public boolean preemptive() {
		return false;
	}

	public int getTimeQuantum(Process process) {
		if(process.getPriority() < 3) {
			return 4;
		}
		else {
			return 2;
		}
	}

}