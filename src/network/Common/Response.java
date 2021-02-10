package network.Common;

import org.json.JSONObject;

import java.io.Serializable;

public class Response extends Packet implements Serializable {
    private TypeResponse typeResponse;
    private int statusResponse;

    public Response(TypeResponse typeResponse, int statusResponse) {
        this.typeResponse = typeResponse;
        this.statusResponse = statusResponse;
        this.typePacket = TypePacket.RESPONSE;
    }

    public Response(TypeResponse typeResponse, int statusResponse, JSONObject jsonObject) {
        this.typeResponse = typeResponse;
        this.statusResponse = statusResponse;
        this.typePacket = TypePacket.RESPONSE;
        super.content = jsonObject.toString();
    }

    public TypeResponse getTypeResponse() {
        return typeResponse;
    }

    public int getStatusResponse() {
        return statusResponse;
    }


}
