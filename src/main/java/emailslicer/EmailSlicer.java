package emailslicer;

import java.util.Scanner;

/**
 * A simple CLI tool that takes an email address as input and slices it
 * into username and domain parts.
 *
 * <p>Translated from the original Python script {@code emailSlicer.py}.</p>
 */
public class EmailSlicer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");

        String input = scanner.nextLine();
        String email = input == null ? "" : input.trim();

        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            System.out.println("Please enter a valid Email Id.");
            return;
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        // Two spaces after the colon to match Python's print("label:", value) behavior,
        // where one space is in the string literal and the other is print's default separator.
        System.out.println("Your username is:  " + username);
        System.out.println("Your domain is:  " + domain);
    }
}
