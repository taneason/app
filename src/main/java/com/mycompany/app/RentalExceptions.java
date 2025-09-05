package com.mycompany.app;

/**
 * Custom exceptions for the rental system
 * Demonstrates proper exception handling design
 */

// Base exception class for rental system
class RentalSystemException extends Exception {
    public RentalSystemException(String message) {
        super(message);
    }
    
    public RentalSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Vehicle-related exceptions
class VehicleException extends RentalSystemException {
    public VehicleException(String message) {
        super(message);
    }
}

class VehicleNotAvailableException extends VehicleException {
    public VehicleNotAvailableException(String vehicleId) {
        super("Vehicle " + vehicleId + " is not available for rental");
    }
}

class VehicleMaintenanceException extends VehicleException {
    public VehicleMaintenanceException(String vehicleId, String status) {
        super("Vehicle " + vehicleId + " requires maintenance: " + status);
    }
}

// Customer-related exceptions
class CustomerException extends RentalSystemException {
    public CustomerException(String message) {
        super(message);
    }
}

class InvalidCredentialsException extends CustomerException {
    public InvalidCredentialsException() {
        super("Invalid email or password provided");
    }
}

class CustomerNotLoggedInException extends CustomerException {
    public CustomerNotLoggedInException() {
        super("Customer must be logged in to perform this action");
    }
}

// Booking-related exceptions
class BookingException extends RentalSystemException {
    public BookingException(String message) {
        super(message);
    }
}

class BookingNotFoundException extends BookingException {
    public BookingNotFoundException(String bookingId) {
        super("Booking with ID " + bookingId + " not found");
    }
}

class BookingAlreadyReturnedException extends BookingException {
    public BookingAlreadyReturnedException(String bookingId) {
        super("Booking " + bookingId + " has already been returned");
    }
}

// Payment-related exceptions
class PaymentException extends RentalSystemException {
    public PaymentException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends PaymentException {
    public InsufficientFundsException(double required, double available) {
        super(String.format("Insufficient funds: Required RM%.2f, Available RM%.2f", 
              required, available));
    }
}
