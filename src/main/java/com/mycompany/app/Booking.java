/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import com.mycompany.app.Customer.CustomerTier;

public class Booking {
    private final String bookingId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final LocalDate startDate;
    private final int durationDays;
    private boolean returned = false;
    private Promotion appliedGroupPromotion;
    private Promotion appliedLongTermPromotion;
    private Driver assignedDriver; // Optional driver
    private double finalCharge;
    private double appliedLoyaltyDiscount = 0; // Track actual loyalty discount applied
    private CustomerTier tierAtBooking; // Store tier at time of booking

    public Booking(String bookingId, Customer customer, Vehicle vehicle, LocalDate startDate, 
                  int durationDays, Promotion groupPromotion, Promotion longTermPromotion, Driver driver) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.durationDays = durationDays;
        this.appliedGroupPromotion = groupPromotion;
        this.appliedLongTermPromotion = longTermPromotion;
        this.assignedDriver = driver;
        this.tierAtBooking = customer.getTier(); // Store current tier at booking time
        vehicle.setAvailable(false);
        calculateFinalCharge();
    }

    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public boolean isReturned() { return returned; }
    public int getDurationDays() { return durationDays; }
    public Driver getAssignedDriver() { return assignedDriver; }
    public boolean hasDriver() { return assignedDriver != null; }
    public LocalDate getStartDate() { return startDate; }
    public double getFinalCharge() { return finalCharge; }
    public CustomerTier getTierAtBooking() { return tierAtBooking; }
    
    // Setters for mutable fields
    public void setReturned(boolean returned) { 
        this.returned = returned; 
    }
    
    public void setAssignedDriver(Driver driver) { 
        this.assignedDriver = driver;
        // Recalculate charges when driver changes
        calculateFinalCharge();
    }
    
    public void setAppliedGroupPromotion(Promotion promotion) {
        this.appliedGroupPromotion = promotion;
        calculateFinalCharge();
    }
    
    public void setAppliedLongTermPromotion(Promotion promotion) {
        this.appliedLongTermPromotion = promotion;
        calculateFinalCharge();
    }

    private void calculateFinalCharge() {
        double baseCharge = durationDays * vehicle.getDailyRate();
        finalCharge = baseCharge;
        
        // Add driver cost if driver is assigned
        if (assignedDriver != null) {
            finalCharge += durationDays * assignedDriver.getDailyRate();
        }
        
        // Apply group discount
        if (appliedGroupPromotion != null && appliedGroupPromotion.isActive()) {
            finalCharge = finalCharge * (1 - appliedGroupPromotion.getDiscountPercentage() / 100);
        }
        
        // Apply long-term discount (can stack with group discount)
        if (appliedLongTermPromotion != null && appliedLongTermPromotion.isActive()) {
            finalCharge = finalCharge * (1 - appliedLongTermPromotion.getDiscountPercentage() / 100);
        }
        
        // Apply customer loyalty discount (can stack with other discounts)
        if (customer.getLoyaltyDiscount() > 0) {
            appliedLoyaltyDiscount = customer.getLoyaltyDiscount();
            finalCharge = finalCharge * (1 - appliedLoyaltyDiscount / 100);
        }
    }

    public double calculateCharge() {
        return finalCharge;
    }

    public void returnVehicle() {
        returned = true;
        vehicle.setAvailable(true);
    }

    public Promotion getAppliedGroupPromotion() {
        return appliedGroupPromotion;
    }
    
    public Promotion getAppliedLongTermPromotion() {
        return appliedLongTermPromotion;
    }

    public double getAppliedLoyaltyDiscount() {
        return appliedLoyaltyDiscount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+-------------- BOOKING DETAILS --------------+\n");
        sb.append("| Booking ID: ").append(bookingId).append("\n");
        sb.append("| Status: ").append(returned ? "Completed [+]" : "Active [*]").append("\n");
        sb.append("|\n");
        sb.append("| Customer Information:\n");
        sb.append("| - Name: ").append(customer.getName()).append("\n");
        sb.append("| - ID: ").append(customer.getCustomerId()).append("\n");
        sb.append("|\n");
        sb.append("| Vehicle Information:\n");
        sb.append("| - Model: ").append(vehicle.getModel()).append("\n");
        sb.append("| - Type: ").append(vehicle.getType()).append("\n");
        sb.append("|\n");
        sb.append("| Rental Details:\n");
        sb.append("| - Start Date: ").append(startDate).append("\n");
        sb.append("| - Duration: ").append(durationDays).append(" days\n");
        
        double baseCharge = durationDays * vehicle.getDailyRate();
        sb.append("| - Base Rate: RM").append(String.format("%.2f", baseCharge)).append("\n");
        
        // Show driver information if driver is assigned
        if (assignedDriver != null) {
            double driverCost = durationDays * assignedDriver.getDailyRate();
            sb.append("| - Driver: ").append(assignedDriver.getName())
              .append(" (").append(assignedDriver.getDriverType()).append(")\n");
            sb.append("| - Driver Cost: RM").append(String.format("%.2f", driverCost)).append("\n");
        }
        
        // Show applied promotions
        boolean hasDiscounts = false;
        if (appliedGroupPromotion != null && appliedGroupPromotion.isActive()) {
            sb.append("| - Group Promotion: ").append(appliedGroupPromotion.getCode())
              .append(" (").append(String.format("%.1f", appliedGroupPromotion.getDiscountPercentage()))
              .append("% off)\n");
            hasDiscounts = true;
        }
        
        if (appliedLongTermPromotion != null && appliedLongTermPromotion.isActive()) {
            sb.append("| - Long-term Promotion: ").append(appliedLongTermPromotion.getCode())
              .append(" (").append(String.format("%.1f", appliedLongTermPromotion.getDiscountPercentage()))
              .append("% off)\n");
            hasDiscounts = true;
        }
        
        if (customer.getLoyaltyDiscount() > 0 && appliedLoyaltyDiscount > 0) {
            sb.append("| - Loyalty Discount (").append(tierAtBooking.getDisplayName())
              .append("): ").append(String.format("%.1f", appliedLoyaltyDiscount))
              .append("% off\n");
            hasDiscounts = true;
        }
        
        if (hasDiscounts) {
            double totalCostBeforeDiscounts = baseCharge;
            if (assignedDriver != null) {
                totalCostBeforeDiscounts += durationDays * assignedDriver.getDailyRate();
            }
            double totalSavings = totalCostBeforeDiscounts - finalCharge;
            sb.append("| - Total Savings: RM").append(String.format("%.2f", totalSavings)).append("\n");
        }
        
        sb.append("| - Final Charge: RM").append(String.format("%.2f", finalCharge));
        if (hasDiscounts) {
            sb.append(" [$]");
        }
        sb.append("\n");
        
        sb.append("+--------------------------------------------+");
        return sb.toString();
    }

    public String detailed() {
        return toString();
    }
}
