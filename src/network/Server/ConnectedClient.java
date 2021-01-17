package network.Server;

import network.Common.Packet;
import network.Common.Response;
import network.Common.TypeResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Runnable qui va s'occuper d'un client
 */
public class ConnectedClient implements Runnable{

    private static int idCounter = 1;
    private int id;
    private Server server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    //TODO private User user;

    public ConnectedClient(Server server, Socket socket){
        this.server = server;
        this.socket = socket;
        id = idCounter;
        idCounter++;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Nouvelle connexion, id= " + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            boolean isActive = true;
            while (isActive){
                Packet packet = (Packet) in.readObject();
                if (packet!=null){
                    if (packet.getTypePacket() == Packet.TypePacket.REQUEST) {
                        System.out.println("Paquet Request");//TODO traitementRequest (new thread) (cast en Request)
                        this.sendPacket(new Response(TypeResponse.TOKEN_AUTHENTIFICATION, 200));
                    }
                    else if (packet.getTypePacket() == Packet.TypePacket.RESPONSE)
                        System.out.println("Paquet Response");//TODO traitementResponse (new thread) (cast en Response)
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet){
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
