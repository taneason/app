/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.app;

/**
 *
 * @author User
 */
public class App {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        seedDemoData(service);
        new ConsoleUI(service).start();
    }

    private static void seedDemoData(RentalService rs) {

        // Premium Vans for Family and Corporate Groups
        rs.addVehicle(new Van(rs.nextVehicleId(), "Toyota Hiace Executive", 12, 8, 280));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Nissan Urvan Premium", 14, 10, 320));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Mercedes Sprinter", 15, 12, 450));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Hyundai Starex Royale", 11, 7, 300));

        // Corporate Customers
        rs.addCustomer(new Customer(rs.nextCustomerId(), "ABC Corporation", "abc@corp.com", "012-8889999", "corp123"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Family Tours Sdn Bhd", "bookings@familytours.com", "012-7776666", "tour123"));
        
        // Individual Customers
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Ali bin Ahmad", "ali@mail.com", "012-1111111", "ali123"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Siti Aminah", "siti@mail.com", "012-2222222", "siti123"));

        // Admin Staff
        rs.addAdmin(new Admin(rs.nextAdminId(), "Fleet Manager", "admin@mail.com", "1234"));
        
        // Sample Drivers
        rs.addDriver(new Driver("D001", "Ahmad Rahman", "LIC001234", 5, Driver.DriverType.STANDARD));
        rs.addDriver(new Driver("D002", "Lee Wei Ming", "LIC005678", 8, Driver.DriverType.PROFESSIONAL));
        rs.addDriver(new Driver("D003", "Rajesh Kumar", "LIC009876", 12, Driver.DriverType.LUXURY));
        rs.addDriver(new Driver("D004", "Fatimah Hassan", "LIC004321", 3, Driver.DriverType.STANDARD));
        rs.addDriver(new Driver("D005", "Chen Wei Lun", "LIC008765", 15, Driver.DriverType.LUXURY));
        
        // Initial Promotions
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Weekly Special: Rent 7 days or more for special discount!", 15.0, 7));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Monthly Deal: Maximum savings for 30-day rentals!", 25.0, 30));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "GROUP", 
            "Group Travel: Special discount for groups of 10 or more!", 20.0, 10));
    }
}
