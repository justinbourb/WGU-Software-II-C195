package Controller;

import DAO.create;
import DAO.read;
import Helpers.switchStage;
import Model.customerModel;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.nio.file.WatchService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private ComboBox<?> firstLevelDivisionComboBox;

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

    /**This function controls the cancel button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void cancelButtonAction(ActionEvent actionEvent) throws IOException {
        //reset edit button flag
        customerModel.modifyCustomerButtonClicked = false;
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the country combo box.
     * @param actionEvent, a JavaFX ActionEvent provided by a combo box
     */
    public void countryComboBoxAction(ActionEvent actionEvent) {
        //see https://stackoverflow.com/questions/39539838/javafx-populating-a-combobox-with-data-from-a-mysql-database-stringconverter-b
    }

    /**This function controls the first level division combo box.
     * @param actionEvent, a JavaFX ActionEvent provided by a combo box
     */
    public void firstLevelDivisionComboBoxAction(ActionEvent actionEvent) {
    }
    
    /**This function controls the save button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void saveButtonAction(ActionEvent actionEvent) throws IOException {
        //capture values from gui
        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phone = phoneNumberText.getText();
        //cast as String
        String country = (String) countryComboBox.getValue();
        String firstLevelDivision = (String) firstLevelDivisionComboBox.getValue();
        //TODO: correct how create.createData is called
        //insert values into database
        try {
            create.createData("Customers",
                    "name, address, postalCode, phone, country, firstLevelDivision",
                    (name + ',' + address + ',' + postalCode  + ',' + phone  + ',' + country  + ',' + firstLevelDivision));
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
        //prefill if editing
        if (customerModel.modifyCustomerButtonClicked == true){
            System.out.println("Was modify button clicked?: " + customerModel.modifyCustomerButtonClicked);
            System.out.println("Line selected: " + customerModel.selectedCustomerIndex);
            titleLabel.setText("Edit Customer");
            //pull data from database where selected customer ID in the mainview tableview
            //matches the ID in the customer table of the database
            String column = "*";
            String table = "customers";
            //TODO: I want this line to find the customer ID selected
            //String where = "Customer_ID = " + mainModel.selectedCustomerIndex.ID;
            String where = "Customer_ID = 1";
            ResultSet results = null;
            try {
                results = read.readData(column, table, where);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //TODO: pull data from Database
            //pull all info into ResultSet and filter results per category?
            /* Here are the Customer Database Columns
            Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
             */

            try {
                if(results.next()) {
                    
                    nameText.setText(results.getString("Customer_Name"));
                    addressText.setText(results.getString("Address"));
                    postalCodeText.setText(results.getString("Postal_Code"));
                    phoneNumberText.setText(results.getString("Phone"));
                    //results.getString finds the name of the item and Integer.parseInt converts it into an int based on posistion
                    countryComboBox.getSelectionModel().select(Integer.parseInt(results.getString("country")));
                    firstLevelDivisionComboBox.getSelectionModel().select(Integer.parseInt(results.getString("firstLevelDivision")));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
