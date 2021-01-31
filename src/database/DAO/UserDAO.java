package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.EXCEPTION.ErrorType;
import database.EXCEPTION.FriendRequestException;

import java.sql.PreparedStatement;
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

    @Override
    public AccountUser insert(AccountUser obj) { throws FriendRequestException {
            /**
             * Insérer une relation dans la base de données. L'objet retourné contient dans son id la même que celle généré par la base de données lors de l'insertion.
             * @return FriendRelation
             * */
            try{
                Statement database_instance = conn.createStatement();
                boolean is_account_user_exist = this.haveAccountUser(obj.getFirstUser(), obj.getSecondUser());
                if(is_account_user_exist){
                    // Si jamais la relation existe déja, on ne peut pas insérer dans la base de données, donc il faut levé une exceptions
                    throw new FriendRequestException("Les utilisateurs spécifiés (" + obj.getFirstUser().getId() +
                            " et " + obj.getSecondUser().getId()+ ") sont déja amis", ErrorType.FRIEND_RELATION_ALREADY_EXIST);
                }

                String query = "INSERT into account_user (first_user, second_user,date) VALUES (?,?,?)";
                PreparedStatement preparedStatement =conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,obj.getFirstUser().getId());
                preparedStatement.setInt(2,obj.getSecondUser().getId());
                preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));

                if(preparedStatement.executeUpdate() > 0){
                    // Si l'insertion a été faite
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if(generatedKeys.next()){
                        // Alors on récupére l'id de ce nouveau tuple inséré et le stocke dans l'objet avant de le renvoyer
                        obj.setId(generatedKeys.getInt(1));
                    }
                }

            }catch (SQLException e){

                System.out.println("Erreur UserDAO, insert");
                System.out.println(e + "\n");
            }

            return obj;
        }

        return null;
    }

    @Override
    public boolean update(AccountUser obj) {
        boolean ok = false;
        try{
            PreparedStatement prepareStatement = conn.prepareStatement("UPDATE account_user SET first_user = ?, second_user = ?, date = ? WHERE id = ?");

            prepareStatement.setInt(1, obj.getFirstUser().getId());
            prepareStatement.setInt(2, obj.getSecondUser().getId());
            prepareStatement.setDate(3,new java.sql.Date(System.currentTimeMillis()));
            prepareStatement.setInt(4, obj.getId());

            int nb = prepareStatement.executeUpdate();
            if(nb > 0){
                ok = true;
            }
        }catch (SQLException e){
            System.out.println(e);
        }


        return ok;
    }
}