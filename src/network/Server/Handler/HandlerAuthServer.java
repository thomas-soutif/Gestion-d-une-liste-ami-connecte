package network.Server.Handler;

import database.CLASSES.AccountUser;
import network.Common.Request;
import network.Common.Response;
import network.Common.TypeResponse;
import org.json.JSONException;
import org.json.JSONObject;

//TODO LIEN BD + GESTION ERREUR
public class HandlerAuthServer {
    private HandlerAuthServer(){}

    public static void handlerUserConnexion(Request request){
        try {
            JSONObject jsonObjectReceived = new JSONObject(request.getContent());
            jsonObjectReceived.getString("pseudo");
            jsonObjectReceived.getString("password");
            //getBd(pseudo/mdp)
            //if exist TODO liens base donnée et traitement erreur
            /////
            AccountUser user = new AccountUser();
            user.setPseudo("Michel");;
            user.setFirstName("Michou");
            user.setName("Delavegas");
            /////
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("user", new JSONObject(user));
            System.out.println(jsonObjectResponse);
            user.setPassword("1234");
            request.getSender().setUser(user);
            Response response = new Response(TypeResponse.TOKEN_AUTHENTICATION, 200, jsonObjectResponse);
            request.getSender().sendPacket(response);
            //else..
        } catch (JSONException e) {
            e.printStackTrace();
            Response response = new Response(TypeResponse.TOKEN_AUTHENTICATION, 400);
            request.getSender().sendPacket(response);
        }
    }

    public static void handlerUserInscription(Request request) {
        try {
            JSONObject jsonObjectReceived = new JSONObject(request.getContent());
            jsonObjectReceived.getString("pseudo");
            jsonObjectReceived.getString("password");
            jsonObjectReceived.getString("prenom");
            jsonObjectReceived.getString("nom");
            //if inscription bd ok
            AccountUser user = new AccountUser();
            user.setPseudo("Michel");
            user.setPassword("1234");
            user.setFirstName("Michou");
            user.setName("Delavegas");
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("user", new JSONObject(user));
            //else
        } catch (JSONException e) {
            e.printStackTrace();
            Response response = new Response(TypeResponse.INSCRIPTION, 400);
            request.getSender().sendPacket(response);
        }
    }
}
