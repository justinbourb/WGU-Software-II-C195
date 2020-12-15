package Helpers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**This class contains functions related to files*/
public class fileFunctions {
    /**
     * This function will create a file at the provided path if
     * it doesn't exist.
     *
     * @param path, a Path path to the file to be created.
     */
    public static void createPath(Path path) {
        if (!Files.exists(path)) {
            try {
                System.out.println("creating file " + path);
                Files.createFile(path);
            } catch (IOException e) {
                System.err.println(e);
            }
        } else {
            System.out.println("file already exists");
        }
    }

    /**
     * This function finds the path specified and finds if it exists
     *
     * @return path, A Path path.
     */
    public static Path findPath() {
        Path path = FileSystems.getDefault().getPath("src/login_activity.txt");
        return path;
    }

    /**This function will read data from a file.
     * @param path, Path path the path of the file to read.
     */
    public static void newBufferedReader(Path path) {
        try (var reader = Files.newBufferedReader(path)) {
            String currentLine = null;
            System.out.println("The file currently contains: ");
            while ((currentLine = reader.readLine()) != null)
                System.out.println(currentLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will append data to a file
     *
     * @param path, a Path path to the file to append to.
     */
    public static void newBufferedWriter(Path path, String textToAppend) throws IOException {
        try (var writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            writer.append(textToAppend);
        } catch (Exception e) {
            System.out.println("Helpers.appendToFile: The file doesn't exist");
        }
    }
}
