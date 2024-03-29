package DAO;

import java.sql.*;

/**This class is part of the Database Access Object design pattern. 
* It is used to read data from the database.*/
public class read {

    /**This function reads data from the database and returns the results as a ResultSet.
    * @param column, the column name as a string
    * @param table, the table as a string
    * @param where, the where as a string
    * @throws SQLException, an SQL Exception
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


    /**This function reads data from the database
     *
     * @param column database column
     * @param table database table
     * @param where where value
     * @param connection a Connection
     * @return data
     * @throws SQLException an exception
     */
    public static ResultSet readData(String column, String table, String where, Connection connection) throws SQLException {
        PreparedStatement query = null;
        try {
            String queryText = "SELECT " + column + " FROM " + table + " WHERE " + where;
            query = connection.prepareStatement(queryText);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }

    /**This function reads the data from the database
     *
     * @param column database column
     * @param table database table
     * @param where where value
     * @param connection a Connection
     * @param start start Timestamp
     * @param end end Timestamp
     * @return returns data from the database
     * @throws SQLException an exception
     */
    public static ResultSet readData(String column, String table, String where, Connection connection, Timestamp start, Timestamp end) throws SQLException {
        PreparedStatement query = null;
        //using ? in the query allows the use of paramterIndex which starts at 1, not 0
        //this allows a Timestamp to be be written or read from the database
        try {
            String queryText = "SELECT " + column + " FROM " + table + " WHERE " + where;
            query = connection.prepareStatement(queryText);
            query.setTimestamp(1, start);
            query.setTimestamp(2, end);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }
    
    /**This function reads data from the database and returns the results as a ResultSet.
    * This overload function is for all results (no WHERE clause).
    * @param column, the column name as a string
    * @param table, the table as a string
    * @throws SQLException, an SQL Exception
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

    /**This function reads the data from the database
     *
     * @param column database column
     * @param table database table
     * @param connection a Connection
     * @return returns the data
     * @throws SQLException an exception
     */
    public static ResultSet readData(String column, String table, Connection connection) throws SQLException {
        PreparedStatement query = null;
        try {
            query = connection.prepareStatement("SELECT " + column + " FROM " + table);
        }
        catch (Exception e) {}
        return query.executeQuery();
    }
}
