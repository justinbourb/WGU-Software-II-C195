package Model;

/**This class holds the appointmentModel*/
public class appointmentModel {
    /** This is the constructor for the appointment Model*/

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

    public appointmentModel(String appointment_ID, String customer_ID, String title, String description, String location, String contact_ID, String type, String start, String end) {
        Appointment_ID = appointment_ID;
        Customer_ID = customer_ID;
        Title = title;
        Description = description;
        Location = location;
        Contact_ID = contact_ID;
        Type = type;
        Start = start;
        End = end;
    }

    public String getAppointment_ID() {
        return Appointment_ID;
    }

    public void setAppointment_ID(String appointment_ID) {
        Appointment_ID = appointment_ID;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(String contact_ID) {
        Contact_ID = contact_ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStart() {
        return Start;
    }

    public void setStart(String start) {
        Start = start;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }
}
