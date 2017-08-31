/*
  Processor.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

	Description: 	A Processor/CPU object which is able to hold a process, add/remove processes
								and increment the time in which a process has spent in the processor.
*/

public class Processor {

	private boolean occupied;
	private Process process;

	private Time time;

	Processor() {
		occupied = false;
		process = null;

		time = Time.getInstance();
	}

	public boolean isOccupied() {
		return occupied;
	}

	/*
		@Input process: Process to be added to the processor.

		Description: Process is added to the processor, records the time entered and sets the processor as occupied.
	*/
	public void pushProcess(Process process) {
		if(process == null) {
			occupied = false;
			return;
		}

		this.process = process;
		this.process.setTimeEnteredCPU(time.getTime());
		occupied = true;
	}

	/*
		@Input increment: Non-negative integer.

		Description: 	For the process currently in the processor, the time the processor has spent in the processor
									is recorded/incremented.
	*/
	public void incrementProcessTime(int increment) {
		if(process != null) {
			process.incrementTotalTimeInCPU(increment);
		}
	}

	public Process peekProcess() {
		return process;
	}

	/*
		Description: Returns and removes process from the processor, records the time exited and sets as not occupied.
	*/
	public Process popProcess() {
		Process returning = process;

		process = null;
		occupied = false;

		returning.setTimeLeftCPU(time.getTime());

		return returning;
	}

}