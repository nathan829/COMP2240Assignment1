/*
  ProcessReleaser.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	This ProcessReleaser class is responsible for releasing processes into the
                Ready Queue when the process has 'arrived' at the current time unit.
*/

import java.util.ArrayList;

public class ProcessReleaser {

  private ArrayList<Process> waitingProcesses;	// ordered based on arrival time.
  private Time time;

  ProcessReleaser() {
    waitingProcesses = new ArrayList<>();
    time = Time.getInstance();
  }

  /*
    @Input processes: List of Process objects.

    Descrition:	One at a time the processes are added via the addProcess(p) method.
  */
  public void addProcesses(ArrayList<Process> processes) {
    for(Process p : processes) {
      addProcess(p);
    }
  }

  /*
    @Input process: A process to be added to ProcessReleaser.

    Description: Process is added to the releaser queue, inserted in order based on
                  it's arrival time.
  */
  private void addProcess(Process process) {
    for(int i = 0; i < waitingProcesses.size(); i++) {
      if(process.getArrivalTime() < waitingProcesses.get(i).getArrivalTime()) {
        waitingProcesses.add(i, process);
        return;
      }
    }

    // Otherwise we append the process.
    waitingProcesses.add(process);
  }

  /*
    Description: Returns the next process in the waiting queue.
  */
  public Process releaseNextProcess() {
    if(waitingProcesses.size() == 0) {
      return null;
    }

    return waitingProcesses.remove(0);
  }

  public boolean hasProcesses() {
    return waitingProcesses.size() != 0;
  }

  /*
    Description: 	Going off the current time, it returns whether the next process has
                  'arrived' in the system or not.
  */
  public boolean hasReadyProcess() {
    if(waitingProcesses.size() == 0) {
      return false;
    }
    return waitingProcesses.get(0).getArrivalTime() <= time.getTime();
  }

}