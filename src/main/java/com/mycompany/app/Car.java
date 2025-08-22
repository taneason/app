/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
public class Car extends Vehicle {
    public Car(String id, String model, int passengerCapacity, double dailyRate) {
        super(id, model, dailyRate, passengerCapacity);
    }

    @Override
    public String getType() { return "Car"; }

    @Override
    public String toString() {
        return "╭──────────────────────────────────────────────╮\n" +
               "│ Vehicle ID: " + getVehicleId() + 
               "\n│ Model: " + getModel() +
               "\n│ Type: " + getType() +
               "\n│ Daily Rate: RM" + getDailyRate() +
               "\n│ Status: " + (isAvailable() ? "Available" : "Rented") +
               "\n│" +
               "\n│ Features:" +
               "\n│ • Passenger Capacity: " + getPassengerCapacity() + " people" +
               "\n╰──────────────────────────────────────────────╯";
    }
}
