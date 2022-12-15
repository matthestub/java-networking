import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) {


        try (Socket socket = new Socket("127.0.0.1", 3333);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String msgToServer;

            do {
                System.out.println("Enter your message: ");
                msgToServer = scanner.nextLine();
                writer.println(msgToServer);

                System.out.println("Echo from server: "+reader.readLine());

            } while (!msgToServer.equalsIgnoreCase("exit"));

        } catch (IOException e) {
            System.out.println("Sth went wrong while R/W operations: "+e.getMessage());
        }

    }

}
