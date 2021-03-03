package network.Common;

import database.CLASSES.AccountUser;
import ihm.AddAFriendModalController;
import ihm.MainWindowController;
import org.json.JSONException;
import org.json.JSONObject;

public enum TypeResponse {
    NONE,
    TOKEN_AUTHENTICATION {
        @Override
        public void ClientHandling(Response response) {
            if (response.getStatusResponse() == 200) {
                try {
                    System.out.println("changement du titre d'un des boutons en 'lien ui' pour tester ");
                    MainWindowController.getInstance().testLinkUI();

                    JSONObject jsonObject = new JSONObject(response.getContent());
                    System.out.println(jsonObject.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Erreur lors de la connexion");
            }
        }
    },
    INSCRIPTION,
    FRIEND_REQUEST,
    FRIENDLIST{
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
    ACCEPT_FRIEND_REQUEST,
    REFUSE_FRIEND_REQUEST,
    REMOVE_FRIEND,
    CONNECTED_FRIEND,
    DISCONNECTION;

    public void ClientHandling(Response response) {
        System.out.println("default");
    }

    public void ServerHandling(Response response) {
        System.out.println("default");
    }

}
