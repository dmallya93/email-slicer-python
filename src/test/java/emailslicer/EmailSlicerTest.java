package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailSlicerTest {

    private static final String LS = System.lineSeparator();
    private static final String PROMPT = "Enter your email: ";

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

        String expected = PROMPT
                + "Your username is:  avimax37" + LS
                + "Your domain is:  gmail.com" + LS;
        assertEquals(expected, output);
    }

    @Test
    void invalidEmail_printsError() {
        String output = runWithInput("invalidemail\n");

        String expected = PROMPT + "Please enter a valid Email Id." + LS;
        assertEquals(expected, output);
    }

    @Test
    void emailWithWhitespace_isTrimmedBeforeParsing() {
        String output = runWithInput("   user@example.com  \n");

        String expected = PROMPT
                + "Your username is:  user" + LS
                + "Your domain is:  example.com" + LS;
        assertEquals(expected, output);
    }

    @Test
    void emailWithMultipleAtSymbols_splitsOnFirstAt() {
        String output = runWithInput("user@sub@example.com\n");

        String expected = PROMPT
                + "Your username is:  user" + LS
                + "Your domain is:  sub@example.com" + LS;
        assertEquals(expected, output);
    }

    @Test
    void emptyInput_printsError() {
        String output = runWithInput("\n");

        String expected = PROMPT + "Please enter a valid Email Id." + LS;
        assertEquals(expected, output);
    }

    @Test
    void atAtStart_printsEmptyUsername() {
        String output = runWithInput("@domain.com\n");

        String expected = PROMPT
                + "Your username is:  " + LS
                + "Your domain is:  domain.com" + LS;
        assertEquals(expected, output);
    }

    @Test
    void onlyWhitespace_printsError() {
        String output = runWithInput("   \n");

        String expected = PROMPT + "Please enter a valid Email Id." + LS;
        assertEquals(expected, output);
    }

    @Test
    void atAtEnd_printsEmptyDomain() {
        String output = runWithInput("user@\n");

        String expected = PROMPT
                + "Your username is:  user" + LS
                + "Your domain is:  " + LS;
        assertEquals(expected, output);
    }

    @Test
    void onlyAtSymbol_printsEmptyUsernameAndDomain() {
        String output = runWithInput("@\n");

        String expected = PROMPT
                + "Your username is:  " + LS
                + "Your domain is:  " + LS;
        assertEquals(expected, output);
    }
}
