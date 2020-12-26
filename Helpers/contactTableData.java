package Helpers;

import DAO.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class contactTableData {
    public static ArrayList getContactNames(Connection connection) throws SQLException {
        ArrayList contactNameArrayList = new ArrayList();
        String column = "Contact_Name";
        String table = "contacts";
        ResultSet results = read.readData(column, table, connection);

        while (results.next()) {
            String contactName = results.getString("Contact_Name");
            contactNameArrayList.add(contactName);
        }
        return contactNameArrayList;
    }
}
