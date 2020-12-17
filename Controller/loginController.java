package Controller;

import Helpers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private Label errorLabel;
    
    private Boolean isFrench = false;

    /**
     * This function controls the submitButton button.  Text is displayed
     * in English or French based on the user's location.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     * @exception SQLException, an SQL Exception
     * @exception ClassNotFoundException, a Class Not Found Exception
     * @exception IOException, an IOException
     */
    @FXML
    public void submitButtonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String name = usernameTextField.getText();
        String password = passwordTextField.getText();
        String column = "*";
        String table = "users";
        String where = "User_Name = '" + name + "'";
        errorLabel.setText("");

        ResultSet results = readData(column, table, where);
        //Calling results.next() "consumes" the next result, so only use this method as needed
        //How to peek at or check this value?
        if(results.next()) {
            String stored_password = results.getString("Password");
            //if username and password match, log in
            if (stored_password.equals(password)) {
                String resourceURL = "/View/mainView.fxml";
                loginAttempts.recordLoginAttempts("user: " + name + " login successful.");
                switchStage.switchStage(actionEvent, resourceURL);
            //print errors when password is wrong
            } else {
                loginAttempts.recordLoginAttempts("user: " + name + " login failed.");
                setErrors();
            }
        //print errors when no user found
        } else {
            //don't record login attempt if no user name is present
            if(name != null) {
                loginAttempts.recordLoginAttempts("user: " + name + " login failed.");
            }
            setErrors();
        }
    }

    /** This function shows the login errors to the gui in English or French */
    private void setErrors() {
        if (isFrench) {
            errorLabel.setText("Le nom d'utilisateur ou le mot de passe ne correspond pas.");
        } else {
            errorLabel.setText("The user name or password did not match.");
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
