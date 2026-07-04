package com.inventory.model;

/**
 * Product class to store product information
 * Simple data model for inventory items
 */
public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private int minimumStock;
    private String category;

    // Constructor
    public Product(int id, String name, int quantity, double price, int minimumStock, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.minimumStock = minimumStock;
        this.category = category;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Check if product is low on stock
    public boolean isLowStock() {
        return quantity <= minimumStock;
    }

    // Convert to string format for file storage
    @Override
    public String toString() {
        return id + "|" + name + "|" + quantity + "|" + price + "|" + minimumStock + "|" + category;
    }

    // Create Product from string format (file storage)
    public static Product fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 6) {
            return new Product(
                Integer.parseInt(parts[0]),
                parts[1],
                Integer.parseInt(parts[2]),
                Double.parseDouble(parts[3]),
                Integer.parseInt(parts[4]),
                parts[5]
            );
        }
        return null;
    }
}
