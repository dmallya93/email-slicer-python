package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for {@link EmailSlicer} that verify behavior parity with the original
 * Python {@code emailSlicer.py} script.
 *
 * <p>Each test redirects {@code System.in} and {@code System.out} to capture
 * console I/O, invokes {@code EmailSlicer.main}, and asserts on the captured output.</p>
 */
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

        assertTrue(output.contains("Enter your email:"), "Should display prompt");
        assertTrue(output.contains("Your username is:  avimax37"), "Should print username");
        assertTrue(output.contains("Your domain is:  gmail.com"), "Should print domain");
    }

    @Test
    void invalidEmail_noAtSymbol_printsError() {
        String output = runWithInput("invalidemail\n");

        assertTrue(output.contains("Enter your email:"), "Should display prompt");
        assertTrue(output.contains("Please enter a valid Email Id."), "Should print error for missing @");
        assertFalse(output.contains("Your username is:"), "Should not print username");
        assertFalse(output.contains("Your domain is:"), "Should not print domain");
    }

    @Test
    void emailWithWhitespace_trimmedBeforeParsing() {
        String output = runWithInput("   user@example.com  \n");

        assertTrue(output.contains("Your username is:  user"), "Should trim whitespace and extract username");
        assertTrue(output.contains("Your domain is:  example.com"), "Should trim whitespace and extract domain");
    }

    @Test
    void emailWithMultipleAtSymbols_splitsOnFirstAt() {
        String output = runWithInput("user@sub@example.com\n");

        assertTrue(output.contains("Your username is:  user"), "Should split on first @ for username");
        assertTrue(output.contains("Your domain is:  sub@example.com"), "Should include everything after first @ as domain");
    }

    @Test
    void emptyInput_treatedAsInvalid() {
        String output = runWithInput("\n");

        assertTrue(output.contains("Please enter a valid Email Id."), "Empty input should be invalid");
        assertFalse(output.contains("Your username is:"), "Should not print username for empty input");
    }

    @Test
    void atSymbolAtStart_emptyUsername() {
        String output = runWithInput("@domain.com\n");

        assertTrue(output.contains("Your username is:  "), "Should handle empty username without crashing");
        assertTrue(output.contains("Your domain is:  domain.com"), "Should extract domain correctly");
    }
}
