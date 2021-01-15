package Reseau.Client;

import Reseau.Common.Packet;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable{
    private SocketClient client;
    private Socket socket;
    private ObjectInputStream in;

    public ClientReceive(SocketClient socketClient, Socket socket) {
        client = socketClient;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());

            boolean isActive = true;
            while (isActive) {
                Packet packet = (Packet) in.readObject();
                if (packet != null) {
                    if (packet.getTypePacket() == Packet.TypePacket.REQUEST)
                        System.out.println("Paquet Request");//TODO traitementRequest (new thread) (cast en Request)
                    else if (packet.getTypePacket() == Packet.TypePacket.RESPONSE)
                        System.out.println("Paquet Response");//TODO traitementResponse (new thread) (cast en Response)
                }
            }
            client.disconnectedServer();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
