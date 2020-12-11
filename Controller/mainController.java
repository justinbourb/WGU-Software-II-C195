  
package Controller;

import com.sun.tools.jconsole.JConsoleContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.io.DataOutput;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Helpers.switchStage;
import DAO.read;

import static DAO.read.readData;

/* TODO
*1) access DB logic
*2) Create customers tableview
*3) etc?
*/


/** This class controls the mainView.fxml */
public class mainController implements Initializable {
    /** This method opens customersView.fxml */
    @FXML
    void addCustomersButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/customersView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }
  
    /** This method opens appointmentsView.fxml */
    @FXML
    void addAppointmentsButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/appointmentsView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }
  
    /** This method deletes a customer. */
    @FXML
    void deleteCustomersButtonAction(ActionEvent event) throws IOException {
        String confirmText = "Pressing ok will delete this customer.";
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = ConfirmView.showAlert(confirmText);
        if (wasOkPressed){
            //DELETE CUSTOMER
        }
    }
  
    /** This method deletes an appointment. */
    @FXML
    void deleteAppointmentButtonAction(ActionEvent event) throws IOException {
        String confirmText = "Pressing ok will delete this customer.";
        String resourceURL = "/View/mainView.fxml";
        boolean wasOkPressed = ConfirmView.showAlert(confirmText);
        if (wasOkPressed){
            //DELETE APPOINTMENT
        }
    }
  
    /** This method opens loginView.fxml */
    @FXML
    void logoutButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/loginView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }

    /** This method opens calendarView.fxml */
    @FXML
    void viewAppointmentsButtonAction(ActionEvent actionEvent) throws IOException {
        String resourceURL = "/View/calendarView.fxml";
        switchStage.switchStage(actionEvent, resourceURL);
    }
  
    /**This function is automatically called by Java.  
    It handles data setup for the GUI to display.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    //fill customer table view

}
