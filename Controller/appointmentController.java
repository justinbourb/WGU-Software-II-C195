package Controller;

import DAO.connect;
import DAO.create;
import DAO.read;
import DAO.update;
import Helpers.contactTableData;
import Helpers.customerTableData;
import Helpers.switchStage;
import Model.appointmentModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Helpers.appointmentTableData.getAppointmentDataDateRange;
import static Helpers.timeFunctions.*;


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
    private Spinner<Integer> startHourSpinner;

    @FXML
    private Spinner<Integer> startMinSpinner;

    @FXML
    private Spinner<Integer> endHourSpinner;

    @FXML
    private Spinner<Integer> endMinSpinner;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

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

    /** This function handles the cancel button
     *
     * @param actionEvent, an ActionEvent
     * @throws IOException, an Exception
     */
    @FXML
    void cancelButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/mainView.fxml";
        //reset was edit appointment clicked
        appointmentModel.editAppointmentButtonClicked = false;
        switchStage.switchStage(actionEvent, resourceURL);
    }

    private boolean checkOverlappingAppointments(String start, String end) throws ParseException {
        //reset error text to prevent duplicate messages
        errorTextArea.clear();
        ObservableList<appointmentModel> appointments = null;

        try (Connection connection = connect.startConnection()){
            appointments = getAppointmentDataDateRange(start, end, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // If the user does not have any appointments within 15 minutes of logging in,
        // display a custom message in the user interface indicating there are no upcoming appointments.
        if (appointments.isEmpty()) {
            return true;
            // A custom message should be displayed in the user interface and include the appointment ID, date, and time.
        } else {
            for (appointmentModel appointment : appointments) {
                errorTextArea.appendText("An overlapping appointment Appointment ID: " + appointment.getAppointment_ID() + " Is starting at: " + appointment.getStart());
            }
            return false;
        }
    }

    /**This function will fill the contactIDText field when a contact is selected.
     */
    @FXML
    void contactComboBoxAction() throws SQLException {
        //It should fill contactIDText with the contact id
        String name = String.valueOf(contactComboBox.getValue());
        String id = contactTableData.getContactID(name);
        contactIDText.setText(id);
    }

    /**This function will fill the customerIDText field when a contact is selected.
     */
    @FXML
    void customerComboBoxAction() {
        //It should fill the customerIDText with the customer id
        String name = String.valueOf(customerComboBox.getValue());
        String id = customerTableData.getCustomerID(name);
        customerIDText.setText(id);
    }

    /** This function sets up the Spinners. It should provide
     * a time range from 8am to 10pm EST.  It should
     * convert the time range for the user's default time zone.*/
    private void initSpinner() throws ParseException {
        //convert start time: 8am EST to local time zone
        Integer startHours = Integer.parseInt(getHoursFromDateTime(getLocalTimeZone("2020-12-31 13:00:00")));
        //convert end time: 9pm EST to local time zone (appointments up to 9:59pm, closing at 10pm)
        Integer endHours = Integer.parseInt(getHoursFromDateTime(getLocalTimeZone("2020-12-31 02:00:00")));


        SpinnerValueFactory<Integer> startHoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHours, endHours, startHours);
        SpinnerValueFactory<Integer> startMinutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);
        SpinnerValueFactory<Integer> endHoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(startHours, endHours, startHours);
        SpinnerValueFactory<Integer> endMinutesFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(00, 59, 00);
        startHourSpinner.setValueFactory(startHoursFactory);
        endHourSpinner.setValueFactory(endHoursFactory);
        startMinSpinner.setValueFactory(startMinutesFactory);
        endMinSpinner.setValueFactory(endMinutesFactory);
    }

    /**This function pre-populates the appointment data.
     *
     * @param connection a database Connection
     * @exception SQLException, an exception
     */
    private void prepopulateAppointmentData(Connection connection) throws SQLException, ParseException {

        //It should only fill data if the edit button was clicked
        if (appointmentModel.editAppointmentButtonClicked) {
            guiLabel.setText("Edit Appointment");
            //pull the appointment data from the database
            ResultSet appointmentResults = read.readData("*", "appointments", "Appointment_ID = " + appointmentModel.selectedAppointmentIndex, connection);
            //fill data to the gui
            if (appointmentResults.next()){
                appointmentIDText.setText(appointmentResults.getString("Appointment_ID"));
                titleText.setText(appointmentResults.getString("Title"));
                locationText.setText(appointmentResults.getString("Location"));
                typeText.setText(appointmentResults.getString("Type"));
                startMinSpinner.getValueFactory().setValue(Integer.valueOf(getMinutesFromDateTime(getLocalTimeZone(appointmentResults.getString("Start")))));
                startHourSpinner.getValueFactory().setValue(Integer.valueOf(getHoursFromDateTime(getLocalTimeZone(appointmentResults.getString("Start")))));
                startDatePicker.getEditor().setText(getDateFromDateTime(getLocalTimeZone(appointmentResults.getString("Start"))));
                startDatePicker.setValue(LocalDate.parse(getDateFromDateTime(getLocalTimeZone(appointmentResults.getString("Start")))));
                endMinSpinner.getValueFactory().setValue(Integer.valueOf(getMinutesFromDateTime(getLocalTimeZone(appointmentResults.getString("End")))));
                endHourSpinner.getValueFactory().setValue(Integer.valueOf(getHoursFromDateTime(getLocalTimeZone(appointmentResults.getString("End")))));
                endDatePicker.getEditor().setText(getDateFromDateTime(getLocalTimeZone(appointmentResults.getString("End"))));
                endDatePicker.setValue(LocalDate.parse(getDateFromDateTime(getLocalTimeZone(appointmentResults.getString("End")))));
                descriptionTextArea.appendText(appointmentResults.getString("Description"));
                //get the customer name from the database
                ResultSet nameResults = read.readData("Customer_Name", "customers", "Customer_ID = " + appointmentResults.getString("Customer_ID"), connection);
                if (nameResults.next()){
                    //set the selection of the combo box
                    customerComboBox.getSelectionModel().select(customerComboBox.getItems().indexOf(nameResults.getString("Customer_Name")));
                    //set customerIDText
                    customerComboBoxAction();
                }
                //get the contact name from the database
                ResultSet contactResults = read.readData("Contact_Name", "contacts", "Contact_ID = " + appointmentResults.getString("Contact_ID"), connection);
                if (contactResults.next()){
                    //set the selection of the combo box
                    contactComboBox.getSelectionModel().select(contactComboBox.getItems().indexOf(contactResults.getString("Contact_Name")));
                    //set contactIDText
                    contactComboBoxAction();
                }
            }
        }
    }

    /** This function formats the date picker fields (start and end).
     */
    private void formatDate(){
//        startMinSpinner.getValueFactory().setValue(45);
//        startDatePicker.getEditor().setText("2020-07-25");
//        startDatePicker.setValue(LocalDate.of(2020,07,25));
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            @Override
            public String toString(LocalDate localDate) {
                return localDate != null ? dateTimeFormatter.format(localDate) : "";
            }

            @Override
            public LocalDate fromString(String s) {
                return s != null && !s.isEmpty() ? LocalDate.parse(s, dateTimeFormatter) : null;
            }
        };
        endDatePicker.setConverter(converter);
        endDatePicker.setPromptText("yyyy-MM-dd");
        startDatePicker.setConverter(converter);
        startDatePicker.setPromptText("yyyy-MM-dd");
    }

    /**This function handles the save button
     *
     * @param actionEvent, an ActionEvent
     * @throws IOException, an Exception
     */
    @FXML
    void saveButtonAction(ActionEvent actionEvent) throws IOException, SQLException, ParseException {
        //capture values from gui
        //title, location, type, start end, description, customer id, contact id, user id
        String title = titleText.getText();
        String location = locationText.getText();
        String type = typeText.getText();
        String startDate = startDatePicker.getEditor().getText();
        String startMin = startMinSpinner.getEditor().getText();
        String startHours = startHourSpinner.getEditor().getText();
        //Min must be two digits for use with getDateTimeFromInput()
        if(startMin.length() == 1){
            startMin = "0" + startMin;
        }
        //Hours must be two digits for use with getDateTimeFromInput()
        if(startHours.length() == 1){
            startHours = "0" + startHours;
        }
        String startTime = startHours + ":" + startMin;
        String endDate = endDatePicker.getEditor().getText();
        String endMin = endMinSpinner.getEditor().getText();
        if(endMin.length() == 1){
            endMin = "0" + endMin;
        }
        String endHours = endHourSpinner.getEditor().getText();
        if(endHours.length() == 1){
            endHours = "0" + endHours;
        }
        String endTime = endHours + ":" + endMin;

        String start = getUTCTimeZone(getDateTimeFromInput(startDate + " " + startTime));
        String end = getUTCTimeZone(getDateTimeFromInput(endDate + " " + endTime));
        String description = descriptionTextArea.getText();
        String customerID = customerIDText.getText();
        String contactID = contactIDText.getText();

        //check for overlapping appointments and prevent saving if so
        boolean anyOverlappingAppointments = checkOverlappingAppointments(start,end);
        if(!anyOverlappingAppointments) {
            //insert values into database if creating a new customer
            if (appointmentModel.editAppointmentButtonClicked == false) {
                String columns = "Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID";
                String values = ("'" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + start + "', '"
                        + end + "', '" + customerID + "', '" + "1" + "', '" + contactID + "'");
                create.createData("appointments", columns, values);
                //else update values in the database if editing a customer
            } else {
                //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
                String set = "Title = '" + title + "', Description = '" + description + "', Location = '" + location + "', Type = '" +
                        type + "', Start = '" + start + "', End = '" + end + "', Customer_ID = '" + customerID + "', User_ID = '" + "1"
                        + "', Contact_ID = '" + contactID + "'";
                String where = "Appointment_ID = " + appointmentIDText.getText();
                update.updateData("appointments", set, where);
            }

            String resourceURL = "/View/mainView.fxml";
            //reset was edit appointment clicked
            appointmentModel.editAppointmentButtonClicked = false;
            switchStage.switchStage(actionEvent, resourceURL);
        }
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

        //Initialize spinner values
        try {
            initSpinner();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatDate();
        //It should fill combo boxes on load
        //It should use try with resources to close connection automatically
        try (var connection = connect.startConnection()) {
            ArrayList customerNames = customerTableData.getCustomerNames(connection);
            ArrayList contactNames = contactTableData.getContactNames(connection);
            customerComboBox.getItems().addAll(customerNames);
            contactComboBox.getItems().addAll(contactNames);
            //It should populate customer information if the edit button was clicked
            prepopulateAppointmentData(connection);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}




