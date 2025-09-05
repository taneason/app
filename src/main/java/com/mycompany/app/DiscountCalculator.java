package com.mycompany.app;

/**
 * Interface for discount calculation strategies
 * Demonstrates Strategy Pattern and polymorphism
 */
public interface DiscountCalculator {
    double calculateDiscount(double baseAmount, int duration, int groupSize);
    String getDiscountDescription();
    boolean isApplicable(int duration, int groupSize);
}
