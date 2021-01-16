package database.DAO;

import database.CLASSES.FriendRelation;
import database.EXCEPTION.ErrorType;
import database.EXCEPTION.FriendRequestException;

import java.sql.SQLException;
import java.sql.Statement;
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
    public FriendRelation insert(FriendRelation obj) throws FriendRequestException {
        try{
            Statement database_instance = conn.createStatement();
            boolean is_friend_relation_exist = this.haveFriendRelation(); // TODO: Ajouter en paramètre deux objets User (user1 : User, user2 : User)

            // TODO: Pas fini, à continuer

        }catch (SQLException e){

            System.out.println("Erreur FriendRelationDAO, insert");
            System.out.println(e.getCause() + "\n");
        }

        throw new FriendRequestException("Les utilisateurs spécifiés sont déja amis", ErrorType.FRIEND_RELATION_ALREADY_EXIST);
    }

    @Override
    public boolean update(FriendRelation obj) {
        return false;
    }
}