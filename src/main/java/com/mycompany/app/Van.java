/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
public class Van extends Vehicle {
    private int luggageSpace;

    public Van(String id, String model, int passengerCapacity, int luggageSpace, double dailyRate) {
        super(id, model, dailyRate, passengerCapacity);
        this.luggageSpace = luggageSpace;
    }

    @Override
    public String getType() { return "Van"; }

    @Override
    public String toString() {
        return "+----------------------------------------------+\n" +
               "| Vehicle ID: " + getVehicleId() + 
               "\n| Model: " + getModel() +
               "\n| Type: " + getType() +
               "\n| Daily Rate: RM" + getDailyRate() +
               "\n| Status: " + (isAvailable() ? "Available" : "Rented") +
               "\n|" +
               "\n| Features:" +
               "\n| - Passenger Capacity: " + getPassengerCapacity() + " people" +
               "\n| - Luggage Space: " + luggageSpace + " bags" +
               "\n| - Perfect for: Family trips, corporate events, group travel" +
               "\n+----------------------------------------------+";
    }
}

