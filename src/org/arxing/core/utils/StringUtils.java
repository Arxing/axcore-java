package org.arxing.core.utils;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String capitalize(String s) {
        return s == null || s.isEmpty() ? null : s.toUpperCase().charAt(0) + s.substring(1);
    }
}
