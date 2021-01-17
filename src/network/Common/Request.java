package network.Common;

import java.io.Serializable;

public class Request extends Packet implements Serializable {
    private TypeRequest typeRequest;

    public Request(TypeRequest typeRequest) {
        this.typeRequest = typeRequest;
        this.typePacket = TypePacket.REQUEST;
    }

    public TypeRequest getTypeRequest() {
        return typeRequest;
    }

}

