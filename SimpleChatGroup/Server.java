import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {

        try {
            while (!serverSocket.isClosed()) {
                Socket socketForClient = serverSocket.accept();
                System.out.println("New client connected to the group chat!");
                ClientManager clientManager = new ClientManager(socketForClient);
                Thread thread = new Thread(clientManager);
                thread.start();
            }
        } catch (IOException e) {
            closeConnection(serverSocket);
        }
    }

    public void closeConnection(ServerSocket serverSocket) {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Sth went wrong during closing ServerSocket: "+e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Server server = new Server(serverSocket);
        server.startServer();
    }

}
