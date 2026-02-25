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
 * Tests for the EmailSlicer CLI application.
 *
 * Each test redirects System.in and System.out to simulate user input
 * and capture printed output, then asserts exact behavioral parity
 * with the original Python emailSlicer.py script.
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
     * Provides simulated stdin input for a test.
     */
    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    /**
     * Returns captured stdout, normalized to use Unix line endings.
     */
    private String getCapturedOutput() {
        return capturedOut.toString().replace("\r\n", "\n");
    }

    @Test
    void testValidEmail() throws Exception {
        provideInput("avimax37@gmail.com\n");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Your username is:  avimax37\n"
                + "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void testInvalidEmail() throws Exception {
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
