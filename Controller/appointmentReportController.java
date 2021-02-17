package Controller;

import DAO.connect;
import Helpers.switchStage;
import Model.appointmentModel;
import Model.appointmentReportModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static Helpers.appointmentTableData.getAppointmentData;

/**This class controls the contactReport view*/
public class appointmentReportController implements Initializable {
    @FXML
    private AnchorPane mainAnchorPage;

    @FXML
    private Label mainLabel;

    @FXML
    private AnchorPane appointmentAnchorPage;

    @FXML
    private Label appointmentsLabel;

    @FXML
    private TableView<appointmentReportModel> appointmentTable;

    @FXML
    private TableColumn<appointmentReportModel, String> monthTableColumn;

    @FXML
    private TableColumn<appointmentReportModel, String> typeTableColumn;

    @FXML
    private TableColumn<appointmentReportModel, String> countTableColumn;

    @FXML
    private Button logoutButton;


    /**This function accepts all available appointment data and creates the required appointment report data
     * which is used to fill the appointment report table view.  It uses the format [Month[appointment type: count]].
     * @param appointmentData ObservableList<appointmentModel>
     * @return appointmentReportData ObservableList<appointmentReportModel>
     */
    private ObservableList<appointmentReportModel> generateAppointmentReportData(ObservableList<appointmentModel> appointmentData) {
        //It should check each appointment and count the number of appointment types per Month
        //Then it should create an appointReportModel in the format Month Type Count and attach it to the

        //observableList appointmentReportData
        ObservableList<appointmentReportModel> appointmentReportData = FXCollections.observableArrayList();
        Map appointmentReportMap = new HashMap();

        //loop over each appointment
        for (appointmentModel appointment: appointmentData){
            Map typeAndCountMap = new HashMap();
            Map blankMap = new HashMap();
            //map is a Java dictionary, key value pair
            //map within a map
            //[January :[type:count]]
            String month = findMonthString(appointment.getStart());
            String type = appointment.getType();

            //create the month key with a blank map if it does not exist
            if(!appointmentReportMap.containsKey(month)){
                appointmentReportMap.put(month, blankMap);
            }

            //[January:[1:1, 2:1, 3:1]
            //innerMap = appointmentReportMap.get(month)
            //if innerMap.containsKey(type)
            //check if the key already exists and increment the count by 1 if so
            if(appointmentReportMap.containsKey(month)){
                typeAndCountMap = (Map) appointmentReportMap.get(month);
            }
            if (typeAndCountMap.containsKey(type)) {
                int currentCount = (int) typeAndCountMap.get(type);
                typeAndCountMap.put(type, currentCount+1);
            //else the key does not exist yet so it's count should be 1
            } else {
                typeAndCountMap.put(type, 1);
            }

            appointmentReportMap.put(month, typeAndCountMap);

        }


        //iterate over each month
        for (Object month : appointmentReportMap.keySet()){
            //iterate over the different types of appointments per month
            for (Object type :((Map<?, ?>) appointmentReportMap.get(month)).keySet()){
                Object count = ((Map<?, ?>) appointmentReportMap.get(month)).get(type);
                //System.out.println("Month: " + month + " Type: " + type + " Count " + ((Map<?, ?>) appointmentReportMap.get(month)).get(type));
                //System.out.println("Month: " + month + " Type: " + type + " Count " + count);
                //create a new appointmentReportModel for each type of appointment per month
                 appointmentReportModel appointmentReport = new appointmentReportModel(month.toString(), type.toString(), count.toString());
                 //add it to appointmentReportData (to be returned)
                 appointmentReportData.add(appointmentReport);
            }

        }
        return appointmentReportData;
    }

    private String findMonthString(String start) {
        String startString = start;
        Timestamp startTimeStamp = Timestamp.valueOf(startString);
        LocalDateTime startLocalDateTime = startTimeStamp.toLocalDateTime();
        String month = String.valueOf(startLocalDateTime.getMonth());
        return month;
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
            ObservableList<appointmentReportModel> appointmentReportData = generateAppointmentReportData(appointmentData);


            //populate the Appointment table from the appointmentData observable list
            appointmentTable.setPlaceholder(new Label("The table is empty or no search results found."));
            appointmentTable.setEditable(true);
            appointmentTable.setItems(appointmentReportData);
            monthTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentReportModel, String>("Month"));
            typeTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentReportModel, String>("Type"));
            countTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentReportModel, String>("Count"));

//            appointmentIDTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("appointment_ID"));
//            custIDTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("customer_ID"));
//            titleTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("title"));
//            appointmentDescripColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("description"));
//            appointmentLocationTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("location"));
//            contactTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("contact_ID"));
//            typeTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("type"));
//            appointmentStartTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("start"));
//            appointmentEndTableColumn.setCellValueFactory(new PropertyValueFactory<appointmentModel, String>("end"));

            //set the first item to be selected by default
            appointmentTable.getSelectionModel().select(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
