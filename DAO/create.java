package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class is part of the Database Access Object design pattern. 
* It is used to create data from the database.*/
public class create {


    /**This function creates data in the database and returns the results as a ResultSet.
    * @param columns, the column(s) name as a string
    * @param table, the table as a string
    * @param values, the where as a string
    * @exception SQLException, an SQL Exception
    * @return results, a ResultsSet of the data found
    */
    public static void createData(String table, String columns, String values) throws SQLException {
        /* example usage:
        INSERT INTO Customers (CustomerName, City, Country)
        VALUES ('Cardinal', 'Stavanger', 'Norway');
        */
        try(Connection connection = connect.startConnection()) {
            String statement = "INSERT INTO " + table + " (" + columns + ")" + " VALUES (" + values + ")";
            System.out.println(statement);
            PreparedStatement query = connection.prepareStatement(statement);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Creation was not successful.");
        }
    }
}
