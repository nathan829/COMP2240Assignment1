/*
  ShortestProcessNext.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

	Description: 	An implementation of a Scheduling Algorithm which represents a shortest process next
								algorithm, where the next chosen process is the one with the shortest service time.
*/

import java.util.ArrayList;

public class ShortestProcessNext implements SchedulingAlgorithm {

	ShortestProcessNext() {}

	public String getAlgorithmName() {
		return "SPN";
	}

	public void orderProcesses(ArrayList<Process> processes) {
		if (processes == null || processes.size() == 0){
				return;
		}

		int maxIndex = -1;
		int maxServiceTime = 0;
		boolean found = false;

		// Selection sort to order processes based on service time.
		for(int i = processes.size()-1; i >= 0; i--) {
			found = false;
			maxIndex = -1;
			maxServiceTime = 0;
			for(int j = 0; j <= i; j++) {
				if(processes.get(j).getServiceTime() > maxServiceTime) {	
					maxServiceTime = processes.get(j).getServiceTime();
					maxIndex = j;
					found = true;
				}
				else if(processes.get(j).getServiceTime() == maxServiceTime) {
					if(processes.get(j).compareID(processes.get(maxIndex)) >= 0) {
						maxServiceTime = processes.get(j).getServiceTime();
						maxIndex = j;
						found = true;
					}
				}
			}
			if(found) {
				swap(maxIndex, i, processes);
			}
		}
	}

	/*
		@Input index1:	Index in List.
		@Input index2:	Index in List.
		@Input processes: List of processes.

		Description:	Swaps the process object in the list whcich are reprsented by the two given indices.
	*/
	public void swap(int index1, int index2, ArrayList<Process> processes) {
		if(index1 == index2) {
			return;
		}
		Process temp = processes.get(index1);
		processes.set(index1, processes.get(index2));
		processes.set(index2, temp);
	}

	public boolean preemptive() {
		return false;
	}

	public int getTimeQuantum(Process process) {
		// Max time allowed in the CPU is it's service time.
		return process.getServiceTime();
	}


}