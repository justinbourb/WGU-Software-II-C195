package Helpers;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
}
