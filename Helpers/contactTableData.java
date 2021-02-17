package Helpers;

import DAO.read;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**This class handles contact information from the database*/
public class contactTableData {
    /** This function returns an arraylist of contact names from the database
     * @param connection, a Connection
     * @return contactNameArrayList, an array list of names
     * @throws SQLException an exception
     */
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

    /**This function finds the Contact_ID from the database, based on name
     *
     * @param name, String a contact name
     * @return customer id or null
     */
    public static String getContactID(String name) {
        try(ResultSet results = read.readData("Contact_ID", "contacts","Contact_Name = '" + name + "'")) {
            ;
            if (results.next()) {
                return results.getString("Contact_ID");
            }
        } catch (Exception e) {}
        return null;
    }
}
