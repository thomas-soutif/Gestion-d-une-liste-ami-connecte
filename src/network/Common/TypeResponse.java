package network.Common;

import database.CLASSES.AccountUser;
import ihm.MainWindowController;
import network.Client.Handler.HandlerAuthClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum TypeResponse {
    NONE,
    TOKEN_AUTHENTICATION {
        @Override
        public void ClientHandling(Response response) {
            HandlerAuthClient.handlerUserConnexionResponse(response);
        }
    },
    INSCRIPTION{
        @Override
        public void ClientHandling(Response response) {
            HandlerAuthClient.handlerUserInscriptionResponse(response);
        }
    },
    FRIEND_REQUEST,
    FRIENDLIST,
    ACCEPT_FRIEND_REQUEST,
    REFUSE_FRIEND_REQUEST,
    REMOVE_FRIEND,
    CONNECTED_FRIEND,
    DISCONNECTION_SOCKET,
    DISCONNECTION_USER;

    public void ClientHandling(Response response) {
        System.out.println("default");
    }

    public void ServerHandling(Response response) {
        System.out.println("default");
    }

}
