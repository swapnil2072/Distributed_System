import java.net.InetAddress;
import java.util.Date;
import java.util.TimeZone;

class LamportClock {
    private int time = 0;

    synchronized void tick() {
        time++;
    }

    synchronized void sendEvent() {
        time++;
    }

    synchronized void receiveEvent(int sentTime) {
        time = Math.max(time, sentTime) + 1;
    }

    synchronized int getTime() {
        return time;
    }
}

class Process {
    private final int id;
    private final LamportClock clock;

    Process(int id, LamportClock clock) {
        this.id = id;
        this.clock = clock;
    }

    void sendEvent(Process receiver) {
        System.out.println("Process " + id + " sends a message to Process " + receiver.id);
        clock.sendEvent();
        receiver.receiveEvent(clock.getTime());
    }

    void internalEvent() {
        System.out.println("Internal event in Process " + id);
        clock.tick();
    }
}

public class LamportClockExample {
    public static void main(String[] args) {
        LamportClock clock1 = new LamportClock();
        LamportClock clock2 = new LamportClock();

        Process process1 = new Process(1, clock1);
        Process process2 = new Process(2, clock2);

        process1.internalEvent();
        process1.sendEvent(process2);
        process2.internalEvent();

        System.out.println("Process 1's logical clock time: " + clock1.getTime());
        System.out.println("Process 2's logical clock time: " + clock2.getTime());
    }
}

