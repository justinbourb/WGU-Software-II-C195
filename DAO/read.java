package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is part of the Database Access Object design pattern. 
* It is used to read data from the database.*/
public class read {

    /**This function reads data from the database and returns the results as a ResultSet.
    * @param column, the column name as a string
    * @param table, the table as a string
    * @param where, the where as a string
    * @exception SQLException, an SQL Exception
    * @return results, a ResultsSet of the data found
    */
    public static ResultSet readData(String column, String table, String where) throws SQLException {
        PreparedStatement query = null;
        try {
            Connection connection = connect.startConnection();
            query = connection.prepareStatement("SELECT " + column + " FROM " + table + " WHERE " + where);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }

    public static ResultSet readData(String column, String table, String where, Connection connection) throws SQLException {
        PreparedStatement query = null;
        try {
            String queryText = "SELECT " + column + " FROM " + table + " WHERE " + where;
            query = connection.prepareStatement(queryText);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }
    
    /**This function reads data from the database and returns the results as a ResultSet.
    * This overload function is for all results (no WHERE clause).
    * @param column, the column name as a string
    * @param table, the table as a string
    * @exception SQLException, an SQL Exception
    * @return results, a ResultsSet of the data found
    */

    public static ResultSet readData(String column, String table) throws SQLException {
        PreparedStatement query = null;
        try {
            Connection connection = connect.startConnection();
            query = connection.prepareStatement("SELECT " + column + " FROM " + table);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }

    public static ResultSet readData(String column, String table, Connection connection) throws SQLException {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement("SELECT " + column + " FROM " + table);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }
}
