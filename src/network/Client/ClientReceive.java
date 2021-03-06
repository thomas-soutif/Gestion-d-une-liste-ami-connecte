package network.Client;

import network.Common.Packet;
import network.Common.Request;
import network.Common.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable {
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
                System.out.println("READ OBJECT");
                if (packet != null) {
                    if (packet.getTypePacket() == Packet.TypePacket.REQUEST)
                        ((Request) packet).getTypeRequest().ClientHandling((Request) packet);
                    else if (packet.getTypePacket() == Packet.TypePacket.RESPONSE){
                        System.out.println("Client receive packet type response");
                        ((Response) packet).getTypeResponse().ClientHandling((Response) packet);
                    }

                }
            }
            client.disconnectedServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
