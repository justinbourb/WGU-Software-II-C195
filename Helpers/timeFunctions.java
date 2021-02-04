package Helpers;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**This class contains functions related to time.*/
public class timeFunctions {
    /**This function captures the time in the local format of the user and UTC time
     * @return returnValue, a UTC time in String format*/
    public static String localDateNow() {
        //Lambda expressions are beneficial in reducing the number of lines of code.
        //sets nowUTC to UTC time
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
     * @param LocalDateTime, a String with date and time
     * @return a String with date
     */
    public static String getDateFromDateTime (LocalDateTime LocalDateTime) {
        LocalDate localDate = LocalDateTime.toLocalDate();
        return String.valueOf(localDate);
    }

    /** This function converts a DateTime string into a HH format.
     *
     * @param LocalDateTime, A String with date and time.
     * @return a String with HH format.
     */
    public static String getHoursFromDateTime (LocalDateTime LocalDateTime) {
        LocalTime localTime = LocalDateTime.toLocalTime();
        return String.valueOf(localTime.getHour());
    }

    /** This function converts a DateTime string into a mm format.
     *
     * @param LocalDateTime, A String with date and time.
     * @return a String with mm format.
     */
    public static String getMinutesFromDateTime (LocalDateTime LocalDateTime) {
        LocalTime localTime = LocalDateTime.toLocalTime();
        return String.valueOf(localTime.getMinute());
    }

    /** This function converts a DateTime string into a HH format.
     *
     * @param DateTime, A String with date and time.
     * @return a String with HH format.
     */
    public static String getMonthFromDateTime (String DateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("MM");
        LocalDateTime localDateTime = LocalDateTime.parse(DateTime, formatter);
        return localDateTime.format(hourFormatter);
    }

    /**This function converts a String input into a localDateTime format.
     *
     * @param input a String
     * @return a localDateTime format.
     */
    public static String getTimeStampFromInput(String input){
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
     * @param inputTimestamp, a Timestamp from the database in UTC time
     * @return the DateTime converted to local time
     * @throws ParseException, an exception
     */

    public static String getLocalTimeZoneString(Timestamp inputTimestamp) {
        //convert timestamp into LocalDateTime object
        LocalDateTime utcLocalDateTime = inputTimestamp.toLocalDateTime();
        //convert LocalDateTime object into UTC ZonedDateTime Object
        //Database time is stored in UTC time, which is why UTC is used here
        //assuming this function is always called on data from the database
        ZonedDateTime utcZonedDateTime = utcLocalDateTime.atZone(ZoneId.of("UTC"));
        //convert utcZonedDateTime into the user time zone
        ZonedDateTime userZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        //output format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //convert ZonedDateTime back into LocalDateTime for printing results
        LocalDateTime userLocalDateTime = userZonedDateTime.toLocalDateTime();
//        System.out.println("Converting UTC time: " + dateTimeFormatter.format(utcZonedDateTime));
//        System.out.println("Returning " + ZoneId.systemDefault() + " time: " + dateTimeFormatter.format(userLocalDateTime));
//        System.out.println("============================================");
        return dateTimeFormatter.format(userLocalDateTime);
    }


    /**This function accepts a Timestamp and returns it's formatted string equivalent.
     *
     * @param inputTimestamp, a Timestamp object
     * @return a String representation via dateTimeFormatter
     */
    public static String getTimeZoneString(Timestamp inputTimestamp) {
        LocalDateTime localLocalDateTime = inputTimestamp.toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(localLocalDateTime);
    }

    public static String getTimeString(Timestamp inputTimestamp) {
        LocalDateTime localLocalDateTime = inputTimestamp.toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTimeFormatter.format(localLocalDateTime);
    }

    public static LocalDateTime getLocalTimeZoneLocalDateTime(Timestamp inputTimestamp) {
        //convert timestamp into LocalDateTime object
        LocalDateTime utcLocalDateTime = inputTimestamp.toLocalDateTime();
        //convert LocalDateTime object into UTC ZonedDateTime Object
        //Database time is stored in UTC time, which is why UTC is used here
        //assuming this function is always called on data from the database
        ZonedDateTime utcZonedDateTime = utcLocalDateTime.atZone(ZoneId.of("UTC"));
        //convert utcZonedDateTime into the user time zone
        ZonedDateTime userZonedDateTime = utcZonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        //output format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //convert ZonedDateTime back into LocalDateTime for printing results
        LocalDateTime userLocalDateTime = userZonedDateTime.toLocalDateTime();
//        System.out.println("Converting UTC time: " + dateTimeFormatter.format(utcZonedDateTime));
//        System.out.println("Returning " + ZoneId.systemDefault() + " time: " + dateTimeFormatter.format(userLocalDateTime));
//        System.out.println("============================================");
        return userLocalDateTime;
    }

    public static LocalDateTime getLocalDateTimeFromTimeStamp(Timestamp inputTimestamp) {
        return inputTimestamp.toLocalDateTime();
    }

    /** This function converts a local DateTime into UTC DateTime
     *
     * @param input, a local DateTime String
     * @return a UTC DateTime String
     * @throws ParseException, an exception
     */
    public static String getUTCTimeZone(String input) throws ParseException {
        Date today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        //return time in UTC time zone
        return df.format(today);
    }

    public static LocalDateTime getLocalDateTimeFromString(String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(input, formatter);
        return localDateTime;
    }

    /**This function accepts a LocalDateTime object in the user's time zone and
     * returns a LocalDateTime object in UTC time.
     * @param input a LocalDateTime in the user's time zone
     * @return a LocalDateTime in UTC time zone
     */
    public static LocalDateTime getUTCLocalDateTimeFromLocalDateTime(LocalDateTime input){
        //create a ZonedDateTime based on input in the user's time zone
        ZonedDateTime userZonedDateTime = input.atZone(ZoneId.systemDefault());
        //convert to UTC timezone for database storage
        ZonedDateTime utcZonedDateTime = userZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        //return a LocalDateTime in UTC time
        return utcZonedDateTime.toLocalDateTime();
    }

    /**This function accepts a string and converts it to a Timestamp, then it converts
     * that LocalDateTime into a UTC LocalDateTime using getUTCLocalDateTimeFromLocalDateTime().
     * A Timestamp is returned.
     * @param input, a string approximating a Timestamp
     *               example: "2020-12-31 08:00"
     * @return a Timestamp in UTC time
     */
    public static Timestamp getUTCTimestampFromString(String input){
        //input string to DateTimeFormatter to LocalDateTime to ZonedDateTime to UTC to Timestamp
        //convert input string to a LocalDateTime
        LocalDateTime localDateTime = getLocalDateTimeFromString(input);
        //convert from user time zone to UTC time zone as LocalDateTime
        LocalDateTime utcDateTime = getUTCLocalDateTimeFromLocalDateTime(localDateTime);
        //return a Timestamp for database storage
        return Timestamp.valueOf(utcDateTime);
    }

    public static Timestamp getUTCTimestampFromLocalDateTime(LocalDateTime input){
        LocalDateTime utcLocalDateTime = getUTCLocalDateTimeFromLocalDateTime(input);
        return Timestamp.valueOf(utcLocalDateTime);
    }

    /**This function will calculate the starting and ending times in the user's
     * local time zone and return the hours as in Integer which can be used
     * to populate the Spinner object in the appointment View.
     * Expected output: 8:00 EST start and 21:00 EST ending time
     * @param startOrFinish a string "start" or "finish"
     * @return a Integer of the starting or finishing times as hours
     */
    public static Integer getStartingHoursInLocalTimeZone(String startOrFinish){
        //generate a UTC LocalDateTime at the business closing hours of 02:00
        LocalDateTime utcLocalDateTime = LocalDateTime.of(2021,1,1,2,0);
        if(startOrFinish.equals("start")){
            //generate a UTC LocalDateTime at the business opening hours of 13:00
            utcLocalDateTime = LocalDateTime.of(2021,1,1,13,0);
        }
        //convert to the Timestamp for use in getLocalTimeZoneLocalDateTime()
        Timestamp utcTimeStamp = Timestamp.valueOf(utcLocalDateTime);
        //convert to the user's local time zone
        LocalDateTime localLocalDateTime = getLocalTimeZoneLocalDateTime(utcTimeStamp);
        //convert to LocalTime to fill out the spinner
        LocalTime userLocalTime = localLocalDateTime.toLocalTime();
        //return the starting or closing hours in user local time as an int
        return userLocalTime.getHour();
    }
}
