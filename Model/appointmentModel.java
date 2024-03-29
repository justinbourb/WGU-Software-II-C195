package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class stores the data from the database in a Java Object*/
public class appointmentModel {
    /** holds the selectedProductIndex */
    public static String selectedAppointmentIndex;
    /** was the modify button clicked? */
    public static boolean editAppointmentButtonClicked = false;


    private String Appointment_ID;
    private String Customer_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Contact_ID;
    private String Type;
    private String Start;
    private String End;
    private String Create_Date;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    private String User_ID;
    private static ObservableList<appointmentModel> filteredAppointments = FXCollections.observableArrayList();;

    /** This is the constructor for the appointment Model*/
    public appointmentModel(String appointment_ID, String customer_ID, String title, String description, String location, String contact_ID, String type, String start, String end) {
        this.Appointment_ID = appointment_ID;
        this.Customer_ID = customer_ID;
        this.Title = title;
        this.Description = description;
        this.Location = location;
        this.Contact_ID = contact_ID;
        this.Type = type;
        this.Start = start;
        this.End = end;
    }

    /**This is an auto-generated getter or setter function*/
    public static ObservableList<appointmentModel> getFilteredAppointments() {
        return filteredAppointments;
    }

    /**This is an auto-generated getter or setter function*/
    public String getAppointment_ID() {
        return Appointment_ID;
    }

    /**This is an auto-generated getter or setter function*/
    public void setAppointment_ID(String appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    /**This is an auto-generated getter or setter function*/
    public String getCustomer_ID() {
        return Customer_ID;
    }

    /**This is an auto-generated getter or setter function*/
    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    /**This is an auto-generated getter or setter function*/
    public String getTitle() {
        return Title;
    }

    /**This is an auto-generated getter or setter function*/
    public void setTitle(String title) {
        Title = title;
    }

    /**This is an auto-generated getter or setter function*/
    public String getDescription() {
        return Description;
    }

    /**This is an auto-generated getter or setter function*/
    public void setDescription(String description) {
        Description = description;
    }

    /**This is an auto-generated getter or setter function*/
    public String getLocation() {
        return Location;
    }

    /**This is an auto-generated getter or setter function*/
    public void setLocation(String location) {
        Location = location;
    }

    /**This is an auto-generated getter or setter function*/
    public String getContact_ID() {
        return Contact_ID;
    }

    /**This is an auto-generated getter or setter function*/
    public void setContact_ID(String contact_ID) {
        Contact_ID = contact_ID;
    }

    /**This is an auto-generated getter or setter function*/
    public String getType() {
        return Type;
    }

    /**This is an auto-generated getter or setter function*/
    public void setType(String type) {
        Type = type;
    }

    /**This is an auto-generated getter or setter function*/
    public String getStart() {
        return Start;
    }

    /**This is an auto-generated getter or setter function*/
    public void setStart(String start) {
        Start = start;
    }

    /**This is an auto-generated getter or setter function*/
    public String getEnd() {
        return End;
    }

    /**This is an auto-generated getter or setter function*/
    public void setEnd(String end) {
        End = end;
    }
}
