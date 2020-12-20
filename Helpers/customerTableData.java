package Helpers;

import DAO.read;
import Model.customerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;


public class customerTableData {

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

            customerModel customer = new customerModel(ID, name, address, phone);
            customerTableData.add(customer);
        }
        return customerTableData;
    }

}
