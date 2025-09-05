/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
import java.util.Scanner;

public class ConsoleUI {
    private final RentalService service;
    private final Scanner sc = new Scanner(System.in);
    private Customer currentCustomer = null;
    
    private static final String LINE = "===================================================";
    private static final String DOUBLE_LINE = "+==================================================+";
    
    public ConsoleUI(RentalService service) {
        this.service = service;
    }
    
    private void printHeader(String title) {
        System.out.println("\n" + DOUBLE_LINE);
        System.out.println("|  " + centerText(title, 45) + "  |");
        System.out.println("+==================================================+");
    }
    
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }
    
    private void pressEnterToContinue() {
        System.out.println(LINE);
        System.out.print("Press Enter to continue...");
        sc.nextLine();
    }

    public void start() {
        while (true) {
            try {
                printHeader("FAMILY & CORPORATE VAN RENTAL SYSTEM");
                System.out.println("Welcome to our premium van rental service!");
                System.out.println(LINE);
                System.out.println("1. Login");
                System.out.println("2. Register New Account");
                System.out.println("3. Admin Login");
                System.out.println("0. Exit");
                System.out.println(LINE);
                
                int choice = ValidationUtil.getValidatedInteger(sc, "Please select an option: ", 0, 3);
                
                switch (choice) {
                    case 1 -> customerLogin();
                    case 2 -> registerCustomer();
                    case 3 -> adminLogin();
                    case 0 -> { 
                        printHeader("Thank you for using our service!");
                        return; 
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    // ------------------- CUSTOMER -------------------
    private void customerLogin() {
        try {
            printHeader("CUSTOMER LOGIN");
            String email = ValidationUtil.getValidatedInput(sc, "Email: ", ValidationUtil::validateEmail);
            String password = ValidationUtil.getValidatedInput(sc, "Password: ", ValidationUtil::validatePassword);
            
            currentCustomer = service.loginCustomer(email, password);
            pressEnterToContinue();
            customerMainMenu();
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            pressEnterToContinue();
        }
    }

    private void customerMainMenu() {
        while (currentCustomer != null && currentCustomer.isLoggedIn()) {
            try {
                printHeader("CUSTOMER MENU - " + currentCustomer.getName());
                System.out.println("1. Display Available Vehicles");
                System.out.println("2. Make a Booking");
                System.out.println("3. Return a Vehicle");
                System.out.println("4. View My Rental History");
                System.out.println("5. My Profile");
                System.out.println("0. Logout");
                System.out.println(LINE);
                
                int choice = ValidationUtil.getValidatedInteger(sc, "Please select an option: ", 0, 5);
                
                switch (choice) {
                    case 1 -> displayAvailableVehicles();
                    case 2 -> rentVehicle();
                    case 3 -> returnVehicle();
                    case 4 -> viewHistory();
                    case 5 -> viewProfile();
                    case 0 -> {
                        currentCustomer.logout();
                        currentCustomer = null;
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }
    
    private void displayAvailableVehicles() {
        printHeader("AVAILABLE VEHICLES");
        var vehicles = service.getAvailableVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles are currently available.");
        } else {
            System.out.println("Here are our available vehicles:\n");
            for (int i = 0; i < vehicles.size(); i++) {
                if (i > 0) System.out.println(); // Add space between vehicles
                System.out.println("OPTION " + (i + 1) + ":");
                System.out.println(vehicles.get(i));
            }
        }
        pressEnterToContinue();
    }
    
    private void viewProfile() {
        printHeader("MY PROFILE");
        System.out.println("Customer ID: " + currentCustomer.getCustomerId());
        System.out.println("Name: " + currentCustomer.getName());
        System.out.println("Email: " + currentCustomer.getEmail());
        System.out.println("Phone: " + currentCustomer.getPhone());
        System.out.println("Member Tier: " + currentCustomer.getTier().getDisplayName());
        System.out.println("Total Rentals: " + currentCustomer.getTotalRentals());
        System.out.println("Total Spent: RM" + String.format("%.2f", currentCustomer.getTotalSpent()));
        System.out.println("Loyalty Discount Rate: " + String.format("%.1f", currentCustomer.getLoyaltyDiscount()) + "%");
        pressEnterToContinue();
    }

    private void registerCustomer() {
        try {
            printHeader("NEW CUSTOMER REGISTRATION");
            System.out.println("Please fill in your information:");
            System.out.println(LINE);
            
            String name = ValidationUtil.getValidatedInput(sc, "Full Name: ", ValidationUtil::validateName);
            String email = ValidationUtil.getValidatedInput(sc, "Email Address: ", ValidationUtil::validateEmail);
            String phone = ValidationUtil.getValidatedInput(sc, "Phone Number: ", ValidationUtil::validatePhone);
            
            System.out.println("\nCreate your password:");
            System.out.println("(Minimum 4 characters long)");
            String password = ValidationUtil.validateAndConfirmPassword(sc);
            
            Customer c = new Customer(service.nextCustomerId(), name, email, phone, password);
            service.addCustomer(c);
            
            System.out.println("\n" + LINE);
            System.out.println("+ Registration successful!");
            System.out.println("Welcome to our service, " + name + "!");
            System.out.println(LINE);
            pressEnterToContinue();
            
            // Automatically log in after registration
            currentCustomer = c;
            currentCustomer.login(email, password);
            customerMainMenu();
        } catch (Exception e) {
            System.out.println("\n" + LINE);
            System.out.println("X Registration failed: " + e.getMessage());
            System.out.println(LINE);
            pressEnterToContinue();
        }
    }

    private void rentVehicle() {
        try {
            printHeader("MAKE A BOOKING");
            
            // Display available vehicles
            var availableVehicles = service.getAvailableVehicles();
            if (availableVehicles.isEmpty()) { 
                System.out.println("Sorry, no vehicles are currently available."); 
                pressEnterToContinue();
                return; 
            }
            
            System.out.println("--- Available Vehicles ---\n");
            for (int i = 0; i < availableVehicles.size(); i++) {
                if (i > 0) System.out.println(); // Add space between vehicles
                System.out.println("OPTION " + (i + 1) + ":");
                System.out.println(availableVehicles.get(i));
            }
            
            // Select vehicle
            System.out.println("\nPlease enter the number (1-" + availableVehicles.size() + ") of your chosen vehicle:");
            int vIdx = ValidationUtil.getValidatedInteger(sc, "Your choice: ", 
                1, availableVehicles.size()) - 1;
            Vehicle selectedVehicle = availableVehicles.get(vIdx);

            // Enter duration and group size
            System.out.println("\nSelected vehicle: " + selectedVehicle.getModel());
            System.out.println("Daily rate: RM" + selectedVehicle.getDailyRate());
            System.out.println("Maximum passenger capacity: " + selectedVehicle.getPassengerCapacity());
            
            int duration = ValidationUtil.getValidatedInteger(sc, "\nEnter rental duration (days, 1-30): ", 1, 30);
            int groupSize = ValidationUtil.getValidatedInteger(sc, "Enter number of passengers (1-" + 
                selectedVehicle.getPassengerCapacity() + "): ", 1, selectedVehicle.getPassengerCapacity());

            // Confirm booking
            double totalCost = duration * selectedVehicle.getDailyRate();
            System.out.println("\nBooking Summary:");
            System.out.println("Vehicle: " + selectedVehicle.getModel());
            System.out.println("Duration: " + duration + " days");
            System.out.println("Total Cost: RM" + totalCost);
            
            String confirm = ValidationUtil.getValidatedInput(sc, "\nConfirm booking (yes/no)? ", input -> {
                if (!input.toLowerCase().matches("^(yes|no)$")) {
                    throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                }
            });
            
            if (confirm.toLowerCase().equals("yes")) {
                Booking booking = service.rentVehicle(currentCustomer, selectedVehicle, duration, groupSize);
                System.out.println("\nBooking created successfully!");
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println(booking.detailed());
            } else {
                System.out.println("\nBooking cancelled.");
            }
            
            pressEnterToContinue();
        } catch (Exception e) {
            System.out.println("Error during booking: " + e.getMessage());
            pressEnterToContinue();
        }
    }

    private void returnVehicle() {
        try {
            printHeader("RETURN VEHICLE");
            
            // Get active bookings for current customer
            var activeBookings = service.getBookingsByCustomer(currentCustomer).stream()
                               .filter(b -> !b.isReturned())
                               .toList();
            
            if (activeBookings.isEmpty()) {
                System.out.println("\n[!] You have no active bookings to return.");
                pressEnterToContinue();
                return;
            }

            System.out.println("\nYour Active Bookings:");
            System.out.println(LINE);
            
            for (int i = 0; i < activeBookings.size(); i++) {
                if (i > 0) System.out.println(); // Add space between bookings
                System.out.println("BOOKING " + (i + 1) + ":");
                System.out.println(activeBookings.get(i));
            }

            int choice = ValidationUtil.getValidatedInteger(sc, 
                "\nSelect booking number to return (1-" + activeBookings.size() + "): ", 
                1, activeBookings.size());
            
            String bid = activeBookings.get(choice - 1).getBookingId();
            
            // Confirm return
            String confirm = ValidationUtil.getValidatedInput(sc, 
                "\nConfirm return of vehicle (yes/no)? ", input -> {
                    if (!input.toLowerCase().matches("^(yes|no)$")) {
                        throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                    }
                });
            
            if (confirm.toLowerCase().equals("yes")) {
                service.returnVehicle(bid);
                System.out.println("\n+ Vehicle returned successfully!");
            } else {
                System.out.println("\nReturn cancelled.");
            }
            
        } catch (Exception e) {
            System.out.println("X Error returning vehicle: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void viewHistory() {
        printHeader("MY RENTAL HISTORY");
        var hist = service.getBookingsByCustomer(currentCustomer);
        if (hist.isEmpty()) {
            System.out.println("\n[!] You have no rental history yet.");
            System.out.println("Make your first booking to get started!");
        } else {
            System.out.println("\nYour past and current bookings:\n");
            for (int i = 0; i < hist.size(); i++) {
                if (i > 0) System.out.println(); // Add space between bookings
                System.out.println(hist.get(i).detailed());
            }
        }
        pressEnterToContinue();
    }

    // ------------------- ADMIN -------------------
    private void adminLogin() {
        try {
            printHeader("ADMIN LOGIN");
            String email = ValidationUtil.getValidatedInput(sc, "Email: ", ValidationUtil::validateEmail);
            String password = ValidationUtil.getValidatedInput(sc, "Password: ", ValidationUtil::validatePassword);
            
            Admin admin = service.findAdminByEmail(email);
            if (admin != null && admin.login(email, password)) {
                pressEnterToContinue();
                adminMainMenu(admin);
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            pressEnterToContinue();
        }
    }
    
    private void adminMainMenu(Admin admin) {
        while (true) {
            try {
                printHeader("ADMIN MENU - " + admin.getRole());
                System.out.println("1. Add New Vehicle");
                System.out.println("2. View All Vehicles");
                System.out.println("3. Delete Vehicle");
                System.out.println("4. View All Bookings");
                System.out.println("5. Manage Pricing");
                System.out.println("6. Set Promotions");
                System.out.println("7. Generate Reports");
                System.out.println("0. Logout");
                System.out.println(LINE);
                
                int choice = ValidationUtil.getValidatedInteger(sc, "Please select an option: ", 0, 7);
                
                switch (choice) {
                    case 1 -> addVehicle();
                    case 2 -> viewAllVehicles();
                    case 3 -> deleteVehicle();
                    case 4 -> viewAllBookings();
                    case 5 -> managePricing();
                    case 6 -> setPromotions();
                    case 7 -> generateReports();
                    case 0 -> { return; }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }
    
    private void viewAllVehicles() {
        printHeader("ALL VEHICLES");
        var vehicles = service.getVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the system.");
        } else {
            vehicles.forEach(System.out::println);
        }
        pressEnterToContinue();
    }
    
    private void viewAllBookings() {
        printHeader("ALL BOOKINGS");
        var bookings = service.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("\n[!] No bookings found in the system.");
        } else {
            System.out.println("\nTotal Bookings: " + bookings.size());
            System.out.println(LINE);
            
            // Group bookings by status
            var activeBookings = bookings.stream().filter(b -> !b.isReturned()).toList();
            var completedBookings = bookings.stream().filter(b -> b.isReturned()).toList();
            
            if (!activeBookings.isEmpty()) {
                System.out.println("\n[A] ACTIVE BOOKINGS (" + activeBookings.size() + "):");
                for (var booking : activeBookings) {
                    System.out.println(booking.detailed() + "\n");
                }
            }
            
            if (!completedBookings.isEmpty()) {
                System.out.println("\n[C] COMPLETED BOOKINGS (" + completedBookings.size() + "):");
                for (var booking : completedBookings) {
                    System.out.println(booking.detailed() + "\n");
                }
            }
        }
        pressEnterToContinue();
    }



    private void managePricing() {
        try {
            System.out.println("\nPricing Management");
            var vehicles = service.getVehicles();
            if (vehicles.isEmpty()) {
                System.out.println("No vehicles in the system.");
                return;
            }

            System.out.println("Select vehicle to update price:");
            for (int i = 0; i < vehicles.size(); i++) {
                System.out.println((i+1) + ". " + vehicles.get(i));
            }
            
            int vIdx = ValidationUtil.getValidatedInteger(sc, "Enter vehicle number: ", 
                1, vehicles.size()) - 1;
            Vehicle v = vehicles.get(vIdx);
            
            double newRate = ValidationUtil.getValidatedDouble(sc, "Enter new daily rate (50-1000): ", 
                50.0, 1000.0);
            v.setDailyRate(newRate);
            System.out.println("Price updated successfully!");
            
        } catch (Exception e) {
            System.out.println("Error updating price: " + e.getMessage());
        }
    }

    private void setPromotions() {
        while (true) {
            try {
                printHeader("PROMOTION MANAGEMENT");
                System.out.println("1. View All Promotions");
                System.out.println("2. Add Group Discount");
                System.out.println("3. Add Long-term Rental Discount");
                System.out.println("4. Activate/Deactivate Promotion");
                System.out.println("0. Back");
                System.out.println(LINE);
                
                int choice = ValidationUtil.getValidatedInteger(sc, "Choose option: ", 0, 4);
                
                switch (choice) {
                    case 0 -> { return; }
                    case 1 -> viewPromotions();
                    case 2 -> addGroupPromotion();
                    case 3 -> addLongTermPromotion();
                    case 4 -> togglePromotion();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    private void viewPromotions() {
        printHeader("ALL PROMOTIONS");
        var allPromotions = service.getAllPromotions();
        if (allPromotions.isEmpty()) {
            System.out.println("\n[!] No promotions found in the system.");
        } else {
            var activePromos = allPromotions.stream().filter(Promotion::isActive).toList();
            var inactivePromos = allPromotions.stream().filter(p -> !p.isActive()).toList();
            
            System.out.println("\n[A] ACTIVE PROMOTIONS (" + activePromos.size() + "):");
            if (activePromos.isEmpty()) {
                System.out.println("No active promotions.");
            } else {
                activePromos.forEach(p -> System.out.println("\n" + p));
            }
            
            System.out.println("\n[I] INACTIVE PROMOTIONS (" + inactivePromos.size() + "):");
            if (inactivePromos.isEmpty()) {
                System.out.println("No inactive promotions.");
            } else {
                inactivePromos.forEach(p -> System.out.println("\n" + p));
            }
        }
        pressEnterToContinue();
    }

    private void addGroupPromotion() {
        try {
            printHeader("ADD GROUP DISCOUNT");
            System.out.println("Set the requirements for group discount:\n");
            
            String description = ValidationUtil.getValidatedInput(sc, "Promotion Description: ", input -> {
                if (input.length() < 5) throw new IllegalArgumentException("Description too short");
            });
            
            int size = ValidationUtil.getValidatedInteger(sc, 
                "Minimum Group Size (5-20 people): ", 5, 20);
            double discount = ValidationUtil.getValidatedDouble(sc, 
                "Discount Percentage (5-25%): ", 5.0, 25.0);
            
            Promotion p = new Promotion(
                service.nextPromotionCode(),
                "GROUP",
                description,
                discount,
                size
            );
            
            service.addPromotion(p);
            System.out.println("\nGroup discount created successfully!");
            System.out.println(p);
            
        } catch (Exception e) {
            System.out.println("Error creating promotion: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void addLongTermPromotion() {
        try {
            printHeader("ADD LONG-TERM RENTAL DISCOUNT");
            System.out.println("Set the requirements for long-term rental discount:\n");
            
            String description = ValidationUtil.getValidatedInput(sc, "Promotion Description: ", input -> {
                if (input.length() < 5) throw new IllegalArgumentException("Description too short");
            });
            
            int days = ValidationUtil.getValidatedInteger(sc, 
                "Minimum Rental Days (7-30 days): ", 7, 30);
            double discount = ValidationUtil.getValidatedDouble(sc, 
                "Discount Percentage (10-30%): ", 10.0, 30.0);
            
            Promotion p = new Promotion(
                service.nextPromotionCode(),
                "LONG_TERM",
                description,
                discount,
                days
            );
            
            service.addPromotion(p);
            System.out.println("\n+ Long-term rental discount created successfully!");
            System.out.println(p);
            
        } catch (Exception e) {
            System.out.println("Error creating promotion: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void togglePromotion() {
        printHeader("ACTIVATE/DEACTIVATE PROMOTION");
        var allPromotions = service.getAllPromotions();
        if (allPromotions.isEmpty()) {
            System.out.println("\n[!] No promotions found in the system.");
            pressEnterToContinue();
            return;
        }

        try {
            System.out.println("\nCurrent Promotions:");
            for (int i = 0; i < allPromotions.size(); i++) {
                System.out.println("\nOPTION " + (i + 1) + ":");
                System.out.println(allPromotions.get(i));
            }

            int idx = ValidationUtil.getValidatedInteger(sc, 
                "\nSelect promotion number to toggle: ", 1, allPromotions.size()) - 1;
            
            Promotion selected = allPromotions.get(idx);
            if (selected.isActive()) {
                service.deactivatePromotion(selected.getCode());
                System.out.println("\n+ Promotion " + selected.getCode() + " has been deactivated.");
            } else {
                service.activatePromotion(selected.getCode());
                System.out.println("\n+ Promotion " + selected.getCode() + " has been activated.");
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void addVehicle() {
        try {
            int type = ValidationUtil.getValidatedInteger(sc, "Enter type (1.Car 2.Van): ", 1, 2);
            
            String model = ValidationUtil.getValidatedInput(sc, "Model: ", input -> {
                if (input.length() < 3) {
                    throw new IllegalArgumentException("Model name must be at least 3 characters long");
                }
            });
            
            double rate = ValidationUtil.getValidatedDouble(sc, "Daily rate (50-1000): ", 50.0, 1000.0);

            if (type == 1) {
                int cap = ValidationUtil.getValidatedInteger(sc, "Passenger capacity (2-7): ", 2, 7);
                
                // Enhanced Car creation with additional features
                System.out.println("\nCar Type Options:");
                System.out.println("1. Economy Car");
                System.out.println("2. Compact Car"); 
                System.out.println("3. Midsize Car");
                System.out.println("4. Luxury Car");
                int carTypeChoice = ValidationUtil.getValidatedInteger(sc, "Select car type: ", 1, 4);
                Car.CarType carType = Car.CarType.values()[carTypeChoice - 1];
                
                String gps = ValidationUtil.getValidatedInput(sc, "Include GPS (yes/no): ", input -> {
                    if (!input.toLowerCase().matches("^(yes|no)$")) {
                        throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                    }
                });
                
                String aircon = ValidationUtil.getValidatedInput(sc, "Include Air Conditioning (yes/no): ", input -> {
                    if (!input.toLowerCase().matches("^(yes|no)$")) {
                        throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                    }
                });
                
                Car car = new Car(service.nextVehicleId(), model, cap, rate, carType, 
                    gps.equalsIgnoreCase("yes"), aircon.equalsIgnoreCase("yes"));
                service.addVehicle(car);
                
            } else {
                int cap = ValidationUtil.getValidatedInteger(sc, "Passenger capacity (8-15): ", 8, 15);
                int lug = ValidationUtil.getValidatedInteger(sc, "Luggage space (4-12 bags): ", 4, 12);
                
                // Enhanced Van creation with additional features
                System.out.println("\nVan Type Options:");
                System.out.println("1. Standard Van");
                System.out.println("2. Luxury Van");
                System.out.println("3. Executive Van");
                int vanTypeChoice = ValidationUtil.getValidatedInteger(sc, "Select van type: ", 1, 3);
                Van.VanType vanType = Van.VanType.values()[vanTypeChoice - 1];
                
                String wifi = ValidationUtil.getValidatedInput(sc, "Include WiFi (yes/no): ", input -> {
                    if (!input.toLowerCase().matches("^(yes|no)$")) {
                        throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                    }
                });
                
                String tv = ValidationUtil.getValidatedInput(sc, "Include Entertainment System (yes/no): ", input -> {
                    if (!input.toLowerCase().matches("^(yes|no)$")) {
                        throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                    }
                });
                
                Van van = new Van(service.nextVehicleId(), model, cap, lug, rate, vanType,
                    wifi.equalsIgnoreCase("yes"), tv.equalsIgnoreCase("yes"));
                service.addVehicle(van);
            }
            System.out.println("+ Vehicle added successfully with enhanced features!");
        } catch (Exception e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void deleteVehicle() {
        try {
            printHeader("DELETE VEHICLE");
            var vehicles = service.getVehicles();
            if (vehicles.isEmpty()) {
                System.out.println("No vehicles in the system to delete.");
                pressEnterToContinue();
                return;
            }

            System.out.println("Select vehicle to delete:");
            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle v = vehicles.get(i);
                System.out.printf("%d. %s (%s) - %s%n", 
                    i + 1, v.getModel(), v.getVehicleId(), 
                    v.isAvailable() ? "Available" : "Currently Rented");
            }
            
            int choice = ValidationUtil.getValidatedInteger(sc, 
                "Enter vehicle number to delete: ", 1, vehicles.size());
            
            Vehicle selectedVehicle = vehicles.get(choice - 1);
            
            System.out.printf("Selected vehicle: %s (%s)%n", 
                selectedVehicle.getModel(), selectedVehicle.getVehicleId());
            
            String confirm = ValidationUtil.getValidatedInput(sc, 
                "Are you sure you want to delete this vehicle? (yes/no): ", input -> {
                    if (!input.toLowerCase().matches("^(yes|no)$")) {
                        throw new IllegalArgumentException("Please enter 'yes' or 'no'");
                    }
                });
            
            if (confirm.toLowerCase().equals("yes")) {
                boolean deleted = service.deleteVehicle(selectedVehicle.getVehicleId());
                if (deleted) {
                    System.out.println("+ Vehicle deleted successfully!");
                } else {
                    System.out.println("X Failed to delete vehicle.");
                }
            } else {
                System.out.println("Vehicle deletion cancelled.");
            }
            
        } catch (Exception e) {
            System.out.println("Error deleting vehicle: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private void generateReports() {
        while (true) {
            try {
                printHeader("REPORTS & ANALYTICS");
                System.out.println("1. Comprehensive Business Report");
                System.out.println("2. Vehicle Performance Report");
                System.out.println("3. Customer Analysis Report");
                System.out.println("4. Revenue Analysis");
                System.out.println("0. Back");
                System.out.println(LINE);
                
                int choice = ValidationUtil.getValidatedInteger(sc, "Choose report type: ", 0, 4);
                
                switch (choice) {
                    case 0 -> { return; }
                    case 1 -> {
                        ReportGenerator reporter = new ReportGenerator(service);
                        reporter.generateComprehensiveReport();
                        pressEnterToContinue();
                    }
                    case 2 -> {
                        ReportGenerator reporter = new ReportGenerator(service);
                        reporter.generateVehiclePerformanceReport();
                        pressEnterToContinue();
                    }
                    case 3 -> generateCustomerReport();
                    case 4 -> generateRevenueReport();
                }
            } catch (Exception e) {
                System.out.println("Error generating report: " + e.getMessage());
                pressEnterToContinue();
            }
        }
    }

    private void generateCustomerReport() {
        printHeader("CUSTOMER ANALYSIS");
        var customers = service.getCustomers();
        
        System.out.println("Customer Statistics:");
        System.out.println("Total Customers: " + customers.size());
        
        // Customer tier distribution
        int bronze = 0, silver = 0, gold = 0, platinum = 0;
        for (var customer : customers) {
            switch (customer.getTier()) {
                case BRONZE -> bronze++;
                case SILVER -> silver++;
                case GOLD -> gold++;
                case PLATINUM -> platinum++;
            }
        }
        
        System.out.println("\nTier Distribution:");
        System.out.println("Bronze: " + bronze + " customers");
        System.out.println("Silver: " + silver + " customers");
        System.out.println("Gold: " + gold + " customers");
        System.out.println("Platinum: " + platinum + " customers");
        
        pressEnterToContinue();
    }

    private void generateRevenueReport() {
        printHeader("REVENUE ANALYSIS");
        var bookings = service.getBookings();
        
        double totalRevenue = 0;
        for (var booking : bookings) {
            totalRevenue += booking.calculateCharge();
        }
        
        System.out.printf("Total Revenue: RM%.2f%n", totalRevenue);
        System.out.printf("Total Bookings: %d%n", bookings.size());
        if (!bookings.isEmpty()) {
            System.out.printf("Average Booking Value: RM%.2f%n", totalRevenue / bookings.size());
        }
        
        pressEnterToContinue();
    }
}
