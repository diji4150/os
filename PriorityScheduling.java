import java.util.Scanner;

class Process {
    int pid; // Process ID
    int arrivalTime;
    int burstTime;
    int priority;
    int completionTime;
    int waitingTime;
    int turnAroundTime;

    Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter arrival time, burst time and priority for process " + (i + 1) + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            int priority = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime, priority);
        }

        // Sort processes by priority (higher priority first), then by arrival time if same priority
        java.util.Arrays.sort(processes, (p1, p2) -> {
            if (p1.priority == p2.priority) {
                return Integer.compare(p1.arrivalTime, p2.arrivalTime);
            } else {
                return Integer.compare(p2.priority, p1.priority); // Higher priority first
            }
        });

        int currentTime = 0;
        double totalTurnaroundTime = 0, totalWaitingTime = 0;

        for (Process p : processes) {
            // If process arrives after the current time, move time forward to process arrival time
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }

            p.completionTime = currentTime + p.burstTime;
            p.turnAroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;

            // Update total time counters for averages
            totalTurnaroundTime += p.turnAroundTime;
            totalWaitingTime += p.waitingTime;

            currentTime = p.completionTime; // Update current time
        }

        System.out.println("\nProcess\tArrival\tBurst\tPriority\tCompletion\tTurnaround\tWaiting");
        for (Process p : processes) {
            System.out.printf("%d\t%d\t%d\t%d\t\t%d\t\t%d\t\t%d\n", p.pid, p.arrivalTime, p.burstTime, p.priority,
                    p.completionTime, p.turnAroundTime, p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTurnaroundTime / n);
        System.out.printf("Average Waiting Time: %.2f\n", totalWaitingTime / n);

        sc.close();
    }
}
