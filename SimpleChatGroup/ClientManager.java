import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientManager extends Thread {

    public static ArrayList<ClientManager> clientManagers = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientName;

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientName = reader.readLine();
            clientManagers.add(this);
            broadcastMessage("SERVER: " + clientName + " has joined the chat group!");
        } catch (IOException e) {
            closeEverything(socket, reader, writer);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = reader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToBroadcast) {
        for (ClientManager clientManager : clientManagers) {
            try {
                if (!clientManager.clientName.equals(clientName)) {
                    clientManager.writer.write(messageToBroadcast);
                    clientManager.writer.newLine();
                    clientManager.writer.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
            }
        }
    }

    public void removeClientManager() {
        clientManagers.remove(this);
        broadcastMessage("SERVER: "+clientName+" has left the group chat!");
    }

    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeClientManager();
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Sth went wrong while closing components: "+e.getMessage());
        }
    }
}
