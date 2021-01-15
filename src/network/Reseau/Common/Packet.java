package Reseau.Common;

import org.json.JSONObject;

public abstract class Packet {
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

