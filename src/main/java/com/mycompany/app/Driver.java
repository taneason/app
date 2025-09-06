/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 * Driver class for optional driver service
 * @author User
 */
public class Driver {
    private final String driverId; // ID should not be changeable
    private String name;
    private String licenseNumber;
    private int experienceYears;
    private double dailyRate;
    private boolean available;
    private DriverType driverType;
    private String phoneNumber;
    private double rating;
    
    public enum DriverType {
        STANDARD("Standard Driver", 50.0),
        PROFESSIONAL("Professional Driver", 80.0),
        LUXURY("Luxury Driver", 120.0);
        
        private final String displayName;
        private final double baseRate;
        
        DriverType(String displayName, double baseRate) {
            this.displayName = displayName;
            this.baseRate = baseRate;
        }
        
        public String getDisplayName() { return displayName; }
        public double getBaseRate() { return baseRate; }
    }
    
    public Driver(String driverId, String name, String licenseNumber, 
                  int experienceYears, DriverType driverType) {
        this.driverId = driverId;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.experienceYears = experienceYears;
        this.driverType = driverType;
        this.dailyRate = calculateDailyRate();
        this.available = true;
        this.phoneNumber = "000-0000000"; // Default phone number
        this.rating = 0.0; // Default rating
    }
    
    /**
     * Expanded constructor with phone number and rating
     */
    public Driver(String driverId, String name, String licenseNumber, 
                  int experienceYears, DriverType driverType, 
                  String phoneNumber, double rating) {
        this.driverId = driverId;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.experienceYears = experienceYears;
        this.driverType = driverType;
        this.dailyRate = calculateDailyRate();
        this.available = true;
        this.phoneNumber = phoneNumber;
        this.rating = (rating >= 0 && rating <= 5) ? rating : 0.0;
    }
    
    private double calculateDailyRate() {
        double baseRate = driverType.getBaseRate();
        // Add experience bonus
        double experienceBonus = experienceYears * 2.0;
        return baseRate + experienceBonus;
    }
    
    // Getters
    public String getDriverId() { return driverId; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getExperienceYears() { return experienceYears; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public DriverType getDriverType() { return driverType; }
    public String getPhoneNumber() { return phoneNumber; }
    public double getRating() { return rating; }
    
    // Setters
    public void setAvailable(boolean available) { this.available = available; }
    
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber != null && !licenseNumber.trim().isEmpty()) {
            this.licenseNumber = licenseNumber;
        }
    }
    
    public void setExperienceYears(int experienceYears) {
        if (experienceYears >= 0) {
            this.experienceYears = experienceYears;
            // Recalculate daily rate when experience changes
            this.dailyRate = calculateDailyRate();
        }
    }
    
    public void setDriverType(DriverType driverType) {
        if (driverType != null) {
            this.driverType = driverType;
            // Recalculate daily rate when type changes
            this.dailyRate = calculateDailyRate();
        }
    }
    
    public void setDailyRate(double dailyRate) {
        if (dailyRate > 0) {
            this.dailyRate = dailyRate;
        }
    }
    
    /**
     * Sets the phone number for the driver
     * @param phoneNumber The phone number in format XXX-XXXXXXX
     */
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.matches("^\\d{3}-\\d{7,8}$")) {
            this.phoneNumber = phoneNumber;
        }
    }
    
    /**
     * Sets the driver's rating
     * @param rating Rating value between 0.0 and 5.0
     */
    public void setRating(double rating) {
        if (rating >= 0 && rating <= 5) {
            this.rating = rating;
        }
    }
    
    // Additional methods
    public String getExperienceLevel() {
        if (experienceYears < 2) return "Beginner";
        if (experienceYears < 5) return "Intermediate";
        if (experienceYears < 10) return "Experienced";
        return "Expert";
    }
    
    public boolean isQualifiedForType(DriverType requiredType) {
        return switch (requiredType) {
            case STANDARD -> true; // All drivers can handle standard
            case PROFESSIONAL -> experienceYears >= 3;
            case LUXURY -> experienceYears >= 5;
        };
    }
    
    public double getHourlyRate() {
        return dailyRate / 8.0; // Assuming 8-hour work day
    }
    
    @Override
    public String toString() {
        return String.format("+-------------- DRIVER PROFILE --------------+\n" +
                           "| Driver ID: %s\n" +
                           "| Name: %s\n" +
                           "| License: %s\n" +
                           "| Phone: %s\n" +
                           "| Experience: %d years (%s)\n" +
                           "| Type: %s\n" +
                           "| Daily Rate: RM%.2f\n" +
                           "| Rating: %.1f/5.0\n" +
                           "| Status: %s\n" +
                           "+-------------------------------------------+",
                           driverId, name, licenseNumber, phoneNumber,
                           experienceYears, getExperienceLevel(),
                           driverType.getDisplayName(), dailyRate,
                           rating,
                           available ? "Available" : "Assigned");
    }
}
