package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static Helpers.timeFunctions.*;
import static Helpers.timeFunctions.getUTCTimestampFromString;


/** This class launches the application */
public class mainLauncher extends Application {

    /**This function is automatically called by Java.
     It builds GUI to display.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
        //mainView is used for testing only
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**This function is automatically called by Java.
     It connects to the database for login purposes.
     * @param args, default parameter provided by Java
     */
    public static void main(String[] args) throws ParseException {
        //testing();
        //testing2();
        launch(args);
    }

    /** This function is not for use in production mode, it holds
     * code to be tested before final implementation.
     */
    public static void testing(){
        String defaultZone = TimeZone.getDefault().getID();
        //date time comes out of the database as a string
        //perhaps rs.getTimeStamp("columnName") and ps.setTimeStamp(index, timeStamp)
        //should be used instead.
        //covert it into a Timestamp object
        Timestamp timestamp = Timestamp.valueOf("2021-01-07 13:00:00");
        //convert the timestamp object into a LocalDateTime object
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        //convert the LocalDateTime into a ZonedDateTime object and set which timezone to use
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
        //When converting the ZonedDateTime into another ZonedDateTime use .withZoneSameInstant
        ZonedDateTime zonedDifferentTime = zonedDateTime.withZoneSameInstant(ZoneId.of(defaultZone));

        //determines the string output for a Zoned or LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //LocalDate conversion
        LocalDate localDifferentDate = zonedDifferentTime.toLocalDate();
        LocalTime localDifferentTime = zonedDifferentTime.toLocalTime();

        //TimeStamp, LocalDateTime and ZonedDateTime conversions
        System.out.println("Timestamp: " + timestamp);
        System.out.println("localDateTime: " + localDateTime);
        System.out.println("===============================");
        System.out.println("localDateTime at zone UTC: " + zonedDateTime);
        System.out.println("formatted UTC  time: " + dateTimeFormatter.format(zonedDateTime));
        System.out.println("===============================");
        System.out.println("localDateTime at zone default: " + zonedDifferentTime);
        //format output using dateTimeFormatter
        System.out.println("formatted local time: " + dateTimeFormatter.format(zonedDifferentTime));
        //LocalDate testing
        System.out.println("===============================");
        System.out.println("local Day of Week: " + localDifferentDate.getDayOfWeek());
        System.out.println("local Month: " + localDifferentDate.getMonth());
        System.out.println("local Day of Month: " + localDifferentDate.getDayOfMonth());
        System.out.println("===============================");
        System.out.println("local Hour: " + localDifferentTime.getHour() + " Local Minute: " + localDifferentTime.getMinute());
        localDifferentTime = LocalTime.of(9, 25);
        System.out.println("localDifferentTime updated to: " + localDifferentTime);

    }
    public static void testing2(){
        System.out.println("Input: 12:30 PST Expected output: 20:30 UTC");
        System.out.println(getUTCTimestampFromString("2021-01-02 12:30"));
        //I expect +8 hours to the time from PST to UTC
        //However my database shows -8 hours from PST to UTC... how is this happening???
        /*
        Start Date and Start time are pulled from the GUI
        A Timestamp is created in UTC time, which seems to be working correctly via getUTCTimestampFromString
        Then the UTC time is written to the database, no further modifications are made to the time
        Is the database automatically adding 8 hours?
         */
        System.out.println("Input: 20:30 PST Expected output: 04:30 UTC");
        System.out.println(getUTCTimestampFromString("2021-01-02 20:30"));
    }
}
