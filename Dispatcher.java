public class Dispatcher {

	private int dispatcherSwitchingTime;
	private ReadyQueue readyQueue;
	private SchedulingAlgorithm algorithm;
	private Processor processor;

	private Time time;

	Dispatcher(int dispatcherSwitchingTime) {
		this.dispatcherSwitchingTime = dispatcherSwitchingTime;
		readyQueue = null;
		algorithm = null;
		processor = null;
		time = Time.getInstance();
	}

	public void setSchedulingAlgorithm(SchedulingAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	public void addNextProcessToProcessor() {
		processor.pushProcess(readyQueue.nextProcess());
	}

	public void run() {
		while(processor.isOccupied() || !readyQueue.isEmpty() || readyQueue.moreProcessesToRelease()) {
			readyQueue.fetchNewProcesses();

			boolean stillOperating = true;

			while(stillOperating) {
				stillOperating = false;

				if(!processor.isOccupied()) {
					if(!readyQueue.isEmpty()) {
						time.incrementTime(dispatcherSwitchingTime);
						processor.pushProcess(readyQueue.nextProcess());
						stillOperating = true;
					}
				}
				else {
					if(processor.processFinished()) {
						processor.addToCompleted();
						stillOperating = true;
					}
					else if(algorithm.preemptive()) {
						if(processor.peekProcess().getTimeLastEnteredCPU() != time.getTime()) {
							if(!readyQueue.isEmpty()) {
								if(((PreemptiveAlgorithm)algorithm).preemptProcess(processor.peekProcess(),readyQueue.peekNextProcess())) {
									Process removed = processor.popProcess();
									readyQueue.addProcess(removed);
									stillOperating = true;
								}
							}
						}
					}
					else {
						if(time.getTime() - processor.peekProcess().getTimeLastEnteredCPU() >= algorithm.getTimeQuantum(processor.peekProcess())) {
							Process removed = processor.popProcess();
							readyQueue.addProcess(removed);
							stillOperating = true;
						}
					}
				}
			}

			time.incrementTime(1);
			processor.incrementProcessTime(1);
		}
	}

	public void setReadyQueue(ReadyQueue readyQueue) {
		this.readyQueue = readyQueue;
	}

}