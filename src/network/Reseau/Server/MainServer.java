package Reseau.Server;

/**
 * Lancement du serveur : java server.Server
 */
public class MainServer {
    public static void main(String[] args) {
        Integer port = 25555; //TODO Mettre fichier config
        Server server = new Server(port);
    }
}
