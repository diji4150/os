import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

class MyProcess {
    int pid; // Process ID
    int arrivalTime;
    int burstTime;
    int remainingTime; // Remaining burst time for round robin
    int completionTime;
    int waitingTime;
    int turnAroundTime;

    // Constructor
    public MyProcess(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class RoundRobinScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        System.out.print("Enter the time quantum: ");
        int timeQuantum = sc.nextInt();

        MyProcess[] processes = new MyProcess[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            processes[i] = new MyProcess(i + 1, arrivalTime, burstTime);
        }

        // Initialize variables
        Queue<MyProcess> queue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        double totalTurnaroundTime = 0, totalWaitingTime = 0;

        // Add processes to queue based on arrival time and process them in round-robin
        while (completed < n) {
            // Add all arrived processes to the queue
            for (MyProcess p : processes) {
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && !queue.contains(p)) {
                    queue.add(p);
                }
            }

            // Process the next process in the queue
            MyProcess currentProcess = queue.poll();
            if (currentProcess != null) {
                int timeToExecute = Math.min(timeQuantum, currentProcess.remainingTime);
                currentTime += timeToExecute;
                currentProcess.remainingTime -= timeToExecute;

                // If the process finishes, calculate its completion, turnaround, and waiting times
                if (currentProcess.remainingTime == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;

                    totalTurnaroundTime += currentProcess.turnAroundTime;
                    totalWaitingTime += currentProcess.waitingTime;
                    completed++;
                } else {
                    // If process is not finished, add it back to the queue
                    queue.add(currentProcess);
                }
            } else {
                currentTime++; // Idle time if no process is available
            }
        }

        // Display process details
        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (MyProcess p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\n", p.pid, p.arrivalTime, p.burstTime, p.completionTime,
                    p.turnAroundTime, p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTurnaroundTime / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWaitingTime / n);

        sc.close();
    }
}
