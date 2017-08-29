import java.util.ArrayList;

public class Processor {

	private boolean occupied;
	private Process process;

	private ArrayList<Process> completedProcesses;

	private Time time;

	Processor() {
		occupied = false;
		completedProcesses = new ArrayList<>();
		time = Time.getInstance();
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void pushProcess(Process process) {
		if(process == null) {
			System.out.println("Pushed process is null");
			occupied = false;
			return;
		}
		this.process = process;
		this.process.setTimeEnteredCPU(time.getTime());
		occupied = true;
	}

	public void addToCompleted() {
		if(!processFinished()) {
			return;
		}

		occupied = false;
		process.setTimeCompleted(time.getTime());
		completedProcesses.add(process);
		process = null;
	}

	public void incrementProcessTime(int increment) {
		if(process != null) {
			process.incrementTotalTimeInCPU(increment);
		}
	}

	public boolean processFinished() {
		if(!occupied) {
			return false;	
		}
		return process.getServiceTime() - process.timeInCPU() <= 0;	// Really equality but will leave
	}

	public Process peekProcess() {
		return process;
	}

	public ArrayList<Process> getCompletedProcesses() {
		return completedProcesses;
	}

	public Process popProcess() {
		Process returning = process;

		process = null;
		occupied = false;

		return returning;
	}

}