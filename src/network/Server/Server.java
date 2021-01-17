package network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Server {
    private int port;
    private List<ConnectedClient> clients;

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

    public void disconnectedClient(ConnectedClient discClient) throws IOException {
        discClient.closeClient();
        clients.remove(discClient);
    }
}
