package emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple CLI tool that parses an email address into username and domain components.
 * <p>
 * This is a Java port of the Python email-slicer script ({@code emailSlicer.py}).
 * It reads an email address from stdin, validates the presence of {@code @},
 * and prints the username and domain parts.
 */
public class EmailSlicer {

    public static void main(String[] args) throws IOException {
        System.out.println("Please enter your Email Id:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String email = reader.readLine().trim();

        if (email.contains("@")) {
            int atIndex = email.indexOf('@');
            String username = email.substring(0, atIndex);
            String domain = email.substring(atIndex + 1);
            System.out.println("Your username is:  " + username);
            System.out.println("Your domain is:  " + domain);
        } else {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
