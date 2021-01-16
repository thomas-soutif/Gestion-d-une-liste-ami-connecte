package database.DAO;

import database.CLASSES.FriendRelation;
import database.CLASSES.UserTemp;

import java.util.List;

public interface IFriendRelationDAO extends DAO<FriendRelation> {

    List<FriendRelation> getAllFriendsOfUser(UserTemp user); // TODO: Modifier par la suite avec la vrai classe User
    boolean haveFriendRelation(UserTemp firstUser, UserTemp secondUser); // TODO: Modifier par la suite avec la vrai classe User



}