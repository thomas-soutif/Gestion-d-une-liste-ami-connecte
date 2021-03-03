package database.DAO;

import database.CLASSES.AccountUser;
import database.EXCEPTION.CustomException;
import database.EXCEPTION.ErrorType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {


    private Object FriendRequestException;

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
                account_user.setPseudo(result.getString("pseudo"));
                account_user.setPassword(result.getString("password"));

                list.add(account_user);
            }
        }catch (SQLException e){
            System.out.println(e);
        }


        return list;
    }

    @Override
    public boolean haveAccountUser(int user1Id) {

        try {
            String query1 = "SELECT COUNT(*) FROM account_user WHERE id = ?";


            PreparedStatement preparedStatement1 =conn.prepareStatement(query1);
            preparedStatement1.setInt(1,user1Id);



            ResultSet result = preparedStatement1.executeQuery();
            while(result.next()) {
                if (result.getInt("count") > 0) {
                    return true;
                }
            }
            return false;
        }
        catch (SQLException e){
            System.out.println(e);
        }

        return false;
    }

    @Override
    public AccountUser getAccountOfUser(int id) {
        try{
            Statement database_instance= conn.createStatement();
            ResultSet result = database_instance.executeQuery("SELECT * FROM account_user WHERE id= " + id);

            while(result.next()){
                AccountUser account_user = new AccountUser();

                account_user.setId(result.getInt("id"));
                account_user.setFirstName(result.getString("firstname"));
                account_user.setName(result.getString("name"));
                account_user.setPseudo(result.getString("pseudo"));
                account_user.setPassword(result.getString("password"));

                return account_user;
            }

        }catch (SQLException e){
            System.out.println(e);
        }

        return null;
    }

    @Override
    public boolean delete(AccountUser obj) {
        boolean ok = false;
        try{
            Statement database_instance = conn.createStatement();
            int nb = database_instance.executeUpdate("DELETE FROM account_user WHERE id= " + obj.getId());
            if(nb >0){
                ok = true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return ok;
    }

    /**
     * Insérer un compte dans la base de données. L'objet retourné contient dans son id la même que celle généré par la base de données lors de l'insertion.
     * @return AccountUser
     *
     * */
    @Override
    public AccountUser insert(AccountUser obj)  throws CustomException {

            try{
                Statement database_instance = conn.createStatement();
                boolean is_account_user_exist = this.haveAccountUser(obj.getId());
                if(is_account_user_exist){
                    // Si jamais le compte existe déja, on ne peut pas le créer de nouveau dans la base de données, donc il faut lever une exception
                    throw new CustomException("Les comptes utilisateurs spécifiés (" + obj.getId() + ") sont déja créés", ErrorType.ACCOUNT_USER_ALREADY_EXIST);
                }

                String query = "INSERT into account_user (name,firstname,pseudo,password) VALUES (?,?,?,?)";
                PreparedStatement preparedStatement =conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, obj.getName());
                preparedStatement.setString(2, obj.getFirstName());
                preparedStatement.setString(3, obj.getPseudo());
                preparedStatement.setString(4, obj.getPassword());

                if(preparedStatement.executeUpdate() > 0){
                    // Si l'insertion a été faite
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if(generatedKeys.next()){
                        // Alors on récupère l'id de ce nouveau tuple inséré et le stocke dans l'objet avant de le renvoyer
                        obj.setId(generatedKeys.getInt(1));
                    }
                }

            }catch (SQLException e){

                System.out.println("Erreur UserDAO, insert");
                System.out.println(e + "\n");
            }

            return obj;
        }

    @Override
    public boolean update(AccountUser obj) {
        boolean ok = false;
        try{
            PreparedStatement prepareStatement = conn.prepareStatement("UPDATE account_user SET name = ?, firstname = ?, pseudo = ?, password = ? WHERE id = ?");
            prepareStatement.setString(1, obj.getName());
            prepareStatement.setString(2,obj.getFirstName());
            prepareStatement.setString(3, obj.getPseudo());
            prepareStatement.setString(4, obj.getPassword());
            prepareStatement.setInt(5, obj.getId());
            int nb = prepareStatement.executeUpdate();
            if(nb > 0){
                ok = true;
            }
        }catch (SQLException e){
            System.out.println(e);
        }


        return ok;
    }

    public AccountUser getUserById(int userID){

        AccountUser account_user = new AccountUser();
        try{
            Statement database_instance= conn.createStatement();
            ResultSet result = database_instance.executeQuery("SELECT * FROM account_user where id = " + userID);
            while (result.next()) {
                account_user.setId(result.getInt("id"));
                account_user.setFirstName(result.getString("firstname"));
                account_user.setName(result.getString("name"));
                account_user.setPseudo(result.getString("pseudo"));
                account_user.setPassword(result.getString("password"));

                return account_user;
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return  null;
    }
}