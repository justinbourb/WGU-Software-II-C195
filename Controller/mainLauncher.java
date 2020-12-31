package Controller;

import DAO.connect;
import Helpers.timeFunctions;
import com.mysql.cj.jdbc.admin.TimezoneDump;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import static Helpers.timeFunctions.getDateTimeFromInput;
import static Helpers.timeFunctions.getUTCTimeZone;

/** This class launches the application */
public class mainLauncher extends Application {

    /**This function is automatically called by Java.
     It builds GUI to display.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("/View/loginView.fxml"));
        //use mainView for testing only, bypasses login
        Parent root = FXMLLoader.load(getClass().getResource("/View/mainView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private static void testing(String input) throws ParseException {
//        https://www.java67.com/2012/12/how-to-display-date-in-multiple-timezone-java.html
        //my function
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);
        localDateTime.format(outputFormatter);
//        System.out.println(localDateTime);
//        //ZoneId zoneId = ZoneId.of("Europe/Berlin");
//        ZoneId zoneId = ZoneId.of(String.valueOf(TimeZone.getDefault().getID()));
//
//        System.out.println(TimeZone.getDefault().getRawOffset());
//        ZoneOffset zoneOffset = zoneId.getRules().getOffset(localDateTime);
//        System.out.println("Zoneoffset: " + zoneOffset);
//        System.out.println("localdatetime: " + localDateTime);

        //online function
        //capturing today's date
        Date today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(localDateTime.format(outputFormatter));
        System.out.println(today);
        //displaying this date on IST timezone
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String IST = df.format(today);
        System.out.println("Date in Indian Timezone (IST) : " + IST);

        //dispalying date on PST timezone
        df.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
        String PST = df.format(today);
        System.out.println("Date in EST Timezone (EST) : " + PST);
        df.setTimeZone(TimeZone.getTimeZone("UTC/Greenwich"));
        String UTC = df.format(today);
        System.out.println("Date in UTC: " + UTC);
    }

    /**This function is automatically called by Java.
     It connects to the database for login purposes.
     * @param args, default parameter provided by Java
     */
    public static void main(String[] args) throws ParseException {
        //Set Locale to France for testing purposes
        //Locale.setDefault(Locale.FRANCE);
        //Set defaut TimeZone to test displaying time in local time zones
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//        System.out.println(TimeZone.getDefault());
//        System.out.println(new Date());
//        ZonedDateTime date = ZonedDateTime.of(2020,12,9,9,23,0,0, ZoneId.of("UTC"));
        //getUTCTimeZone(getDateTimeFromInput("2020-12-30 10:00"));
        launch(args);
    }
}
