package network.Common;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.CLASSES.FriendRequest;
import database.DAO.FriendRelationDAO;
import database.DAO.FriendRequestDAO;
import database.DAO.UserDAO;
import database.EXCEPTION.CustomException;
import ihm.MainWindowController;
import org.json.JSONException;
import org.json.JSONObject;

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
                user.setId(9);
                user.setPseudo("Ace");
                user.setPassword("jadoreacejésouisunkikoo");
                user.setFirstName("Fabien");
                user.setName("Soles");
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
    USER_ADD_FRIEND_LIST {
        @Override
        public void ServerHandling(Request request) {
            System.out.println("Get Add Friend List request");
            AccountUser user = request.getSender().getUser();
            if (user == null) {
                Response response = new Response(TypeResponse.USER_ADD_FRIEND_LIST, 401);
                request.getSender().sendPacket(response);
                return;
            } else {

                UserDAO userDAO = new UserDAO();
                List<AccountUser> list_user = userDAO.getAccountOfUsers();
                List<AccountUser> list_user_filtered = new ArrayList<>();

                FriendRelationDAO friendrelationDAO = new FriendRelationDAO();
                FriendRequestDAO friendRequestDAO = new FriendRequestDAO();

                int userId = user.getId();
                for (AccountUser otherUser : list_user) {
                    // If ourself don't add
                    int otherUserId = otherUser.getId();
                    if (userId == otherUserId) {
                        continue;
                    }

                    // If in friend relation don't add
                    if (friendrelationDAO.haveFriendRelation(user, otherUser))
                        continue;

                    // If in friend request don't add
                    if (friendRequestDAO.isFriendRequestExist(userId, otherUserId))
                        continue;

                    list_user_filtered.add(otherUser);
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("list_add_friend_list", list_user_filtered);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("Envoie de la liste des amis disponible pour ajout");
                Response response = new Response(TypeResponse.USER_ADD_FRIEND_LIST, 200, jsonObject);
                System.out.println("Send packet");
                request.getSender().sendPacket(response);
            }
        }
    },
    ADD_FRIEND_REQUEST {
        @Override
        public void ServerHandling(Request request) {
            FriendRequestDAO friendRequestDao = new FriendRequestDAO();
            UserDAO userDAO = new UserDAO();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(request.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int friend_add_request_id = jsonObject.getInt("friend_add_request_id");
            AccountUser user1 = request.getSender().getUser();
            AccountUser user2 = userDAO.getUserById(friend_add_request_id);
            FriendRequest friend_request = new FriendRequest();
            friend_request.setFrom_user(user1);
            friend_request.setTo_user(user2);
            try {
                System.out.println("try insert friend");
                friendRequestDao.insert(friend_request);
                Response response = new Response(TypeResponse.ADD_FRIEND_RESPONSE, 200, jsonObject);
                request.getSender().sendPacket(response);
            } catch (CustomException e) {
                System.out.println(e);
                // An error occurs
                Response response = new Response(TypeResponse.ADD_FRIEND_RESPONSE, 500, jsonObject);
                request.getSender().sendPacket(response);
            }
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

