package database.DAO;

import database.CLASSES.AccountUser;

import java.util.List;

public class UserDAO implements IUserDAO {


    @Override
    public List<AccountUser> getAccountOfUsers() {
        return null;
    }

    @Override
    public boolean delete(AccountUser obj) {
        return false;
    }

    @Override
    public AccountUser insert(AccountUser obj) {
        return null;
    }

    @Override
    public boolean update(AccountUser obj) {
        return false;
    }
}