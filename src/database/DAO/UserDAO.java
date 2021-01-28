package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {


    @Override
    public List<AccountUser> getAccountOfUsers() {
        List<AccountUser> list= new ArrayList<>();

        try{
            Statement database_instance= conn.createStatement();
            ResultSet result = database_instance.executeQuery("SELECT * FROM account_user ORDER BY id");

            while(result.next()){
                AccountUser account_user = new AccountUser();

                account_user.setId(result.getInt("id"));
                account_user.setFirstName(result.getString("firstname"));
                account_user.setName(result.getString("name"));
                account_user.setPassword(result.getString("password"));

                list.add(account_user);
            }
        }catch (SQLException e){
            System.out.println(e);
        }


        return list;
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