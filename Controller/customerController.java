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

import java.io.DataOutput;
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
        try {
            connect.closeConnection();
        } catch (Exception e) {}

        //reset edit button flag
        customerModel.modifyCustomerButtonClicked = false;
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**
     * This function will populate the first level division data based
     * on which country is selected.
     */
    public void countryComboBoxAction() {
        Integer countryID = countryComboBox.getSelectionModel().getSelectedIndex() + 1;
        try (var connection = connect.startConnection()) {
            //remove any pre-existing data
            firstLevelDivisionComboBox.getItems().clear();
            //get data from database
            ArrayList firstLevelDivisionData = firstLevelDivisionTableData.getFirstLevelDivisionNames(connection, String.valueOf(countryID));
            //populate firstLevelDivisionComboBox
            firstLevelDivisionComboBox.getItems().addAll(firstLevelDivisionData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /**This function will prepopulate the customer text fields if the edit button was clicked.
     *
     * @param connection, a database Connection
     * @throws SQLException, an exception
     */
    private void prepopulateCustomerData(Connection connection) throws SQLException {
        if (customerModel.modifyCustomerButtonClicked == true) {
            int countryID;
            String divisionName = null;
            titleLabel.setText("Edit Customer");
            //pull the customer data from the database
            ResultSet customerResults = read.readData("*", "customers", "Customer_ID = " + customerModel.selectedCustomerIndex, connection);
            //fill in the text boxes
            if (customerResults.next()) {
                idText.setText(customerResults.getString("Customer_ID"));
                nameText.setText(customerResults.getString("Customer_Name"));
                addressText.setText(customerResults.getString("Address"));
                postalCodeText.setText(customerResults.getString("Postal_Code"));
                phoneNumberText.setText(customerResults.getString("Phone"));
                //find the division name and country ID based on the Division_ID field in the customer table
                ResultSet divisionResultSet = read.readData("*", "first_level_divisions", "Division_ID = " + customerResults.getString("Division_ID"), connection);
                if (divisionResultSet.next()) {
                    divisionName = divisionResultSet.getString("Division");
                    countryID = Integer.parseInt(divisionResultSet.getString("COUNTRY_ID"));
                    //select the correct country in the combo box
                    countryComboBox.getSelectionModel().select(countryID - 1);
                    //fill up first level division data
                    countryComboBoxAction();
                    //select the correct first level division in the combo box
                    firstLevelDivisionComboBox.getSelectionModel().select(firstLevelDivisionComboBox.getItems().indexOf(divisionName));
                }
            }
        }
    }

    /**This function is automatically called by Java.
     It handles data setup for the GUI to display.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connect.closeConnection();
        } catch (Exception e) {}
        //It should fill countries combo box on load
        //It should use try with resources to close connection automatically
        try (var connection = connect.startConnection()) {
           ArrayList countryData = countryTableData.getCountryNames(connection);
           countryComboBox.getItems().addAll(countryData);
           //It should populate customer information if the edit button was clicked
           prepopulateCustomerData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//class bracket
}