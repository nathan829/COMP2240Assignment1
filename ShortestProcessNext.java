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

  public Process selectNextProcess(ArrayList<Process> processes) {
    if (processes.isEmpty()){
        return null;
    }

    int minIndex = -1;
    int minServiceTime = Integer.MAX_VALUE;

    // Selection sort to order processes based on service time.
    for(int i = 0; i < processes.size(); i++) {
      if(processes.get(i).getServiceTime() < minServiceTime) {	
        minServiceTime = processes.get(i).getServiceTime();
        minIndex = i;
      }
      else if(processes.get(i).getServiceTime() == minServiceTime) {
        if(processes.get(i).getQueueEntryID() < processes.get(minIndex).getQueueEntryID()) {
          // Choose the proces who first entered the queue.
          minServiceTime = processes.get(i).getServiceTime();
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

  public boolean preemptive() {
    return false;
  }

  public int getTimeQuantum(Process process) {
    // Max time allowed in the CPU is it's service time.
    return process.getServiceTime();
  }


}