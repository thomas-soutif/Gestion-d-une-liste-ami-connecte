package database;

import java.sql.Connection;

public class TestDAOMain{

public static void main(String[]args){

        System.out.println("test");
        Connection myCo = ConnexionPostGreSQL.getInstance().getConnection();
}

}