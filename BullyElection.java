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
            System.out.println("4. Send a Message to Coordinator");
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
                    sendMessageToCoordinator(processes);
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
        Process initiatingProcess = processes.get(initiatingProcessID - 1);

        for (Process process : processes) {
            if (process.processID > initiatingProcessID && process.active) {
                System.out.println("Process " + initiatingProcessID + " initiates an election.");
                highestProcessID = initiatingProcessID;

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
                if (highestProcessID == initiatingProcessID) {
                    System.out.println("Process " + initiatingProcessID + " is elected as the leader.");
                } else {
                    System.out.println("Process " + highestProcessID + " is elected as the leader.");
                }

                break;
            }
        }
    }

    public static void sendMessageToCoordinator(ArrayList<Process> processes) {
        for (Process process : processes) {
            if (process.active && process.processID == findCoordinator(processes)) {
                System.out.println("Message sent to Coordinator (Process " + process.processID + ") " );
                return;
            }
        }
        System.out.println("No coordinator found to send the message.");
    }

    public static int findCoordinator(ArrayList<Process> processes) {
        int highestProcessID = -1;
        for (Process process : processes) {
            if (process.active && process.processID > highestProcessID) {
                highestProcessID = process.processID;
            }
        }
        return highestProcessID;
    }
}
