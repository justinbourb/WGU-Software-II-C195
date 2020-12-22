package Helpers;

import DAO.read;
import Model.firstLevelDivisionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** This class generates firstLevelDivisionTableData */
public class firstLevelDivisionTableData {

    /**This function pulls the first level division data from the database, creates a firstLevelDivisionModel object and adds all
     * objects to an observableArrayList so the database data can be added to a tableView.
     * @return firstLevelDivisionTableData, an observableArrayList
     * @throws SQLException
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

    /** This function gets the names of all the first level divisions and returns them
     * as an ArrayList.  Which can be used to populate the combo box menu."
     * @return data, an ArrayList of first level division names.
     * @throws SQLException
     */
    public static ArrayList getFirstLevelDivisionNames() throws SQLException {
        ArrayList data = new ArrayList<String>();
        String column = "*";
        String table = "first_level_divisions";
        ResultSet results = read.readData(column, table);

        while(results.next()){
            data.add(results.getString("Division"));
        }

        return data;
    }

}
