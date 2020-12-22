package Helpers;

import DAO.read;
import Model.appointmentModel;
import Model.customerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/** This class generates appointmentTableData */
public class appointmentTableData {

    /**This function pulls the appointment data from the database, creates a appointmentModel object and adds all
     * objects to an observableArrayList so the database data can be added to a tableView.
     * @return customerTableData, an observableArrayList
     * @throws SQLException, an exception
     */
    public static ObservableList<appointmentModel> getAppointmentData() throws SQLException {
        ObservableList<appointmentModel> appointmentTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "appointments";
        ResultSet results = read.readData(column, table);

        while (results.next()) {

            String Appointment_ID = results.getString("Appointment_ID");
            String Customer_ID = results.getString("Customer_ID");
            String Title = results.getString("Title");
            String Description = results.getString("Description");
            String Location = results.getString("Location");
            String Contact_ID = results.getString("Contact_ID");
            String Type = results.getString("Type");
            String Start = results.getString("Start");
            String End = results.getString("End");

            appointmentModel appointment = new appointmentModel(Appointment_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, Start, End);
            appointmentTableData.add(appointment);
        }
        return appointmentTableData;
    }

}