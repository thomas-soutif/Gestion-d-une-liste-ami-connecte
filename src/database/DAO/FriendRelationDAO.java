package database.DAO;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.EXCEPTION.ErrorType;
import database.EXCEPTION.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRelationDAO implements IFriendRelationDAO{


    @Override
    public List<FriendRelation> getAllFriendsOfUser(AccountUser user) {
        /**
         * Renvoie une liste contenant tout les tuples de la table où le user a son id dans un des champs (de first_user ou second_user)
         * @return List<FriendRelation>
         * */
        List<FriendRelation> list= new ArrayList<>();

        try{
            Statement databasae_instance= conn.createStatement();
            ResultSet result = databasae_instance.executeQuery("SELECT * FROM friend_relation WHERE first_user =" + user.getId() + " OR second_user =" + user.getId() + " ORDER BY id");

            while(result.next()){
                FriendRelation friendRelation = new FriendRelation();
                AccountUser user1 = new AccountUser();
                user1.setId(result.getInt("first_user"));
                AccountUser user2 = new AccountUser();
                user2.setId(result.getInt("second_user"));
                friendRelation.setId(result.getInt("id"));
                friendRelation.setFirstUser(user1);
                friendRelation.setSecondUser(user2);
                friendRelation.setDate(result.getDate("date"));

                list.add(friendRelation);
            }
        }catch (SQLException e){
            System.out.println(e);
        }


        return list;
    }

    @Override
    public boolean haveFriendRelation(AccountUser firstUser, AccountUser secondUser) {
        /**
         * Vérifie si deux utilisateurs sont déja amis et retourne true si c'est le cas, sinon false
         * @return boolean
         * */
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
    public List<FriendRelation> getAllFriendRelation() {
        /**
         * Récupère toute les données de la table sous forme de liste
         * @return List<FriendRelation>
         * */
        List<FriendRelation> list= new ArrayList<>();

        try{
            Statement databasae_instance= conn.createStatement();
            ResultSet result = databasae_instance.executeQuery("SELECT * FROM friend_relation ORDER BY id");

            while(result.next()){
                FriendRelation friendRelation = new FriendRelation();
                AccountUser user1 = new AccountUser();
                user1.setId(result.getInt("first_user"));
                AccountUser user2 = new AccountUser();
                user2.setId(result.getInt("second_user"));
                friendRelation.setId(result.getInt("id"));
                friendRelation.setFirstUser(user1);
                friendRelation.setSecondUser(user2);
                friendRelation.setDate(result.getDate("date"));

                list.add(friendRelation);
            }
        }catch (SQLException e){
            System.out.println(e);
        }
        return list;
    }

    @Override
    public boolean delete(FriendRelation obj) {

        boolean ok = false;
        try{
            Statement database_instance = conn.createStatement();
            int nb = database_instance.executeUpdate("DELETE FROM friend_relation WHERE id= " + obj.getId());
            if(nb >0){
                ok = true;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return ok;
    }

    @Override
    public FriendRelation insert(FriendRelation obj) throws CustomException {
        /**
         * Insérer une relation dans la base de données. L'objet retourné contient dans son id la même que celle généré par la base de données lors de l'insertion.
         * @return FriendRelation
         * */
        try{
            Statement database_instance = conn.createStatement();
            boolean is_friend_relation_exist = this.haveFriendRelation(obj.getFirstUser(), obj.getSecondUser());
            if(is_friend_relation_exist){
                // Si jamais la relation existe déja, on ne peut pas insérer dans la base de données, donc il faut levé une exceptions
                throw new CustomException("Les utilisateurs spécifiés (" + obj.getFirstUser().getId() +
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

        boolean ok = false;
        try{
            PreparedStatement prepareStatement = conn.prepareStatement("UPDATE friend_relation SET first_user = ?, second_user = ?, date = ? WHERE id = ?");

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