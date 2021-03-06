package network.Server;

import java.io.IOException;
import java.util.Properties;

/**
 * Lancement du serveur : java server.Server
 */
public class MainServer {
    public static void main(String[] args) {
        Properties servProperties = new Properties();
        try {
            servProperties.load(MainServer.class.getResourceAsStream("../Common/network.properties"));
            Integer port = Integer.valueOf(servProperties.getProperty("port"));
            Server server = new Server(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
