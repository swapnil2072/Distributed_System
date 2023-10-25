import java.util.ArrayList;
import java.util.Scanner;

class Process {
    int processID;
    boolean active = true;

    Process(int processID) {
        this.processID = processID;
    }
}

public class RingElection {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        ArrayList<Process> processes = new ArrayList<>();

        for (int i = 1; i <= numProcesses; i++) {
            processes.add(new Process(i));
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Kill a Process");
            System.out.println("2. Revive a Process");
            System.out.println("3. Start an Election");
            System.out.println("4. Send a Message to Process");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter the ID of the process to kill: ");
                    int processToKill = scanner.nextInt();
                    processes.get(processToKill - 1).active = false;
                    System.out.println("Process " + processToKill + " has been killed.");
                    break;
                case 2:
                    System.out.print("Enter the ID of the process to revive: ");
                    int processToRevive = scanner.nextInt();
                    processes.get(processToRevive - 1).active = true;
                    System.out.println("Process " + processToRevive + " has been revived.");
                    break;
                case 3:
                    System.out.print("Enter the process ID initiating the election: ");
                    int initiatingProcessID = scanner.nextInt();
                    initiateElection(processes, initiatingProcessID);
                    break;
                case 4:
                    sendMessageToProcess(processes);
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

  public static void initiateElection(ArrayList<Process> processes, int initiatingProcessID) {
    int currentProcessID = initiatingProcessID;
    boolean electionMessagePassed = false;
    int highestProcessID = -1; // Initialize with a value that is not a valid process ID

    // Display the list of active processes
    System.out.print("Active processes during election: ");
    for (Process process : processes) {
        if (process.active && process.processID != initiatingProcessID) {
            System.out.print(process.processID + " ");
            highestProcessID = Math.max(highestProcessID, process.processID);
        }
    }
    System.out.println();

    do {
        Process currentProcess = processes.get(currentProcessID - 1);
        if (currentProcess.active) {
            System.out.println("Process " + currentProcessID + " initiates an election.");

            // Pass election message to the next process in the ring
            int nextProcessID = (currentProcessID % processes.size()) + 1;
            System.out.println("Election message sent from Process " + currentProcessID + " to Process " + nextProcessID);

            // Update highestProcessID
            highestProcessID = Math.max(highestProcessID, nextProcessID);

            // Update current process ID
            currentProcessID = nextProcessID;
            electionMessagePassed = true;
        } else {
            // Move to the next process in the ring
            currentProcessID = (currentProcessID % processes.size()) + 1;
        }
    } while (currentProcessID != initiatingProcessID && !electionMessagePassed);

    if (electionMessagePassed) {
        System.out.println("Election message has completed the ring.");
        System.out.println("Process " + highestProcessID + " is elected as the leader.");
    } else {
        System.out.println("No active process to initiate the election.");
    }
}



    public static void sendMessageToProcess(ArrayList<Process> processes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the process ID to send a message to: ");
        int targetProcessID = scanner.nextInt();

        Process targetProcess = processes.get(targetProcessID - 1);

        if (targetProcess.active) {
            System.out.println("Message sent to Process " + targetProcessID);
        } else {
            System.out.println("Process " + targetProcessID + " is not active. Message not sent.");
        }
    }
}
