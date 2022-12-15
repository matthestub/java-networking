import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerHelper extends Thread{

    private Socket socket;
    private String clientName;
    private String logFileName;

    public ServerHelper(Socket socket, int count) {
        this.socket = socket;
        this.clientName = "Client"+count;
        this.logFileName = "log-"+clientName+".txt";
    }

    @Override
    public void run() {

        File logFile = new File(logFileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             FileWriter fileWriter = new FileWriter(logFile);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("New Client connected!");
            String msgFromClient;

            while (socket.isConnected()) {

                msgFromClient = reader.readLine();
                System.out.println("Received from "+clientName+": "+msgFromClient);

                fileWriter.write(msgFromClient);
                fileWriter.write("\n");
                fileWriter.flush();

                if (msgFromClient.equalsIgnoreCase("exit")) {
                    System.out.println("Closing connection with: "+clientName);
                    writer.println("Closing connection...");
                    socket.close();
                    break;
                }

                writer.println("Echo from server: "+msgFromClient);

            }
        } catch (IOException e) {
            System.out.println("Sth went wrong while R/W operations: "+e.getMessage());
        }

    }
}
