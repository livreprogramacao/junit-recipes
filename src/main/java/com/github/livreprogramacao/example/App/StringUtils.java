package com.github.livreprogramacao.example.App;

import java.util.Objects;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isPalindrome(String s) {
        if (s == null) return false;
        String clean = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        return clean.equals(new StringBuilder(clean).reverse().toString());
    }

    public static String reverse(String s) {
        if (s == null) return null;
        return new StringBuilder(s).reverse().toString();
    }

    public static String joinWithDelimiter(String delimiter, String... parts) {
        Objects.requireNonNull(delimiter);
        if (parts == null || parts.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(delimiter);
            sb.append(parts[i] == null ? "null" : parts[i]);
        }
        return sb.toString();
    }
}
