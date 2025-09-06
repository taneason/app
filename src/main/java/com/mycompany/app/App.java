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

    /**
     * Initializes the rental system with sample data for demonstration purposes.
     * This includes vehicles of different types, customers, drivers, promotions,
     * and some sample bookings to demonstrate the system's functionality.
     * 
     * @param rs The RentalService to populate with sample data
     */
    private static void seedDemoData(RentalService rs) {
        // Premium Vans for Family and Corporate Groups
        rs.addVehicle(new Van(rs.nextVehicleId(), "Toyota Hiace Executive", 12, 8, 280, Van.VanType.STANDARD, true, false));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Nissan Urvan Premium", 14, 10, 320, Van.VanType.STANDARD, true, true));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Mercedes Sprinter", 15, 12, 450, Van.VanType.LUXURY, true, true));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Hyundai Starex Royale", 11, 7, 300, Van.VanType.EXECUTIVE, true, false));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Ford Transit Premium", 13, 9, 350, Van.VanType.LUXURY, true, true));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Toyota Alphard", 7, 4, 480, Van.VanType.LUXURY, true, true));
        rs.addVehicle(new Van(rs.nextVehicleId(), "Volkswagen Caravelle", 9, 6, 420, Van.VanType.EXECUTIVE, true, true));
        
        // Cars of different types
        rs.addVehicle(new Car(rs.nextVehicleId(), "Toyota Camry", 5, 180.0, Car.CarType.MIDSIZE, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Honda City", 5, 120.0, Car.CarType.ECONOMY, false, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "BMW 5 Series", 5, 350.0, Car.CarType.LUXURY, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Mercedes E-Class", 5, 380.0, Car.CarType.LUXURY, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Toyota Vios", 5, 110.0, Car.CarType.ECONOMY, false, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Honda Civic", 5, 150.0, Car.CarType.COMPACT, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Nissan Almera", 5, 115.0, Car.CarType.ECONOMY, false, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Mazda 6", 5, 190.0, Car.CarType.MIDSIZE, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Audi A6", 5, 360.0, Car.CarType.LUXURY, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Toyota Corolla", 5, 140.0, Car.CarType.COMPACT, true, true));
        rs.addVehicle(new Car(rs.nextVehicleId(), "Proton X70", 5, 160.0, Car.CarType.MIDSIZE, true, true));
        
        // Corporate Customers
        rs.addCustomer(new Customer(rs.nextCustomerId(), "ABC Corporation", "abc@corp.com", "012-8889999", "corp123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Family Tours Sdn Bhd", "bookings@familytours.com", "012-7776666", "tour123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Global Enterprises", "contact@global.com", "012-3456789", "global123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Tech Solutions Inc", "info@techsolutions.com", "012-9876543", "tech123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Sun Travel Agency", "bookings@suntravel.com", "012-5557777", "travel123456"));

        // Individual Customers
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Ali bin Ahmad", "ali@mail.com", "012-1111111", "ali123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Siti Aminah", "siti@mail.com", "012-2222222", "siti123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "John Smith", "john@mail.com", "012-3333333", "john123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Mei Ling", "meiling@mail.com", "012-4444444", "mei123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Kumar Patel", "kumar@mail.com", "012-5555555", "kumar123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Sarah Johnson", "sarah@mail.com", "012-6666666", "sarah123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Mohammed Yusof", "yusof@mail.com", "012-7777777", "yusof123456"));
        rs.addCustomer(new Customer(rs.nextCustomerId(), "Jessica Tan", "jessica@mail.com", "012-8888888", "jessica123456"));
        
        // Admin Staff
        rs.addAdmin(new Admin(rs.nextAdminId(), "Fleet Manager", "admin@mail.com", "admin123456"));
        rs.addAdmin(new Admin(rs.nextAdminId(), "Customer Service Manager", "support@mail.com", "012-9990000", "support123456", Admin.AdminLevel.SENIOR));
        rs.addAdmin(new Admin(rs.nextAdminId(), "Operations Director", "operations@mail.com", "012-9991111", "ops123456", Admin.AdminLevel.SUPERVISOR));
        rs.addAdmin(new Admin(rs.nextAdminId(), "Junior Admin", "junior@mail.com", "012-9992222", "junior123456", Admin.AdminLevel.JUNIOR));
        
        // Sample Drivers with complete information
        rs.addDriver(new Driver("D001", "Ahmad Rahman", "LIC001234", 5, Driver.DriverType.STANDARD, "012-3336666", 4.8));
        rs.addDriver(new Driver("D002", "Lee Wei Ming", "LIC005678", 8, Driver.DriverType.PROFESSIONAL, "012-4447777", 4.7));
        rs.addDriver(new Driver("D003", "Rajesh Kumar", "LIC009876", 12, Driver.DriverType.LUXURY, "012-5558888", 4.9));
        rs.addDriver(new Driver("D004", "Fatimah Hassan", "LIC004321", 3, Driver.DriverType.STANDARD, "012-6669999", 4.6));
        rs.addDriver(new Driver("D005", "Chen Wei Lun", "LIC008765", 15, Driver.DriverType.LUXURY, "012-7770000", 5.0));
        rs.addDriver(new Driver("D006", "David Wong", "LIC012345", 6, Driver.DriverType.PROFESSIONAL, "012-8881111", 4.5));
        rs.addDriver(new Driver("D007", "Sarah Abdullah", "LIC023456", 9, Driver.DriverType.LUXURY, "012-8882222", 4.8));
        rs.addDriver(new Driver("D008", "Raju Singh", "LIC034567", 4, Driver.DriverType.STANDARD, "012-8883333", 4.2));
        rs.addDriver(new Driver("D009", "Nurul Huda", "LIC045678", 7, Driver.DriverType.PROFESSIONAL, "012-8884444", 4.4));
        rs.addDriver(new Driver("D010", "Jason Lim", "LIC056789", 10, Driver.DriverType.LUXURY, "012-8885555", 4.7));
        
        // Initial Promotions
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Weekly Special: Rent 7 days or more for special discount!", 15.0, 7));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Monthly Deal: Maximum savings for 30-day rentals!", 25.0, 30));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "GROUP", 
            "Group Travel: Special discount for groups of 10 or more!", 20.0, 10));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "GROUP", 
            "Family Package: Special rates for family of 5 or more!", 12.0, 5));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "GROUP", 
            "Corporate Event: Special discount for corporate bookings!", 18.0, 8));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Weekend Getaway: Special rates for 3-day rentals!", 10.0, 3));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Two-Week Holiday: Special discount for 14-day rentals!", 20.0, 14));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "GROUP", 
            "School Trip: Special discount for educational groups!", 25.0, 15));
        rs.addPromotion(new Promotion(rs.nextPromotionCode(), "LONG_TERM", 
            "Business Travel: 5-day business trip discount!", 12.0, 5));
        
        // Set up promotions status
        // First promotion active
        rs.activatePromotion("P501");
        
        // Second promotion active
        rs.activatePromotion("P502");
        
        // Third promotion active
        rs.activatePromotion("P503");
        
        // Fourth promotion inactive (simulate an expired promotion)
        rs.deactivatePromotion("P504");
        
        // Create some initial bookings for demonstration
        try {
            // Find customers for demo bookings
            Customer customer1 = rs.getCustomers().stream()
                .filter(c -> c.getName().equals("Ali bin Ahmad")).findFirst().orElse(null);
                
            Customer customer2 = rs.getCustomers().stream()
                .filter(c -> c.getName().equals("ABC Corporation")).findFirst().orElse(null);
                
            Customer customer3 = rs.getCustomers().stream()
                .filter(c -> c.getName().equals("Sarah Johnson")).findFirst().orElse(null);
                
            Customer customer4 = rs.getCustomers().stream()
                .filter(c -> c.getName().equals("Family Tours Sdn Bhd")).findFirst().orElse(null);
            
            Customer customer5 = rs.getCustomers().stream()
                .filter(c -> c.getName().equals("Mei Ling")).findFirst().orElse(null);
                
            // Find vehicles for demo bookings
            Vehicle car1 = rs.getVehicles().stream()
                .filter(v -> v.getModel().equals("Honda Civic")).findFirst().orElse(null);
                
            Vehicle car2 = rs.getVehicles().stream()
                .filter(v -> v.getModel().equals("BMW 5 Series")).findFirst().orElse(null);
                
            Vehicle car3 = rs.getVehicles().stream()
                .filter(v -> v.getModel().equals("Toyota Vios")).findFirst().orElse(null);
                
            Vehicle van1 = rs.getVehicles().stream()
                .filter(v -> v.getModel().equals("Toyota Hiace Executive")).findFirst().orElse(null);
                
            Vehicle van2 = rs.getVehicles().stream()
                .filter(v -> v.getModel().equals("Mercedes Sprinter")).findFirst().orElse(null);
                
            // Find drivers for demo bookings
            Driver driver1 = rs.findDriverById("D003");
            Driver driver2 = rs.findDriverById("D005");
            
            // Create some past and current bookings if objects found
            if (customer1 != null && car1 != null) {
                // Past booking (already returned)
                // Using days=3, groupSize=1 (single person)
                Booking pastBooking = rs.rentVehicle(customer1, car1, 3, 1);
                // Simulate returned vehicle
                if (pastBooking != null) {
                    pastBooking.returnVehicle();
                }
            }
            
            if (customer2 != null && van1 != null && driver1 != null) {
                // Current booking with driver (not yet returned)
                // Using days=7, groupSize=8 (corporate group)
                rs.rentVehicleWithDriver(customer2, van1, 7, 8, driver1);
            }
            
            // Add more varied booking examples
            if (customer3 != null && car2 != null) {
                // Luxury car rental
                Booking luxuryBooking = rs.rentVehicle(customer3, car2, 2, 2);
                // Already returned
                if (luxuryBooking != null) {
                    luxuryBooking.returnVehicle();
                }
            }
            
            if (customer4 != null && van2 != null && driver2 != null) {
                // Group tour with luxury van and driver
                rs.rentVehicleWithDriver(customer4, van2, 14, 12, driver2);
            }
            
            if (customer5 != null && car3 != null) {
                // Economy car for longer term
                rs.rentVehicle(customer5, car3, 30, 1);
            }
            
            // Simulate multiple rentals for Ali to reach silver tier
            if (customer1 != null) {
                for (int i = 0; i < 3; i++) {
                    // Find an available economy car
                    Vehicle economyCar = rs.getVehicles().stream()
                        .filter(v -> v instanceof Car && v.isAvailable())
                        .findFirst().orElse(null);
                    
                    if (economyCar != null) {
                        Booking booking = rs.rentVehicle(customer1, economyCar, 2, 1);
                        if (booking != null) {
                            booking.returnVehicle();
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error creating demo bookings: " + e.getMessage());
        }
    }
}
