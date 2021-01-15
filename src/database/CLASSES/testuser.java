package database.CLASSES;
import java.sql.*;

public class testuser {

        public static void main(String[] args) {
            System.out.println("Table Creation user!");
            Connection con = null;
            String url = "jdbc:postgresql://postgresql-xelar.alwaysdata.net:5432/xelar_friends_list";
            String dbName = "xelar_friends_list";
            String driverName = "postgresql-42.2.18.jar";
            String userName = "xelar_project-list-ami";
            String password = "2fmjWLrf@YjVKBt";
            try{
                Class.forName(driverName).newInstance();
                con = DriverManager.getConnection(url+dbName, userName, password);
                try{
                    Statement st = con.createStatement();
                    String table = "CREATE TABLE User(Use_code integer, Use_name varchar(10))";
                    st.executeUpdate(table);
                    System.out.println("Table creation process successfully!");
                }
                catch(SQLException s){
                    System.out.println("Table all ready exists!");
                }
                con.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
