package com.mycompany.app;

/**
 * Interface for all rentable items
 * Demonstrates interface-based polymorphism
 */
public interface Rentable {
    boolean isAvailable();
    void setAvailable(boolean available);
    double getDailyRate();
    String getRentalInfo();
}
