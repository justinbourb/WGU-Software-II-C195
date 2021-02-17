package Controller;

import DAO.connect;
import Helpers.switchStage;
import Model.appointmentModel;
import Model.customerModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Helpers.appointmentTableData.getAppointmentData;

/**This class controls the contactReport view*/
public class locationReportController implements Initializable {
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
    private TextField locationSearchTextField;

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

    /**This function controls the appointment location search.
     * @param keyEvent, a JavaFX keyEvent provided by a key click
     * @throws SQLException an exception
     */
    @FXML
    private void onKeyReleasedAppointmentSearchLocationText(KeyEvent keyEvent) throws SQLException {
        searchLocation(locationSearchTextField.getText());
        if(!(appointmentModel.getFilteredAppointments().isEmpty())) {
            appointmentTable.setItems(appointmentModel.getFilteredAppointments());
        } else{
            ObservableList<appointmentModel> appointmentData = getAppointmentData();
            appointmentTable.setItems(appointmentData);
        }
    }

    /**This function searches by contact ID to fill out the table view report
     *
     * @param searchInput Integer of contact ID to search
     */
    private void searchLocation(String searchInput) {
        try (var connection = connect.startConnection()) {
            ObservableList<appointmentModel> appointmentData;
            appointmentData = getAppointmentData(connection);

            //check if filtered list contains data
            if (!(appointmentModel.getFilteredAppointments().isEmpty())){
                appointmentModel.getFilteredAppointments().clear();
            }

            //if int, search against start month
            for (appointmentModel appointment : appointmentData){
                if(appointment.getLocation().contains(searchInput)){
                    appointmentModel.getFilteredAppointments().add(appointment);
                }
            }

        } catch (Exception e) {}
    }

    /**This function handles the go back button and returns to the main view
     * @param actionEvent an actionEvent
     * @throws IOException IOException an exception
     */
    @FXML
    void goBackButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/mainView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
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
            ObservableList<appointmentModel> appointmentData;
            appointmentData = getAppointmentData(connection);


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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
