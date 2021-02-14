package network.Common;

import database.CLASSES.AccountUser;
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
//                    MainWindowController.getInstance().testLinkUI();

//                    JSONObject jsonObject = new JSONObject(response.getContent());
//                    System.out.println(jsonObject.toString());
//                    AccountUser user = (AccountUser) jsonObject.get("user");
//                    System.out.println(user.getId() + user.getFirstName() + user.getName() + user.getPseudo() + user.getPassword());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Authentification incorrect");
            }
        }
    },
    INSCRIPTION,
    FRIEND_REQUEST,
    FRIENDLIST{

        @Override
        public void ClientHandling(Response response) {
            System.out.println("List Friend Client Handling");
            JSONObject jsonObject = new JSONObject(response.getContent());
            MainWindowController.getInstance().setFriendListOnUI(jsonObject);

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
