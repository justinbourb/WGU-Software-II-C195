package Helpers;

import java.sql.Connection;
import java.sql.*;

/** This class is not used.*/
public class DatabaseExample {


    private static Connection conn = null;
    private Statement stmt = null;
    private String dbUser = null;
    private String dbPass = null;

    public static void startConnection() {

        //Connection String
        //Server name:  52.206.157.109
        //Database name:  U03QIu
        //Username:  U03QIu
        //Password:  53688051379
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://wgudb.ucertify.com:3306/WJ06rpB";

        //  Database credentials
        final String DBUSER = "U06rpB";
        final String DBPASS = "53688851020";

        boolean res = false;

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not find JDBC driver.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not connect to the database.");
        }

    }
}