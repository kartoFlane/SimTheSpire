package com.kartoflane.spiresim.util;

public class StringUtils {
    private StringUtils() {
    }

    /**
     * Example: snake_case -> SnakeCase
     */
    public static String convertSnakeCaseToUpperCamelCase(String input) {
        return convertSnakeCaseToCamelCase(input, false);
    }

    /**
     * Example: snake_case -> snakeCase
     */
    public static String convertSnakeCaseToLowerCamelCase(String input) {
        return convertSnakeCaseToCamelCase(input, true);
    }

    private static String convertSnakeCaseToCamelCase(String input, boolean lowerCaseFirstLetter) {
        StringBuilder buffer = new StringBuilder();

        for (String word : input.split("_")) {
            if (lowerCaseFirstLetter && buffer.length() == 0) {
                buffer.append(Character.toLowerCase(word.charAt(0)));
            } else {
                buffer.append(Character.toUpperCase(word.charAt(0)));
            }
            if (word.length() > 1) {
                String tail = word.substring(1).toLowerCase();
                buffer.append(tail);
            }
        }

        return buffer.toString();
    }

    public static String lowercaseFirstLetter(String input) {
        if (input == null) return null;
        if (input.equals("")) return "";

        return Character.toLowerCase(input.charAt(0)) + input.substring(1);
    }
}
