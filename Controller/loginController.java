package Controller;

import Helpers.switchStage;
import com.sun.tools.jconsole.JConsoleContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.io.DataOutput;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import DAO.read;

import static DAO.read.readData;

/** This class controls the mainView.fxml */
public class loginController  implements Initializable {

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button submitButton;
    
    @FXML
    private Label welcomeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private TextArea errorTextArea;
    
    private Boolean isFrench = false;
    
    /*Requirements:
    1.  Create a log-in form with the following capabilities:

complete - open next gui page after login •  accepts a user ID and password and provides an appropriate error message

incomplete - show as label on gui •  determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form

 complete •  displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form

incomplete - show as label on gui •  automatically translates error control messages into English or French based on the user’s computer language setting
*/

    /**
     * This function controls the submitButton button.  Text is displayed
     * in English or French based on the user's location.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    public void submitButtonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String name = usernameTextField.getText();
        String password = passwordTextField.getText();
        String column = "*";
        String table = "users";
        String where = "User_Name = '" + name + "'";
        errorTextArea.clear();
        errorTextArea.setVisible(false);


        ResultSet results = readData(column, table, where);
        //Calling results.next() "consumes" the next result, so only use this method as needed
        //How to peek at or check this value?
        while(results.next()) {
            String stored_name = results.getString("User_Name");
            String stored_password = results.getString("Password");
            //if username and password match, log in
            if (stored_password.equals(password)) {
                String resourceURL = "/View/mainView.fxml";
                switchStage.switchStage(actionEvent, resourceURL);
            //else print errors
            } else {
                errorTextArea.setVisible(true);
                if (isFrench) {
                    errorTextArea.appendText("Le nom d'utilisateur ou le mot de passe ne correspond pas.");
                } else {
                    errorTextArea.appendText("The user name or password did not match.");
                }
            }
        }
    }
    
    /** This function controls initialization of the loginView.fxml and translates code to French based on
    user locale.  Text is translated by Google translate. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        String language = locale.getDisplayLanguage();
        locationLabel.setText("Your computer location is set to: " + locale);
        if (language.equals("French")) {
            // translate code to French
            usernameTextField.setPromptText("Nom d'utilisateur"); 
            passwordTextField.setPromptText("mot de passe");

            welcomeLabel.setText("Bienvenue dans l'application de planification de bureau");
            submitButton.setText("soumettre");
            isFrench = true;
            
        }
    }

}
