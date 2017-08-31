/*
  FirstComeFirstServed.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	An implementation of a Scheduling Algorithm which represents a very basic first come 
                first served dispatching algorithm.
*/

import java.util.ArrayList;

public class FirstComeFirstServed implements SchedulingAlgorithm {

  FirstComeFirstServed() {}

  /*
    Description: FCFS just chooses the first process to enter the queue.
  */
  public Process selectNextProcess(ArrayList<Process> processes) {
    if(processes.isEmpty()) {
      return null;
    }

    return processes.get(0);
  }

  public boolean preemptive() {
    return false;
  }

  public int getTimeQuantum(Process process) {
    // Max time allowed in the CPU is it's service time.
    return process.getServiceTime();
  }

  public String getAlgorithmName() {
    return "FCFS";
  }

}