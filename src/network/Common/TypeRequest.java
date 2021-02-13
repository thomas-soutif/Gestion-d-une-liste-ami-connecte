package network.Common;

import database.CLASSES.AccountUser;
import ihm.MainWindowController;
import org.json.JSONException;
import org.json.JSONObject;

public enum TypeRequest {
    NONE,
    TOKEN_AUTHENTICATION {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("Traitement d'une connexion en cours du client " + request.getSender().getId());
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
    },
    INSCRIPTION {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("INSCRIPTION str");
            Response response = new Response(TypeResponse.TOKEN_AUTHENTICATION, 200);
            request.getSender().sendPacket(response);

        }
    },
    FRIEND_REQUEST,
    FRIENDLIST,
    ACCEPT_FRIEND_REQUEST,
    REFUSE_FRIEND_REQUEST,
    REMOVE_FRIEND,
    CONNECTED_FRIEND,
    DISCONNECTION;

    public void ClientHandling(Request request) {
        System.out.println("default");
//        MainWindowController.getInstance().testLinkUI();
    }

    public void ServerHandling(Request request) {
        System.out.println("default");
    }
}

