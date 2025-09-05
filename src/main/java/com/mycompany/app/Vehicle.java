/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import java.time.LocalDate;

/**
 * Abstract Vehicle class demonstrating inheritance and interface implementation
 */
public abstract class Vehicle implements Rentable {
    private final String vehicleId;
    private String model;
    private boolean available = true;
    private double dailyRate;
    private int passengerCapacity;
    private MaintenanceStatus maintenanceStatus;
    private LocalDate lastMaintenanceDate;
    private int totalRentals;

    public Vehicle(String vehicleId, String model, double dailyRate, int passengerCapacity) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.dailyRate = dailyRate;
        this.passengerCapacity = passengerCapacity;
        this.maintenanceStatus = MaintenanceStatus.EXCELLENT;
        this.lastMaintenanceDate = LocalDate.now();
        this.totalRentals = 0;
    }

    public String getVehicleId() { return vehicleId; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public int getPassengerCapacity() { return passengerCapacity; }
    public int getTotalRentals() { return totalRentals; }

    public void setAvailable(boolean available) { 
        this.available = available; 
        if (!available) {
            totalRentals++;
            checkMaintenanceNeeded();
        }
    }
    
    public void setDailyRate(double rate) { 
        ValidationUtil.validatePositiveNumber(rate, "Daily rate");
        this.dailyRate = rate; 
    }

    @Override
    public MaintenanceStatus getMaintenanceStatus() {
        return maintenanceStatus;
    }

    @Override
    public void performMaintenance() {
        this.maintenanceStatus = MaintenanceStatus.EXCELLENT;
        this.lastMaintenanceDate = LocalDate.now();
        System.out.println("Maintenance completed for " + model + " (" + vehicleId + ")");
    }

    @Override
    public String getRentalInfo() {
        return String.format("%s - %s (RM%.2f/day, %d passengers, %s)", 
            vehicleId, model, dailyRate, passengerCapacity, maintenanceStatus.getDescription());
    }

    private void checkMaintenanceNeeded() {
        if (totalRentals % 10 == 0 && totalRentals > 0) {
            switch (maintenanceStatus) {
                case EXCELLENT -> maintenanceStatus = MaintenanceStatus.GOOD;
                case GOOD -> maintenanceStatus = MaintenanceStatus.FAIR;
                case FAIR -> maintenanceStatus = MaintenanceStatus.POOR;
                case POOR -> {
                    maintenanceStatus = MaintenanceStatus.OUT_OF_SERVICE;
                    setAvailable(false);
                    System.out.println("WARNING: " + model + " requires immediate maintenance!");
                }
                case OUT_OF_SERVICE -> {
                    // Vehicle already out of service, no further action needed
                }
            }
        }
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public abstract String getType();
    public abstract String getSpecialFeatures();

    @Override
    public String toString() {
        return "[" + vehicleId + "] " + model + " (" + getType() + ") RM" + dailyRate + "/day " +
               (available ? "Available" : "Rented") + " - " + maintenanceStatus.getDescription();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return vehicleId.equals(vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return vehicleId.hashCode();
    }
}
