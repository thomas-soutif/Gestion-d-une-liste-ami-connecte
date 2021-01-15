package database;

import java.sql.Connection;
import database.ConnexionPostGreSQL;
public class TestDAOMain{

public static void main(String[]args){

        System.out.println("test");
        Connection myCo = ConnexionPostGreSQL.getInstance().getConnection();
}


}