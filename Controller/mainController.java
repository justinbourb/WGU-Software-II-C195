  
package Controller;

import Helpers.confirmView;
import Helpers.customerTableData;
import Model.customerModel;
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
        customerModel.selectedCustomerIndex = customerTable.getSelectionModel().getFocusedIndex();
        customerModel.modifyCustomerButtonClicked = true;
        String resourceURL = "/View/customerView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function controls the modifyAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @throws IOException an exception
     */
    @FXML
    void modifyAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        customerModel.selectedAppointmentIndex = appointmentTable.getSelectionModel().getFocusedIndex();
        customerModel.modifyAppointmentButtonClicked = true;
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
    It handles data setup for the GUI to display. To generate a tableView
     from a Database, java requires an intermediary step of creating
     a Object and adding the object to a ObservableList.  Thus we use
     Model.customerModel to hold data from the database.  Then add the
     customerModel to an ObservableList then add the list to the tableView.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    //check out https://stackoverflow.com/questions/18497699/populate-a-tableview-using-database-in-javafx

        ObservableList<customerModel> data = null;
        try {
            data = customerTableData.getCustomersData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerTable.setPlaceholder(new Label("The table is empty or no search results found."));
        customerTable.setEditable(true);
        customerTable.setItems(data);
        customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("ID"));
        customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("name"));
        customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("address"));
        customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("phone"));

        appointmentTable.setPlaceholder(new Label("The table is empty or no search results found."));
      //TODO: test this implementation and modify as needed, see link for an example
    }
}
