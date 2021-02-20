package network.Common;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.CLASSES.FriendRequest;
import database.DAO.FriendRelationDAO;
import database.DAO.FriendRequestDAO;
import ihm.MainWindowController;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayList;
import java.util.List;

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
                user.setPseudo("Xelèèèèèèèèèreeee");
                user.setPassword("jemappelleThomas");
                user.setFirstName("Thomas");
                user.setName("Soutif");
                user.setId(6);
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
    FRIENDLIST {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("Get Friend List request");
            AccountUser user = request.getSender().getUser();
            if (user == null) {
                Response response = new Response(TypeResponse.FRIENDLIST, 401);
                request.getSender().sendPacket(response);
            } else {

                FriendRelationDAO dao = new FriendRelationDAO();

                List<FriendRelation> list_friend_relation = dao.getAllFriendsOfUser(user);
                List<AccountUser> list_user = new ArrayList<>();

                for (FriendRelation friend_relation : list_friend_relation) {
                    if (friend_relation.getFirstUser().getId() != user.getId()) {
                        list_user.add(friend_relation.getFirstUser());
                    } else if (friend_relation.getSecondUser().getId() != user.getId()) {
                        list_user.add(friend_relation.getSecondUser());
                    }
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("list", list_user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("Envoie de la liste d'ami");
                Response response = new Response(TypeResponse.FRIENDLIST, 200, jsonObject);
                request.getSender().sendPacket(response);

            }

        }

    },
    FRIEND_REQUEST_LIST {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("Get FriendRequest List");
            AccountUser user = request.getSender().getUser();
            if (user == null) {
                Response response = new Response(TypeResponse.FRIEND_REQUEST_LIST, 401);
                request.getSender().sendPacket(response);
                return;
            }

            FriendRequestDAO dao = new FriendRequestDAO();
            List<AccountUser> list_user = new ArrayList<>();
            List<FriendRequest> list_friend_relation = dao.getFriendRequestsOfUser(user.getId());
            for (FriendRequest request_tuple : list_friend_relation) {
                if (request_tuple.getFrom_user().getId() != user.getId()) {
                    list_user.add(request_tuple.getFrom_user());
                } else if (request_tuple.getTo_user().getId() != user.getId()) {
                    list_user.add(request_tuple.getTo_user());
                }
            }

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("list", list_user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Response response = new Response(TypeResponse.FRIEND_REQUEST_LIST, 200, jsonObject);
            request.getSender().sendPacket(response);

        }
    },
    ACCEPT_FRIEND_REQUEST,
    REFUSE_FRIEND_REQUEST,
    REMOVE_FRIEND,
    CONNECTED_FRIEND,
    DISCONNECTION;

    public void ClientHandling(Request request) {
        System.out.println("default");
    }

    public void ServerHandling(Request request) {
        System.out.println("default");
    }
}

