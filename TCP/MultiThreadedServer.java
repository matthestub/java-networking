import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {

    public static int count;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(3333)) {

            while (true) {
                Socket socket = serverSocket.accept();
                new ServerHelper(socket, ++count).start();
            }

        } catch (IOException e) {
            System.out.println("Sth went wrong while opening server socket: "+e.getMessage());
        }

    }
}
