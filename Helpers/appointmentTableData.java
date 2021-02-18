package Helpers;

import DAO.read;
import Model.appointmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static Helpers.timeFunctions.*;

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

    /**This functions get the appointment data from the database
     *
     * @param connection a Connetion
     * @return the appointment data
     * @throws SQLException an exception
     */
    public static ObservableList<appointmentModel> getAppointmentData(Connection connection) throws SQLException {
        ObservableList<appointmentModel> appointmentTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "appointments";
        ResultSet results = read.readData(column, table, connection);

        while (results.next()) {

            String Appointment_ID = results.getString("Appointment_ID");
            String Customer_ID = results.getString("Customer_ID");
            String Title = results.getString("Title");
            String Description = results.getString("Description");
            String Location = results.getString("Location");
            String Contact_ID = results.getString("Contact_ID");
            String Type = results.getString("Type");
            Timestamp Start = results.getTimestamp("Start");
            //convert time to local time zone
            //String startString = timeFunctions.getLocalTimeZoneString(Start);
            String startString = getTimeZoneString(Start);
            Timestamp End = results.getTimestamp("End");
            //convert time to local time zone
            //String endString = timeFunctions.getLocalTimeZoneString(End);
            String endString = getTimeZoneString(End);

            appointmentModel appointment = new appointmentModel(Appointment_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, startString, endString);
            appointmentTableData.add(appointment);
        }
        return appointmentTableData;
    }

    /**This functions get the appointment data from the database based on date range
     *
     * @param connection a Connection
     * @param dateRange a date range
     * @return the appointment data
     * @throws SQLException an exception
     * @throws ParseException an exception
     */
    public static ObservableList<appointmentModel> getAppointmentDataDateRange(Connection connection, Integer dateRange) throws SQLException, ParseException {
        ObservableList<appointmentModel> appointmentTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "appointments";
        LocalDate before = LocalDate.now().minusDays(dateRange);
        LocalDate after = LocalDate.now().plusDays(dateRange);

        String where = "Start >= '" + before + "' and Start <= '" + after + "'";
        ResultSet results = read.readData(column, table, where, connection);

        while (results.next()) {

            String Appointment_ID = results.getString("Appointment_ID");
            String Customer_ID = results.getString("Customer_ID");
            String Title = results.getString("Title");
            String Description = results.getString("Description");
            String Location = results.getString("Location");
            String Contact_ID = results.getString("Contact_ID");
            String Type = results.getString("Type");
            Timestamp Start = results.getTimestamp("Start");
            //convert time to local time zone
            //String startString = timeFunctions.getLocalTimeZoneString(Start);
            String startString = getTimeZoneString(Start);
            Timestamp End = results.getTimestamp("End");
            //convert time to local time zone
            //String endString = timeFunctions.getLocalTimeZoneString(End);
            String endString = getTimeZoneString(End);
            appointmentModel appointment = new appointmentModel(Appointment_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, startString, endString);
            appointmentTableData.add(appointment);
        }
        return appointmentTableData;
    }


    /**this function get the appointment data from the database based on date range
     *
     * @param before the starting Timestamp
     * @param after the ending Timestamp
     * @param connection a Connection
     * @return the appoint data as an ObservableList
     * @throws SQLException an exception
     * @throws ParseException an exception
     */
    public static ObservableList<appointmentModel> getAppointmentDataDateRange(Timestamp before, Timestamp after, Connection connection) throws SQLException, ParseException {
        ObservableList<appointmentModel> appointmentTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "appointments";

        String where = "Start <= '" + before + "' and End >= '" + after + "'";
        ResultSet results = read.readData(column, table, where, connection);

        while (results.next()) {

            String Appointment_ID = results.getString("Appointment_ID");
            String Customer_ID = results.getString("Customer_ID");
            String Title = results.getString("Title");
            String Description = results.getString("Description");
            String Location = results.getString("Location");
            String Contact_ID = results.getString("Contact_ID");
            String Type = results.getString("Type");
            Timestamp Start = results.getTimestamp("Start");
            //convert time to local time zone
            String startString = timeFunctions.getLocalTimeZoneString(Start);
            Timestamp End = results.getTimestamp("End");
            //convert time to local time zone
            String endString = timeFunctions.getLocalTimeZoneString(End);
            appointmentModel appointment = new appointmentModel(Appointment_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, startString, endString);
            appointmentTableData.add(appointment);
        }
        return appointmentTableData;
    }

    /** This function gets the appoint data by a given Date range
     *
     * @param before the starting Timestamp
     * @param after the ending Timestamp
     * @param connection a Connection
     * @param appointment_ID the appointment ID
     * @return the appoint data as an ObservableList
     * @throws SQLException an exception
     * @throws ParseException an exception
     */
    public static ObservableList<appointmentModel> getAppointmentDataDateRange(Timestamp before, Timestamp after, Connection connection, String appointment_ID) throws SQLException, ParseException {
        ObservableList<appointmentModel> appointmentTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "appointments";
        String where = "";
        if(!appointment_ID.isEmpty()){
            where = "Start <= ? and End >= ? and Appointment_ID != " + appointment_ID;
        } else {
            where = "Start <= ? and End >= ?";
        }

        //using ? in the query allows the use of paramterIndex which starts at 1, not 0
        //this allows a Timestamp to be be written or read from the database
        ResultSet results = read.readData(column, table, where, connection, before, after);

        while (results.next()) {

            String Appointment_ID = results.getString("Appointment_ID");
            String Customer_ID = results.getString("Customer_ID");
            String Title = results.getString("Title");
            String Description = results.getString("Description");
            String Location = results.getString("Location");
            String Contact_ID = results.getString("Contact_ID");
            String Type = results.getString("Type");
            Timestamp Start = results.getTimestamp("Start");
            //convert time to local time zone
            String startString = timeFunctions.getLocalTimeZoneString(Start);
            Timestamp End = results.getTimestamp("End");
            //convert time to local time zone
            String endString = timeFunctions.getLocalTimeZoneString(End);
            appointmentModel appointment = new appointmentModel(Appointment_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, startString, endString);
            appointmentTableData.add(appointment);
        }
        return appointmentTableData;
    }

    /**This function will query the DB and return an ObservableList of any overlapping appointments.
     *
     * @param before a Timestamp of the start of the meeting to check for overlap
     * @param after a Timestmap of the end of the meeting to check for overlap
     * @param connection a Connection to the database
     * @param appointment_ID the appointment ID of the appoint which is being checked
     * @return an ObservableList of any overlapping appointments
     * @throws SQLException an exception
     */
    public static ObservableList<appointmentModel> checkOverlappingAppointmentsDatabaseLogic(Timestamp before, Timestamp after, Connection connection, String appointment_ID) throws SQLException{
        ObservableList<appointmentModel> appointmentTableData = FXCollections.observableArrayList();
        String column = "*";
        String table = "appointments";
        String where = "Appointment_ID != " + appointment_ID;
        ResultSet results;
        //if creating a new appointment, it will not have an appointment_ID assigned yet
        //thus querying a blank id creates an sql exception
        if (appointment_ID.isEmpty()){
            results = read.readData(column, table, connection);
            //if there is an appointment ID we should not consider the same appointment as an overlap
            //thus our database query excludes the appointment being modified
        } else {
            results = read.readData(column, table, where, connection);
        }

        LocalDateTime startTimeToCheckLocalDateTime = before.toLocalDateTime();
        LocalDateTime endTimeToCheckLocalDateTime = after.toLocalDateTime();


        while (results.next()) {
            Timestamp Start = results.getTimestamp("Start");
            Timestamp End = results.getTimestamp("End");
            LocalDateTime dbStartLocalDateTime = Start.toLocalDateTime();
            LocalDateTime dbEndLocalDateTime = End.toLocalDateTime();

            String Appointment_ID = results.getString("Appointment_ID");
            String Customer_ID = results.getString("Customer_ID");
            String Title = results.getString("Title");
            String Description = results.getString("Description");
            String Location = results.getString("Location");
            String Contact_ID = results.getString("Contact_ID");
            String Type = results.getString("Type");
            //convert to string for appointment object
            String startString = getTimeString(Start);
            //convert to string for appointment object
            String endString = getTimeString(End);

            //check if an appointment is within the time range of an existing appointment  (first two or statements)
            //check if an existing appointments are within the appointment to check (third or statement)
            //if so, add the appointment to the results (appointmentTableData)
            if (startTimeToCheckLocalDateTime.isAfter(dbStartLocalDateTime) && startTimeToCheckLocalDateTime.isBefore(dbEndLocalDateTime) ||
                    (endTimeToCheckLocalDateTime.isAfter(dbStartLocalDateTime) && endTimeToCheckLocalDateTime.isBefore(dbEndLocalDateTime)) ||
                    (dbStartLocalDateTime.isAfter(startTimeToCheckLocalDateTime) && dbStartLocalDateTime.isBefore(endTimeToCheckLocalDateTime))){
                appointmentModel appointment = new appointmentModel(Appointment_ID, Customer_ID, Title, Description, Location, Contact_ID, Type, startString, endString);
                appointmentTableData.add(appointment);
            }

        }
        return appointmentTableData;
    }
}