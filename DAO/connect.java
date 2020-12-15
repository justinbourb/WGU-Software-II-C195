package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
Connection String
Server name: wgudb.ucertify.com
Port: 3306
Database name: WJ06rpB
Username: U06rpB
Password: 53688851020
*/

/** This class creates a connection to the database.*/
public class connect {
    //db url parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06rpB";
    // db url
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    //mysqlDriver
    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;
    private static final String username = "U06rpB";
    private static final String password = "53688851020";

    /**This function starts the database connection.
     * @return connection - a database connection*/
    public static Connection startConnection() throws ClassNotFoundException {
        try {
            Class.forName(MYSQLJBCDriver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**This function closes the database connection.*/
    public static void closeConnection() throws SQLException {
        try {
            connection.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}