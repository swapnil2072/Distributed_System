import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Echo Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Create a new thread to handle the client's communication
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();

                // Allow the server to send messages to the client
                Scanner userInput = new Scanner(System.in);
                String message;
                while (true) {
                    System.out.print("Server: Enter message to send to the client");
                    message = userInput.nextLine();

                    if ("exit".equalsIgnoreCase(message)) {
                        break;
                    }

                    clientHandler.sendMessageToClient(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println("\nReceived from client " + clientSocket.getInetAddress() + ": " + inputLine);
                // Handle client messages here if needed
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Connection closed for client " + clientSocket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToClient(String message) {
        writer.println(message);
    }
}
