import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("Connected to Echo Server");

            // Create a thread to read and display messages from the server
          Thread serverReaderThread = new Thread(() -> {
    try (BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
        String serverResponse;
        while ((serverResponse = serverReader.readLine()) != null) {
            System.out.println("\nReceived from server: " + serverResponse);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
});
serverReaderThread.start();

Scanner scanner = new Scanner(System.in);
String message;

while (true) {
    System.out.print("Enter message to send to server (type 'exit' to quit): ");
    message = scanner.nextLine();

    if ("exit".equalsIgnoreCase(message)) {
        break;
    }

    writer.println(message); // Send message to the server
}

            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}