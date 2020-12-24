package Controller;

import DAO.connect;
import DAO.create;
import DAO.read;
import Helpers.countryTableData;
import Helpers.firstLevelDivisionTableData;
import Helpers.switchStage;
import Model.customerModel;
import Model.firstLevelDivisionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/** This class controls the customerView.fxml */
public class customerController implements Initializable {

    @FXML
    private Label titleLabel;

    @FXML
    private Label IDLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label postalCodeLabel;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea errorTextArea;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ComboBox<firstLevelDivisionModel> firstLevelDivisionComboBox;

/* Requirements
    •  Customer records and appointments can be added, updated, and deleted.

-  When deleting a customer record, all of the customer’s appointments must be deleted first, due to foreign key constraints.

•  When adding and updating a customer, text fields are used to collect the following data: customer name, address, postal code, and phone number.

-  Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.


Note: The address text field should not include first-level division and country data. Please use the following examples to format addresses:

•  U.S. address: 123 ABC Street, White Plains

•  Canadian address: 123 ABC Street, Newmarket

•  UK address: 123 ABC Street, Greenwich, London


-  When updating a customer, the customer data autopopulates in the form.


•  Country and first-level division data is prepopulated in separate combo boxes or lists in the user interface for the user to choose. The first-level list should be filtered by the user’s selection of a country (e.g., when choosing U.S., filter so it only shows states).

•  All of the original customer information is displayed on the update form.

-  Customer_ID must be disabled.

•  All of the fields can be updated except for Customer_ID.

•  Customer data is displayed using a TableView, including first-level division data. A list of all the customers and their information may be viewed in a TableView, and updates of the data can be performed in text fields on the form.

•  When a customer record is deleted, a custom message should display in the user interface.
     */

    //TODO: update customerView.FXML - correct variable names
    //TODO: verify database column names and test functionality

    /**
     * This function controls the cancel button.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void cancelButtonAction(ActionEvent actionEvent) throws IOException {
        connect.closeConnection();
        //reset edit button flag
        customerModel.modifyCustomerButtonClicked = false;
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**
     * This function controls the country combo box.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a combo box
     */
    public void countryComboBoxAction(ActionEvent actionEvent) {
        //see https://stackoverflow.com/questions/39539838/javafx-populating-a-combobox-with-data-from-a-mysql-database-stringconverter-b
        System.out.println(countryComboBox.getSelectionModel().getSelectedIndex());
    }

    /**
     * This function controls the first level division combo box.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a combo box
     */
    public void firstLevelDivisionComboBoxAction(ActionEvent actionEvent) {
    }

    private void populateComboBoxes(Connection connection){

    }

    /**
     * This function controls the save button.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void saveButtonAction(ActionEvent actionEvent) throws IOException {
        //capture values from gui
        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phone = phoneNumberText.getText();
        String country = countryComboBox.getItems().toString();
        String firstLevelDivision = firstLevelDivisionComboBox.getItems().toString();

        //insert values into database
        try {
            create.createData("Customers",
                    "name, address, postalCode, phone, country, firstLevelDivision",
                    (name + ',' + address + ',' + postalCode + ',' + phone + ',' + country + ',' + firstLevelDivision));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //reset edit button flag
        customerModel.modifyCustomerButtonClicked = false;
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*TODO: first level division data should load based on country selected.
            Right now all first level division are being loaded.
         */

        //It should load the countries list on load
        //It should disable the first level division list until a country is picked
        //It should populate the first level division list after a country is picked

        //prefill if editing
        try (var connection = connect.startConnection()) {

            ArrayList firstLevelDivisionData = null;
            ArrayList countryData = null;
            try {
                firstLevelDivisionData = firstLevelDivisionTableData.getFirstLevelDivisionNames(connection);
                countryData = countryTableData.getCountryNames(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            firstLevelDivisionComboBox.getItems().addAll((firstLevelDivisionData));
            countryComboBox.getItems().addAll(countryData);

            if (customerModel.modifyCustomerButtonClicked == true) {
                System.out.println("Was modify button clicked?: " + customerModel.modifyCustomerButtonClicked);
                System.out.println("Customer ID selected: " + customerModel.selectedCustomerIndex);
                titleLabel.setText("Edit Customer");

                //pull data from database where selected customer ID in the mainview tableview
                //matches the ID in the customer table of the database
                String column = "*";
                String table = "customers";
                String where = "Customer_ID = " + customerModel.selectedCustomerIndex;
                String divisionName = null;
                String countryID = null;
                String countryName = null;

                ResultSet customerResults = null;
                try {
                    customerResults = read.readData(column, table, where, connection);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                /* Here are the Customer Database Columns
                Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
                 */
                //pre-fill the form information from the database


                try {
                    if (customerResults.next()) {
                        ResultSet divisionResultSet = read.readData("*", "first_level_divisions", "Division_ID = " + customerResults.getString("Division_ID"), connection);
                        if (divisionResultSet.next()) {
                            divisionName = divisionResultSet.getString("Division");
                            countryID = divisionResultSet.getString("COUNTRY_ID");
                        }
                        ResultSet countryResultSet = read.readData("Country", "countries", "Country_ID = " + countryID, connection);
                        if (countryResultSet.next()) {
                            countryName = countryResultSet.getString("Country");
                        }
                        nameText.setText(customerResults.getString("Customer_Name"));
                        addressText.setText(customerResults.getString("Address"));
                        postalCodeText.setText(customerResults.getString("Postal_Code"));
                        phoneNumberText.setText(customerResults.getString("Phone"));
                        //results.getString finds the name of the item and Integer.parseInt converts it into an int based on position
                        //countryComboBox.getSelectionModel().select(Integer.parseInt(results.getString("country")));


                        //set the default combo box selection to match the index of the division name.
                        //The index in the firstLevelDivisionData ArrayList is the same index used in the firstLevelDivisionComboBox
                        firstLevelDivisionComboBox.getSelectionModel().select(firstLevelDivisionData.indexOf(divisionName));
                        countryComboBox.getSelectionModel().select(countryData.indexOf(countryName));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            //if editing bracket
            }
            //try with resources bracket
        } catch (Exception e) {
            e.printStackTrace();
        }
    //initialize bracket
    }
//class bracket
}