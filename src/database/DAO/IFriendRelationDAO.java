package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;

import java.util.List;

public interface IFriendRelationDAO extends DAO<FriendRelation> {

    List<FriendRelation> getAllFriendsOfUser(AccountUser user); // TODO: Modifier par la suite avec la vrai classe User
    boolean haveFriendRelation(AccountUser firstUser, AccountUser secondUser); // TODO: Modifier par la suite avec la vrai classe User



}