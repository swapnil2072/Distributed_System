import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Shared interface between client and server
interface RemoteService {
    String performOperation(String input);
}

// Server-side implementation of the RemoteService interface
class RemoteServiceImpl implements RemoteService {
    @Override
    public String performOperation(String input) {
        // Perform some operation and return the result
        return "Server processed: " + input;
    }
}


public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                // Handle client requests using a separate thread
                new Thread(new RequestHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandler implements Runnable {
        private final Socket socket;

        public RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
            ) {
                // Read the method name and parameters from the client
                String methodName = (String) input.readObject();
                String inputParameter = (String) input.readObject();

                // Invoke the appropriate method based on the method name
                RemoteService remoteService = new RemoteServiceImpl();
                String result = remoteService.performOperation(inputParameter);

                // Send the result back to the client
                output.writeObject(result);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
