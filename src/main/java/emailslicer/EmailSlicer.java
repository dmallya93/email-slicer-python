package emailslicer;

import java.util.Scanner;

/**
 * A simple CLI tool that reads an email address from standard input,
 * validates it, and splits it into username and domain components.
 *
 * This is a Java port of the Python emailSlicer.py script.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine().strip();

        if (email.indexOf("@") != -1) {
            String username = email.substring(0, email.indexOf("@"));
            String domain = email.substring(email.indexOf("@") + 1);
            System.out.println("Your username is:  " + username);
            System.out.println("Your domain is:  " + domain);
        } else {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
