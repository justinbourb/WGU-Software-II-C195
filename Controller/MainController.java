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

public class MainController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBConnection.startConnection();
        launch(args);
        //DBConnection.closeConnection();
    }
}
