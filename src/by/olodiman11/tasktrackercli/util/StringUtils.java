package by.olodiman11.tasktrackercli.util;

public enum StringUtils {
    ;
    public static String strip(String str, String sequence) {
        String result = str;
        while(result.length() >= 2 * sequence.length()
                && result.startsWith(sequence)
                && result.endsWith(sequence)) {
            result = result.substring(sequence.length(), result.length() - sequence.length());
        }
        return result;
    }

    public static String unquote(String str) {
        return strip(str, "\"");
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
