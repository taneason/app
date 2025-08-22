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
        
        // Find applicable promotion
        Promotion applicablePromo = null;
        double bestDiscount = 0;
        
        for (Promotion p : getActivePromotions()) {
            // Check for group discount
            if (p.getType().equals("GROUP") && groupSize >= p.getThreshold() && 
                p.getDiscountPercentage() > bestDiscount) {
                applicablePromo = p;
                bestDiscount = p.getDiscountPercentage();
            }
            // Check for long-term discount
            else if (p.getType().equals("LONG_TERM") && days >= p.getThreshold() && 
                     p.getDiscountPercentage() > bestDiscount) {
                applicablePromo = p;
                bestDiscount = p.getDiscountPercentage();
            }
        }
        
        Booking b = new Booking(nextBookingId(), c, v, LocalDate.now(), days, applicablePromo);
        bookings.add(b);
        return b;
    }

    public void returnVehicle(String bookingId) {
        Booking b = bookings.stream().filter(x -> x.getBookingId().equals(bookingId)).findFirst().orElse(null);
        if (b != null && !b.isReturned()) {
            b.returnVehicle();
            System.out.println("\n═══════════ RETURN SUMMARY ═══════════");
            System.out.println("Vehicle: " + b.getVehicle().getModel());
            System.out.println("Duration: " + b.getDurationDays() + " days");
            if (b.getAppliedPromotion() != null && b.getAppliedPromotion().isActive()) {
                System.out.println("Applied Promotion: " + b.getAppliedPromotion().getCode() + 
                    " (" + String.format("%.1f", b.getAppliedPromotion().getDiscountPercentage()) + "% off)");
            }
            System.out.println("Final Charge: RM" + String.format("%.2f", b.calculateCharge()));
            System.out.println("═════════════════════════════════════");
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
