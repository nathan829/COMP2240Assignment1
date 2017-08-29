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
		return process.getServiceTime();
	}


}