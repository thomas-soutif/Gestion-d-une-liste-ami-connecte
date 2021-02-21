package network.Common;

import database.CLASSES.AccountUser;
import ihm.MainWindowController;
import network.Server.Handler.HandlerAuthServer;
import org.json.JSONException;
import org.json.JSONObject;

public enum TypeRequest {
    NONE,
    TOKEN_AUTHENTICATION {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("Traitement d'une connexion en cours du client " + request.getSender().getId());
            HandlerAuthServer.handlerUserConnexion(request);
        }
    },
    INSCRIPTION {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("Traitement d'une inscription en cours du client " + request.getSender().getId());
            HandlerAuthServer.handlerUserInscription(request);

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

    public void ClientHandling(Request request) {
        System.out.println("default");
    }

    public void ServerHandling(Request request) {
        System.out.println("default");
    }
}

