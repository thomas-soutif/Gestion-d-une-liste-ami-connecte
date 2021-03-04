package network.Client;

import network.Common.Packet;
import network.Common.Request;
import network.Common.TypeRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient {
    private static SocketClient socketClient;

    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private SocketClient(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        socket = new Socket(address, port);
        socket.setKeepAlive(true);
        out = new ObjectOutputStream(socket.getOutputStream());
        Thread threadClientR = new Thread(new ClientReceive(this, socket));
        threadClientR.start();
        socketClient = this;
    }

    public static boolean create(String adresse, int port) {
        if (socketClient == null) {
            try {
                new SocketClient(adresse, port);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void close() {
        if (socketClient != null) {
            try {
                Request request = new Request(TypeRequest.DISCONNECTION);
                sendPacketStatic(request);
                socketClient.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendPacketStatic(Packet packet) {
        socketClient.sendPacket(packet);
    }

    public static void sendPacketAsyncStatic(Packet packet) {
        socketClient.sendPacketAsync(packet);
    }

    private void sendPacketAsync(Packet packet) {
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

    private void sendPacket(Packet packet) {
        try {
            out.writeObject(packet);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectedServer() throws IOException {
        out.close();
        socket.close();
        if (in != null)
            in.close();
    }
}
