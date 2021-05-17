package conectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

public Connection connection;
    static final String DB_URL = "jdbc:mysql://localhost/highscore";
    static final String USER = "root";
    static final String PASS = "";


    public Connection getConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           connection = DriverManager.getConnection(DB_URL,USER,PASS);

        } catch (ClassNotFoundException e) {
            System.out.println("Chyba1");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Chyba2");
            e.printStackTrace();
        }

        return connection;
    }

}
