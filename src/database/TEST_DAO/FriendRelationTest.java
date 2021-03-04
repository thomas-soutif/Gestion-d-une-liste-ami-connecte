package database.TEST_DAO;
import database.CLASSES.FriendRelation;
import database.DAO.FriendRelationDAO;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
public class FriendRelationTest {
    FriendRelationDAO dao;

    @Before
    public void setup() throws Exception{
        // On va s'assurer que toute les données que l'on veut tester sont dans la base de données et correct
//        FriendRelationDAO friendRelationDao = new FriendRelationDAO();
//        FriendRelation relation1 =  friendRelationDao.getFriendRelationById(1);
//        FriendRelation relation2 = friendRelationDao.getFriendRelationById(2);
//
//        if(relation1.getDate()
        dao = new FriendRelationDAO();
    }
    @Test
    public void TestUnicityConstraintBetweenFirstUserAndSecondUser(){

        List<FriendRelation> all_friend_relation = dao.getAllFriendRelation();

        for(FriendRelation relation : all_friend_relation){
            int actual_id = relation.getId();
            for(FriendRelation relation2 : all_friend_relation){
                if(relation2.getId() != actual_id){
                    if(relation2.getFirstUser().getId() == relation.getSecondUser().getId() || relation2.getSecondUser().getId() == relation.getFirstUser().getId() || relation2.getSecondUser().getId() == relation.getSecondUser().getId()){
                        fail("Conflit d'unicité avec les users " + relation.getFirstUser().getId() + " et " + relation.getSecondUser().getId());
                    }
                }
            }

        }
        assertTrue(true); // Tout va bien
    }
}
