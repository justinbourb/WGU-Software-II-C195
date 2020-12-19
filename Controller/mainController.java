  
package Controller;

import DAO.read;
import Helpers.confirmView;
import Model.mainModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Helpers.switchStage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private TableView<ResultSet> customerTable;

    @FXML
    private TableColumn<ResultSet , Integer> customerIDTableColumn;

    @FXML
    private TableColumn<ResultSet , String> customerNameTableColumn;

    @FXML
    private TableColumn<ResultSet , String> customerAddressTableColumn;

    @FXML
    private TableColumn<ResultSet , String> customerPhoneTableColumn;

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
    private TableView<ResultSet> appointmentTable;

    @FXML
    private TableColumn<ResultSet , Integer> appointmentIDTableColumn;

    @FXML
    private TableColumn<ResultSet , Integer> custIDTableColumn;

    @FXML
    private TableColumn<ResultSet , String> titleTableColumn;

    @FXML
    private TableColumn<ResultSet , String> appointmentLocationTableColumn;

    @FXML
    private TableColumn<ResultSet , String> contactTableColumn;

    @FXML
    private TableColumn<ResultSet , String> typeTableColumn;

    @FXML
    private TableColumn<ResultSet , String> appointmentStartTableColumn;

    @FXML
    private TableColumn<ResultSet , String> appointmentEndTableColumn;

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
    void deleteCustomersButtonAction(ActionEvent actionEvent) {
        String confirmText = "Pressing ok will delete this customer.";
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = confirmView.showAlert(confirmText);
        if (wasOkPressed){
            //DELETE CUSTOMER
        }
    }

    /**This function controls the deleteAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent actionEvent) {
        String confirmText = "Pressing ok will delete this appointment.";
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = confirmView.showAlert(confirmText);
        if (wasOkPressed){
            //DELETE APPOINTMENT
        }
    }

    /**This function controls the editCustomer button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void editCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/customerView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the editAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    public void editAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
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
    /**This function controls the modifyCustomer button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    @FXML
    public void modifyCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        mainModel.selectedCustomerIndex = customerTable.getSelectionModel().getFocusedIndex();
        mainModel.modifyCustomerButtonClicked = true;
        String resourceURL = "/View/customerView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the modifyAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    @FXML
    void modifyAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        mainModel.selectedAppointmentIndex = appointmentTable.getSelectionModel().getFocusedIndex();
        mainModel.modifyAppointmentButtonClicked = true;
        String resourceURL = "/View/appointmentView.fxml";
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
    It handles data setup for the GUI to display.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    //check out https://stackoverflow.com/questions/18497699/populate-a-tableview-using-database-in-javafx
         
        //fill customer and appointment table view
        //prepopulate customer and appointment with any data stored in the ResultSet 
        //retrieved from the database
        String column = "*";
        String table = "customers";
        ResultSet results = null;
        try {
            results = read.readData(column, table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //setItems tells the customerTable which data set it is using
        //the result set is cast as an ObservableList
        /* Here are the Customer Database Columns
        Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
         */
        customerTable.setItems((ObservableList<ResultSet>) results);
        customerTable.setPlaceholder(new Label("The table is empty or no search results found."));
        //populate the table columns, who thought setCellValueFactory was a great name to use??
        customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
      
      //TODO: test this implementation and modify as needed, see link for an example
    }
}
