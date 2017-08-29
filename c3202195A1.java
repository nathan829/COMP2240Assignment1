/*
	Author: Nathan Anstess
	Student Number: c3202195
	Date created: 24/08/17
	Last updated: 29/05/17

	Description: 	Entry component to the program. Reads in the name of an input file, converts this
								file to a list of process objects and simulates them in an operating system 
								environment under different scheduling algorithms, then outputting the statistical
								results.
*/

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class c3202195A1 {

	public static void main(String[] args) {
		String fileName = "";

		try {
			fileName = args[0];
		}  
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("ERROR: Enter the file name in command line.");
			System.exit(-1);
		} 
		
		c3202195A1 entryComponent = new c3202195A1();

		try {
			entryComponent.run(fileName);
		}
		catch(Exception e) {
			System.out.println("Error occurred in program exection.");
			e.printStackTrace();
		}
		
		System.exit(0);
	}

	/*
		@Input fileName: Name of input file.

		Description: 	Controls flow of program. Reads the file and generates it's objects and then
									passes this data on to another method to build and run the simultation.
									Outputting the results.
	*/
	private void run(String fileName) {
		ArrayList<SchedulingAlgorithm> algorithms = new ArrayList<>();
		algorithms.add(new FirstComeFirstServed());
		algorithms.add(new ShortestProcessNext());
		algorithms.add(new PreemptivePriority());
		algorithms.add(new PriorityRoundRobin());

		// ith average time for the ith scheduling algorithm above.
		ArrayList<Float> averageTurnAroundTimes = new ArrayList<>();
		ArrayList<Float> averageWaitingTimes = new ArrayList<>();

		String results = "";

		try {
			for(SchedulingAlgorithm algorithm : algorithms) {
				Scanner scanner = new Scanner(new File(fileName));
				ArrayList<Object> dispatcherAndProcesses = readDispatcherAndProcesses(scanner);
				
				// Dispatcher is first object in the dispatcherAndProcesses array.
				Dispatcher dispatcher = (Dispatcher)dispatcherAndProcesses.remove(0);

				// Processes are the remaining objects in the dispatcherAndProcesses array.
				ArrayList<Process> processes = new ArrayList<>();
				int arrSize = dispatcherAndProcesses.size();
				for(int i = 0; i < arrSize; i++) {
					processes.add((Process)dispatcherAndProcesses.remove(0));
				}
				
				results += buildAndRunSystem(dispatcher, processes, algorithm);

				averageTurnAroundTimes.add(calculateAverageTurnAroundTime(processes));
				averageWaitingTimes.add(calculateAverageWaitingTime(processes));
			}

			results += getSummary(algorithms, averageTurnAroundTimes, averageWaitingTimes);
		} 
		catch(FileNotFoundException e) {
			System.out.println("ERROR: File not found.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		// If we wish to write to a file instead.
		// writeToFile("output.txt", results);

		System.out.println(results);
	}

	private void writeToFile(String fileName, String results) {
		try {
			File file = new File(fileName);

			PrintWriter fileWriter = new PrintWriter(new FileOutputStream(file, false));
			fileWriter.println(results);
			fileWriter.close();
		}	
		catch(Exception e) {
			System.out.println("ERROR: could not write to file " + fileName);
		}
	}

	private String getSummary(ArrayList<SchedulingAlgorithm> algs, ArrayList<Float> tts, ArrayList<Float> wts) {
		String summary = "Summary\n";
		summary += "Algorithm       Average Turnaround Time   Average Waiting Time\n";
		int algLength = 16;
		int ttLength = 26;

		for(int i = 0; i < algs.size(); i++) {
			summary += formatAlgorithmTimes(algs.get(i).getAlgorithmName(), tts.get(i), wts.get(i)) + "\n";
		}
		return summary;
	}

	private String formatAlgorithmTimes(String name, float tt, float wt) {
		DecimalFormat df = new DecimalFormat("#.00");
		// System.out.println(df.format(decimalNumber));
		int algLength = 16;
		int ttLength = 26;
		String ttString = df.format(tt);
		String wtString = df.format(wt);
		return name + spaces(algLength-name.length()) + ttString + spaces(ttLength-ttString.length()) + wtString;
	}

	private float calculateAverageWaitingTime(ArrayList<Process> processes) {
		int sum = 0;
		for(Process process : processes) {
			int tt = process.getTimeCompleted()-process.getArrivalTime();
			sum += (tt - process.timeInCPU());
		}

		return (float)sum/processes.size();
	}

	private float calculateAverageTurnAroundTime(ArrayList<Process> processes) {
		int sum = 0;
		for(Process process : processes) {
			sum += process.getTimeCompleted()-process.getArrivalTime();
		}

		return (float)sum/processes.size();
	}

	private ArrayList<Object> readDispatcherAndProcesses(Scanner scanner) {
		if(!scanner.hasNext() || !scanner.next().equalsIgnoreCase("begin")) {
			return null;
		}

		String term = "";
		ArrayList<Object> arr = new ArrayList<>();
		
		if(scanner.hasNext() && (term = scanner.next()).equalsIgnoreCase("disp:")) {
			int runningTime = scanner.nextInt();
			if(scanner.hasNext() && (term = scanner.next()).equalsIgnoreCase("end")) {
				arr.add(new Dispatcher(runningTime));
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}

		Process process = null;

		while(scanner.hasNext() && !(term = scanner.next()).equalsIgnoreCase("eof")) {
			if(term.equalsIgnoreCase("id:")) {
				process = new Process(scanner.next());
			}
			else if(term.equalsIgnoreCase("end") && (process != null)) {
				arr.add(process);
				process = null;
			}
			else if(term.equalsIgnoreCase("arrive:")) {
				if(process == null) {
					return null;
				}
				process.setArrivalTime(scanner.nextInt());
			}
			else if(term.equalsIgnoreCase("execsize:")) {
				if(process == null) {
					return null;
				}
				process.setServiceTime(scanner.nextInt());
			}
			else if(term.equalsIgnoreCase("priority:")) {
				if(process == null) {
					return null;
				}
				process.setPriority(scanner.nextInt());
			}
		}

		return arr;
	}
		
	private String buildAndRunSystem(Dispatcher dispatcher, ArrayList<Process> processes, SchedulingAlgorithm algorithm) {
		// Process releaser.
		ProcessReleaser processReleaser = new ProcessReleaser();
		processReleaser.addProcesses(processes);
		
		// Initialise ready queue with algorithm.
		ReadyQueue readyQueue = new ReadyQueue(algorithm, processReleaser);
		dispatcher.setReadyQueue(readyQueue);
		dispatcher.setSchedulingAlgorithm(algorithm);

		// Initialise processor.
		Processor processor = new Processor();
		dispatcher.setProcessor(processor);

		Time.getInstance().resetTime();
		dispatcher.run();

		// Output results.
		return formatOutput(processor.getCompletedProcesses(), algorithm.getAlgorithmName());
	}

	private String formatOutput(ArrayList<Process> processes, String algorithmName) {
		String results = "";
		// Order processes based on ID. Basic bubble sort.
		boolean continuing = true;
		while(continuing) {
			continuing = false;
			for(int i = 1; i < processes.size(); i++) {
				if(processes.get(i-1).compareID(processes.get(i)) >= 0) {
					Process temp = processes.get(i);
					processes.set(i, processes.get(i-1));
					processes.set(i-1, temp);
					continuing = true;
				}
			}
		}

		results += algorithmName + ":\n";
		int numberOftimeEntered = getNumberOfTimesEntered(processes);
		for(int i = 0; i < numberOftimeEntered; i++) {
			int minTime = Integer.MAX_VALUE;
			int minIndex = -1;
			Process minProcess = null;
			for(int j = 0; j < processes.size(); j++) {
				ArrayList<Integer> cpuEntryTimes = processes.get(j).timesEnteredCPU();
				for(int k = 0; k < cpuEntryTimes.size(); k++) {
					if(cpuEntryTimes.get(k) < minTime) {
						minTime = cpuEntryTimes.get(k);
						minIndex = k;
						minProcess = processes.get(j);
					}
				}
			}
			results += "T" + minTime + ": " + minProcess.getID() + "(" + minProcess.getPriority() + ")\n";
			minProcess.timesEnteredCPU().remove(minIndex);
		}

		results += "\nProcess Turnaround Time Waiting Time\n";
		for(int i = 0; i < processes.size(); i++) {
			int tut = processes.get(i).getTimeCompleted()-processes.get(i).getArrivalTime();
			results += formatProcessTimes(processes.get(i).getID(), "" + tut, "" + (tut-processes.get(i).timeInCPU())) + "\n";
		}

		return results + "\n";
	}

	public String formatProcessTimes(String id, String tt, String wt) {
		int idLength = 8;
		int ttLength = 16;
		return id + spaces(idLength-id.length()) + tt + spaces(ttLength-tt.length()) + wt;
	}

	public String spaces(int quantity) {
		String str = "";
		for(int i = 0; i < quantity; i++) {
			str += " ";
		}
		return str;
	}
	
	private int getNumberOfTimesEntered(ArrayList<Process> processes) {
		int sum = 0;
		for(Process process : processes) {
			sum += process.timesEnteredCPU().size();
		}
		return sum;
	}

}