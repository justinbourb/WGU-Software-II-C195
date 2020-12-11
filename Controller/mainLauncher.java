package Controller;

import Helpers.DBConnection;
import Helpers.DatabaseExample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.Utilities;
import java.sql.SQLException;

public class mainLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
