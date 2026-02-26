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

        assertTrue(output.contains("Enter your email: "));
        assertTrue(output.contains("Your username is:  avimax37"));
        assertTrue(output.contains("Your domain is:  gmail.com"));
    }

    @Test
    void invalidEmail_printsError() {
        String output = runWithInput("invalidemail\n");

        assertTrue(output.contains("Enter your email: "));
        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void emailWithWhitespace_isTrimmedBeforeParsing() {
        String output = runWithInput("   user@example.com  \n");

        assertTrue(output.contains("Your username is:  user"));
        assertTrue(output.contains("Your domain is:  example.com"));
    }

    @Test
    void emailWithMultipleAtSymbols_splitsOnFirstAt() {
        String output = runWithInput("user@sub@example.com\n");

        assertTrue(output.contains("Your username is:  user"));
        assertTrue(output.contains("Your domain is:  sub@example.com"));
    }

    @Test
    void emptyInput_printsError() {
        String output = runWithInput("\n");

        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void atAtStart_printsEmptyUsername() {
        String output = runWithInput("@domain.com\n");

        assertTrue(output.contains("Your username is:  "));
        assertTrue(output.contains("Your domain is:  domain.com"));
    }
}
