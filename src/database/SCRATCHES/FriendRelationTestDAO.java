import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.DAO.FriendRelationDAO;

import java.util.List;

class FriendRelationTestDAO {
    public static void main(String[] args) {

        System.out.println("test");
        // On créer un objet
        FriendRelationDAO dao = new FriendRelationDAO();
//        try{
//            // Ensuite on set des informations pour tester
//            AccountUser user1 = new AccountUser();
//            AccountUser user2 = new AccountUser();
//            user1.setId(1);
//            user2.setId(2);
//            FriendRelation relation = new FriendRelation();
//            relation.setFirstUser(user1);
//            relation.setSecondUser(user2);
//
//            // Remarque : ici on n'a pas set l'id de la nouvelle relation car elle n'existe pas en base de données non plus
//            dao.insert(relation);
//
//            // A présent notre objet relation contient l'id qui est la même que celle que la base de données à générer lors de l'insertion
//            System.out.println(relation);
//
//            // Suppression de la relation créer
////            dao.delete(relation);
//
//            // Modification de l'objet en base de données
//            user2.setId(3);
//            relation.setSecondUser(user2);
//            dao.update(relation);
//
//
//        }catch(FriendRequestException e){
//            System.out.println(e.getErrorType()); // Affiche le type de l'erreur, ici FRIEND_ALREADY_EXIST
//            System.out.println(e.getMessage());
//
//        }

        AccountUser user1 = new AccountUser();
        user1.setId(3);
        List<FriendRelation> result = dao.getAllFriendRelation();
        for(FriendRelation relation : result){
            System.out.println(relation);
        }
        System.out.println("-----------------");

        List<FriendRelation> result2 = dao.getAllFriendsOfUser(user1);
        for(FriendRelation relation : result2){
            System.out.println(relation);
        }


    }
}