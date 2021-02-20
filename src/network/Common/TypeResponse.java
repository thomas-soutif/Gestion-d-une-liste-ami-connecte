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
                System.out.println("why");
                MainWindowController.getInstance().setUIAfterFriendRequestHasBeenAcceptedByServer(jsonObject);
            }
            else{
                MainWindowController.getInstance().setUIAfterFriendRequestHasBeenAcceptedByServer(jsonObject);
            }
        }
    },
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
