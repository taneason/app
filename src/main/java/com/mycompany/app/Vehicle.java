/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
public abstract class Vehicle {
    private final String vehicleId;
    private String model;
    private boolean available = true;
    private double dailyRate;
    private int passengerCapacity;

    public Vehicle(String vehicleId, String model, double dailyRate, int passengerCapacity) {
        this.vehicleId = vehicleId;
        this.model = model;
        this.dailyRate = dailyRate;
        this.passengerCapacity = passengerCapacity;
    }

    public String getVehicleId() { return vehicleId; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public int getPassengerCapacity() { return passengerCapacity; }

    public void setAvailable(boolean available) { this.available = available; }
    
    public void setDailyRate(double rate) { this.dailyRate = rate; }

    public abstract String getType();

    @Override
    public String toString() {
        return "[" + vehicleId + "] " + model + " (" + getType() + ") RM" + dailyRate + "/day " +
               (available ? "Available" : "Rented");
    }
}
