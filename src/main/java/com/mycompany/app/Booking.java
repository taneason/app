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

public class Booking {
    private final String bookingId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final LocalDate startDate;
    private final int durationDays;
    private boolean returned = false;
    private Promotion appliedPromotion;
    private double finalCharge;

    public Booking(String bookingId, Customer customer, Vehicle vehicle, LocalDate startDate, 
                  int durationDays, Promotion promotion) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.durationDays = durationDays;
        this.appliedPromotion = promotion;
        vehicle.setAvailable(false);
        calculateFinalCharge();
    }

    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public boolean isReturned() { return returned; }
    public int getDurationDays() { return durationDays; }

    private void calculateFinalCharge() {
        double baseCharge = durationDays * vehicle.getDailyRate();
        if (appliedPromotion != null && appliedPromotion.isActive()) {
            if (appliedPromotion.getType().equals("GROUP")) {
                finalCharge = baseCharge * (1 - appliedPromotion.getDiscountPercentage() / 100);
            } else if (appliedPromotion.getType().equals("LONG_TERM") && 
                      durationDays >= appliedPromotion.getThreshold()) {
                finalCharge = baseCharge * (1 - appliedPromotion.getDiscountPercentage() / 100);
            } else {
                finalCharge = baseCharge;
            }
        } else {
            finalCharge = baseCharge;
        }
    }

    public double calculateCharge() {
        return finalCharge;
    }

    public void returnVehicle() {
        returned = true;
        vehicle.setAvailable(true);
    }

    public Promotion getAppliedPromotion() {
        return appliedPromotion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╭──────────────── BOOKING DETAILS ────────────────────╮\n");
        sb.append("│ Booking ID: ").append(bookingId).append("\n");
        sb.append("│ Status: ").append(returned ? "Completed ✓" : "Active ⌛").append("\n");
        sb.append("│\n");
        sb.append("│ Customer Information:\n");
        sb.append("│ • Name: ").append(customer.getName()).append("\n");
        sb.append("│ • ID: ").append(customer.getCustomerId()).append("\n");
        sb.append("│\n");
        sb.append("│ Vehicle Information:\n");
        sb.append("│ • Model: ").append(vehicle.getModel()).append("\n");
        sb.append("│ • Type: ").append(vehicle.getType()).append("\n");
        sb.append("│\n");
        sb.append("│ Rental Details:\n");
        sb.append("│ • Start Date: ").append(startDate).append("\n");
        sb.append("│ • Duration: ").append(durationDays).append(" days\n");
        
        double baseCharge = durationDays * vehicle.getDailyRate();
        sb.append("│ • Base Rate: RM").append(String.format("%.2f", baseCharge)).append("\n");
        
        if (appliedPromotion != null && appliedPromotion.isActive()) {
            sb.append("│ • Applied Promotion: ").append(appliedPromotion.getCode())
              .append(" (").append(String.format("%.1f", appliedPromotion.getDiscountPercentage()))
              .append("% off)\n");
            sb.append("│ • Final Charge: RM").append(String.format("%.2f", finalCharge))
              .append(" 💰\n");
        } else {
            sb.append("│ • Final Charge: RM").append(String.format("%.2f", finalCharge))
              .append("\n");
        }
        
        sb.append("╰─────────────────────────────────────────────────────╯");
        return sb.toString();
    }

    public String detailed() {
        return toString();
    }
}
