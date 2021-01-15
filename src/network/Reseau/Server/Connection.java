package Reseau.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Runnable qui va accepter de nouveaux clients
 */
public class Connection implements Runnable{

    private Server server;
    private ServerSocket serverSocket;

    public Connection(Server server){
        this.server = server;
        try {
            this.serverSocket = new ServerSocket(server.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket sockNewClient = serverSocket.accept();
                ConnectedClient newClient = new ConnectedClient(server, sockNewClient);
                server.addClient(newClient);
                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
