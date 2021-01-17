package network.Client;

import network.Common.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
    private String adresse;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public SocketClient(String adresse, int port) throws IOException {
        this.adresse=adresse;
        this.port=port;
        socket=new Socket(adresse, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        Thread threadClientR = new Thread(new ClientReceive(this, socket));
        threadClientR.start();
    }

    public void sendPacket(Packet packet) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.writeObject(packet);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void disconnectedServer() throws IOException
    {
        out.close();
        socket.close();
        if (in!=null)
            in.close();
    }
}
