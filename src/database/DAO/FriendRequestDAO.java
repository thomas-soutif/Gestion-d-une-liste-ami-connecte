package database.DAO;

import database.CLASSES.FriendRequest;
import database.EXCEPTION.ErrorType;
import database.EXCEPTION.CustomException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FriendRequestDAO implements IFriendRequestDAO {


    @Override
    public boolean delete(FriendRequest obj) {
        boolean ok = false;
        try{
            Statement database_instance = conn.createStatement();
            int nb = database_instance.executeUpdate("DELETE FROM friend_request WHERE id= " + obj.getId());
            if(nb >0){
                ok = true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return ok;
    }

    @Override
    public FriendRequest insert(FriendRequest obj) throws CustomException {
        try{
            Statement database_instance = conn.createStatement();
            boolean isFriendRequestExist = this.isFriendRequestExist(obj.getFrom_user().getId(), obj.getTo_user().getId());
            if(isFriendRequestExist){
                // Si jamais la relation existe déja, on ne peut pas insérer dans la base de données, donc il faut levé une exceptions
                throw new CustomException("Les utilisateurs spécifiés (" + obj.getFrom_user().getId() +
                        " et " + obj.getTo_user().getId()+ ") ont déja une requête d'ami", ErrorType.FRIEND_REQUEST_ALREADY_EXIST);
            }

            String query = "INSERT into friend_request (from_user, to_user,date) VALUES (?,?,?)";
            PreparedStatement preparedStatement =conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,obj.getFrom_user().getId());
            preparedStatement.setInt(2,obj.getTo_user().getId());
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

            System.out.println("Erreur FriendRequestDAO, insert");
            System.out.println(e + "\n");
        }

        return obj;
    }

    @Override
    public boolean update(FriendRequest obj) throws CustomException {
        if(obj.getId() == null){
            throw new CustomException("L'objet FriendRequest doit avoir un id set et existant pour pouvoir être mit à jour dans la bd", ErrorType.ID_IS_NULL);
        }

        boolean ok = false;

        if(isFriendRequestExist(obj.getFrom_user().getId(), obj.getTo_user().getId())){
            throw new CustomException("Les utilisateurs spécifiés (" + obj.getFrom_user().getId() +
                    " et " + obj.getTo_user().getId()+ ") ont déja une requete d'ami", ErrorType.FRIEND_REQUEST_ALREADY_EXIST);
        }
        try{
            PreparedStatement prepareStatement = conn.prepareStatement("UPDATE friend_request SET from_user = ?, to_user = ?, date = ? WHERE id = ?");

            prepareStatement.setInt(1, obj.getFrom_user().getId());
            prepareStatement.setInt(2, obj.getTo_user().getId());
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

    @Override
    public List<FriendRequest> getFriendRequestsOfUser(int userId) {
        return null;
    }


    @Override
    public boolean isFriendRequestExist(int user1Id, int user2Id) {
        boolean have_request_relation = true;
        try{
            String query1 = "SELECT COUNT(*) FROM friend_request WHERE from_user = ? AND to_user = ?";
            String query2 = "SELECT COUNT(*) FROM friend_request WHERE to_user = ? AND from_user  = ?";

            PreparedStatement preparedStatement1 =conn.prepareStatement(query1);
            preparedStatement1.setInt(1,user1Id);
            preparedStatement1.setInt(2,user2Id);

            PreparedStatement preparedStatement2 =conn.prepareStatement(query2);
            preparedStatement2.setInt(1,user1Id);
            preparedStatement2.setInt(2,user2Id);



            ResultSet result = preparedStatement1.executeQuery();
            while(result.next()){
                if(result.getInt("count") > 0){
                    return true;
                }else{
                    have_request_relation = false;
                }
            }
            // On test la deuxième query
            result = preparedStatement2.executeQuery();
            while(result.next()){
                if(result.getInt("count") > 0){
                    have_request_relation = true;
                }else{
                    have_request_relation = false;
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return have_request_relation;
    }
}