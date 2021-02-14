import database.CLASSES.AccountUser;
import database.DAO.UserDAO;
import database.EXCEPTION.CustomException;

import java.util.ArrayList;
import java.util.List;

class UserTestDAO {
    public static void main(String[] args) {


        UserDAO dao = new UserDAO();

        AccountUser user = new AccountUser();
        user.setFirstName("Thomas");
        user.setName("Soutif");
        user.setPseudo("Xelèèèèèèèèèreeee");
        user.setPassword("jemappelleThomas");

        try{

            dao.insert(user);
        }catch (CustomException e){
            System.out.println(e);
            System.exit(1);
        }

        List<AccountUser> list = dao.getAccountOfUsers();

        for(AccountUser account : list)  {
            System.out.println(account.getFirstName());
        }

    }
}