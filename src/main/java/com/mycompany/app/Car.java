/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 * Car class demonstrating inheritance and polymorphism
 */
public class Car extends Vehicle {
    private CarType carType;
    private boolean hasGPS;
    private boolean hasAirCon;

    public enum CarType {
        ECONOMY("Economy", 1.0),
        COMPACT("Compact", 1.2),
        MIDSIZE("Midsize", 1.5),
        LUXURY("Luxury", 2.0);

        private final String displayName;
        private final double priceMultiplier;

        CarType(String displayName, double priceMultiplier) {
            this.displayName = displayName;
            this.priceMultiplier = priceMultiplier;
        }

        public String getDisplayName() { return displayName; }
        public double getPriceMultiplier() { return priceMultiplier; }
    }

    public Car(String id, String model, int passengerCapacity, double dailyRate) {
        this(id, model, passengerCapacity, dailyRate, CarType.ECONOMY, true, true);
    }

    public Car(String id, String model, int passengerCapacity, double dailyRate, 
               CarType carType, boolean hasGPS, boolean hasAirCon) {
        super(id, model, dailyRate, passengerCapacity);
        this.carType = carType;
        this.hasGPS = hasGPS;
        this.hasAirCon = hasAirCon;
    }

    @Override
    public String getType() { 
        return "Car (" + carType.getDisplayName() + ")"; 
    }

    @Override
    public String getSpecialFeatures() {
        StringBuilder features = new StringBuilder();
        features.append("Car features: ");
        if (hasGPS) features.append("GPS Navigation, ");
        if (hasAirCon) features.append("Air Conditioning, ");
        features.append("Type: ").append(carType.getDisplayName());
        return features.toString();
    }

    @Override
    public double getDailyRate() {
        return super.getDailyRate() * carType.getPriceMultiplier();
    }

    // Getters
    public CarType getCarType() { return carType; }
    public boolean hasGPS() { return hasGPS; }
    public boolean hasAirCon() { return hasAirCon; }

    @Override
    public String toString() {
        return "+----------------------------------------------+\n" +
               "| Vehicle ID: " + getVehicleId() + 
               "\n| Model: " + getModel() +
               "\n| Type: " + getType() +
               "\n| Daily Rate: RM" + String.format("%.2f", getDailyRate()) +
               "\n| Status: " + (isAvailable() ? "Available" : "Rented") +
               "\n| Condition: " + getMaintenanceStatus().getDescription() +
               "\n|" +
               "\n| Features:" +
               "\n| - Passenger Capacity: " + getPassengerCapacity() + " people" +
               "\n| - GPS: " + (hasGPS ? "Yes" : "No") +
               "\n| - Air Conditioning: " + (hasAirCon ? "Yes" : "No") +
               "\n| - Category: " + carType.getDisplayName() +
               "\n+----------------------------------------------+";
    }
}
