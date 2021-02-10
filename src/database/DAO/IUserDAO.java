package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRequest;

import java.util.List;

// Extend interface DAO depuis l'interface IUserDAO avec User en type
public interface IUserDAO extends DAO<AccountUser>{
    List<AccountUser> getAccountOfUsers(String name, String firstname, String pseudo, String password);

    boolean haveAccountUser(int user1Id, int user2Id);
}
