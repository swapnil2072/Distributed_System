import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())
        ) {
            // Specify the method and its parameters to be called on the server
            output.writeObject("performOperation");
            output.writeObject("Client data");

            // Receive and print the result from the server
            String result = (String) input.readObject();
            System.out.println("Result from server: " + result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
