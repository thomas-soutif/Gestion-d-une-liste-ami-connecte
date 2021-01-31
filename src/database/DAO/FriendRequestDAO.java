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
        return false;
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
    public boolean update(FriendRequest obj) {
        return false;
    }

    @Override
    public List<FriendRequest> getFriendRequestsOfUser(int userId) {
        return null;
    }


    @Override
    public boolean isFriendRequestExist(int user1Id, int user2Id) {
        return false;
    }
}