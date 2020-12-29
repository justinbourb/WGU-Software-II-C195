  
package Controller;

import DAO.connect;
import DAO.delete;
import Helpers.appointmentTableData;
import Helpers.confirmView;
import Helpers.customerTableData;
import Model.appointmentModel;
import Model.customerModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

import Helpers.switchStage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/* TODO
*1) access DB logic
*2) Create customers tableview
*3) etc?
*/


/** This class controls the mainView.fxml */
public class mainController implements Initializable {
    @FXML
    private AnchorPane mainAnchorPage;

    @FXML
    private AnchorPane customerAnchorPage;

    @FXML
    private Label customersLabel;

    @FXML
    private TextField customersSearchText;

    @FXML
    private TableView<customerModel> customerTable;

    @FXML
    private TableColumn<customerModel, String> customerIDTableColumn;

    @FXML
    private TableColumn<customerModel, String> customerNameTableColumn;

    @FXML
    private TableColumn<customerModel, String> customerAddressTableColumn;

    @FXML
    private TableColumn<customerModel, String> customerPhoneTableColumn;

    @FXML
    private TableColumn<customerModel, String> customerDivisionTableColumn;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button editCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private Label mainLabel;

    @FXML
    private AnchorPane appointmentAnchorPage;

    @FXML
    private Label appointmentsLabel;

    @FXML
    private TextField appointmentsSearchText;

    @FXML
    private TableView<appointmentModel> appointmentTable;

    @FXML
    private TableColumn<appointmentModel, String> appointmentIDTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> custIDTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> titleTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> appointmentDescripColumn;

    @FXML
    private TableColumn<appointmentModel, String> appointmentLocationTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> contactTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> typeTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> appointmentStartTableColumn;

    @FXML
    private TableColumn<appointmentModel, String> appointmentEndTableColumn;

    @FXML
    private Button appointmentAddButton;

    @FXML
    private Button appointmentEditButton;

    @FXML
    private Button appointmentDeleteButton;

    @FXML
    private RadioButton fifteenDaysToggleButton;

    @FXML
    private ToggleGroup appointmentViewToggle;

    @FXML
    private RadioButton thirtyDaysToggleButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TextArea errorTextArea;

    /**This function controls the addCustomers button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    @FXML
    void addCustomersButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/customerView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the addAppointments button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    @FXML
    void addAppointmentsButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/appointmentView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the deleteCustomer button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    void deleteCustomersButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        String confirmText = "Pressing ok will delete this customer.";
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = confirmView.showAlert(confirmText);
        if (wasOkPressed){
            customerModel customer = customerTable.getSelectionModel().getSelectedItem();
            try {
                delete.deleteData("customers", "Customer_ID = " + customer.getID());
                //foreign key constraint throws the following error
            } catch (SQLIntegrityConstraintViolationException e) {
                //TODO: add this to an error message
                System.out.println("Please delete all of the customer's appointments before deleting the customer");
            }
            switchStage.switchStage(actionEvent, resourceURL);
        }
    }

    /**This function controls the deleteAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        String confirmText = "Pressing ok will delete this appointment.";
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = confirmView.showAlert(confirmText);
        if (wasOkPressed){
            appointmentModel appointment = appointmentTable.getSelectionModel().getSelectedItem();
            delete.deleteData("appointments", "Appointment_ID = " + appointment.getAppointment_ID());
            switchStage.switchStage(actionEvent, resourceURL);
        }
    }

    /**This function controls the editCustomer button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void editCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        //get the customer ID from the selected row
        customerModel customer = customerTable.getSelectionModel().getSelectedItem();
        //if nothing is selected, use the first row
        if (customer == null){
            customerTable.getSelectionModel().select(0);
            customer = customerTable.getSelectionModel().getSelectedItem();
        }
        customerModel.selectedCustomerIndex = customer.getID();
        customerModel.editCustomerButtonClicked = true;
        String resourceURL = "/View/customerView.fxml";
        try {
            connect.closeConnection();
        } catch (Exception e){}
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the editAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void editAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        //get the appointment ID from the selected row
        appointmentModel appointment = appointmentTable.getSelectionModel().getSelectedItem();
        //if nothing is selected, use the first row
        if (appointment == null){
            appointmentTable.getSelectionModel().select(0);
            appointment = appointmentTable.getSelectionModel().getSelectedItem();
        }
        appointmentModel.selectedAppointmentIndex = appointment.getAppointment_ID();
        appointmentModel.editAppointmentButtonClicked = true;
        try {
            connect.closeConnection();
        } catch (Exception e){}
        String resourceURL = "/View/appointmentView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }
  
    /** This method opens loginView.fxml
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    @FXML
    void logoutButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/loginView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the customerSearch Text button.
     * @param keyEvent, a JavaFX keyEvent provided by a key click
     */
    public void onKeyReleasedCustomerSearchText(KeyEvent keyEvent) {
    }

    /**This function controls the customerSearch Text button.
     * @param keyEvent, a JavaFX keyEvent provided by a key click
     */
    public void onKeyReleasedAppointmentSearchText(KeyEvent keyEvent) {
    }

    /**This function controls the appointment radio buttons.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    public void radioButtonHandler(ActionEvent actionEvent) {
    }

    /**This function is automatically called by Java.  
    It handles data setup for the GUI to display. To generate a tableView
     from a Database, java requires an intermediary step of creating
     a Object and adding the object to an ObservableList.  Thus we use
     Model.customerModel and Model.appointmentModel to hold data from the database.
     Then add the models to an ObservableList then add the list to the tableView.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try (var connection = connect.startConnection()){
            ObservableList<customerModel> customerData = customerTableData.getCustomersData(connection);
            //customerModel.customerData = customerTableData.getCustomersData(connection);
            ObservableList<appointmentModel> appointmentData = appointmentTableData.getAppointmentData(connection);

            customerTable.setPlaceholder(new Label("The table is empty or no search results found."));
            customerTable.setEditable(true);
            customerTable.setItems(customerData);
            customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("ID"));
            customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("name"));
            customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("address"));
            customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("phone"));
            customerDivisionTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel, String>("divisionName"));
            customerTable.getSelectionModel().select(0);



            appointmentTable.setPlaceholder(new Label("The table is empty or no search results found."));
            appointmentTable.setEditable(true);
            appointmentTable.setItems(appointmentData);
            appointmentIDTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("appointment_ID"));
            custIDTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("customer_ID"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("title"));
            appointmentDescripColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("description"));
            appointmentLocationTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("location"));
            contactTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("contact_ID"));
            typeTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("type"));
            appointmentStartTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("start"));
            appointmentEndTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("end"));
            appointmentTable.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
