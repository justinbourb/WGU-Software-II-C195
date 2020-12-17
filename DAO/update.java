package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is part of the Database Access Object design pattern. 
* It is used to update data from the database.*/

public class update {
  
    /**This function updates data from the database and returns the results as a ResultSet.
    * @param column, the column name as a string
    * @param table, the table as a string
    * @param where, the where as a string
    * @exception SQLException, an SQL Exception
    * @return results, a ResultsSet of the data found
    */
    public static ResultSet updateData(String column, String table, String where) throws SQLException {
        /* Example usage:
        UPDATE Customers
        SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        WHERE CustomerID = 1;
        */
        Connection connection = connect.startConnection();
        PreparedStatement query = connection.prepareStatement("UPDATE " + column + " SET " + table + " WHERE " + where);
        return query.executeQuery();
    }
}
