package by.olodiman11.tasktrackercli.util;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum FileUtils {
    ;

    public static Path createFileIfAbsent(Path path) {
        try {
            return Files.createFile(path);
        } catch (FileAlreadyExistsException _) {
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Path writeString(Path path, CharSequence content) {
        try {
            return Files.writeString(path, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}