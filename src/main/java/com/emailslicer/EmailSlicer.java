package com.emailslicer;

import java.util.Scanner;

/**
 * Email Slicer CLI application.
 * Reads an email address from stdin and extracts the username and domain.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine().strip();

        if (email.indexOf("@") != -1) {
            String username = email.substring(0, email.indexOf("@"));
            String domain = email.substring(email.indexOf("@") + 1);
            System.out.println("Your username is: " + username);
            System.out.println("Your domain is: " + domain);
        } else {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
