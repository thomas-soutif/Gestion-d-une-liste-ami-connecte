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
     */
    public static void handlerUserConnexionRequest(String pseudo, String motDePasse){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo",pseudo);
            jsonObject.put("password",motDePasse);
            Request request = new Request(TypeRequest.TOKEN_AUTHENTICATION, jsonObject);
            SocketClient.sendPacketAsyncStatic(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param pseudo Pseudo de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @param prenom Prenom de l'utilisateur
     * @param nom Nom de l'utilisateur
     */
    public static void handlerUserInscriptionRequest(String pseudo, String motDePasse, String prenom, String nom){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pseudo",pseudo);
            jsonObject.put("password",motDePasse);
            jsonObject.put("prenom", prenom);
            jsonObject.put("nom", nom);
            Request request = new Request(TypeRequest.INSCRIPTION, jsonObject);
            SocketClient.sendPacketAsyncStatic(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
