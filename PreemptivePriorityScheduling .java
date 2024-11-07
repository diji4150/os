import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int pid;
    int priority;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(int pid, int priority, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class PreemptivePriorityScheduling {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Process> processes = new ArrayList<>();

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter priority for Process " + (i + 1) + ": ");
            int priority = scanner.nextInt();
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();

            processes.add(new Process(i + 1, priority, arrivalTime, burstTime));
        }

        int time = 0;
        int completed = 0;

        while (completed != numProcesses) {
            // Get processes that have arrived and are not completed
            Process currentProcess = processes.stream()
                    .filter(p -> p.arrivalTime <= time && p.remainingTime > 0)
                    .min(Comparator.comparingInt(p -> p.priority))
                    .orElse(null);

            if (currentProcess != null) {
                // Execute process for 1 unit of time
                currentProcess.remainingTime--;
                time++;

                // Check if the process is completed
                if (currentProcess.remainingTime == 0) {
                    completed++;
                    currentProcess.completionTime = time;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                }
            } else {
                time++;
            }
        }

        // Display results
        System.out.println("\nProcess\tPriority\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process process : processes) {
            System.out.println(process.pid + "\t" + process.priority + "\t\t" + process.arrivalTime + "\t\t" +
                    process.burstTime + "\t\t" + process.completionTime + "\t\t" + process.turnaroundTime + "\t\t" +
                    process.waitingTime);
        }

        scanner.close();
    }
}
