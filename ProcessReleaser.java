import java.util.ArrayList;

public class ProcessReleaser {

	private ArrayList<Process> waitingProcesses;	// ordered based on arrival time.
	private Time time;

	ProcessReleaser() {
		waitingProcesses = new ArrayList<>();
		time = Time.getInstance();
	}

	public void addProcesses(ArrayList<Process> processes) {
		for(Process p : processes) {
			addProcess(p);
		}
	}

	private void addProcess(Process process) {
		for(int i = 0; i < waitingProcesses.size(); i++) {
			if(process.getArrivalTime() < waitingProcesses.get(i).getArrivalTime()) {
				waitingProcesses.add(i, process);
				return;
			}
		}

		// Append process.
		waitingProcesses.add(process);
	}

	public Process releaseNextProcess() {
		if(waitingProcesses.size() == 0) {
			return null;
		}

		return waitingProcesses.remove(0);
	}

	public boolean hasProcesses() {
		return waitingProcesses.size() != 0;
	}

	public boolean hasReadyProcess() {
		if(waitingProcesses.size() == 0) {
			return false;
		}
		return waitingProcesses.get(0).getArrivalTime() <= time.getTime();
	}

}