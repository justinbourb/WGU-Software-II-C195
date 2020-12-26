package Controller;

import DAO.connect;
import Helpers.contactTableData;
import Helpers.countryTableData;
import Helpers.customerTableData;
import Helpers.switchStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    @FXML
    private Label guiLabel;

    @FXML
    private Label appointmentIDLabel;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private TextField appointmentIDText;

    @FXML
    private TextField customerIDText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField locationText;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea errorTextArea;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    private Label typeLabel;

    @FXML
    private TextField typeText;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label endLabel;

    @FXML
    private Label startLabel;

    @FXML
    private TextField endText;

    @FXML
    private TextField startText;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private Label titleLabel1;

    @FXML
    private TextField contactIDText;

/* Requirements
a.  Write code that enables the user to add, update, and delete appointments. The code should also include the following functionalities:

•  A custom message is displayed in the user interface with the Appointment_ID and type of appointment canceled.

•  All of the original appointment information is displayed on the update form in local time zone.

*/

    /** This function handles the cancel button
     *
     * @param actionEvent, an ActionEvent
     * @throws IOException, an Exception
     */
    @FXML
    void cancelButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function will fill the contactIDText field when a contact is selected.
     */
    @FXML
    void contactComboBoxAction() {
        //It should fill contactIDText with the contact id
    }

    /**This function will fill the customerIDText field when a contact is selected.
     */
    @FXML
    void customerComboBoxAction() {
        //It should fill the cusomterIDText with the customer id
    }

    private void prepopulateAppointmentData(Connection connection) {

    }

    /**This function handles the save button
     *
     * @param actionEvent, an ActionEvent
     * @throws IOException, an Exception
     */
    @FXML
    void saveButtonAction(ActionEvent actionEvent) throws IOException {
        //add save logic
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function is automatically called by Java.
     It handles data setup for the GUI to display.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //prefill data if editing
        //It should prefill customer ID and contact ID
        //It should use try with resources to automatically close the database connection
        //It should prefill appoint data if editing
        try {
            connect.closeConnection();
        } catch (Exception e) {}
        //It should fill countries combo box on load
        //It should use try with resources to close connection automatically
        try (var connection = connect.startConnection()) {
            ArrayList customerNames = customerTableData.getCustomerNames(connection);
            ArrayList contactNames = contactTableData.getContactNames(connection);
            customerComboBox.getItems().addAll(customerNames);
            contactComboBox.getItems().addAll(contactNames);
            //It should populate customer information if the edit button was clicked
            prepopulateAppointmentData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}




