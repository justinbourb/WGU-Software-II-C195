package Controller;

import Helpers.loginAttempts;
import Helpers.switchStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
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

    @FXML
    private Label pleaseLogInLabel;

    /**
     * This function controls the submitButton button.  Text is displayed
     * in English or French based on the user's location.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    public void submitButtonAction(ActionEvent actionEvent) {
        String name = usernameTextField.getText();
        String password = passwordTextField.getText();
        String column = "*";
        String table = "users";
        String where = "User_Name = '" + name + "'";
        errorLabel.setText("");
        
        //try with resources will automatically close the database connection
        try(ResultSet results = readData(column, table, where)) {

            //Calling results.next() "consumes" the next result, so only use this method as needed
            //How to peek at or check this value?
            if (results.next()) {
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
                if (name != null) {
                    loginAttempts.recordLoginAttempts("user: " + name + " login failed.");
                }
                setErrors();
            }
        } catch (Exception e) {}
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
        if (language.equals("français")) {
            // translate code to French
            usernameTextField.setPromptText("Nom d'utilisateur"); 
            passwordTextField.setPromptText("mot de passe");

            welcomeLabel.setText("Bienvenue dans l'application de planification de bureau");
            submitButton.setText("soumettre");
            pleaseLogInLabel.setText("Merci de vous connecter pour continuer.");
            locationLabel.setText("L'emplacement de votre ordinateur est défini sur:" + locale);
            isFrench = true;
        }
    }
}
