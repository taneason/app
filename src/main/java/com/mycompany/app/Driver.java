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
    private final String driverId;
    private final String name;
    private final String licenseNumber;
    private final int experienceYears;
    private final double dailyRate;
    private boolean available;
    private final DriverType driverType;
    
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
    
    // Setters
    public void setAvailable(boolean available) { this.available = available; }
    
    @Override
    public String toString() {
        return String.format("+-------------- DRIVER PROFILE --------------+\n" +
                           "| Driver ID: %s\n" +
                           "| Name: %s\n" +
                           "| License: %s\n" +
                           "| Experience: %d years\n" +
                           "| Type: %s\n" +
                           "| Daily Rate: RM%.2f\n" +
                           "| Status: %s\n" +
                           "+-------------------------------------------+",
                           driverId, name, licenseNumber, experienceYears,
                           driverType.getDisplayName(), dailyRate,
                           available ? "Available" : "Assigned");
    }
}
