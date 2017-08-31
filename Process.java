/*
  Process.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	A Process object which holds history data about where it has been
                and the times at which these events occured.
*/

import java.util.ArrayList;

public class Process {

  private final String ID;

  private int priority;
  private int serviceTime;
  private int arrivalTime;
  
  private int timeEnteredReadyQueue;
  private int timeCompleted;

  // Processor time events.
  private ArrayList<Integer> timesEnteredCPU;
  private ArrayList<Integer> timesLeftCPU;
  private int totalTimeInCPU;

  // A unique numerical ID given on entry to ready queue. Serves as
  // a way of determining which process entered the queue first.
  private int queueEntryID;

  Process(String id) {
    ID = id;
    serviceTime = 0;
    arrivalTime = 0;
    priority = 5;       // Lowest priority as default.

    totalTimeInCPU = 0;
    timesEnteredCPU = new ArrayList<>();
    timesLeftCPU = new ArrayList<>();
    timeCompleted = -1;
    queueEntryID = -1;
  }

  public void setQueueEntryID(int id) {
    queueEntryID = id;
  } 

  public int getQueueEntryID() {
    return queueEntryID;
  }

  public void setTimeEnteredCPU(int timeEnteredCPU) {
    timesEnteredCPU.add(timeEnteredCPU);
  }

  public int getTimeLastEnteredCPU() {
    return timesEnteredCPU.get(timesEnteredCPU.size()-1);
  }

  public ArrayList<Integer> timesEnteredCPU() {
    return timesEnteredCPU;
  }

  public void setTimeLeftCPU(int timeLeftCPU) {
    timesLeftCPU.add(timeLeftCPU);
  }

  public ArrayList<Integer> timesLeftCPU() {
    return timesLeftCPU;
  }

  public void setTimeEnteredReadyQueue(int time) {
    timeEnteredReadyQueue = time;
  }

  public int getTimeEnteredReadyQueue() {
    return timeEnteredReadyQueue;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public void setArrivalTime(int arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public int getArrivalTime() {
    return arrivalTime;
  }

  public void setTimeCompleted(int timeCompleted) {
    this.timeCompleted = timeCompleted;
  }

  public int getTimeCompleted() {
    return timeCompleted;
  }

  public void setServiceTime(int serviceTime) {
    this.serviceTime = serviceTime;
  }

  public int getServiceTime() {
    return serviceTime;
  }

  public String getID() {
    return ID;
  }

  public void incrementTotalTimeInCPU(int increment) {
    totalTimeInCPU += increment;
  } 

  public int timeInCPU() {
    return totalTimeInCPU;
  }

  /*
    @Input process: A Process object.

    Description:  Process ID's are in the form P1, P2, ... etc. This method compares them 
                  based on their numerical value. Returns -1 if this process comes before
                  the process given as parameter, 1 if the other way around and 0 if the
                  processes have the same ID (which should not happen unless the same 
                  reference).
  */
  public int compareID(Process process) {
    if(Integer.parseInt(ID.substring(1)) < Integer.parseInt(process.getID().substring(1))) {
      return -1;
    }
    else if(Integer.parseInt(ID.substring(1)) < Integer.parseInt(process.getID().substring(1))) {
      return 1;
    }
    return 0;
  }

  public boolean completed() {
    return serviceTime - totalTimeInCPU <= 0;		// Completed when equality.
  }

}