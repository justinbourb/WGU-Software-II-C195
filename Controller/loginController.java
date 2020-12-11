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
    
    private Boolean isFrench;
    
    /*Requirements:
    1.  Create a log-in form with the following capabilities:

incomplete - open next gui page after login •  accepts a user ID and password and provides an appropriate error message

incomplete - show as label on gui •  determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form

 complete •  displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form

incomplete - show as label on gui •  automatically translates error control messages into English or French based on the user’s computer language setting
*/

    /**
     * This function controls the modifyPart button.
     *
     * @param actionEvent, a JavaFX ActionEvent provided by a button click
     */
    @FXML
    public void submitButtonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String name = usernameTextField.getText();
        String password = passwordTextField.getText();
        String column = "*";
        String table = "users";
        String where = "User_Name = '" + name + "'";

        ResultSet results = readData(column, table, where);
        System.out.println(results.next());
        if (!results.next()) {
            if (isFrench) {
                System.out.println("(aucun résultat) Le nom d'utilisateur ou le mot de passe ne correspond pas");
            } else {
                System.out.println("(no results) the user name or password did not match");
            }
        }
        while(results.next()) {
            String user_name = results.getString("User_Name");
            String stored_password = results.getString("Password");
            if (stored_password.equals(password)) {
                if (isFrench) {
                    System.out.println("Connexion réussie");
                } else {
                    System.out.println("log in successful");
                }
            } else {
                if (isFrench) {
                    System.out.println("(aucun résultat) Le nom d'utilisateur ou le mot de passe ne correspond pas");
                } else {
                    System.out.println("the user name or password did not match");
                }
            }
        }
    }
    
    /** This function controls initialization of the mainView.fxml and translates code to French based on
    user locale.  Text is translated by Google translate. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = Locale.getDefault();
        String language = locale.getDisplayLanguage();
        if language.equals("French") {
            // translate code to French
            usernameTextField.setPromptText("Nom d'utilisateur"); 
            passwordTextField.setPromptText("mot de passe");
            passwordTextField.getParent().requestFocus(); 
            welcomeLabel.setText("Bienvenue dans l'application de planification de bureau");
            submitButton.setText("soumettre");
            isFrench = true;
            
        }
    }

}
