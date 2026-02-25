package emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Email Slicer CLI application.
 *
 * Reads an email address from stdin, validates it contains '@',
 * and prints the username and domain parts. This is a direct port
 * of the original Python emailSlicer.py script.
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
