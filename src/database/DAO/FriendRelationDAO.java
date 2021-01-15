package database.DAO;

import database.CLASSES.FriendRelation;

import java.util.List;

public class FriendRelationDAO implements IFriendRelationDAO{


    @Override
    public List<FriendRelation> getAllFriendsOfUser() {
        return null;
    }

    @Override
    public boolean haveFriendRelation() {
        return false;
    }

    @Override
    public boolean delete(FriendRelation obj) {
        return false;
    }

    @Override
    public FriendRelation insert(FriendRelation obj) {
        return null;
    }

    @Override
    public boolean update(FriendRelation obj) {
        return false;
    }
}