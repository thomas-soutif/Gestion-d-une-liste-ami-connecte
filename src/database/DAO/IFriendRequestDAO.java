package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRequest;

import java.util.List;

public interface  IFriendRequestDAO extends DAO<FriendRequest> {

    List<FriendRequest> getFriendRequestsOfUser(AccountUser user);

}