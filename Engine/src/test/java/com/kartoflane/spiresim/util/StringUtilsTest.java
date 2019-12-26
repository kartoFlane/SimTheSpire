package com.kartoflane.spiresim.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void convertSnakeCaseToCamelCaseShouldProduceCamelCase_Lower() {
        // Prepare
        final String input = "SNAKE_CASE_TEXT";
        final String expectedResult = "snakeCaseText";

        // Execute
        String output = StringUtils.convertSnakeCaseToLowerCamelCase(input);

        // Check
        assertEquals(expectedResult, output);
    }

    @Test
    public void convertSnakeCaseToCamelCaseShouldProduceCamelCase_Upper() {
        // Prepare
        final String input = "SNAKE_CASE_TEXT";
        final String expectedResult = "SnakeCaseText";

        // Execute
        String output = StringUtils.convertSnakeCaseToUpperCamelCase(input);

        // Check
        assertEquals(expectedResult, output);
    }

    @Test
    public void lowercaseFirstLetter() {
        // Prepare
        final String input = "TestString";
        final String expectedResult = "testString";

        // Execute
        String output = StringUtils.lowercaseFirstLetter(input);

        // Check
        assertEquals(expectedResult, output);
    }
}
