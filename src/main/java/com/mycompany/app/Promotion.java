package com.mycompany.app;

public class Promotion {
    private String code;
    private String type;  // "GROUP" or "LONG_TERM"
    private String description;
    private double discountPercentage;
    private int threshold;  // group size or number of days
    private boolean active;

    public Promotion(String code, String type, String description, 
                    double discountPercentage, int threshold) {
        this.code = code;
        this.type = type;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.threshold = threshold;
        this.active = true;
    }

    public String getCode() { return code; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public double getDiscountPercentage() { return discountPercentage; }
    public int getThreshold() { return threshold; }
    public boolean isActive() { return active; }
    
    // Setters
    public void setCode(String code) { 
        if (code != null && !code.trim().isEmpty()) {
            this.code = code; 
        }
    }
    
    public void setType(String type) { 
        if (type != null && (type.equals("GROUP") || type.equals("LONG_TERM"))) {
            this.type = type; 
        }
    }
    
    public void setDescription(String description) { 
        if (description != null && !description.trim().isEmpty()) {
            this.description = description; 
        }
    }
    
    public void setDiscountPercentage(double discountPercentage) { 
        if (discountPercentage >= 0 && discountPercentage <= 100) {
            this.discountPercentage = discountPercentage; 
        }
    }
    
    public void setThreshold(int threshold) { 
        if (threshold > 0) {
            this.threshold = threshold; 
        }
    }
    
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "+------------- PROMOTION -------------+\n" +
               "| Code: " + code + "\n" +
               "| Type: " + (type.equals("GROUP") ? "Group Discount" : "Long-term Rental") + "\n" +
               "| Description: " + description + "\n" +
               "| Discount: " + String.format("%.1f%%", discountPercentage) + "\n" +
               "| Requirement: " + formatThreshold() + "\n" +
               "| Status: " + (active ? "+ Active" : "- Inactive") + "\n" +
               "+------------------------------------+";
    }

    private String formatThreshold() {
        if (type.equals("GROUP")) {
            return "Group size >= " + threshold + " people";
        } else {
            return "Rental duration >= " + threshold + " days";
        }
    }
}
