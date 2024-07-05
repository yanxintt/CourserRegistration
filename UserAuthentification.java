package asstest7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class UserAuthentification{
    private String filePath;
    private Scanner scanner;
    private static String username;

    public UserAuthentification(String filePath) {
        this.filePath = filePath;
        this.scanner = new Scanner(System.in); 
    }
    
    public static String getUsername() {
    	return username;
    }

    public boolean isValidUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String storedUsername = parts[0];
                    String storedPassword = parts[2];
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true; // Username and password match
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Username or password not found
    }

    public boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String storedUsername = parts[0];
                    if (storedUsername.equals(username)) {
                        return true; // Username exists
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Username not found
    }
    public boolean login() {
        System.out.print("Enter your username: ");
        username = scanner.nextLine().toUpperCase(); // Convert input to uppercase

        if (!userExists(username)) {
            System.out.println("Invalid username. Please try again.");
            return false; // Login failed
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (isValidUser(username, password)) {
            System.out.println("Welcome! " + username + ".");
            return true; // Login successful
        } else {
            System.out.println("Invalid password. Please try again.");
            return false; // Login failed
        }
    }
}
