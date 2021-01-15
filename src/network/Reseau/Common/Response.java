package Reseau.Common;

public class Response extends Packet{
    private TypeResponse typeResponse;
    private int statusResponse;

    public Response(TypeResponse typeResponse, int statusResponse) {
        this.typeResponse = typeResponse;
        this.statusResponse = statusResponse;
        this.typePacket = TypePacket.RESPONSE;
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }

    public int getStatusResponse() {
        return statusResponse;
    }


}
