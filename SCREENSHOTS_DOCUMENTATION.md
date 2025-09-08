# Application Screenshots Documentation

## 🖥️ Vehicle Rental System - User Interface Demonstrations

### 1. Main Menu Interface
```
+==================================================+
|      FAMILY & CORPORATE VAN RENTAL SYSTEM       |
+==================================================+
Welcome to our premium van rental service!
===================================================
1. Login
2. Register New Account
3. Admin Login
0. Exit
===================================================
Please select an option:
```

**Description:** The main menu provides a clean, professional interface for users to access different system functions. It clearly displays the system name and offers logical navigation options for customers and administrators.

---

### 2. Customer Registration Process
```
=== Customer Registration ===
Enter your name: John Doe
Enter your email: john.doe@email.com
Enter your phone: +1234567890
Enter your password: ********
Confirm password: ********
Initial balance (minimum $100): 500.00

Registration successful!
Customer ID: C001
Welcome, John Doe!
Your account has been created with Premium tier status.
```

**Description:** The registration process collects essential customer information with proper validation. The system automatically assigns customer IDs and tier status based on initial balance.

---

### 3. Customer Dashboard
```
+==================================================+
|              CUSTOMER DASHBOARD                  |
+==================================================+
Welcome back, John Doe! (Premium Tier)
Account Balance: $500.00
Active Bookings: 0

1. Browse Available Vehicles
2. Make a Booking
3. View My Bookings
4. Return Vehicle
5. Update Profile
6. View Promotions
7. Logout
===================================================
Please select an option:
```

**Description:** The customer dashboard provides a personalized experience showing account status, balance, and booking information. The menu is organized by frequency of use and importance.

---

### 4. Vehicle Listing Report
```
=== Available Vehicles ===
+--------+-------------------+----------+----------------+
| ID     | Vehicle Details   | Rate/Day | Special Features|
+--------+-------------------+----------+----------------+
| V001   | Toyota Camry      | $50.00   | GPS: Yes       |
|        | (SEDAN)           |          | AC: Yes        |
+--------+-------------------+----------+----------------+
| V002   | Honda Civic       | $45.00   | GPS: No        |
|        | (HATCHBACK)       |          | AC: Yes        |
+--------+-------------------+----------+----------------+
| V003   | Ford Transit      | $80.00   | Capacity: 1500kg|
|        | (CARGO VAN)       |          | Load Height: 1.8m|
+--------+-------------------+----------+----------------+
| V004   | Mercedes Sprinter | $120.00  | Seats: 12      |
|        | (PASSENGER VAN)   |          | AC: Yes        |
+--------+-------------------+----------+----------------+

Total Available Vehicles: 4
```

**Description:** The vehicle listing displays comprehensive information in a tabulated format, making it easy for customers to compare options. Each vehicle shows type-specific features and daily rates.

---

### 5. Booking Creation Process
```
=== Make a Booking ===
Selected Vehicle: Toyota Camry (V001)
Daily Rate: $50.00

Enter rental start date (DD/MM/YYYY): 15/09/2025
Enter rental duration (days): 3

=== Booking Summary ===
Vehicle: Toyota Camry (V001)
Duration: 3 days (15/09/2025 - 18/09/2025)
Base Cost: $150.00
Premium Discount (10%): -$15.00
Final Total: $135.00

Need a driver? (Y/N): N

Confirm booking? (Y/N): Y

+==================================================+
|              BOOKING CONFIRMED                   |
+==================================================+
Booking ID: B001
Customer: John Doe
Vehicle: Toyota Camry (V001)
Total Cost: $135.00
Status: CONFIRMED
Thank you for your business!
```

**Description:** The booking process guides users through vehicle selection, date entry, and cost calculation. It clearly shows all charges and discounts before final confirmation.

---

### 6. Admin Dashboard
```
+==================================================+
|              ADMIN DASHBOARD                     |
+==================================================+
Welcome, Admin Sarah (Manager Level)
System Status: OPERATIONAL

=== Quick Stats ===
Total Vehicles: 12    | Available: 8     | In Use: 4
Total Customers: 25   | Active Today: 12
Current Bookings: 6   | Revenue Today: $850.00

1. Manage Vehicles
2. Manage Customers
3. View All Bookings
4. Generate Reports
5. Manage Promotions
6. System Settings
7. Logout
===================================================
Please select an option:
```

**Description:** The admin dashboard provides comprehensive system overview with key performance indicators. It offers quick access to all administrative functions with real-time statistics.

---

### 7. Vehicle Management Interface
```
=== Vehicle Management ===
1. Add New Vehicle
2. Update Vehicle
3. Remove Vehicle
4. View All Vehicles
5. Vehicle Maintenance
6. Back to Main Menu

Current Fleet Status:
- Cars: 8 (6 available, 2 in use)
- Vans: 4 (2 available, 2 in use)
- Under Maintenance: 0

Select an option:
```

**Description:** Vehicle management provides comprehensive fleet control with clear status indicators. Administrators can easily track vehicle availability and maintenance status.

---

### 8. Booking Management Report
```
=== All Bookings Report ===
+------+-------------+----------------+--------+-----------+
| ID   | Customer    | Vehicle        | Days   | Status    |
+------+-------------+----------------+--------+-----------+
| B001 | John Doe    | Toyota Camry   | 3      | ACTIVE    |
| B002 | Alice Smith | Ford Transit   | 5      | ACTIVE    |
| B003 | Bob Johnson | Honda Civic    | 2      | RETURNED  |
| B004 | Carol White | Mercedes Spr.  | 7      | ACTIVE    |
+------+-------------+----------------+--------+-----------+

Total Revenue This Month: $2,450.00
Average Booking Duration: 4.2 days
Customer Satisfaction: 4.8/5.0
```

**Description:** The booking management report provides a comprehensive view of all rentals with filtering options. It includes key metrics for business intelligence and decision making.

---

### 9. Profile Update Interface
```
=== Update Profile ===
Current Information:
Name: John Doe
Email: john.doe@email.com
Phone: +1234567890

1. Update Name
2. Update Email
3. Update Phone
4. Change Password
5. View Rental History
6. Back to Dashboard

Enter your choice:

=== Change Password ===
Current password: ********
New password: ********
Confirm new password: ********

Password updated successfully!
Your account security has been enhanced.
```

**Description:** Profile management allows customers to maintain their account information securely. The password change process requires current password verification for security.

---

### 10. Error Handling Examples
```
=== Input Validation ===
Enter your email: invalid-email
❌ Error: Please enter a valid email address format.

Enter rental duration: -2
❌ Error: Rental duration must be at least 1 day.

Enter vehicle ID: V999
❌ Error: Vehicle not found. Please enter a valid vehicle ID.

=== System Exceptions ===
❌ BookingNotFoundException: Booking B999 not found in system.
❌ InsufficientFundsException: Account balance too low for this rental.
❌ VehicleNotAvailableException: Selected vehicle is currently unavailable.
```

**Description:** The system provides comprehensive error handling with clear, user-friendly messages. Input validation prevents common errors and guides users to correct input.

---

## 📊 Key Features Demonstrated

### 1. **User Experience**
- Clean, intuitive interface design
- Consistent navigation patterns
- Clear feedback and confirmation messages
- Professional appearance with branded headers

### 2. **Data Management**
- Real-time availability tracking
- Comprehensive customer profiles
- Detailed booking records
- Financial transaction tracking

### 3. **Business Logic**
- Dynamic pricing with tier-based discounts
- Availability management
- Rental duration calculations
- Revenue tracking and reporting

### 4. **Security Features**
- Password-protected accounts
- Role-based access control
- Input validation and sanitization
- Secure profile updates

### 5. **Error Handling**
- Custom exception types
- User-friendly error messages
- Input validation
- Graceful failure recovery

---

## 🎯 Technical Implementation Highlights

### **Object-Oriented Design**
- Clean class hierarchies (Person → Customer/Admin, Vehicle → Car/Van)
- Interface implementations (Rentable)
- Polymorphic method calls
- Encapsulated data access

### **Design Patterns**
- Template Method (login process)
- Strategy (pricing calculations)
- Factory (object creation)

### **Code Quality**
- Consistent naming conventions
- Comprehensive documentation
- Modular design
- Extensible architecture

This interface demonstration shows a fully functional rental management system with professional-grade user experience and robust technical implementation.
