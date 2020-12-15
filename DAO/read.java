package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class read {

    public static ResultSet readData(String column, String table, String where) throws SQLException, ClassNotFoundException {
        Connection connection = connect.startConnection();
        PreparedStatement query = connection.prepareStatement("SELECT " + column + " FROM " + table + " WHERE " + where);
        ResultSet results = query.executeQuery();
        return results;
    }
}
