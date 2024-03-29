  
package Controller;

import DAO.connect;
import DAO.delete;
import DAO.update;
import Helpers.appointmentTableData;
import Helpers.confirmView;
import Helpers.customerTableData;
import Helpers.switchStage;
import Model.appointmentModel;
import Model.customerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static Helpers.appointmentTableData.getAppointmentData;
import static Helpers.timeFunctions.getMonthFromDateTime;

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
    private TextField appointmentsSearchTypeOrMonthText;

    @FXML
    private TextField appointmentsSearchContactOrLocationText;

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
    private RadioButton threeHundredSixtyFiveDaysToggleButton;

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

    /** This function will query the database for all appointments
     *  the display any appointments within 15 minutes of
     * the users local time else the GUI will display no upcoming appointments.
     */

    private void checkFifteenMinutes() {
        //reset error text to prevent duplicate messages
        errorTextArea.clear();
        ObservableList<appointmentModel> appointments = FXCollections.observableArrayList();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlusFifteen = now.plus(15, ChronoUnit.MINUTES);

        try (Connection connection = connect.startConnection()){
            appointments = getAppointmentData(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If the user does not have any appointments within 15 minutes of logging in,
        // display a custom message in the user interface indicating there are no upcoming appointments.
        if (appointments.isEmpty()) {
            errorTextArea.appendText("No upcoming appointments within 15 minutes.");
            // A custom message should be displayed in the user interface and include the appointment ID, date, and time.
        } else {
            for (appointmentModel appointment : appointments) {
                //find the appointment Start and convert it into a LocalDateTime for comparison
                String appointmentStartString = appointment.getStart();
                Timestamp appointmentStartTimestamp = Timestamp.valueOf(appointmentStartString);
                LocalDateTime appointmentStartLocalDateTime = appointmentStartTimestamp.toLocalDateTime();
                LocalDateTime appointmentStartPlusFifteenLocalDateTime = appointmentStartLocalDateTime.plus(15, ChronoUnit.MINUTES);

                //if the start time is after now and now + 15 minutes is after start the appointment starting within 15 minutes
                //thus a warning or notice should appear on the GUI
                if(appointmentStartLocalDateTime.isAfter(now) && nowPlusFifteen.isAfter(appointmentStartLocalDateTime)) {
                    errorTextArea.appendText("Appointment ID: " + appointment.getAppointment_ID() + " Is starting at: " + appointment.getStart());
                }
            }
        }
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
                //remove error messages
                errorTextArea.clear();
                switchStage.switchStage(actionEvent, resourceURL);
                //foreign key constraint throws the following error
                 } catch (SQLIntegrityConstraintViolationException e) {
                errorTextArea.appendText("Please delete all of the customer's appointments before deleting the customer.");
            }

        }
    }

    /**This function controls the deleteAppointment button.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent actionEvent) throws SQLException, IOException {
        appointmentModel appointment = appointmentTable.getSelectionModel().getSelectedItem();
        String confirmText = "Pressing ok will delete this appointment.  Appointment ID: " + appointment.getAppointment_ID() + " Appointment Type: " + appointment.getType();
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = confirmView.showAlert(confirmText);
        if (wasOkPressed){

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

    /**This function controls the customerSearch Text.
     * @param keyEvent, a JavaFX keyEvent provided by a key click
     */
    @FXML
    private void onKeyReleasedCustomerSearchText(KeyEvent keyEvent) {
    }

    /**This function controls the appointment type or month search.
     * @param keyEvent, a JavaFX keyEvent provided by a key click
     */
    @FXML
    private void onKeyReleasedAppointmentSearchTypeOrMonthText(KeyEvent keyEvent) {
        searchAppointmentTypeOrMonth(appointmentsSearchTypeOrMonthText.getText());
        if(!(appointmentModel.getFilteredAppointments().isEmpty())) {
            appointmentTable.setItems(appointmentModel.getFilteredAppointments());
        }
    }

    /**This function controls the appointment contact or location search.
     * @param keyEvent, a JavaFX keyEvent provided by a key click
     */
    @FXML
    private void onKeyReleasedAppointmentSearchContactOrLocationText(KeyEvent keyEvent){
        searchAppointmentContactOrLocation(appointmentsSearchContactOrLocationText.getText());
        if(!(appointmentModel.getFilteredAppointments().isEmpty())) {
            appointmentTable.setItems(appointmentModel.getFilteredAppointments());
        }
    }

    private void searchAppointmentTypeOrMonth(String searchInput) {
        try (var connection = connect.startConnection()) {
            ObservableList<appointmentModel> appointmentData;
            appointmentData = getAppointmentData(connection);
            boolean itsInt;
            int intSearch = -1;
            String stringSearch = null;
            //check if filtered list contains data
            if (!(appointmentModel.getFilteredAppointments().isEmpty())){
                appointmentModel.getFilteredAppointments().clear();
            }
            //check if the search input is an int or a string
            try {
                intSearch = Integer.parseInt(searchInput, 10);
                itsInt = true;
            } catch (Exception e){
                stringSearch = searchInput;
                itsInt = false;
            }
            //if int, search against start month
            for (appointmentModel appointment : appointmentData){
                if (itsInt){
                    Integer month = Integer.parseInt(getMonthFromDateTime(appointment.getStart()));
                    if(month == intSearch){
                        appointmentModel.getFilteredAppointments().add(appointment);
                    }
                    //search against appointment type
                } else {
                    if(appointment.getType().contains(stringSearch)){
                        appointmentModel.getFilteredAppointments().add(appointment);
                    }
                }
            }
        } catch (Exception e) {}
    }

    private void searchAppointmentContactOrLocation(String searchInput) {
        try (var connection = connect.startConnection()) {
            ObservableList<appointmentModel> appointmentData;
            appointmentData = getAppointmentData(connection);
            boolean itsInt;
            int intSearch = -1;
            String stringSearch = null;
            //check if filtered list contains data
            if (!(appointmentModel.getFilteredAppointments().isEmpty())){
                appointmentModel.getFilteredAppointments().clear();
            }
            //check if the search input is an int or a string
            try {
                intSearch = Integer.parseInt(searchInput, 10);
                itsInt = true;
            } catch (Exception e){
                stringSearch = searchInput;
                itsInt = false;
            }
            //if int, search against start month
            for (appointmentModel appointment : appointmentData){
                if (itsInt){
                    Integer contactID = Integer.parseInt(appointment.getContact_ID());
                    if(contactID == intSearch){
                        appointmentModel.getFilteredAppointments().add(appointment);
                    }
                    //search against appointment type
                } else {
                    if(appointment.getLocation().contains(stringSearch)){
                        appointmentModel.getFilteredAppointments().add(appointment);
                    }
                }
            }
        } catch (Exception e) {}
    }

    /**This function controls the appointment radio buttons.
     * Selecting a radio button will call initialize() which will
     * repopulate the appointment and customer tables.
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    public void radioButtonHandler(ActionEvent actionEvent) {
        initialize(null, null);
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
            //check for any appointments within 15 minutes
            checkFifteenMinutes();
            ObservableList<appointmentModel> appointmentData;
            Integer dateRange = 0;
            if(fifteenDaysToggleButton.isSelected()){
                dateRange = 15;
            }
            if (thirtyDaysToggleButton.isSelected()){
                dateRange = 30;
            }
            if(threeHundredSixtyFiveDaysToggleButton.isSelected()){
                dateRange = 365;
            }

            ObservableList<customerModel> customerData = customerTableData.getCustomersData(connection);
            //customerModel.customerData = customerTableData.getCustomersData(connection);
            //if date range is set, query by date range
            if(dateRange > 0) {
                appointmentData = appointmentTableData.getAppointmentDataDateRange(connection, dateRange);
            //else provide all results
            } else {
                appointmentData = getAppointmentData(connection);
            }
            customerTable.setPlaceholder(new Label("The table is empty or no search results found."));
            customerTable.setEditable(true);
            customerTable.setItems(customerData);


            customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("ID"));
            //setCellValueFactory prefills tableColumn with data from the database
            customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("name"));
            //setCellFactory allows editing, by creating a text field when double clicked
            customerNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            //setOnEditCommit is what happens after the edit takes place
            customerNameTableColumn.setOnEditCommit(editEvent());
            customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("address"));
            customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel,String>("phone"));
            customerDivisionTableColumn.setCellValueFactory(new PropertyValueFactory<customerModel, String>("divisionName"));
            customerTable.getSelectionModel().select(0);


            //populate the Appointment table from the appointmentData observable list
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
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**There is a bit of Java magic happening in this function.
     * This function generalizes the EventHandler when editing a TableView TableColumn.
     * It can be reused for each edit event for the customer and appointment tables,
     * with the exception of the contact and division columns.  I haven't worked out
     * how to handle those two columns just yet.  They will likely need a drop down menu.
     * The return type is required TableColumn.setOnEditCommit().
     *
     * @return new EventHandler<TableColumn.CellEditEvent<model, data type>>()
     */
    private EventHandler<TableColumn.CellEditEvent<customerModel, String>> editEvent() {
        return (new EventHandler<TableColumn.CellEditEvent<customerModel, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<customerModel, String> cellEditEvent) {
                String setColumn = String.valueOf(cellEditEvent.getTableColumn().getText());
                String set = cellEditEvent.getNewValue();
                String where = cellEditEvent.getRowValue().getID();
                String whereColumn = cellEditEvent.getTableView().getColumns().get(0).getText();
                String table;

                set = setColumn + " = '" + set + "'";
                //the if statement checks if the customer table or appointment table is being edited
                //and changes the values of the where query accordingly
                if (whereColumn.equals("Customer_ID")) {
                    where = whereColumn + " = " + where;
                    table = "customers";
                } else {
                    where = "Appointment_ID = " + where;
                    table = "appointments";
                }

                try {
                    update.updateData(table, set, where);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**This function handles the contact report button and opens the related view
     * @param actionEvent an actionEvent
     * @throws IOException IOException an exception
     */
    public void contactReportButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/contactReportView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function handles the contact report button and opens the related view
     * @param actionEvent an actionEvent
     * @throws IOException IOException an exception
     */
    public void locationReportButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/locationReportView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /**This function handles the contact report button and opens the related view
     * @param actionEvent an actionEvent
     * @throws IOException IOException an exception
     */
    public void appointmentReportButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/appointmentReportView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }
}
