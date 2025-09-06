/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 * Abstract Vehicle class demonstrating inheritance and interface implementation
 */
public abstract class Vehicle implements Rentable {
    protected final String vehicleId;  // Changed to protected for inheritance
    protected String model;             // Changed to protected for inheritance
    protected boolean available = true; // Changed to protected for inheritance
    protected double dailyRate;         // Changed to protected for inheritance
    protected int passengerCapacity;    // Changed to protected for inheritance
    protected int totalRentals;         // Changed to protected for inheritance

    public Vehicle(String vehicleId, String model, double dailyRate, int passengerCapacity) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.dailyRate = dailyRate;
        this.passengerCapacity = passengerCapacity;
        this.totalRentals = 0;
    }

    public String getVehicleId() { return vehicleId; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public int getPassengerCapacity() { return passengerCapacity; }
    public int getTotalRentals() { return totalRentals; }
    
    // Setters
    public void setModel(String model) { 
        if (model != null && !model.trim().isEmpty()) {
            this.model = model; 
        }
    }
    
    public void setPassengerCapacity(int capacity) { 
        if (capacity > 0) {
            this.passengerCapacity = capacity; 
        }
    }

    public void setAvailable(boolean available) { 
        this.available = available; 
        if (!available) {
            totalRentals++;
        }
    }
    
    public void setDailyRate(double rate) { 
        ValidationUtil.validatePositiveNumber(rate, "Daily rate");
        this.dailyRate = rate; 
    }

    @Override
    public String getRentalInfo() {
        return String.format("%s - %s (RM%.2f/day, %d passengers)", 
            vehicleId, model, dailyRate, passengerCapacity);
    }

    public void incrementTotalRentals() {
        this.totalRentals++;
    }

    public abstract String getType();
    public abstract String getSpecialFeatures();

    @Override
    public String toString() {
        return "[" + vehicleId + "] " + model + " (" + getType() + ") RM" + dailyRate + "/day " +
               (available ? "Available" : "Rented");
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
