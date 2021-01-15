package database.DAO;
import database.ConnexionPostGreSQL;

import java.sql.Connection;

public interface DAO<T> {

    public Connection conn = ConnexionPostGreSQL.getInstance().getConnection();



}