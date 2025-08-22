/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
public class Customer {
    private final String customerId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private boolean isLoggedIn;

    public Customer(String customerId, String name, String email, String phone, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isLoggedIn = false;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean isLoggedIn() { return isLoggedIn; }

    public boolean login(String email, String password) {
        if (this.email.equalsIgnoreCase(email) && this.password.equals(password)) {
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.isLoggedIn = false;
    }

    @Override
    public String toString() {
        return "[" + customerId + "] " + name + " (" + email + ", " + phone + ")";
    }
}
