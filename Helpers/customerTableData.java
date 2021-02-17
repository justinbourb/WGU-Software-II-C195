package Helpers;

import DAO.read;
import Model.customerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** This class generates customerTableData */
public class customerTableData {

    /**This function pulls the customer data from the database, creates a customerModel object and adds all
     * objects to an observableArrayList so the database data can be added to a tableView.
     * @return customerTableData, an observableArrayList
     * @throws SQLException an exception
     */
    public static ObservableList<customerModel> getCustomersData() throws SQLException {
        ObservableList<customerModel> customerTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "customers";
        ResultSet results = read.readData(column, table);

        while (results.next()) {
            String ID = results.getString("Customer_ID");
            String name = results.getString("Customer_Name");
            String address = results.getString("Address");
            String phone = results.getString("Phone");
            String divisionID = results.getString("Division_ID");

            String divisionName = null;
            ResultSet nameResults = read.readData("Division", "first_level_divisions", "Division_ID = " + divisionID);
            if (nameResults.next()){
                divisionName = nameResults.getString("Division");
            }
            customerModel customer = new customerModel(ID, name, address, phone, divisionID, divisionName);
            customerTableData.add(customer);
        }
        return customerTableData;
    }

    /**This function get the customer data from the database.
     *
     * @param connection a Connection
     * @return the customer data as an ObservableList
     * @throws SQLException an exception
     */
    public static ObservableList<customerModel> getCustomersData(Connection connection) throws SQLException {
        ObservableList<customerModel> customerTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "customers";
        ResultSet results = read.readData(column, table, connection);

        while (results.next()) {
            String ID = results.getString("Customer_ID");
            String name = results.getString("Customer_Name");
            String address = results.getString("Address");
            String phone = results.getString("Phone");
            String divisionID = results.getString("Division_ID");

            String divisionName = null;
            ResultSet nameResults = read.readData("Division", "first_level_divisions", "Division_ID = " + divisionID, connection);
            if (nameResults.next()){
                divisionName = nameResults.getString("Division");
            }
            customerModel customer = new customerModel(ID, name, address, phone, divisionID, divisionName);
            customerTableData.add(customer);
        }
        return customerTableData;
    }

    /**This function gets the customer name
     *
     * @param connection a Connection
     * @return the customer names as an array
     * @throws SQLException an exception
     */
    public static ArrayList getCustomerNames(Connection connection) throws SQLException {
        ArrayList customerNameArrayList = new ArrayList();
        String column = "Customer_Name";
        String table = "customers";
        ResultSet results = read.readData(column, table, connection);

        while (results.next()) {
            String customerName = results.getString("Customer_Name");
            customerNameArrayList.add(customerName);
        }
        return customerNameArrayList;
    }

    /**This function gets the customerID
     *
     * @param name the name used to find the ID
     * @return the ID or null
     */
    public static String getCustomerID(String name) {
        try(ResultSet results = read.readData("Customer_ID", "customers","Customer_Name = '" + name + "'")) {
            ;
            if (results.next()) {
                return results.getString("Customer_ID");
            }
        } catch (Exception e) {}
        return null;
    }
}
