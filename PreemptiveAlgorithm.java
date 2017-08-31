/*
  PreemptiveAlgorithm.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	A Preemptive Algorithm interface which describes how preemption will occur.
*/

public interface PreemptiveAlgorithm {

  /*
    @Input cpuProcess:	Process currently in the processor/CPU.
    @Input candidateProcess:	Process that has the potential to preempt.

    Description: 	Compares the two processes and determines whether the candidate process
                  should preempt the cpu process.
  */
  abstract boolean preemptProcess(Process cpuProcess, Process candidateProcess);

}