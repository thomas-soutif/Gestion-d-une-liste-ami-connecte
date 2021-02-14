package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRequest;

import java.util.List;

// Extend interface DAO depuis l'interface IUserDAO avec User en type
public interface IUserDAO extends DAO<AccountUser>{
    List<AccountUser> getAccountOfUsers();

    boolean haveAccountUser(int user1Id);
    AccountUser getAccountOfUser(int id);
}
