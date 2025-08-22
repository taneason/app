/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 *
 * @author User
 */
public class Admin {
    private final String adminId;
    private String name;
    private String email;
    private String password;

    public Admin(String adminId, String name, String email, String password) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getAdminId() { return adminId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public boolean login(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "[ADMIN " + adminId + "] " + name + " (" + email + ")";
    }
}

