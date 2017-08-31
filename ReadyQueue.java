/*
  ReadyQueue.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

	Description: 	Ready Queue works closely with the Scheduling Algorithm interface to return
								processes who are next in line to be run in the processor. The Ready Queue
								is also responsible for fetching new processes from the Process Releaser 
								object. 
*/

import java.util.ArrayList;

public class ReadyQueue {

	private ArrayList<Process> processes;
	private SchedulingAlgorithm algorithm;
	private ProcessReleaser processReleaser;

	private Time time;

	private static int queueEntryID;

	ReadyQueue(SchedulingAlgorithm algorithm, ProcessReleaser processReleaser) {
		this.algorithm = algorithm;
		this.processReleaser = processReleaser;

		time = Time.getInstance();		// Time singleton.

		processes = new ArrayList<>();
		queueEntryID = 0;
	}

	/*
		Description: Returns the next process, ordered according to the scheduling algorithm.
	*/
	public Process nextProcess() {
		if(processes.size() == 0) {
			return null;
		}

		algorithm.orderProcesses(processes);
		return processes.remove(0);
	}

	/*	
		Description: 	Adds a process to the ready queue, assigned an entry ID and ordered by the
									scheduling algorithm.
	*/
	public void addProcess(Process process) {
		process.setQueueEntryID(queueEntryID++);
		processes.add(process);

		algorithm.orderProcesses(processes);
	}

	/*	
		Description: 	For the current time unit, all processes that are ready to be released are 
									fetched from the process releaser, keeping the readyQueue up-to-date.
	*/
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

		// Reorder the processes if any new processes were fetched.
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

}