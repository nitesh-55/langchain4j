package dev.langchain4j.utils;

public class HelperUtils {

    public static String ensureNotBlank(String string, String name) {
        if (string == null || string.trim().isEmpty()) {
            throw illegalArgument("%s cannot be null or blank", name);
        }

        return string;
    }

    public static IllegalArgumentException illegalArgument(String format, Object... args) {
        return new IllegalArgumentException(format.formatted(args));
    }
    public static int ensureGreaterThanZero(Integer i, String name) {
        if (i == null || i <= 0) {
            throw illegalArgument("%s must be greater than zero, but is: %s", name, i);
        }

        return i;
    }
}
