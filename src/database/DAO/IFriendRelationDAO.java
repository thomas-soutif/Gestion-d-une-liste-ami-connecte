package database.DAO;

import database.CLASSES.FriendRelation;

import java.util.List;

public interface IFriendRelationDAO extends DAO<FriendRelation> {

    List<FriendRelation> getAllFriendsOfUser(); // TODO: Ajouter en paramètre un objet User (user : User)
    boolean haveFriendRelation(); // TODO: Ajouter en paramètre deux objets User (user1 : User, user2 : User)


}