package com.mycompany.app;

/**
 * Abstract base class for all person types in the system
 * Demonstrates inheritance and encapsulation principles
 */
public abstract class Person {
    protected String id;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;
    protected boolean isLoggedIn;

    public Person(String id, String name, String email, String phone, String password) {
        validateInput(id, name, email, phone, password);
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.isLoggedIn = false;
    }

    private void validateInput(String id, String name, String email, String phone, String password) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        ValidationUtil.validateName(name);
        ValidationUtil.validateEmail(email);
        ValidationUtil.validatePhone(phone);
    }

    // Abstract methods for polymorphism
    public abstract String getRole();
    public abstract boolean hasPermission(String permission);
    
    // Template method pattern
    public final boolean login(String email, String password) {
        if (authenticate(email, password)) {
            this.isLoggedIn = true;
            onLoginSuccess();
            return true;
        }
        return false;
    }
    
    protected abstract boolean authenticate(String email, String password);
    protected abstract void onLoginSuccess();

    public void logout() {
        this.isLoggedIn = false;
        onLogout();
    }
    
    protected abstract void onLogout();

    // Getters with proper encapsulation
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPassword() { return password; }
    public boolean isLoggedIn() { return isLoggedIn; }

    // Public setters with validation
    public void setName(String name) {
        ValidationUtil.validateName(name);
        this.name = name;
    }

    public void setEmail(String email) {
        ValidationUtil.validateEmail(email);
        this.email = email;
    }

    public void setPhone(String phone) {
        ValidationUtil.validatePhone(phone);
        this.phone = phone;
    }
    
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        this.password = password;
    }
    
    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    // Password verification method
    public boolean verifyPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    // Protected setters for inheritance (keeping for backward compatibility)
    protected void setNameProtected(String name) {
        ValidationUtil.validateName(name);
        this.name = name;
    }

    protected void setEmailProtected(String email) {
        ValidationUtil.validateEmail(email);
        this.email = email;
    }

    protected void setPhoneProtected(String phone) {
        ValidationUtil.validatePhone(phone);
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s: %s (%s)", getRole(), name, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
