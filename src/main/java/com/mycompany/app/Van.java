/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 * Van class demonstrating inheritance and polymorphism
 */
public class Van extends Vehicle {
    private int luggageSpace;
    private VanType vanType;
    private boolean hasWifi;
    private boolean hasTV;

    public enum VanType {
        STANDARD("Standard Van", 1.0),
        LUXURY("Luxury Van", 1.5),
        EXECUTIVE("Executive Van", 2.0);

        private final String displayName;
        private final double priceMultiplier;

        VanType(String displayName, double priceMultiplier) {
            this.displayName = displayName;
            this.priceMultiplier = priceMultiplier;
        }

        public String getDisplayName() { return displayName; }
        public double getPriceMultiplier() { return priceMultiplier; }
    }

    public Van(String id, String model, int passengerCapacity, int luggageSpace, double dailyRate) {
        this(id, model, passengerCapacity, luggageSpace, dailyRate, VanType.STANDARD, false, false);
    }

    public Van(String id, String model, int passengerCapacity, int luggageSpace, double dailyRate,
               VanType vanType, boolean hasWifi, boolean hasTV) {
        super(id, model, dailyRate, passengerCapacity);
        this.luggageSpace = luggageSpace;
        this.vanType = vanType;
        this.hasWifi = hasWifi;
        this.hasTV = hasTV;
    }

    @Override
    public String getType() { 
        return "Van (" + vanType.getDisplayName() + ")"; 
    }

    @Override
    public String getSpecialFeatures() {
        StringBuilder features = new StringBuilder();
        features.append("Van features: ");
        features.append("Luggage Space: ").append(luggageSpace).append(" bags, ");
        if (hasWifi) features.append("WiFi, ");
        if (hasTV) features.append("Entertainment System, ");
        features.append("Type: ").append(vanType.getDisplayName());
        return features.toString();
    }

    @Override
    public double getDailyRate() {
        return super.getDailyRate() * vanType.getPriceMultiplier();
    }

    // Getters
    public int getLuggageSpace() { return luggageSpace; }
    public VanType getVanType() { return vanType; }
    public boolean hasWifi() { return hasWifi; }
    public boolean hasTV() { return hasTV; }
    
    // Setters
    public void setLuggageSpace(int luggageSpace) { 
        if (luggageSpace >= 0) {
            this.luggageSpace = luggageSpace; 
        }
    }
    
    public void setVanType(VanType vanType) { 
        if (vanType != null) {
            this.vanType = vanType; 
        }
    }
    
    public void setHasWifi(boolean hasWifi) { 
        this.hasWifi = hasWifi; 
    }
    
    public void setHasTV(boolean hasTV) { 
        this.hasTV = hasTV; 
    }

    @Override
    public String toString() {
        return "+----------------------------------------------+\n" +
               "| Vehicle ID: " + getVehicleId() + 
               "\n| Model: " + getModel() +
               "\n| Type: " + getType() +
               "\n| Daily Rate: RM" + String.format("%.2f", getDailyRate()) +
               "\n| Status: " + (isAvailable() ? "Available" : "Rented") +
               "\n|" +
               "\n| Features:" +
               "\n| - Passenger Capacity: " + getPassengerCapacity() + " people" +
               "\n| - Luggage Space: " + luggageSpace + " bags" +
               "\n| - WiFi: " + (hasWifi ? "Yes" : "No") +
               "\n| - Entertainment: " + (hasTV ? "Yes" : "No") +
               "\n| - Category: " + vanType.getDisplayName() +
               "\n| - Perfect for: Family trips, corporate events, group travel" +
               "\n+----------------------------------------------+";
    }
}

