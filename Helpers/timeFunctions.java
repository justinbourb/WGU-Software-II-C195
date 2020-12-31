package Helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**This class contains functions related to time.*/
public class timeFunctions {
    /**This function captures the time in the local format of the user and UTC time
     * @return returnValue, a UTC time in String format*/
    public static String localDateNow() {
        //Lambda expressions are beneficial in reducing the number of lines of code.
        Supplier<ZonedDateTime> nowUTC = () -> ZonedDateTime.now(ZoneOffset.UTC);
        //Supplier<ZonedDateTime> now = () -> ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return nowUTC.get().format(formatter);
    }

    /**
     * This is a functional interface for a Supplier
     */
    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }

    /**This function converts a DateTime string into a date string.
     *
     * @param DateTime, a String with date and time
     * @return a String with date
     */
    public static String getDateFromDateTime (String DateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime localDateTime = LocalDateTime.parse(DateTime, formatter);
        return localDateTime.format(dateFormatter);
    }

    /** This function converts a DateTime string into a HH format.
     *
     * @param DateTime, A String with date and time.
     * @return a String with HH format.
     */
    public static String getHoursFromDateTime (String DateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH");
        LocalDateTime localDateTime = LocalDateTime.parse(DateTime, formatter);
        return localDateTime.format(hourFormatter);
    }

    /** This function converts a DateTime string into a mm format.
     *
     * @param DateTime, A String with date and time.
     * @return a String with mm format.
     */
    public static String getMinutesFromDateTime (String DateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter minuteFormatter = DateTimeFormatter.ofPattern("mm");
        LocalDateTime localDateTime = LocalDateTime.parse(DateTime, formatter);
        return localDateTime.format(minuteFormatter);
    }

    /**This function converts a String input into a localDateTime format.
     *
     * @param input a String
     * @return a localDateTime format.
     */
    public static String getDateTimeFromInput (String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);
        return localDateTime.format(outputFormatter);
    }

    /**This function accepts a DateTime string from the database.  Per the instructions
     * information in the database is assumed to be from the "UTC" timezone.  Thus this
     * function first stores the default time zone as set on the local computer,
     * then it sets the default to UTC before accessing the data from the database.
     * Then it converts the database time into local time and restores the default time
     * zone to it's previous setting.
     * @param input, a DateTime string from the database
     * @return the DateTime converted to local time
     * @throws ParseException
     */
    public static String getLocalTimeZone(String input) throws ParseException {
        //store original timezone settings
        String defaultZone = TimeZone.getDefault().getID();
        //set UTC as default, all database time is in UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Date today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(defaultZone));
        //format the time as local time
        String localTimeFormat = df.format(today);
        //restore default timezone settings
        TimeZone.setDefault(TimeZone.getTimeZone(defaultZone));
        //return time in local time zone
        return localTimeFormat;
    }
}
