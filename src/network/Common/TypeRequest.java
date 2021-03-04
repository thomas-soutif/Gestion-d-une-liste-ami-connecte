package network.Common;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.CLASSES.FriendRequest;
import database.DAO.FriendRelationDAO;
import database.DAO.FriendRequestDAO;
import database.DAO.UserDAO;
import database.EXCEPTION.CustomException;
import database.CLASSES.FriendRequest;
import database.DAO.FriendRelationDAO;
import database.DAO.FriendRequestDAO;
import database.DAO.UserDAO;
import database.EXCEPTION.CustomException;
import ihm.MainWindowController;
import network.Server.ConnectedClient;
import network.Server.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.awt.desktop.SystemSleepEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public enum TypeRequest {
    GET_USER_INFO_FOR_MAIN_WINDOWS{
        @Override
        public void ServerHandling(Request request) {
            System.out.println("receive get user info");
            AccountUser user = request.getSender().getUser();
            if (user == null){
                // L'utilisateur n'est pas authentifié, on lui refuse la demande
                Response response = new Response(TypeResponse.GET_USER_INFO_FOR_MAIN_WINDOWS, 401);
                request.getSender().sendPacket(response);
                return;
            }
            //Alors la thread lié à cet utilisateur existe, et il est connecté, donc on lui renvoie ses informations
            System.out.println("get user ok");
            JSONObject customObject = new JSONObject();
            customObject.put("pseudo",user.getPseudo());
            Response response = new Response(TypeResponse.GET_USER_INFO_FOR_MAIN_WINDOWS, 200, customObject);
            request.getSender().sendPacket(response);
        }
    },
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
                if(jsonObjectReceived.getString("pseudo").equals("Jym")){
                    user.setPseudo("Jym");
                    user.setPassword("1234");
                    user.setName("DOSSOU");
                    user.setFirstName("Jean-Michel");
                    user.setId(8);
                }else{
                    user.setPseudo("Xelèèèèèèèèèreeee");
                    user.setPassword("jemappelleThomas");
                    user.setFirstName("Thomas");
                    user.setName("Soutif");
                    user.setId(6);
                }
//                user.setPseudo("Xelèèèèèèèèèreeee");
//                user.setPassword("jemappelleThomas");
//                user.setFirstName("Thomas");
//                user.setName("Soutif");
//                user.setId(6);
//                user.setId(9);
//                user.setPseudo("Ace");
//                user.setPassword("jadoreacejésouisunkikoo");
//                user.setFirstName("Fabien");
//                user.setName("Soles");

//                    user.setPseudo("Jym");
//                    user.setPassword("1234");
//                    user.setId(8);
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
                List<JSONObject> list_user = new ArrayList<>();

                for (FriendRelation friend_relation : list_friend_relation) {
                    JSONObject customObject = new JSONObject();
                    if (friend_relation.getFirstUser().getId() != user.getId()) {
                        customObject.put("firstname",friend_relation.getFirstUser().getFirstName());
                        customObject.put("name",friend_relation.getFirstUser().getName());
                        customObject.put("pseudo",friend_relation.getFirstUser().getPseudo());
                        customObject.put("friend_relation_id",friend_relation.getId());
                        list_user.add(customObject);
                    } else if (friend_relation.getSecondUser().getId() != user.getId()) {
                        customObject.put("firstname",friend_relation.getSecondUser().getFirstName());
                        customObject.put("name",friend_relation.getSecondUser().getName());
                        customObject.put("pseudo",friend_relation.getSecondUser().getPseudo());
                        customObject.put("friend_relation_id",friend_relation.getId());
                        list_user.add(customObject);
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

            for (FriendRequest request_tuple : list_friend_request) {
                JSONObject customObject = new JSONObject();
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
    ACCEPT_FRIEND_REQUEST   {
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
            jsonObject.put("firstname",userWhoAskedForTheFriendRelation.getFirstName()).put("pseudo", userWhoAskedForTheFriendRelation.getPseudo()).put(
                    "name",userWhoAskedForTheFriendRelation.getName()).put("id",userWhoAskedForTheFriendRelation.getId());
            try {
                friendRelationDao.insert(friend_relation);
                // If everything is ok
                jsonObject.put("friend_relation_id",friend_relation.getId());
                // On doit supprimer la friend request de la base de données
                friendRequestDao.delete(friend_request);

                //Mise à jour de l'interface de l'ami accepté si il est connecté

                List<ConnectedClient> connectedClient =Server.getClients();

                for(ConnectedClient client : connectedClient){
                    if(client.getUser() != null && client.getUser().getId() == userWhoAskedForTheFriendRelation.getId()){
                        JSONObject customObject = new JSONObject();
                        customObject.put("firstname",user.getFirstName());
                        customObject.put("name",user.getName());
                        customObject.put("pseudo",user.getPseudo());
                        customObject.put("friend_relation_id",friend_relation.getId());
                        Response response = new Response(TypeResponse.ADD_ONE_FRIEND_RELATION,201,customObject);
                        client.sendPacket(response);
                        break;
                    }
                }
                Response response = new Response(TypeResponse.ACCEPT_FRIEND_REQUEST, 200, jsonObject);
                request.getSender().sendPacket(response);
            } catch (CustomException | SQLException e){
                System.out.println(e);
                // An error occurs

                Response response = new Response(TypeResponse.ACCEPT_FRIEND_REQUEST, 500, jsonObject);
                request.getSender().sendPacket(response);
            }



        }

    },
    REFUSE_FRIEND_REQUEST{
        @Override
        public void ServerHandling(Request request) {

            FriendRequestDAO friendRequestDao = new FriendRequestDAO();
            JSONObject jsonObject = new JSONObject(request.getContent());
            int friend_request_id = jsonObject.getInt("friend_request_id");
            AccountUser user = request.getSender().getUser();
            FriendRequest friend_request = friendRequestDao.getFriendRequestById(friend_request_id);
            //Just have to delete the friend request
            try {
                friendRequestDao.delete(friend_request);
                // If everything is ok
                Response response = new Response(TypeResponse.REFUSE_FRIEND_REQUEST, 200, jsonObject);
                request.getSender().sendPacket(response);
            } catch ( SQLException e){
                System.out.println(e);
                // An error occurs
                Response response = new Response(TypeResponse.REFUSE_FRIEND_REQUEST, 500, jsonObject);
                request.getSender().sendPacket(response);
            }



        }
    },
    REMOVE_FRIEND{
        @Override
        public void ServerHandling(Request request) {

            FriendRelationDAO friendRelationDAO = new FriendRelationDAO();
            JSONObject jsonObject = new JSONObject(request.getContent());
            int friend_relation_id = jsonObject.getInt("friend_relation_id");
            FriendRelation relation = friendRelationDAO.getFriendRelationById(friend_relation_id);
            // Si jamais on a relation = null, ça veut dire la relation n'existe plus en base de données, vérifier si le utilisateur est connecté
            if(relation == null){

                List<ConnectedClient> connectedClient =Server.getClients();
                for(ConnectedClient client : connectedClient){

                    if(client.getUser() != null && client.getUser().getId() == request.getSender().getUser().getId() ){
                        JSONObject customObject = new JSONObject();
                        customObject.put("friend_relation_id",friend_relation_id);
                        Response response = new Response(TypeResponse.REMOVE_ONE_FRIEND_RELATION,204,customObject);
                        client.sendPacket(response);
                        break;
                    }
                }

                Response response = new Response(TypeResponse.REMOVE_FRIEND, 500, jsonObject);
                request.getSender().sendPacket(response);
                return;
            }



            try{
                friendRelationDAO.delete(relation);
                AccountUser userToSend = new AccountUser();

                if(relation.getFirstUser().getId() == request.getSender().getUser().getId()){
                    userToSend = relation.getSecondUser();
                }else if(relation.getSecondUser().getId() == request.getSender().getUser().getId()){
                    userToSend = relation.getFirstUser();
                }


                //Une fois supprimé, on vérifie si l'ami en lien est connecté pour lui faire mettre à jour son ui
                List<ConnectedClient> connectedClient =Server.getClients();
                for(ConnectedClient client : connectedClient){

                    if(client.getUser() != null && userToSend != null && client.getUser().getId() == userToSend.getId() ){
                        JSONObject customObject = new JSONObject();
                        customObject.put("friend_relation_id",friend_relation_id);
                        Response response = new Response(TypeResponse.REMOVE_ONE_FRIEND_RELATION,204,customObject);
                        client.sendPacket(response);
                        break;
                    }
                }



                Response response = new Response(TypeResponse.REMOVE_FRIEND, 200, jsonObject);
                request.getSender().sendPacket(response);

            } catch (Exception e) {
                System.out.println(e);

                Response response = new Response(TypeResponse.REMOVE_FRIEND, 500, jsonObject);
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
                // On va envoyer une reqûete au client concerné par cette demande pour afficher dans ses requêtes d'amis (si seulement il est connecté)
                List<ConnectedClient> connectedClient =Server.getClients();
                for(ConnectedClient client : connectedClient){
                    if(client.getUser() != null && client.getUser().getId() == user2.getId()){
                        JSONObject customObject = new JSONObject();
                        customObject.put("firstname",user1.getFirstName());
                        customObject.put("name",user1.getName());
                        customObject.put("pseudo",user1.getPseudo());
                        customObject.put("request_id",friend_request.getId());
                        Response response = new Response(TypeResponse.ADD_ONE_FRIEND_REQUEST,201,customObject);
                        client.sendPacket(response);
                        break;
                    }
                }
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
    CONNECTED_FRIEND,
    DISCONNECTION;

    public void ClientHandling(Request request) {
        System.out.println("default");
    }

    public void ServerHandling(Request request) {
        System.out.println("default");
    }
}

