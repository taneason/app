package com.mycompany.app;

import java.util.Scanner;

public class ValidationUtil {
    public static void validateEmail(String email) throws IllegalArgumentException {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public static void validatePhone(String phone) throws IllegalArgumentException {
        if (phone == null || !phone.matches("^\\d{3}-\\d{7,8}$")) {
            throw new IllegalArgumentException("Invalid phone format (must be XXX-XXXXXXX)");
        }
    }

    public static void validateName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().length() < 2) {
            throw new IllegalArgumentException("Name must be at least 2 characters long");
        }
    }

    public static String validateAndConfirmPassword(Scanner scanner) throws IllegalArgumentException {
        String password = getValidatedInput(scanner, "Enter password: ", input -> {
            if (input == null || input.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long");
            }
        });
        
        // Validate the confirmation password without storing it
        getValidatedInput(scanner, "Confirm password: ", input -> {
            if (!input.equals(password)) {
                throw new IllegalArgumentException("Passwords do not match");
            }
        });
        
        return password;
    }

    public static void validatePassword(String password) throws IllegalArgumentException {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }

    public static void validatePositiveNumber(double number, String fieldName) throws IllegalArgumentException {
        if (number <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0");
        }
    }

    public static void validatePositiveInteger(int number, String fieldName) throws IllegalArgumentException {
        if (number <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0");
        }
    }

    public static String getValidatedInput(java.util.Scanner scanner, String prompt, 
            java.util.function.Consumer<String> validator) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                validator.accept(input);
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static double getValidatedDouble(java.util.Scanner scanner, String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                if (value < min || value > max) {
                    throw new IllegalArgumentException(
                        String.format("Value must be between %.2f and %.2f", min, max));
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static int getValidatedInteger(java.util.Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value < min || value > max) {
                    throw new IllegalArgumentException(
                        String.format("Value must be between %d and %d", min, max));
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
