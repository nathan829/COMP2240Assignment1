import java.util.ArrayList;

public interface SchedulingAlgorithm {

	abstract String getAlgorithmName();

	abstract void orderProcesses(ArrayList<Process> readyProcesses);

	abstract int getTimeQuantum(Process process);

	abstract boolean preemptive();

}