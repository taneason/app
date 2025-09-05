/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Admin> admins = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Promotion> promotions = new ArrayList<>();

    private int custSeq = 100;
    private int adminSeq = 900;
    private int vehSeq = 200;
    private int bookSeq = 300;
    private int promoSeq = 500;

    // --- ID generate ---
    public String nextCustomerId() { return "C" + (++custSeq); }
    public String nextAdminId() { return "A" + (++adminSeq); }
    public String nextVehicleId() { return "V" + (++vehSeq); }
    public String nextBookingId() { return "B" + (++bookSeq); }

    // --- Customer ---
    public void addCustomer(Customer c) { 
        if (findCustomerByEmail(c.getEmail()) != null) {
            throw new IllegalArgumentException("Email already registered");
        }
        customers.add(c); 
    }
    
    public List<Customer> getCustomers() { return customers; }
    
    public Customer findCustomerByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
    
    public Customer loginCustomer(String email, String password) {
        Customer customer = findCustomerByEmail(email);
        if (customer != null && customer.login(email, password)) {
            return customer;
        }
        throw new IllegalArgumentException("Invalid email or password");
    }

    // --- Admin ---
    public void addAdmin(Admin a) { admins.add(a); }
    public Admin findAdminByEmail(String email) {
        return admins.stream().filter(ad -> ad.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    // --- Vehicle ---
    public void addVehicle(Vehicle v) { vehicles.add(v); }
    
    public boolean deleteVehicle(String vehicleId) {
        // Check if vehicle has active bookings
        boolean hasActiveBookings = bookings.stream()
            .anyMatch(b -> b.getVehicle().getVehicleId().equals(vehicleId) && !b.isReturned());
        
        if (hasActiveBookings) {
            throw new IllegalStateException("Cannot delete vehicle with active bookings");
        }
        
        // Remove the vehicle
        return vehicles.remove(vehicles.stream()
            .filter(v -> v.getVehicleId().equals(vehicleId))
            .findFirst()
            .orElse(null));
    }
    public List<Vehicle> getVehicles() { return vehicles; }
    public List<Vehicle> getAvailableVehicles() {
        return vehicles.stream().filter(Vehicle::isAvailable).toList();
    }

    // --- Booking ---
    public Booking rentVehicle(Customer c, Vehicle v, int days, int groupSize) {
        if (!v.isAvailable()) throw new IllegalStateException("Vehicle not available.");
        
        if (groupSize > v.getPassengerCapacity()) {
            throw new IllegalArgumentException("Group size exceeds vehicle capacity of " + 
                v.getPassengerCapacity() + " passengers");
        }
        
        // Find applicable promotions (can apply multiple)
        Promotion groupPromo = null;
        Promotion longTermPromo = null;
        
        for (Promotion p : getActivePromotions()) {
            // Check for group discount
            if (p.getType().equals("GROUP") && groupSize >= p.getThreshold()) {
                if (groupPromo == null || p.getDiscountPercentage() > groupPromo.getDiscountPercentage()) {
                    groupPromo = p;
                }
            }
            // Check for long-term discount
            else if (p.getType().equals("LONG_TERM") && days >= p.getThreshold()) {
                if (longTermPromo == null || p.getDiscountPercentage() > longTermPromo.getDiscountPercentage()) {
                    longTermPromo = p;
                }
            }
        }
        
        Booking b = new Booking(nextBookingId(), c, v, LocalDate.now(), days, groupPromo, longTermPromo);
        bookings.add(b);
        
        // Update customer statistics immediately when booking is created
        c.addRental(b.calculateCharge());
        
        return b;
    }

    public void returnVehicle(String bookingId) {
        Booking b = bookings.stream().filter(x -> x.getBookingId().equals(bookingId)).findFirst().orElse(null);
        if (b != null && !b.isReturned()) {
            b.returnVehicle();
            System.out.println("\n=========== RETURN SUMMARY ===========");
            System.out.println("Vehicle: " + b.getVehicle().getModel());
            System.out.println("Duration: " + b.getDurationDays() + " days");
            
            // Show applied promotions
            if (b.getAppliedGroupPromotion() != null && b.getAppliedGroupPromotion().isActive()) {
                System.out.println("Group Promotion: " + b.getAppliedGroupPromotion().getCode() + 
                    " (" + String.format("%.1f", b.getAppliedGroupPromotion().getDiscountPercentage()) + "% off)");
            }
            if (b.getAppliedLongTermPromotion() != null && b.getAppliedLongTermPromotion().isActive()) {
                System.out.println("Long-term Promotion: " + b.getAppliedLongTermPromotion().getCode() + 
                    " (" + String.format("%.1f", b.getAppliedLongTermPromotion().getDiscountPercentage()) + "% off)");
            }
            
            System.out.println("Final Charge: RM" + String.format("%.2f", b.calculateCharge()));
            System.out.println("=====================================");
        } else {
            throw new IllegalArgumentException("Invalid booking or already returned.");
        }
    }

    public List<Booking> getBookings() { return bookings; }
    public List<Booking> getBookingsByCustomer(Customer c) {
        return bookings.stream().filter(b -> b.getCustomer().equals(c)).toList();
    }

    // --- Promotions ---
    public String nextPromotionCode() {
        return "P" + (++promoSeq);
    }

    public void addPromotion(Promotion p) {
        promotions.add(p);
    }

    public List<Promotion> getActivePromotions() {
        return promotions.stream().filter(Promotion::isActive).toList();
    }

    public List<Promotion> getAllPromotions() {
        return promotions;
    }

    public void deactivatePromotion(String code) {
        promotions.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .ifPresent(p -> p.setActive(false));
    }

    public void activatePromotion(String code) {
        promotions.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .ifPresent(p -> p.setActive(true));
    }
}
