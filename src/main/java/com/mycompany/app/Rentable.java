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
    void performMaintenance();
    MaintenanceStatus getMaintenanceStatus();
    
    enum MaintenanceStatus {
        EXCELLENT("Excellent condition"),
        GOOD("Good condition"), 
        FAIR("Fair condition - minor issues"),
        POOR("Poor condition - needs attention"),
        OUT_OF_SERVICE("Out of service");
        
        private final String description;
        
        MaintenanceStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() { return description; }
    }
}
