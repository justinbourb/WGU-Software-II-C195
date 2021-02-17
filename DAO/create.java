package DAO;

import java.sql.*;

/**This class is part of the Database Access Object design pattern. 
* It is used to create data from the database.*/
public class create {


    /**This function creates data in the database and returns the results as a ResultSet.
    * @param columns, the column(s) name as a string
    * @param table, the table as a string
    * @param values, the where as a string
    */
    public static void createData(String table, String columns, String values) {
        /* example usage:
        INSERT INTO Customers (CustomerName, City, Country)
        VALUES ('Cardinal', 'Stavanger', 'Norway');
        */
        try(Connection connection = connect.startConnection()) {
            String statement = "INSERT INTO " + table + " (" + columns + ")" + " VALUES (" + values + ")";
            PreparedStatement query = connection.prepareStatement(statement);

            query.executeUpdate();

        } catch (Exception e) {
            System.out.println("Creation was not successful.");
        }
    }

    /** This function creates data in the database
     *
     * @param table table
     * @param columns column
     * @param values values
     * @param start start time
     * @param end end time
     * @throws SQLException an exception
     */
    public static void createData(String table, String columns, String values, Timestamp start, Timestamp end) throws SQLException {
        /* example usage:
        INSERT INTO Customers (CustomerName, City, Country)
        VALUES ('Cardinal', 'Stavanger', 'Norway');
        */
        //using ? (not '?') in the Values field allows the use of the parameterIndex which starts at 1 instead of 0
        try(Connection connection = connect.startConnection()) {
            String statement = "INSERT INTO " + table + " (" + columns + ")" + " VALUES (" + values + ")";
            PreparedStatement query = connection.prepareStatement(statement);
            query.setTimestamp(1, start);
            query.setTimestamp(2, end);
            query.executeUpdate();

        } catch (Exception e) {
            System.out.println("Creation was not successful.");
            e.printStackTrace();
        }
    }
}
