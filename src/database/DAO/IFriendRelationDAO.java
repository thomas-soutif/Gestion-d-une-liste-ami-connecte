package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;

import java.util.List;

public interface IFriendRelationDAO extends DAO<FriendRelation> {

    List<FriendRelation> getAllFriendsOfUser(AccountUser user);
    boolean haveFriendRelation(AccountUser firstUser, AccountUser secondUser);
    List<FriendRelation> getAllFriendRelation();


}