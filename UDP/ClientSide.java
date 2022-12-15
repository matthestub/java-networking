import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClientSide {

    public static void main(String[] args) {

        int serverPort = 4444;
        InetAddress serverAddress = null;
        DatagramSocket clientSocket = null;
        DatagramPacket packet = null;

        Scanner scanner = new Scanner(System.in);
        String msgToSend;

        try {
            serverAddress = InetAddress.getLocalHost();
            clientSocket = new DatagramSocket(5555);

            do {
                System.out.println("Enter msg to send: ");
                msgToSend = scanner.nextLine();

                byte[] buffer = msgToSend.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                clientSocket.send(packet);

                byte[] bufferToReadFromSrv = new byte[50];
                packet = new DatagramPacket(bufferToReadFromSrv, bufferToReadFromSrv.length);
                clientSocket.receive(packet);

                System.out.println(new String(bufferToReadFromSrv, 0, packet.getLength()));

            } while (!msgToSend.equalsIgnoreCase("exit"));

        } catch (IOException e) {
            System.out.println("Sth went wrong: "+e.getMessage());
        } finally {
            if (clientSocket != null) {
                clientSocket.close();
            }
        }

    }

}
