package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link EmailSlicer} to ensure behavioral parity with the original Python script.
 * <p>
 * Each test redirects {@code System.in} and {@code System.out} to simulate CLI interaction
 * and capture output for assertion.
 */
class EmailSlicerTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Simulates stdin with the given input string.
     */
    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    /**
     * Returns the captured stdout output with normalized line endings.
     */
    private String getCapturedOutput() {
        return capturedOutput.toString().replace("\r\n", "\n");
    }

    @Test
    void testValidEmailInput() throws Exception {
        provideInput("avimax37@gmail.com\n");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Your username is:  avimax37\n"
                + "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void testInvalidEmailInput() throws Exception {
        provideInput("invalidemail\n");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void testWhitespaceHandling() throws Exception {
        provideInput("  user@domain.com  \n");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Your username is:  user\n"
                + "Your domain is:  domain.com\n";
        assertEquals(expected, getCapturedOutput());
    }
}
