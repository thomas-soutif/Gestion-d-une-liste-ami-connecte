package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRequest;

import java.util.List;

public class FriendRequestDAO implements IFriendRequestDAO {


    @Override
    public boolean delete(FriendRequest obj) {
        return false;
    }

    @Override
    public FriendRequest insert(FriendRequest obj) {
        return null;
    }

    @Override
    public boolean update(FriendRequest obj) {
        return false;
    }

    @Override
    public List<FriendRequest> getFriendRequestsOfUser(AccountUser user) {
        return null;
    }
}