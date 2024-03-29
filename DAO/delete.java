package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is part of the Database Access Object design pattern. 
* It is used to delete data from the database.*/
public class delete {

    /**This function deletes data from the database and returns the results as a ResultSet.
    * @param table, the table as a string
    * @param where, the where as a string
    * @throws SQLException, an SQL Exception
    */
    public static void deleteData(String table, String where) throws SQLException {
        /* Example usage:
        DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
        */
        Connection connection = connect.startConnection();
        PreparedStatement query = connection.prepareStatement("DELETE FROM " + table + " WHERE " + where);
        query.executeUpdate();
    }

}
