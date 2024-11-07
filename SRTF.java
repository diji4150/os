import java.util.Scanner;

class Process {
    int id, arrivalTime, burstTime, remainingTime, waitingTime, turnaroundTime, completionTime;
    boolean isCompleted; // This field is correctly defined

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime; // Initialize remaining time as burst time
        this.isCompleted = false; // Initialize isCompleted as false
    }
}

public class SRTF {
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

        int currentTime = 0, completed = 0;
        int totalWaitingTime = 0, totalTurnaroundTime = 0;

        // Main loop runs until all processes are completed
        while (completed != n) {
            // Find the process with the shortest remaining time that has arrived
            int idx = -1;
            int minRemainingTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                // Check if process has arrived, not completed, and has the shortest remaining time
                if (processes[i].arrivalTime <= currentTime && !processes[i].isCompleted && processes[i].remainingTime < minRemainingTime) {
                    minRemainingTime = processes[i].remainingTime;
                    idx = i;
                }
            }

            if (idx == -1) { // No process is ready to execute at current time
                currentTime++;
                continue;
            }

            // Process execution (preemptive)
            Process currentProcess = processes[idx];
            currentProcess.remainingTime--;

            // If the process is completed
            if (currentProcess.remainingTime == 0) {
                currentProcess.isCompleted = true; // Mark as completed
                completed++;
                currentProcess.completionTime = currentTime + 1;
                currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                totalWaitingTime += currentProcess.waitingTime;
                totalTurnaroundTime += currentProcess.turnaroundTime;
            }

            currentTime++;
        }

        // Output the results
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time\tCompletion Time");
        for (Process process : processes) {
            System.out.println("P" + process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime
                    + "\t\t" + process.waitingTime + "\t\t" + process.turnaroundTime + "\t\t" + process.completionTime);
        }

        // Display average waiting and turnaround time
        System.out.println("\nAverage Waiting Time: " + (float) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (float) totalTurnaroundTime / n);

        scanner.close();
    }
}
