package Helpers;

import DAO.read;
import Model.customerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class generates customerTableData */
public class customerTableData {

    /**This function pulls the customer data from the database, creates a customerModel object and adds all
     * objects to an observableArrayList so the database data can be added to a tableView.
     * @return customerTableData, an observableArrayList
     * @throws SQLException
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

            customerModel customer = new customerModel(ID, name, address, phone, divisionID);
            customerTableData.add(customer);
        }
        return customerTableData;
    }

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

            customerModel customer = new customerModel(ID, name, address, phone, divisionID);
            customerTableData.add(customer);
        }
        return customerTableData;
    }

}
