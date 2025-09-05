package com.mycompany.app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive reporting and analytics system
 * Demonstrates complex data processing and analysis
 */
public class ReportGenerator {
    private final RentalService rentalService;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReportGenerator(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public void generateComprehensiveReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("        COMPREHENSIVE BUSINESS REPORT");
        System.out.println("        Generated on: " + LocalDate.now().format(dateFormatter));
        System.out.println("=".repeat(60));

        generateFleetAnalysis();
        generateCustomerAnalysis();
        generateRevenueAnalysis();
        generatePromotionEffectiveness();
    }

    private void generateFleetAnalysis() {
        System.out.println("\n[1] FLEET ANALYSIS");
        System.out.println("-".repeat(40));

        var vehicles = rentalService.getVehicles();
        Map<String, Long> vehicleTypeCount = vehicles.stream()
            .collect(Collectors.groupingBy(
                v -> v.getType().split(" ")[0], // Get base type (Car/Van)
                Collectors.counting()
            ));

        System.out.println("Fleet Composition:");
        vehicleTypeCount.forEach((type, count) -> 
            System.out.printf("  %s: %d vehicles%n", type, count));

        // Utilization analysis
        long availableVehicles = vehicles.stream()
            .mapToLong(v -> v.isAvailable() ? 1 : 0)
            .sum();
        
        double utilizationRate = ((double)(vehicles.size() - availableVehicles) / vehicles.size()) * 100;
        System.out.printf("Fleet Utilization: %.1f%% (%d/%d vehicles rented)%n", 
            utilizationRate, vehicles.size() - availableVehicles, vehicles.size());
    }

    private void generateCustomerAnalysis() {
        System.out.println("\n[2] CUSTOMER ANALYSIS");
        System.out.println("-".repeat(40));

        var customers = rentalService.getCustomers();
        System.out.printf("Total Registered Customers: %d%n", customers.size());

        // Customer tier distribution
        Map<Customer.CustomerTier, Long> tierDistribution = customers.stream()
            .collect(Collectors.groupingBy(
                Customer::getTier,
                Collectors.counting()
            ));

        System.out.println("Customer Tier Distribution:");
        tierDistribution.forEach((tier, count) ->
            System.out.printf("  %s: %d customers%n", tier.getDisplayName(), count));

        // Top customers by spending
        var topCustomers = customers.stream()
            .sorted((c1, c2) -> Double.compare(c2.getTotalSpent(), c1.getTotalSpent()))
            .limit(5)
            .collect(Collectors.toList());

        System.out.println("Top 5 Customers by Spending:");
        for (int i = 0; i < topCustomers.size(); i++) {
            Customer c = topCustomers.get(i);
            System.out.printf("  %d. %s - RM%.2f (%d rentals)%n", 
                i + 1, c.getName(), c.getTotalSpent(), c.getTotalRentals());
        }
    }

    private void generateRevenueAnalysis() {
        System.out.println("\n[3] REVENUE ANALYSIS");
        System.out.println("-".repeat(40));

        var bookings = rentalService.getBookings();
        double totalRevenue = bookings.stream()
            .mapToDouble(Booking::calculateCharge)
            .sum();

        System.out.printf("Total Revenue: RM%.2f%n", totalRevenue);
        System.out.printf("Total Bookings: %d%n", bookings.size());
        System.out.printf("Average Booking Value: RM%.2f%n", 
            bookings.isEmpty() ? 0 : totalRevenue / bookings.size());

        // Revenue by vehicle type
        Map<String, Double> revenueByType = bookings.stream()
            .collect(Collectors.groupingBy(
                b -> b.getVehicle().getType().split(" ")[0],
                Collectors.summingDouble(Booking::calculateCharge)
            ));

        System.out.println("Revenue by Vehicle Type:");
        revenueByType.forEach((type, revenue) ->
            System.out.printf("  %s: RM%.2f%n", type, revenue));

        // Completed vs Active bookings
        long completedBookings = bookings.stream()
            .mapToLong(b -> b.isReturned() ? 1 : 0)
            .sum();
        
        System.out.printf("Completed Bookings: %d%n", completedBookings);
        System.out.printf("Active Bookings: %d%n", bookings.size() - completedBookings);
    }

    private void generatePromotionEffectiveness() {
        System.out.println("\n[4] PROMOTION EFFECTIVENESS");
        System.out.println("-".repeat(40));

        var bookings = rentalService.getBookings();
        var bookingsWithGroupPromo = bookings.stream()
            .filter(b -> b.getAppliedGroupPromotion() != null)
            .collect(Collectors.toList());
        
        var bookingsWithLongTermPromo = bookings.stream()
            .filter(b -> b.getAppliedLongTermPromotion() != null)
            .collect(Collectors.toList());

        System.out.printf("Bookings with Group Promotions: %d (%.1f%%)%n", 
            bookingsWithGroupPromo.size(), 
            bookings.isEmpty() ? 0 : (double)bookingsWithGroupPromo.size() / bookings.size() * 100);

        System.out.printf("Bookings with Long-term Promotions: %d (%.1f%%)%n", 
            bookingsWithLongTermPromo.size(),
            bookings.isEmpty() ? 0 : (double)bookingsWithLongTermPromo.size() / bookings.size() * 100);

        // Calculate total discounts given
        double totalDiscounts = bookings.stream()
            .mapToDouble(this::calculateTotalDiscount)
            .sum();

        System.out.printf("Total Discounts Given: RM%.2f%n", totalDiscounts);

        if (!bookings.isEmpty()) {
            System.out.printf("Average Discount per Booking: RM%.2f%n", 
                totalDiscounts / bookings.size());
        }
    }

    private double calculateTotalDiscount(Booking booking) {
        double baseCharge = booking.getDurationDays() * booking.getVehicle().getDailyRate();
        return baseCharge - booking.calculateCharge();
    }

    public void generateVehiclePerformanceReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       VEHICLE PERFORMANCE REPORT");
        System.out.println("=".repeat(50));

        var vehicles = rentalService.getVehicles();
        
        // Sort by total rentals (most popular first)
        var sortedVehicles = vehicles.stream()
            .sorted((v1, v2) -> Integer.compare(v2.getTotalRentals(), v1.getTotalRentals()))
            .collect(Collectors.toList());

        System.out.println("Vehicle Performance Ranking:");
        for (int i = 0; i < Math.min(10, sortedVehicles.size()); i++) {
            Vehicle v = sortedVehicles.get(i);
            System.out.printf("%2d. %s (%s) - %d rentals%n",
                i + 1, v.getModel(), v.getVehicleId(), v.getTotalRentals());
        }
    }
}
