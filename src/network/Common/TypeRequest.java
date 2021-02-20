package network.Common;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.CLASSES.FriendRequest;
import database.DAO.FriendRelationDAO;
import database.DAO.FriendRequestDAO;
import database.EXCEPTION.CustomException;
import ihm.MainWindowController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.desktop.SystemSleepEvent;
import java.sql.SQLException;
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
            List<JSONObject> list_to_send = new ArrayList<>();
            List<FriendRequest> list_friend_request = dao.getFriendRequestsOfUser(user.getId());
            JSONObject customObject = new JSONObject();
            for (FriendRequest request_tuple : list_friend_request) {
                customObject.put("request_id",request_tuple.getId());
                if (request_tuple.getFrom_user().getId() != user.getId()) {
                    customObject.put("firstname",request_tuple.getFrom_user().getFirstName());
                    customObject.put("name",request_tuple.getFrom_user().getName());
                    customObject.put("pseudo",request_tuple.getFrom_user().getPseudo());
                } else if (request_tuple.getTo_user().getId() != user.getId()) {
                    customObject.put("firstname",request_tuple.getTo_user().getFirstName());
                    customObject.put("name",request_tuple.getTo_user().getName());
                    customObject.put("pseudo",request_tuple.getTo_user().getPseudo());
                }
                list_to_send.add(customObject);
            }

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("list_friend_request", list_to_send);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Response response = new Response(TypeResponse.FRIEND_REQUEST_LIST, 200, jsonObject);
            request.getSender().sendPacket(response);

        }
    },
    ACCEPT_FRIEND_REQUEST{
        @Override
        public void ServerHandling(Request request) {

            FriendRequestDAO friendRequestDao = new FriendRequestDAO();
            FriendRelationDAO friendRelationDao = new FriendRelationDAO();
            JSONObject jsonObject = new JSONObject(request.getContent());
            int friend_request_id = jsonObject.getInt("friend_request_id");
            AccountUser user = request.getSender().getUser();
            FriendRequest friend_request = friendRequestDao.getFriendRequestById(friend_request_id);
            AccountUser userWhoAskedForTheFriendRelation = null;

            if (friend_request.getFrom_user().getId() != user.getId()) {
                // On ajoute celui qui a fait la demande d'ami
                userWhoAskedForTheFriendRelation = friend_request.getFrom_user();
            } else if (friend_request.getTo_user().getId() != user.getId()) {
                // On ajoute celui qui a fait la demande d'ami
                userWhoAskedForTheFriendRelation = friend_request.getTo_user();
            }
            // We construct the friend relation to add it to the database via the DAO
            FriendRelation friend_relation = new FriendRelation();
            friend_relation.setFirstUser(userWhoAskedForTheFriendRelation);
            friend_relation.setSecondUser(user);
            try {
                friendRelationDao.insert(friend_relation);
                // If everything is ok
                Response response = new Response(TypeResponse.ACCEPT_FRIEND_REQUEST, 200, jsonObject);
                request.getSender().sendPacket(response);
            } catch (CustomException e){
                System.out.println(e);
                // An error occurs
                Response response = new Response(TypeResponse.ACCEPT_FRIEND_REQUEST, 500, jsonObject);
                request.getSender().sendPacket(response);
            }



        }

    },
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

