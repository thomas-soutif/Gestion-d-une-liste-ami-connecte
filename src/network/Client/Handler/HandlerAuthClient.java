package network.Client.Handler;

import network.Client.SocketClient;
import network.Common.Request;
import network.Common.TypeRequest;
import org.json.JSONException;
import org.json.JSONObject;

public final class HandlerAuthClient {
    private HandlerAuthClient(){}

    /**
     * @param pseudo Pseudo de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @return success
     */
    public static boolean handlerUserConnexion(String pseudo, String motDePasse){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo",pseudo);
            jsonObject.put("password",motDePasse);
            Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION, jsonObject);
            SocketClient.sendPacketAsyncStatic(request);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
