package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailSlicerTest {

    private PrintStream originalOut;
    private InputStream originalIn;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        originalIn = System.in;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private String runWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        EmailSlicer.main(new String[]{});
        return outContent.toString();
    }

    @Test
    void validEmail_printsUsernameAndDomain() {
        String output = runWithInput("avimax37@gmail.com\n");

        assertTrue(output.contains("Your username is:  avimax37"),
                "Expected username output, got: " + output);
        assertTrue(output.contains("Your domain is:  gmail.com"),
                "Expected domain output, got: " + output);
    }

    @Test
    void invalidEmail_noAtSign_printsError() {
        String output = runWithInput("invalidemail\n");

        assertTrue(output.contains("Please enter a valid Email Id."),
                "Expected error message, got: " + output);
    }

    @Test
    void emailWithWhitespace_trimmedBeforeParsing() {
        String output = runWithInput("   user@example.com  \n");

        assertTrue(output.contains("Your username is:  user"),
                "Expected trimmed username, got: " + output);
        assertTrue(output.contains("Your domain is:  example.com"),
                "Expected trimmed domain, got: " + output);
    }

    @Test
    void emailWithMultipleAtSigns_splitsOnFirstAt() {
        String output = runWithInput("user@sub@example.com\n");

        assertTrue(output.contains("Your username is:  user"),
                "Expected username before first @, got: " + output);
        assertTrue(output.contains("Your domain is:  sub@example.com"),
                "Expected domain after first @, got: " + output);
    }

    @Test
    void emptyInput_printsError() {
        String output = runWithInput("\n");

        assertTrue(output.contains("Please enter a valid Email Id."),
                "Expected error for empty input, got: " + output);
    }

    @Test
    void atSignAtStart_emptyUsername() {
        String output = runWithInput("@domain.com\n");

        assertTrue(output.contains("Your username is:  "),
                "Expected empty username output, got: " + output);
        assertTrue(output.contains("Your domain is:  domain.com"),
                "Expected domain output, got: " + output);
    }

    @Test
    void promptIsDisplayed() {
        String output = runWithInput("test@test.com\n");

        assertTrue(output.contains("Enter your email:"),
                "Expected prompt text, got: " + output);
    }
}
