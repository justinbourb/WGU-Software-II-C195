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
    * @exception ClassNotFoundException, a Class Not Found Exception
    * @return results, a ResultsSet of the data found
    */
    public static ResultSet readData(String column, String table, String where) throws SQLException, ClassNotFoundException {
        Connection connection = connect.startConnection();
        PreparedStatement query = connection.prepareStatement("SELECT " + column + " FROM " + table + " WHERE " + where);
        ResultSet results = query.executeQuery();
        return results;
    }
}
