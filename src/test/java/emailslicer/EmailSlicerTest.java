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

    @Test
    void validEmail_printsUsernameAndDomain() {
        String input = "avimax37@gmail.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Your username is:  avimax37"));
        assertTrue(output.contains("Your domain is:  gmail.com"));
    }

    @Test
    void invalidEmail_printsError() {
        String input = "invalidemail\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void emailWithWhitespace_trimsBeforeParsing() {
        String input = "   user@example.com  \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Your username is:  user"));
        assertTrue(output.contains("Your domain is:  example.com"));
    }

    @Test
    void emailWithMultipleAtSymbols_splitsOnFirst() {
        String input = "user@sub@example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Your username is:  user"));
        assertTrue(output.contains("Your domain is:  sub@example.com"));
    }

    @Test
    void emptyInput_printsError() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void atStartOfEmail_printsEmptyUsername() {
        String input = "@domain.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Your username is:  "));
        assertTrue(output.contains("Your domain is:  domain.com"));
    }
}
