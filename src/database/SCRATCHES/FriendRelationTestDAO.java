package database.SCRATCHES;

import database.CLASSES.AccountUser;
import database.CLASSES.FriendRelation;
import database.DAO.FriendRelationDAO;
import database.EXCEPTION.FriendRequestException;

class FriendRelationTestDAO {
    public static void main(String[] args) {

        System.out.println("test");
        // On créer un objet
        FriendRelationDAO dao = new FriendRelationDAO();
        try{
            // Ensuite on set des informations pour tester
            AccountUser user1 = new AccountUser();
            AccountUser user2 = new AccountUser();
            user1.setId(1);
            user2.setId(3);
            FriendRelation relation = new FriendRelation();
            relation.setFirstUser(user1);
            relation.setSecondUser(user2);

            // Remarque : ici on n'a pas set l'id de la nouvelle relation car elle n'existe pas en base de données non plus
            dao.insert(relation);

            // A présent notre objet relation contient l'id qui est la même que celle que la base de données à générer lors de l'insertion
            System.out.println(relation);

        }catch(FriendRequestException e){
            System.out.println(e.getErrorType()); // Affiche le type de l'erreur, ici FRIEND_ALREADY_EXIST
            System.out.println(e.getMessage());
        }


    }
}