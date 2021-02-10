package network.Common;

import network.Server.ConnectedClient;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    protected String content;
    protected TypePacket typePacket;
    protected ConnectedClient sender;

    public void setContent(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public TypePacket getTypePacket() {
        return typePacket;
    }

    public ConnectedClient getSender() {
        return sender;
    }

    public void setSender(ConnectedClient sender) {
        this.sender = sender;
    }

    public enum TypePacket{
        REQUEST,
        RESPONSE
    }

}

