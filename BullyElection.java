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

        System.out.print("Enter the process ID initiating the election: ");
        int initiatingProcessID = scanner.nextInt();

        initiateElection(processes, initiatingProcessID);
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
}
