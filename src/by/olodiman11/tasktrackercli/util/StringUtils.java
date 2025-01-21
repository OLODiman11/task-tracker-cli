package by.olodiman11.tasktrackercli.util;

public interface StringUtils {
    static String strip(String str, String sequence) {
        String result = str;
        while(result.length() >= 2 * sequence.length()
                && result.startsWith(sequence)
                && result.endsWith(sequence)) {
            result = result.substring(sequence.length(), result.length() - sequence.length());
        }
        return result;
    }
}
