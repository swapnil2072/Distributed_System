import java.net.InetAddress;
import java.util.Date;
import java.util.TimeZone;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class NTPClient {
    public static void main(String[] args) {
        try {
            String ntpServer = "time.nist.gov"; // NTP server address
            InetAddress address = InetAddress.getByName(ntpServer);
            long differenceBetweenEpochs = 2208988800L;

            byte[] buffer = new byte[48];
            buffer[0] = 0x1B; // NTP request header
            long requestTime = System.currentTimeMillis();
            long requestTicks = (requestTime / 1000) + differenceBetweenEpochs;
            Utils.writeLong(buffer, 40, (requestTicks << 32));

            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, 123);
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            socket.send(request);

            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            long responseTicks = Utils.readLong(buffer, 43);
            long responseTime = (System.currentTimeMillis() / 1000) + differenceBetweenEpochs;
            long serverTime = responseTicks - responseTime + requestTime;

            Date date = new Date(serverTime * 1000);
            System.out.println("NTP time: " + date.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Utils {
    static void writeLong(byte[] buffer, int offset, long value) {
        for (int i = 0; i < 8; ++i) {
            buffer[offset + i] = (byte) (value >> ((7 - i) * 8));
        }
    }

    static long readLong(byte[] buffer, int offset) {
        long value = 0;
        for (int i = 0; i < 8; ++i) {
            value = (value << 8) | (buffer[offset + i] & 0xFF);
        }
        return value;
    }
}

