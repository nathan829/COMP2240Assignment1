import java.util.ArrayList;

public class ReadyQueue {

	private ArrayList<Process> processes;
	private SchedulingAlgorithm algorithm;
	private ProcessReleaser processReleaser;

	private Time time;

	private static int queueEntryID;

	ReadyQueue(SchedulingAlgorithm algorithm, ProcessReleaser processReleaser) {
		processes = new ArrayList<>();
		this.algorithm = algorithm;
		this.processReleaser = processReleaser;
		time = Time.getInstance();
		queueEntryID = 0;
	}

	public Process nextProcess() {
		if(processes.size() == 0) {
			return null;
		}

		algorithm.orderProcesses(processes);
		return processes.remove(0);
	}

	public void addProcess(Process process) {
		process.setQueueEntryID(queueEntryID++);
		processes.add(process);
		algorithm.orderProcesses(processes);
	}

	public void fetchNewProcesses() {
		Process fetched = null;
		boolean newProcessFetched = false;

		while(processReleaser.hasReadyProcess()) {
			fetched = processReleaser.releaseNextProcess();

			fetched.setQueueEntryID(queueEntryID++);

			fetched.setTimeEnteredReadyQueue(time.getTime());
			processes.add(fetched);
			newProcessFetched = true;
		}

		if(newProcessFetched) {
			algorithm.orderProcesses(processes);
		}
	}

	public boolean isEmpty() {
		return processes.size() == 0;
	}

	public Process peekNextProcess() {
		if(processes.size() == 0) {
			return null;
		}
		return processes.get(0);
	}

	public boolean moreProcessesToRelease() {
		return processReleaser.hasProcesses();
	}

	public void displayProcesses() {
		for(int i = 0; i < processes.size(); i++) {
			System.out.println(processes.get(i));
		}
	}

}