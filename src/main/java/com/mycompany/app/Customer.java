/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 * Customer class demonstrating inheritance from Person
 * Includes rental history tracking and customer-specific behavior
 */
public class Customer extends Person {
    private int totalRentals;
    private double totalSpent;
    private CustomerTier tier;

    public enum CustomerTier {
        BRONZE(0, "Bronze", 0.0),
        SILVER(5, "Silver", 5.0),
        GOLD(15, "Gold", 10.0),
        PLATINUM(30, "Platinum", 15.0);

        private final int requiredRentals;
        private final String displayName;
        private final double loyaltyDiscount;

        CustomerTier(int requiredRentals, String displayName, double loyaltyDiscount) {
            this.requiredRentals = requiredRentals;
            this.displayName = displayName;
            this.loyaltyDiscount = loyaltyDiscount;
        }

        public int getRequiredRentals() { return requiredRentals; }
        public String getDisplayName() { return displayName; }
        public double getLoyaltyDiscount() { return loyaltyDiscount; }
    }

    public Customer(String customerId, String name, String email, String phone, String password) {
        super(customerId, name, email, phone, password);
        this.totalRentals = 0;
        this.totalSpent = 0.0;
        this.tier = CustomerTier.BRONZE;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public boolean hasPermission(String permission) {
        return switch (permission.toLowerCase()) {
            case "rent_vehicle", "view_history", "update_profile" -> true;
            default -> false;
        };
    }

    @Override
    protected boolean authenticate(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.verifyPassword(password);
    }

    @Override
    protected void onLoginSuccess() {
        System.out.println("Welcome back, " + getName() + "! (" + tier.getDisplayName() + " member)");
    }

    @Override
    protected void onLogout() {
        System.out.println("Thank you for using our service, " + getName() + "!");
    }

    // Customer-specific methods
    public void addRental(double amount) {
        totalRentals++;
        totalSpent += amount;
        updateTier();
    }

    private void updateTier() {
        CustomerTier newTier = CustomerTier.BRONZE;
        for (CustomerTier tier : CustomerTier.values()) {
            if (totalRentals >= tier.getRequiredRentals()) {
                newTier = tier;
            }
        }
        
        if (newTier != this.tier) {
            CustomerTier oldTier = this.tier;
            this.tier = newTier;
            System.out.println("Congratulations! You've been upgraded from " + 
                oldTier.getDisplayName() + " to " + newTier.getDisplayName() + " tier!");
        }
    }

    public double getLoyaltyDiscount() {
        return tier.getLoyaltyDiscount();
    }

    // Getters
    public String getCustomerId() { return getId(); }
    public int getTotalRentals() { return totalRentals; }
    public double getTotalSpent() { return totalSpent; }
    public CustomerTier getTier() { return tier; }
    
    // Setters
    public void setTotalRentals(int totalRentals) {
        if (totalRentals >= 0) {
            this.totalRentals = totalRentals;
            updateTier();
        }
    }
    
    public void setTotalSpent(double totalSpent) {
        if (totalSpent >= 0) {
            this.totalSpent = totalSpent;
        }
    }
    
    public void setTier(CustomerTier tier) {
        if (tier != null) {
            this.tier = tier;
        }
    }

    @Override
    public String toString() {
        return String.format("Customer: %s (%s) - %s Tier - %d rentals, RM%.2f spent", 
            getName(), getEmail(), tier.getDisplayName(), totalRentals, totalSpent);
    }
}
