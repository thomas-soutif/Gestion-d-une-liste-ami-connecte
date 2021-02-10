package network.Common;

import org.json.JSONObject;

import java.io.Serializable;

public class Request extends Packet implements Serializable {
    private TypeRequest typeRequest;

    public Request(TypeRequest typeRequest) {
        this.typeRequest = typeRequest;
        this.typePacket = TypePacket.REQUEST;
    }

    public Request(TypeRequest typeRequest, JSONObject jsonObject) {
        this.typeRequest = typeRequest;
        this.typePacket = TypePacket.REQUEST;
        super.content = jsonObject.toString();
    }

    public TypeRequest getTypeRequest() {
        return typeRequest;
    }

}

