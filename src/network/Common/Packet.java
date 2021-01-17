package network.Common;

import org.json.JSONObject;

import java.io.Serializable;

public abstract class Packet implements Serializable {
    protected JSONObject content;
    protected TypePacket typePacket;

    public void setContent(JSONObject content){
        this.content = content;
    }

    public JSONObject getContent() {
        return content;
    }

    public TypePacket getTypePacket() {
        return typePacket;
    }

    public enum TypePacket{
        REQUEST,
        RESPONSE
    }
}

