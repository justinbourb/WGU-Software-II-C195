package DAO;

import java.sql.*;

/**This class is part of the Database Access Object design pattern. 
* It is used to update data from the database.*/

public class update {
  
    /**This function updates data from the database and returns the results as a ResultSet.
    * @param table, the table as a string
    * @param set, the data to set
    * @param where, the where as a string
    * @exception SQLException, an SQL Exception
    */
    public static void updateData(String table, String set, String where) throws SQLException {
        /* Example usage:
        UPDATE Customers
        SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        WHERE CustomerID = 1;
        */
        try(Connection connection = connect.startConnection()) {
            String statement = "UPDATE " + table + " SET " + set + " WHERE " + where;
            PreparedStatement query = connection.prepareStatement(statement);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update not successful.");
            e.printStackTrace();
        }
    }

    public static void updateDataTimestamp(String table, String set, String where, Timestamp start, Timestamp end) throws SQLException {
        /* Example usage:
        UPDATE Customers
        SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        WHERE CustomerID = 1;
        */
        try(Connection connection = connect.startConnection()) {
            //using ? allows the use of parameterIndex, which starts a 1 instead of 0
            String statement = "UPDATE " + table + " SET Start = ?, End = ? " + " WHERE " + where;
            PreparedStatement query = connection.prepareStatement(statement);
             //how to find parameter index???
            query.setTimestamp(1, start);
            query.setTimestamp(2, end);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update not successful.");
            e.printStackTrace();
        }
    }
}
