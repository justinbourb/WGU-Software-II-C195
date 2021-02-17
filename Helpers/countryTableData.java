package Helpers;

import DAO.read;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** This class generates firstLevelDivisionTableData */
public class countryTableData {
    /** This function gets the names of all the countries and returns them
     * as an ArrayList.  Which can be used to populate the combo box menu."
     * @return data, an ArrayList of first level division names.
     * @throws SQLException an exception
     * @param connection a Connection
     */
    public static ArrayList getCountryNames(Connection connection) throws SQLException {
        ArrayList data = new ArrayList<String>();
        String column = "*";
        String table = "countries";
        ResultSet results = read.readData(column, table);

        while(results.next()){
            data.add(results.getString("Country"));
        }

        return data;
    }
}
