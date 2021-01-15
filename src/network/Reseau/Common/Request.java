package Reseau.Common;

public class Request extends Packet{
    private TypeRequest typeRequest;

    public Request(TypeRequest typeRequest) {
        this.typeRequest = typeRequest;
        this.typePacket = TypePacket.REQUEST;
    }

    public TypeRequest getTypeRequest() {
        return typeRequest;
    }

}

