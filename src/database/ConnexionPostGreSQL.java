package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnexionPostGreSQL
{

    private Connection connection;
    private static ConnexionPostGreSQL instance = null;
    private static String connect_url;
    private static String login;
    private static String password;

    protected ConnexionPostGreSQL() // Constructeur
    {
        connect_url = "jdbc:postgresql://postgresql-xelar.alwaysdata.net:5432/xelar_friends_list";
        login = "xelar_project-list-ami";
        password = "2fmjWLrf@YjVKBt";
        try
        {
            System.out.println("Connexion à " + connect_url );
            connection = DriverManager.getConnection(connect_url, login, password);
            System.out.println("Connecté.");

        }
        catch (SQLException e)
        {
            System.out.println("Connexion impossible pour l'utilisateur "+ login + "sur la base de donnée " + connect_url + " Veuillez vérifier vos identifiants.");
            System.out.println(e.getMessage() + "\n");

        }
    }

    public Connection getConnection()
    {

        return connection;
    }

    public static ConnexionPostGreSQL getInstance()
    {

        if(instance == null)
            instance = new ConnexionPostGreSQL();

        return instance;
    }

}
