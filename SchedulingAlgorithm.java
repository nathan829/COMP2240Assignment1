/*
  SchedulingAlgorithm.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

	Description: 	Provides an interface which can fully describe an algorithm for the dispatching of processes.
*/

import java.util.ArrayList;

public interface SchedulingAlgorithm {

	abstract String getAlgorithmName();

	/*
		@Input readyProcesses: A reference to a List of Process objects.

		Description:	Orders the processes according to the definition of the scheduling algorithm.
	*/
	abstract void orderProcesses(ArrayList<Process> readyProcesses);

	/*	
		@Input process: A Process object.

		Description: Returns the maximum time the process should remain in the processor/CPU.
	*/
	abstract int getTimeQuantum(Process process);

	/*
		Description: Returns whether this algorithm requires preemptive actions are to be taken.
	*/
	abstract boolean preemptive();

}