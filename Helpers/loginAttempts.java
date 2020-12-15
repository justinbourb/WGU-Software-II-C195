package Helpers;

import java.io.IOException;
import java.nio.file.Path;

/**This class records user log in attempts to login_activity.txt */
public class loginAttempts {
    /**This function will record all user log-in attempts, dates, and time stamps and whether each log-in attempt was
     * successful in a file named login_activity.txt. Each new record is appended to the existing file and saves the
     * information to the root folder of the application.
     * @param userNameAndStatus, a String stating "user name login was successful or failed"
     */
     public static void recordLoginAttempts(String userNameAndStatus) {
        Path path = fileFunctions.findPath();
        fileFunctions.createPath(path);
        fileFunctions.newBufferedReader(path);
        try {
            fileFunctions.newBufferedWriter(path, timeFunctions.localDateNow() + " " + userNameAndStatus + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}