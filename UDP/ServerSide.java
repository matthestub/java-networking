import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerSide {

    public static void main(String[] args) {
        DatagramSocket datagramSocket = null;
        DatagramPacket packet = null;
        try {
            datagramSocket = new DatagramSocket(4444);
            String msgReceived;

            while (true) {
                byte[] buffer = new byte[50];
                packet = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(packet);
                msgReceived = new String(buffer, 0, packet.getLength());

                InetAddress receiverAddress = packet.getAddress();
                int receiverPort = packet.getPort();

                System.out.println("Received: "+msgReceived+" - from address: "+receiverAddress + ", from port: "+receiverPort);

                String msgToSend = "Echo from UDP server: "+msgReceived;

                byte[] buffer2 = msgToSend.getBytes();
                packet = new DatagramPacket(buffer2, buffer2.length, receiverAddress, receiverPort);

                if (!msgReceived.equalsIgnoreCase("exit")) {
                    datagramSocket.send(packet);
                } else {
                    String endingMsg = "Closing connection...";
                    System.out.println(endingMsg);
                    byte[] buffer3 = endingMsg.getBytes();
                    packet = new DatagramPacket(buffer3, buffer3.length, receiverAddress, receiverPort);
                    datagramSocket.send(packet);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Sth went wrong: "+e.getMessage());
        } finally {
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }


}
