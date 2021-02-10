import database.CLASSES.AccountUser;
import database.CLASSES.FriendRequest;
import database.DAO.FriendRequestDAO;
import database.EXCEPTION.CustomException;

import java.util.List;

class FriendRequestTestDAO {
    public static void main(String[] args) {
        FriendRequestDAO dao = new FriendRequestDAO();

        // Ensuite on set des informations pour tester
        AccountUser user1 = new AccountUser();
        AccountUser user2 = new AccountUser();
        user1.setId(1);
        user2.setId(3);
        FriendRequest relation = new FriendRequest();
        relation.setFrom_user(user1);
        relation.setTo_user(user2);

        // Remarque : ici on n'a pas set l'id de la nouvelle relation car elle n'existe pas en base de données non plus
        try{
            dao.insert(relation);
        }catch(CustomException e){
            System.out.println(e.getErrorType()); // Affiche le type de l'erreur, ici FRIEND_ALREADY_EXIST
            System.out.println(e.getMessage());
            System.out.println("de");

        }

        try{
            user2.setId(2);
            relation.setTo_user(user2);
            relation.setId(1);
            dao.update(relation);
        }catch (CustomException e){
            System.out.println(e.getErrorType());
            System.out.println(e.getMessage());
            System.out.println("po");
        }


        List<FriendRequest> list = dao.getFriendRequestsOfUser(user1.getId());

        for(FriendRequest request : list){
            System.out.println(request);
        }

        // A présent notre objet relation contient l'id qui est la même que celle que la base de données à générer lors de l'insertion
//            System.out.println(relation);
//
//            // Suppression de la relation créer
////            dao.delete(relation);
//
//            // Modification de l'objet en base de données
//            user2.setId(3);
//            relation.setSecondUser(user2);
//            dao.update(relation);



    }
}