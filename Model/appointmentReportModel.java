package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This class stores the data from the database in a Java Object*/
public class appointmentReportModel {

    private String Month;
    private String Type;
    private String Count;

    private static ObservableList<appointmentReportModel> appointmentReports = FXCollections.observableArrayList();;

    /** This is the constructor for the appointment report Model*/
    public appointmentReportModel(String month, String type, String count) {
        this.Month = month;
        this.Type = type;
        this.Count = count;
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
    public String getCount() {
        return Count;
    }

    /**This is an auto-generated getter or setter function*/
    public void setCount(String count) {
        Count = count;
    }

    /**This is an auto-generated getter or setter function*/
    public static ObservableList<appointmentReportModel> getAppointmentReports() {
        return appointmentReports;
    }

    /**This is an auto-generated getter or setter function*/
    public static void setAppointmentReports(ObservableList<appointmentReportModel> appointmentReports) {
        appointmentReportModel.appointmentReports = appointmentReports;
    }

    /**This is an auto-generated getter or setter function*/
    public String getMonth() {
        return Month;
    }

    /**This is an auto-generated getter or setter function*/
    public void setMonth(String month) {
        Month = month;
    }
}
