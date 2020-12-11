package Helpers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.*;

/** This class will setup and show a new Stage (GUI Screen) after a button click.*/
public class switchStage {

    private static Stage stage;
    private static Parent scene;

    /** This class will setup and show a new Stage (GUI Screen) after a button click.
    *@param event, a ActionEvent provided by a button click
    *@param resourceURL, a string pointing to a .fxml file
    */
    @FXML
    static void switchStage(ActionEvent event, String resourceURL) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = load(switchStage.class.getResource(resourceURL));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
