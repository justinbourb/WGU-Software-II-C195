package Controller;

import DAO.connect;
import DAO.create;
import DAO.read;
import DAO.update;
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

•  Customer data is displayed using a TableView, including first-level division data. A list of all the customers and their information may be viewed in a TableView, and updates of the data can be performed in text fields on the form.

•  When a customer record is deleted, a custom message should display in the user interface.
     */


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
        customerModel.editCustomerButtonClicked = false;
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


    /**
     * This function controls the save button.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void saveButtonAction(ActionEvent actionEvent) throws IOException, SQLException {
        //capture values from gui
        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phone = phoneNumberText.getText();
        String country = countryComboBox.getValue().toString();
        String firstLevelDivision = String.valueOf(firstLevelDivisionComboBox.getValue());
        String divisionID = firstLevelDivisionTableData.getFirstLevelDivisionID(firstLevelDivision);

        //insert values into database if creating a new customer
        if (customerModel.editCustomerButtonClicked == false) {
            create.createData("customers",
                    "Customer_Name, Address, Postal_Code, Phone, Division_ID",
                    ("'"+ name + "', '" + address + "', '" + postalCode + "', '" + phone + "', '" + divisionID + "'"));
          //else update values in the database if editing a customer
        } else {
            //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
            String set = "Customer_Name = '" + name + "', Address = '" + address + "', Postal_Code = '" + postalCode + "', Phone = '" + phone + "', Division_ID = 6" + divisionID;
            String where = "Customer_ID = " + idText.getText();
            update.updateData("customers",set, where);
        }

        //reset edit button flag
        customerModel.editCustomerButtonClicked = false;
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function will prepopulate the customer text fields if the edit button was clicked.
     *
     * @param connection, a database Connection
     * @throws SQLException, an exception
     */
    private void prepopulateCustomerData(Connection connection) throws SQLException {
        if (customerModel.editCustomerButtonClicked == true) {
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