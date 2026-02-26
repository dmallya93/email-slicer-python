package emailslicer;

import java.util.Scanner;

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

        // Preserve Python's print spacing: print("Your username is:", username)
        // Python's print inserts a space between comma-separated args, resulting in two spaces total
        System.out.println("Your username is:  " + username);
        System.out.println("Your domain is:  " + domain);
    }
}
