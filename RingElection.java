import java.util.Scanner;
import java.util.ArrayList;

class Process{
	int processId;
	boolean active;
	
	Process(int processId){
		this.processId = processId;
		this.active = true;
	}
}

public class RingElection {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number of processes:");
		int noOfProcess = in.nextInt();
		
		ArrayList<Process> processes = new ArrayList<>();
		for(int i = 1 ; i < noOfProcess + 1 ; i++) {
			Process process  = new Process(i);
			processes.add(process);
		}
		
		while(true) {
			System.out.println("1.kill process");
			System.out.println("2.revive process");
			System.out.println("3.start Election ");
			System.out.println("4.send Message to process");
			System.out.println("5.exit");
			
			System.out.println("Enter the choice:");
			int choice = in.nextInt();
			
			switch(choice) {
			case 1 : 	System.out.println("Enter the id of process to kill");
						int processToKill = in.nextInt();
						processes.get(processToKill).active = false;
						System.out.println("process "+ processToKill + "has been killed");
						break;
			case 2 : 	System.out.println("Enter the id of process to revive");
						int processToRevive = in.nextInt();
						processes.get(processToRevive).active = true;
						System.out.println("process "+ processToRevive + "has been revived");
				         break;
			case 3 : 	System.out.println("Enter the Process Id initiating the election:");
						int processIdStartingElection = in.nextInt();
						startElection(processes,processIdStartingElection);
						break;
			case 4 : 	System.out.println("Enter the Id of process to send message");
						int recieverId = in.nextInt();
						if(processes.get(recieverId).active) {
							System.out.println("message send to process:" + recieverId);
						}else {
							System.out.println("process " + recieverId + "is not active");
						}
						break;
			case 5 : 	System.exit(0);
						break;
			default : System.out.println("enter valid choice");
						break;
			}
		}
	}
	private static void startElection(ArrayList<Process> processes, int electionInitiatingId) {
	    System.out.println("Process " + electionInitiatingId + " started election.");
	    int highestProcessId = electionInitiatingId;
	    int currentProcessId = electionInitiatingId;
	    ArrayList<Process> list = new ArrayList<>();

	    do {
	        Process currentProcess = processes.get(currentProcessId);
	        if (currentProcess.active) {
	            list.add(currentProcess);
	            int nextProcessID = (currentProcessId % (processes.size()-1)) + 1;
	            System.out.println("Election message sent from Process " + currentProcessId + " to Process " + nextProcessID);
	            currentProcessId = nextProcessID;
	        } else {
	            currentProcessId = (currentProcessId % (processes.size()-1)) + 1;
	        }
	    } while (currentProcessId != electionInitiatingId);

	    System.out.print("List of processes: ");
	    for (Process process : list) {
	        System.out.print(process.processId + " ");
	        if (process.processId > highestProcessId) {
	            highestProcessId = process.processId;
	        }
	    }
	    
	    System.out.println("\nProcess " + highestProcessId + " elected as the new leader.");
	}

	
	}