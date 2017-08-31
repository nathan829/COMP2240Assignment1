/*
  FirstComeFirstServed.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	An implementation of a Scheduling Algorithm as well as Preemptive Algorithm which preempts
                on higher priority processes.
*/

import java.util.ArrayList;

public class PreemptivePriority implements SchedulingAlgorithm, PreemptiveAlgorithm {

  PreemptivePriority() {}

  public boolean preemptive() {
    return true;											
  }

  public String getAlgorithmName() {
    return "PP";
  }

  public boolean preemptProcess(Process cpuProcess, Process candidateProcess) {
    // Higher value means less priority. 0 is the highest priority.
    return cpuProcess.getPriority() > candidateProcess.getPriority();
  }

  public int getTimeQuantum(Process process) {
    // Max time in processor is the remaining time that the process needs to finish.
    return process.getServiceTime() - process.timeInCPU();
  }

  public Process selectNextProcess(ArrayList<Process> processes) {
    if (processes.isEmpty()){
        return null;
    }

    int minIndex = -1;
    int minPriority = 10;   // Any number larger than 5.

    // Iterated through and find the process witht he lowest priority value (highest priority process).
    for(int i = 0; i < processes.size(); i++) {
      if(processes.get(i).getPriority() < minPriority) {	
        minPriority = processes.get(i).getPriority();
        minIndex = i;
      }
      else if(processes.get(i).getPriority() == minPriority) {
        // Choose the proces who first entered the queue.
        if(processes.get(i).getQueueEntryID() < processes.get(minIndex).getQueueEntryID()) {
          minPriority = processes.get(i).getPriority();
          minIndex = i;
        }
      }
    }

    if(minIndex == -1) {
      return null;
    }
    else {
      return processes.get(minIndex);
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

}