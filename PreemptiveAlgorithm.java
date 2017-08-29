public interface PreemptiveAlgorithm {

	abstract boolean preemptProcess(Process cpuProcess, Process candidateProcess);

}