package network.Server.Handler;

import database.CLASSES.AccountUser;
import network.Common.Request;
import network.Common.Response;
import network.Common.TypeResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class HandlerAuthServer {
    private HandlerAuthServer(){}

    public static void handlerUserConnexion(Request request){
        try {
            JSONObject jsonObjectReceived = new JSONObject(request.getContent());
            jsonObjectReceived.getString("pseudo");
            jsonObjectReceived.getString("password");
            //getBd(pseudo/mdp)
            //if exist TODO liens base donnée et traitement erreur
            JSONObject jsonObjectResponse = new JSONObject();
            AccountUser user = new AccountUser();
            user.setPseudo("Michel");
            user.setPassword("1234");
            user.setFirstName("Michou");
            user.setName("Delavegas");
            jsonObjectResponse.put("user", new JSONObject(user));
            System.out.println(jsonObjectResponse);
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
}
