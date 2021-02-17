package Helpers;

import DAO.connect;
import DAO.read;
import Model.firstLevelDivisionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.DataOutput;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.DoubleAccumulator;

/** This class generates firstLevelDivisionTableData */
public class firstLevelDivisionTableData {

    /**This function pulls the first level division data from the database, creates a firstLevelDivisionModel object and adds all
     * objects to an observableArrayList so the database data can be added to a tableView.
     * @return firstLevelDivisionTableData, an observableArrayList
     * @throws SQLException an exception
     */
    public static ObservableList<firstLevelDivisionModel> getFirstLevelDivisionData() throws SQLException {
        ObservableList<firstLevelDivisionModel> firstLevelDivsionTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "first_level_divisions";
        ResultSet results = read.readData(column, table);

        while(results.next()){
            String division_id = results.getString("Division_ID");
            String division = results.getString("Division");
            String COUNTRY_ID = results.getString("COUNTRY_ID");
            firstLevelDivisionModel firstLevelDivision = new firstLevelDivisionModel(division_id,division,COUNTRY_ID);
            firstLevelDivsionTableData.add(firstLevelDivision);
        }
        return firstLevelDivsionTableData;
    }

    /** This function gets the names of all the first level divisions
     *  for a specific country and returns them
     * as an ArrayList.  Which can be used to populate the combo box menu."
     * @param connection a Connection
     * @param  country_ID a string
     * @return data, an ArrayList of first level division names.
     * @throws SQLException an exception
     */
    public static ArrayList getFirstLevelDivisionNames(Connection connection, String country_ID) throws SQLException {
        ArrayList data = new ArrayList<String>();
        String column = "*";
        String table = "first_level_divisions";
        String where = "COUNTRY_ID = " + country_ID;
        ResultSet results = read.readData(column, table, where, connection);

        while(results.next()){
            data.add(results.getString("Division"));
        }

        return data;
    }

    /** This function returns the firstLevelDivisionID from the provided String name
     *
     * @param name a String
     * @return the ID
     * @throws SQLException an exception
     */
    public static String getFirstLevelDivisionID(String name) throws SQLException {
        String returnValue = null;
        String column = "*";
        String table = "first_level_divisions";
        String where = "Division = '" + name + "'";
        ResultSet results = read.readData(column, table, where);
        if (results.next()) {
            returnValue = results.getString("Division_ID");
        }
        try {
            connect.closeConnection();
        } catch (Exception e) {}
        return returnValue;
    }
}
