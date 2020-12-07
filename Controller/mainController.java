package Controller;

import com.sun.tools.jconsole.JConsoleContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.DataOutput;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.read;

import static DAO.read.readData;

public class mainController {

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button submitButton;


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
            System.out.println("(no results) the user name or password did not match");
        }
        while(results.next()) {
            String user_name = results.getString("User_Name");
            String stored_password = results.getString("Password");
            if (stored_password.equals(password)) {
                System.out.println("log in successful");
            } else {
                System.out.println("the user name or password did not match");
            }
        }
    }

}