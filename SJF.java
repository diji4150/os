import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int id, arrivalTime, burstTime, waitingTime, turnaroundTime, completionTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}

public class SJF {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];

        // Input arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();

            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            int burstTime = scanner.nextInt();

            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort processes by arrival time
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        int totalWaitingTime = 0, totalTurnaroundTime = 0;

        // Process scheduling based on SJF after arrival
        for (int i = 0; i < n; i++) {
            // Find the process with the shortest burst time from those that have arrived
            int idx = -1;
            int minBurst = Integer.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (processes[j].arrivalTime <= currentTime && processes[j].completionTime == 0 && processes[j].burstTime < minBurst) {
                    minBurst = processes[j].burstTime;
                    idx = j;
                }
            }

            if (idx == -1) { // If no process has arrived yet
                currentTime++;
                i--;
                continue;
            }

            // Process execution
            Process currentProcess = processes[idx];
            currentProcess.waitingTime = currentTime - currentProcess.arrivalTime;
            currentTime += currentProcess.burstTime;
            currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;
            currentProcess.completionTime = currentTime;

            totalWaitingTime += currentProcess.waitingTime;
            totalTurnaroundTime += currentProcess.turnaroundTime;
        }

        // Output the results
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time\tCompletion Time");
        for (Process process : processes) {
            System.out.println("P" + process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime
                    + "\t\t" + process.waitingTime + "\t\t" + process.turnaroundTime + "\t\t" + process.completionTime);
        }

        System.out.println("\nAverage Waiting Time: " + (float) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (float) totalTurnaroundTime / n);
    }
}
