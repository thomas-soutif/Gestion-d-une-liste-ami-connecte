package database.DAO;

import database.CLASSES.FriendRequest;

import java.util.List;

public interface  IFriendRequestDAO extends DAO<FriendRequest> {

    List<FriendRequest> getFriendRequestsOfUser(); // TODO: Ajouter en paramètre un objet user (user : User)

}