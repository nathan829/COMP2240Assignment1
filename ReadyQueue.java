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

  ReadyQueue(SchedulingAlgorithm algorithm, ProcessReleaser processReleaser) {
    this.algorithm = algorithm;
    this.processReleaser = processReleaser;

    time = Time.getInstance();		// Time singleton.

    processes = new ArrayList<>();
  }

  /*
    Description:  Returns the next process, using the selection function defined by the
                  scheduling algorithm.
  */
  public Process nextProcess() {
    Process nextProcess = algorithm.selectNextProcess(processes);
    processes.remove(nextProcess);
    return nextProcess;
  }

  /*	
    Description: 	Adds a process to the ready queue, unordered.
  */
  public void addProcess(Process process) {
    process.setQueueEntryID(Identifier.getID());
    processes.add(process);
  }

  /*	
    Description: 	For the current time unit, all processes that are ready to be released are 
                  fetched from the process releaser, keeping the readyQueue up-to-date.
  */
  public void fetchNewProcesses() {
    while(processReleaser.hasReadyProcess()) {
      Process fetched = processReleaser.releaseNextProcess();

      fetched.setQueueEntryID(Identifier.getID());

      fetched.setTimeEnteredReadyQueue(time.getTime());
      processes.add(fetched);
    }
  }

  public boolean isEmpty() {
    return processes.isEmpty();
  }

  public Process peekNextProcess() {
    return algorithm.selectNextProcess(processes);
  }

  public boolean moreProcessesToRelease() {
    return processReleaser.hasProcesses();
  }

}