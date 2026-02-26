package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the EmailSlicer CLI application.
 */
class EmailSlicerTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void setUp() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    private String getCapturedOutput() {
        return capturedOutput.toString();
    }

    @Test
    void testValidEmail() {
        provideInput("avimax37@gmail.com\n");
        EmailSlicer.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: avimax37"),
                "Output should contain the correct username");
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Output should contain the correct domain");
    }

    @Test
    void testInvalidEmailMissingAt() {
        provideInput("invalid-email\n");
        EmailSlicer.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain the error message for invalid email");
    }

    @Test
    void testEmptyInput() {
        provideInput("\n");
        EmailSlicer.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Empty input should be treated as invalid email");
    }

    @Test
    void testEmailWithMultipleAtSymbols() {
        provideInput("a@b@c\n");
        EmailSlicer.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: a"),
                "Username should be the part before the first @");
        assertTrue(output.contains("Your domain is: b@c"),
                "Domain should be everything after the first @");
    }

    @Test
    void testEmailWithWhitespace() {
        provideInput("  user@example.com  \n");
        EmailSlicer.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: user"),
                "Whitespace should be stripped before processing");
        assertTrue(output.contains("Your domain is: example.com"),
                "Domain should be correct after stripping whitespace");
    }

    @Test
    void testPromptIsDisplayed() {
        provideInput("test@test.com\n");
        EmailSlicer.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "The prompt should be displayed");
    }
}
