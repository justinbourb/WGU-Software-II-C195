package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is part of the Database Access Object design pattern. 
* It is used to update data from the database.*/

public class update {
  
    /**This function updates data from the database and returns the results as a ResultSet.
    * @param table, the table as a string
    * @param set, the data to set
    * @param where, the where as a string
    * @exception SQLException, an SQL Exception
    * @return results, a ResultsSet of the data found
    */
    public static void updateData(String table, String set, String where) throws SQLException {
        /* Example usage:
        UPDATE Customers
        SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        WHERE CustomerID = 1;
        */
        Connection connection = connect.startConnection();
        String statement = "UPDATE " + table + " SET " + set + " WHERE " + where;
        System.out.println(statement);
        PreparedStatement query = connection.prepareStatement(statement);
        query.executeUpdate();
    }
}
