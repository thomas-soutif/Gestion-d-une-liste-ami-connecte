package network.Server;

import database.CLASSES.AccountUser;
import network.Common.Packet;
import network.Common.Request;
import network.Common.TypeRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Runnable qui va s'occuper d'un client
 */
public class ConnectedClient implements Runnable {

    private static int idCounter = 1;
    private int id;
    private Server server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private AccountUser user;

    public ConnectedClient(Server server, Socket socket) throws SocketException {
        this.server = server;
        this.socket = socket;
        this.socket.setKeepAlive(true);
        id = idCounter;
        idCounter++;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Nouvelle connexion, id = " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //region Props
    public AccountUser getUser() {
        return user;
    }

    public void setUser(AccountUser user) {
        this.user = user;
    }
    //endregion

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            boolean isActive = true;
            while (isActive) {
                try{
                    Packet packet = (Packet) in.readObject();
                    System.out.println("Reception d'un paquet du client d'id " + id);
                    if (packet != null) {
                        packet.setSender(this);
                        if (packet.getTypePacket() == Packet.TypePacket.REQUEST) {
                            System.out.println("Paquet Request");
                            if (((Request)packet).getTypeRequest() == TypeRequest.DISCONNECTION_SOCKET)
                                isActive = false;
                            ((Request) packet).getTypeRequest().ServerHandling((Request) packet);
                        } else if (packet.getTypePacket() == Packet.TypePacket.RESPONSE)
                            System.out.println("Paquet Response");

                    }
                }catch (SocketException e){
                    System.out.println(e);
                    isActive = false;

                }


            }
           Server.disconnectedClient(this);
            System.out.println("Deconnexion du client d'id "+ id);
            server.disconnectedClient(this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() throws IOException {
        this.in.close();
        this.out.close();

        this.socket.close();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }
}
