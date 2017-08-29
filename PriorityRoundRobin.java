import java.util.ArrayList;

public class PriorityRoundRobin implements SchedulingAlgorithm {

	private Time time;

	PriorityRoundRobin() {
		time = Time.getInstance();
	}	

	public String getAlgorithmName() {
		return "PRR";
	}

	public void orderProcesses(ArrayList<Process> processes) {
		if (processes == null || processes.size() == 0){
				return;
		}

		int minIndex = -1;
		int minPriority = -1;
		boolean found = false;

		for(int i = processes.size()-1; i >= 0; i--) {
			found = false;
			minIndex = -1;
			minPriority = -1;
			for(int j = 0; j <= i; j++) {
				if(processes.get(j).getQueueEntryID() > minPriority) {	
					minPriority = processes.get(j).getQueueEntryID();
					minIndex = j;
					found = true;
				}
				else if(processes.get(j).getQueueEntryID() == minPriority) {
					if(processes.get(j).compareID(processes.get(minIndex)) >= 0) {
						minPriority = processes.get(j).getQueueEntryID();
						minIndex = j;
						found = true;
					}
				}
			}
			if(found) {
				swap(minIndex, i, processes);
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
		if(process.getPriority() < 3) {
			return 4;
		}
		else {
			return 2;
		}
	}

}