package Controller;

import DAO.connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** This class launches the application */
public class mainLauncher extends Application {

    /**This function is automatically called by Java.
     It builds GUI to display.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**This function is automatically called by Java.
     It connects to the database for login purposes.
     * @param args, default parameter provided by Java
     * @exception ClassNotFoundException, an exception
     */
    public static void main(String[] args) throws ClassNotFoundException {
        connect.startConnection();
        launch(args);
        try {
            connect.closeConnection();
        } catch (Exception e) {

        }
    }
}
