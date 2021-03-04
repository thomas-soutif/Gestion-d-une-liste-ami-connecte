package network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Server {private static List<ConnectedClient> clients;

    private int port;

    public Server(int port){
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public int getPort() {
        return port;
    }

    public void addClient(ConnectedClient newClient){
        clients.add(newClient);
    }

    public static List<ConnectedClient> getClients() {
        return clients;
    }

    public void disconnectedClient(ConnectedClient discClient) throws IOException {
        discClient.closeClient();
        clients.remove(discClient);


    }
}
