package network.Server.Handler;

import database.CLASSES.AccountUser;
import database.DAO.IUserDAO;
import database.DAO.UserDAO;
import network.Common.Request;
import network.Common.Response;
import network.Common.TypeResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class HandlerAuthServer {
    private HandlerAuthServer(){}

    public static void handlerUserConnexion(Request request){
        Response response;
        try {
            JSONObject jsonObjectReceived = new JSONObject(request.getContent());
            IUserDAO userDAO = new UserDAO();
            AccountUser user = userDAO.getAccountUser(jsonObjectReceived.getString("pseudo"),jsonObjectReceived.getString("password"));
            if (user != null){
                JSONObject jsonObjectResponse = new JSONObject();
                jsonObjectResponse.put("user", new JSONObject(user));
                System.out.println(jsonObjectResponse);
                request.getSender().setUser(user);
                response = new Response(TypeResponse.TOKEN_AUTHENTICATION, 200, jsonObjectResponse);
                request.getSender().sendPacket(response);
            }
            else {
                response = new Response(TypeResponse.TOKEN_AUTHENTICATION, 400);
                request.getSender().sendPacket(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            response = new Response(TypeResponse.TOKEN_AUTHENTICATION, 400);
            request.getSender().sendPacket(response);
        }
    }

    public static void handlerUserInscription(Request request) {
        try {
            JSONObject jsonObjectReceived = new JSONObject(request.getContent());
            AccountUser user = new AccountUser();
            user.setPseudo(jsonObjectReceived.getString("pseudo"));
            user.setPassword(jsonObjectReceived.getString("password"));
            user.setFirstName(jsonObjectReceived.getString("prenom"));
            user.setName(jsonObjectReceived.getString("nom"));
            IUserDAO userDAO = new UserDAO();
            userDAO.insert(user);
            JSONObject jsonObjectResponse = new JSONObject();
            jsonObjectResponse.put("user", new JSONObject(user));
            Response response = new Response(TypeResponse.INSCRIPTION,200,jsonObjectResponse);
            request.getSender().sendPacket(response);
        } catch (Exception e) {
            e.printStackTrace();
            Response response = new Response(TypeResponse.INSCRIPTION, 400);
            request.getSender().sendPacket(response);
        }
    }
}
