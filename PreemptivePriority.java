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

  public void orderProcesses(ArrayList<Process> processes) {
    if (processes == null || processes.size() == 0){
        return;
    }

    int minIndex = -1;
    int minPriority = -1;
    boolean found = false;

    // Selection sort to the order the processes based on their priorities. 
    for(int i = processes.size()-1; i >= 0; i--) {
      found = false;
      minIndex = -1;
      minPriority = -1;
      for(int j = 0; j <= i; j++) {
        if(processes.get(j).getPriority() > minPriority) {	
          minPriority = processes.get(j).getPriority();
          minIndex = j;
          found = true;
        }
        else if(processes.get(j).getPriority() == minPriority) {
          if(processes.get(j).compareID(processes.get(minIndex)) >= 0) {
            minPriority = processes.get(j).getPriority();
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