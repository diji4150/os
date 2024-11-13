import java.util.Scanner;

public class FCFSWithArrival {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        // Input arrival and burst times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = sc.nextInt();
        }

        // Sorting based on arrival time
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrivalTime[j] > arrivalTime[j + 1]) {
                    // Swap arrival time
                    int temp = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[j + 1];
                    arrivalTime[j + 1] = temp;

                    // Swap burst time
                    temp = burstTime[j];
                    burstTime[j] = burstTime[j + 1];
                    burstTime[j + 1] = temp;
                }
            }
        }

        // Calculate completion time
        completionTime[0] = arrivalTime[0] + burstTime[0];
        for (int i = 1; i < n; i++) {
            if (arrivalTime[i] > completionTime[i - 1]) {
                completionTime[i] = arrivalTime[i] + burstTime[i];
            } else {
                completionTime[i] = completionTime[i - 1] + burstTime[i];
            }
        }

        // Calculate turnaround time and waiting time
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }

        // Display the result
        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTurnaround\tWaiting");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + arrivalTime[i] + "\t" + burstTime[i] + "\t" + completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i
        }
    }
}
