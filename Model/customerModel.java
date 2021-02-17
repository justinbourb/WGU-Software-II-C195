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
    public static boolean editCustomerButtonClicked = false;

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

    /**This is an auto-generated getter or setter function*/
    public String getID() {
        return ID;
    }

    /**This is an auto-generated getter or setter function*/
    public void setID(String ID) {
        this.ID = ID;
    }

    /**This is an auto-generated getter or setter function*/
    public String getName() {
        return name;
    }

    /**This is an auto-generated getter or setter function*/
    public void setName(String name) {
        this.name = name;
    }

    /**This is an auto-generated getter or setter function*/
    public String getAddress() {
        return address;
    }

    /**This is an auto-generated getter or setter function*/
    public void setAddress(String address) {
        this.address = address;
    }

    /**This is an auto-generated getter or setter function*/
    public String getPhone() {
        return phone;
    }

    /**This is an auto-generated getter or setter function*/
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**This is an auto-generated getter or setter function*/
    public String getDivisionName() {
        return divisionName;
    }

    /**This is an auto-generated getter or setter function*/
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**This is an auto-generated getter or setter function*/
    public String getDivisionID() {
        return divisionID;
    }

    /**This is an auto-generated getter or setter function*/
    public void setDivisionID(String divisionID) {
        this.divisionID = divisionID;
    }
}
