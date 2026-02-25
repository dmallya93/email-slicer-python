package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for EmailSlicer that verify behavioral equivalence with the Python original.
 * Each test redirects System.in and System.out to verify exact output.
 */
class EmailSlicerTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Helper to run EmailSlicer.main with the given input and return captured output.
     */
    private String runWithInput(String input) {
        String inputWithNewline = input + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(inputWithNewline.getBytes(StandardCharsets.UTF_8)));
        EmailSlicer.main(new String[]{});
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    /**
     * Build expected output lines separated by the platform line separator.
     */
    private String expectedOutput(String... lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Test
    void testValidEmail() {
        String output = runWithInput("avimax37@gmail.com");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Your username is:  avimax37",
                "Your domain is:  gmail.com"
        );
        assertEquals(expected, output);
    }

    @Test
    void testInvalidEmailNoAtSign() {
        String output = runWithInput("invalidemail");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Please enter a valid Email Id."
        );
        assertEquals(expected, output);
    }

    @Test
    void testWhitespaceHandling() {
        String output = runWithInput("  user@domain.com  ");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Your username is:  user",
                "Your domain is:  domain.com"
        );
        assertEquals(expected, output);
    }

    @Test
    void testEmptyInput() {
        String output = runWithInput("");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Please enter a valid Email Id."
        );
        assertEquals(expected, output);
    }

    @Test
    void testAtSignAtStart() {
        String output = runWithInput("@domain.com");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Your username is:  ",
                "Your domain is:  domain.com"
        );
        assertEquals(expected, output);
    }

    @Test
    void testAtSignAtEnd() {
        String output = runWithInput("user@");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Your username is:  user",
                "Your domain is:  "
        );
        assertEquals(expected, output);
    }

    @Test
    void testMultipleAtSigns() {
        String output = runWithInput("user@mid@domain.com");
        String expected = expectedOutput(
                "Please enter your Email Id:",
                "Your username is:  user",
                "Your domain is:  mid@domain.com"
        );
        assertEquals(expected, output);
    }
}
