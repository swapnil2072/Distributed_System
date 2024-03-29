import java.util.ArrayList;
import java.util.Scanner;

class Process {
    int processID;
    boolean active = true;

    Process(int processID) {
        this.processID = processID;
    }
}

public class BullyElection {
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
    int highestProcessID = initiatingProcessID;

    System.out.println("Process " + initiatingProcessID + " initiates an election.");

    // Send election message to higher priority processes
    for (Process higherProcess : processes) {
        if (higherProcess.processID > initiatingProcessID && higherProcess.active) {
            System.out.println("Election message sent from Process " + initiatingProcessID + " to Process " + higherProcess.processID);
        }
    }

    // Handle responses
    for (Process higherProcess : processes) {
        if (higherProcess.processID > initiatingProcessID && higherProcess.active) {
            System.out.println("Process " + higherProcess.processID + " responds to Process " + initiatingProcessID);
            if (higherProcess.processID > highestProcessID) {
                highestProcessID = higherProcess.processID;
            }
        }
    }

    // Declare the new leader
   System.out.println("Process " + highestProcessID + " is elected as the leader.");

}


    public static void sendMessageToProcess(ArrayList<Process> processes) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the process ID to send a message to: ");
        int targetProcessID = scanner.nextInt();

        Process targetProcess = processes.get(targetProcessID - 1);

        if (targetProcess.active) {
            
            System.out.println("Message sent to Process " + targetProcessID  );
        } else {
            System.out.println("Process " + targetProcessID + " is not active. Message not sent.");
        }
    }
}
