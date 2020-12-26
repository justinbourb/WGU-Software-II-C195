package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class stores the data from the database in a Java Object*/
public class customerModel {
    /* Here are the Customer Database Columns
        Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID
    */
    /**holds the selectedCustomerIndex*/
    public static String selectedCustomerIndex;
    /** was the modify button clicked? */
    public static boolean modifyCustomerButtonClicked = false;
    /** holds the selectedProductIndex */
    public static String selectedAppointmentIndex;
    /** was the modify button clicked? */
    public static boolean modifyAppointmentButtonClicked = false;

    private String ID;
    private String name;
    private String address;
    private String phone;
    private String divisionID;
    private String divisionName;

    /** This is the constructor for the customer Model*/
    public customerModel(String ID, String name, String address, String phone, String divisionID, String divisionName) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
    public String getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(String divisionID) {
        this.divisionID = divisionID;
    }
}
