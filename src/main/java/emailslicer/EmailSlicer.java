package emailslicer;

import java.util.Scanner;

public class EmailSlicer {
    private static final String AT_SYMBOL = "@";

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");

        final String input = scanner.nextLine();
        final String email = input == null ? "" : input.trim();

        sliceAndPrint(email);
    }

    static void sliceAndPrint(String email) {
        final int atIndex = email.indexOf(AT_SYMBOL);
        if (atIndex == -1) {
            System.out.println("Please enter a valid Email Id.");
            return;
        }

        final String username = email.substring(0, atIndex);
        final String domain = email.substring(atIndex + 1);

        System.out.println("Your username is:  " + username);
        System.out.println("Your domain is:  " + domain);
    }
}
