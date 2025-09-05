/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

/**
 * Admin class demonstrating inheritance and polymorphism
 * Provides administrative privileges and system management
 */
public class Admin extends Person {
    private String password;
    private AdminLevel level;
    private int actionsPerformed;

    public enum AdminLevel {
        JUNIOR("Junior Admin", 1),
        SENIOR("Senior Admin", 2),
        MANAGER("Fleet Manager", 3),
        SUPERVISOR("System Supervisor", 4);

        private final String title;
        private final int accessLevel;

        AdminLevel(String title, int accessLevel) {
            this.title = title;
            this.accessLevel = accessLevel;
        }

        public String getTitle() { return title; }
        public int getAccessLevel() { return accessLevel; }
    }

    public Admin(String adminId, String name, String email, String password) {
        this(adminId, name, email, "000-0000000", password, AdminLevel.MANAGER);
    }

    public Admin(String adminId, String name, String email, String phone, String password, AdminLevel level) {
        super(adminId, name, email, phone);
        ValidationUtil.validatePassword(password);
        this.password = password;
        this.level = level;
        this.actionsPerformed = 0;
    }

    @Override
    public String getRole() {
        return level.getTitle();
    }

    @Override
    public boolean hasPermission(String permission) {
        return switch (permission.toLowerCase()) {
            case "view_vehicles", "view_bookings", "view_customers" -> level.getAccessLevel() >= 1;
            case "add_vehicle", "update_pricing", "manage_promotions" -> level.getAccessLevel() >= 2;
            case "delete_vehicle", "manage_users", "system_reports" -> level.getAccessLevel() >= 3;
            case "system_admin", "backup_data", "audit_logs" -> level.getAccessLevel() >= 4;
            default -> false;
        };
    }

    @Override
    protected boolean authenticate(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    @Override
    protected void onLoginSuccess() {
        System.out.println("Admin login successful. Welcome, " + getRole() + " " + getName());
    }

    @Override
    protected void onLogout() {
        System.out.println("Admin " + getName() + " logged out. Actions performed this session: " + actionsPerformed);
        actionsPerformed = 0;
    }

    // Admin-specific methods
    public void recordAction() {
        actionsPerformed++;
    }

    public boolean canPerformAction(String action) {
        if (!hasPermission(action)) {
            System.out.println("Access denied: " + getRole() + " does not have permission for: " + action);
            return false;
        }
        recordAction();
        return true;
    }

    // Getters
    public String getAdminId() { return getId(); }
    public AdminLevel getLevel() { return level; }
    public int getActionsPerformed() { return actionsPerformed; }

    @Override
    public String toString() {
        return String.format("%s: %s (%s) - Access Level %d", 
            getRole(), getName(), getEmail(), level.getAccessLevel());
    }
}

