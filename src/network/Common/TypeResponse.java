package network.Common;

import database.CLASSES.AccountUser;
import ihm.AddAFriendModalController;
import ihm.AddAFriendModalController;
import ihm.MainWindowController;
import network.Client.Handler.HandlerAuthClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum TypeResponse {
    GET_USER_INFO_FOR_MAIN_WINDOWS{
        @Override
        public void ClientHandling(Response response) {

            if(response.getStatusResponse() == 401){
                // Refusé car non authentifié, on ne fait rien

            }else if(response.getStatusResponse() == 200){
                System.out.println("set pseudo before");
                MainWindowController.getInstance().setPseudoOnWindowsUi(new JSONObject(response.getContent()));
            }
        }
    },
    REMOVE_ONE_FRIEND_RELATION{
        @Override
        public void ClientHandling(Response response) {
            if(response.getStatusResponse() == 204){
                MainWindowController.getInstance().setUIAfterFriendRemove(new JSONObject(response.getContent()));
            }
        }
    },
    ADD_ONE_FRIEND_RELATION{
        @Override
        public void ClientHandling(Response response) {

            if(response.getStatusResponse() == 201){
                MainWindowController.getInstance().addOneFriendRelationOnUi(new JSONObject(response.getContent()));
            }
        }
    },
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
    FRIENDLIST{

        @Override
        public void ClientHandling(Response response) {
            System.out.println("List Friend Client Handling");
            JSONObject jsonObject = new JSONObject(response.getContent());
            MainWindowController.getInstance().setFriendListOnUI(jsonObject);

        }
    },
    FRIEND_REQUEST_LIST{
        @Override
        public void ClientHandling(Response response){
            System.out.println("List Request Friend Handling");
            JSONObject jsonObject = new JSONObject(response.getContent());
            MainWindowController.getInstance().setFriendRequestListOnUI(jsonObject);
        }
    },
    ACCEPT_FRIEND_REQUEST{
        @Override
        public void ClientHandling(Response response){
            System.out.println("ACCEPT FRIEND REQUEST HANDLING");
            JSONObject jsonObject = new JSONObject(response.getContent());
            System.out.println(response.getStatusResponse());
            if(response.getStatusResponse() == 200){
                // If the server accepted the friend request,we can modify the UI
                MainWindowController.getInstance().setUIAfterFriendRequestHasBeenAcceptedByServer(jsonObject);
            }
            else{
                //MainWindowController.getInstance().setUIAfterFriendRequestHasBeenAcceptedByServer(jsonObject);
                //TODO: Ajouter si j'ai le temps un message d'erreur pour dire qu'il y'a eu un problème
            }
        }
    },
    REFUSE_FRIEND_REQUEST{
        @Override
        public void ClientHandling(Response response){
            System.out.println("REFUSE FRIEND REQUEST HANDLING");
            JSONObject jsonObject = new JSONObject(response.getContent());
            System.out.println(response.getStatusResponse());
            if(response.getStatusResponse() == 200){
                MainWindowController.getInstance().setUIAfterFriendRequestHasBeenRefusedByServer(jsonObject);
            }
            else{
                //MainWindowController.getInstance().setUIAfterFriendRequestHasBeenAcceptedByServer(jsonObject);
                //TODO: Ajouter si j'ai le temps un message d'erreur pour dire qu'il y'a eu un problème
            }
        }
    },
    REMOVE_FRIEND{
        @Override
        public void ClientHandling(Response response){
            System.out.println("REMOVE FRIEND REQUEST HANDLING");
            JSONObject jsonObject = new JSONObject(response.getContent());
            System.out.println(response.getStatusResponse());
            if(response.getStatusResponse() == 200){
                MainWindowController.getInstance().setUIAfterFriendRemove(jsonObject);
            }
            else{
                //MainWindowController.getInstance().setUIAfterFriendRequestHasBeenAcceptedByServer(jsonObject);
                //TODO: Ajouter si j'ai le temps un message d'erreur pour dire qu'il y'a eu un problème
            }
        }
    },
    USER_ADD_FRIEND_LIST{
        @Override
        public void ClientHandling(Response response) {
            System.out.println("List Friend add");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response.getContent());
                AddAFriendModalController.getInstance().setFriendAddListOnUI(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    },
    ADD_FRIEND_RESPONSE{
        @Override
        public void ClientHandling(Response response) {
            System.out.println("List Friend Client Handling");
            JSONObject jsonObject = null;
            try {
                System.out.println("try add response");
                jsonObject = new JSONObject(response.getContent());
                AddAFriendModalController.getInstance().setButtonAcceptIU(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    },
    ADD_ONE_FRIEND_REQUEST{
        @Override
        public void ClientHandling(Response response){
            JSONObject jsonObject = new JSONObject(response.getContent());
            if(response.getStatusResponse() == 201){
                MainWindowController.getInstance().addFriendRequestOnUi(jsonObject);
            }

        }
    },
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
