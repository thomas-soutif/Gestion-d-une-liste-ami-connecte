package database.DAO;

import database.CLASSES.FriendRelation;
import database.CLASSES.UserTemp;
import database.EXCEPTION.ErrorType;
import database.EXCEPTION.FriendRequestException;

import java.sql.*;
import java.util.List;

public class FriendRelationDAO implements IFriendRelationDAO{


    @Override
    public List<FriendRelation> getAllFriendsOfUser(UserTemp user) {
        return null;
    }

    @Override
    public boolean haveFriendRelation(UserTemp firstUser, UserTemp secondUser) {
        boolean have_friend_relation = true;
        try{
            String query = "SELECT COUNT(*) FROM friend_relation WHERE first_user = ? AND second_user = ?";
            PreparedStatement preparedStatement =conn.prepareStatement(query);
            preparedStatement.setInt(1,firstUser.getId());
            preparedStatement.setInt(2,secondUser.getId());

            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                if(result.getInt("count") > 0){
                    have_friend_relation= true;
                }else{
                    have_friend_relation = false;
                }
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return have_friend_relation;
    }

    @Override
    public boolean delete(FriendRelation obj) {
        return false;
    }

    @Override
    public FriendRelation insert(FriendRelation obj) throws FriendRequestException {
        /**
         * Insérer une relation dans la base de données. L'objet retourné contient dans son id la même que celle généré par la base de données lors de l'insertion.
         * @return FriendRelation
         * */
        try{
            Statement database_instance = conn.createStatement();
            boolean is_friend_relation_exist = this.haveFriendRelation(obj.getFirstUser(), obj.getSecondUser()); // TODO: Ajouter en paramètre deux objets User (user1 : User, user2 : User)
            if(is_friend_relation_exist){
                // Si jamais la relation existe déja, on ne peut pas insérer dans la base de données, donc il faut levé une exceptions
                throw new FriendRequestException("Les utilisateurs spécifiés (" + obj.getFirstUser().getId() +
                        " et " + obj.getSecondUser().getId()+ ") sont déja amis", ErrorType.FRIEND_RELATION_ALREADY_EXIST);
            }

            String query = "INSERT into friend_relation (first_user, second_user,date) VALUES (?,?,?)";
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

            System.out.println("Erreur FriendRelationDAO, insert");
            System.out.println(e + "\n");
        }

        return obj;
    }

    @Override
    public boolean update(FriendRelation obj) {
        return false;
    }
}