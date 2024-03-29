package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
    /**an ArrayList of all Connections*/
    public static ArrayList<Connection> allConnections = new ArrayList();

    /**This function starts the database connection.
     * @return connection - a database connection*/
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            connection = DriverManager.getConnection(jdbcURL, username, password);
            allConnections.add(connection);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**This function closes the database connection.*/
    public static void closeConnection() {
        try {
            for(Connection eachConnection : allConnections) {
                eachConnection.close();
                allConnections.remove(eachConnection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}