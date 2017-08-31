/*
  Dispatcher.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	The dispatcher has a ready queue and a processor as member data and is
                responsible for the scheduling of processes between these two entities.
                It relies on the scheduling algorithm interface to handle alot of the 
                decision making. The dispathcer will stop running when there are no 
                more processes left in the system.
*/

import java.util.ArrayList;

public class Dispatcher {

  private int dispatcherSwitchingTime;

  private ReadyQueue readyQueue;
  private SchedulingAlgorithm algorithm;
  private Processor processor;

  private ArrayList<Process> completedProcesses;

  private Time time;

  Dispatcher(int dispatcherSwitchingTime) {
    this.dispatcherSwitchingTime = dispatcherSwitchingTime;
    readyQueue = null;
    algorithm = null;
    processor = null;
    time = Time.getInstance();

    completedProcesses = new ArrayList<>();
  }

  public void setSchedulingAlgorithm(SchedulingAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  public void setProcessor(Processor processor) {
    this.processor = processor;
  }

  /*
    Description:	While there are more processes to move around, the dispatcher will continue
                  to move processes around and incremement the time step.
  */
  public void run() {
    while(processor.isOccupied() || !readyQueue.isEmpty() || readyQueue.moreProcessesToRelease()) {
      // The process releaser will release proceses to the ready queue if any are ready to enter
      // at the present time value.
      readyQueue.fetchNewProcesses();

      boolean stillOperating = true;

      // While there are still more actions to take for the present time value.
      while(stillOperating) {
        stillOperating = false;

        if(!processor.isOccupied()) {
          if(!readyQueue.isEmpty()) {
            // If the processor is not occupied and there are process(es) in the ready queue,
            // then the next process can be moved into the processor.
            time.incrementTime(dispatcherSwitchingTime);
            processor.pushProcess(readyQueue.nextProcess());
            stillOperating = true;
          }
        }
        else {
          if(processor.peekProcess().completed()) {
            // If the processor is occupied but the process has finished, then it is
            // added to the completed process list.
            Process completed = processor.popProcess();
            completed.setTimeCompleted(time.getTime());
            completedProcesses.add(completed);
            stillOperating = true;
          }
          else if(algorithm.preemptive()) {
            if(processor.peekProcess().getTimeLastEnteredCPU() != time.getTime()) {
              if(!readyQueue.isEmpty()) {
                if(((PreemptiveAlgorithm)algorithm).preemptProcess(processor.peekProcess(),readyQueue.peekNextProcess())) {
                  // If the processor is occupied and a preemptive method, and if the next process in
                  // the ready queue needs to reempt, then the processor removes the current process 
                  // and returns to the ready queue.
                  Process removed = processor.popProcess();
                  readyQueue.addProcess(removed);
                  stillOperating = true;
                }
              }
            }
          }
          else if(time.getTime() - processor.peekProcess().getTimeLastEnteredCPU() >= algorithm.getTimeQuantum(processor.peekProcess())) {
            if(!readyQueue.isEmpty()) {
              // If the processor is occupied and has reached it's time quantum, then it is removed
              // only if there are processes waiting in the ready queue.
              Process removed = processor.popProcess();
              readyQueue.addProcess(removed);
              stillOperating = true;
            }
          }
        }
      }

      // Increment the global time and the time the process has spent in the processor.
      time.incrementTime(1);
      processor.incrementProcessTime(1);
    }
  }

  public ArrayList<Process> getCompletedProcesses() {
    return completedProcesses;
  }

  public void setReadyQueue(ReadyQueue readyQueue) {
    this.readyQueue = readyQueue;
  }

}