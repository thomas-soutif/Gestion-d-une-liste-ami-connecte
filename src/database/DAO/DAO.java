package database.DAO;
import database.ConnexionPostGreSQL;
import database.EXCEPTION.CustomException;

import java.sql.Connection;

public interface DAO<T> {

    public Connection conn = ConnexionPostGreSQL.getInstance().getConnection();

    /**
     * Permet la suppression d'un tuple de la base
     *
     * @param obj
     */
    boolean delete(T obj);

    /**
     * Permet de créer une entrée dans la base de données par rapport à un objet
     *
     * @param obj
     */
    T insert(T obj) throws Exception;

    /**
     * Permet de mettre à jour les données d'un tuple dans la base à partir d'un
     * objet passé en paramètre
     *
     * @param obj
     */
    boolean update(T obj) throws CustomException;

}