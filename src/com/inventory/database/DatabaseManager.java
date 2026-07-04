package com.inventory.database;

import com.inventory.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager - File-based CRUD operations
 * Handles reading and writing product data to a file
 */
public class DatabaseManager {
    private static final String FILE_NAME = "inventory_data.txt";
    private int nextId = 1;

    // Constructor
    public DatabaseManager() {
        initializeDatabase();
    }

    // Initialize database file if it doesn't exist
    private void initializeDatabase() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Database file created: " + FILE_NAME);
            } catch (IOException e) {
                System.out.println("Error creating database file: " + e.getMessage());
            }
        }
        updateNextId();
    }

    // Update next ID based on existing products
    private void updateNextId() {
        List<Product> products = getAllProducts();
        nextId = 1;
        for (Product p : products) {
            if (p.getId() >= nextId) {
                nextId = p.getId() + 1;
            }
        }
    }

    // CREATE - Add a new product
    public boolean addProduct(String name, int quantity, double price, int minimumStock, String category) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            Product product = new Product(nextId++, name, quantity, price, minimumStock, category);
            bw.write(product.toString());
            bw.newLine();
            bw.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false;
        }
    }

    // READ - Get all products
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Product product = Product.fromString(line);
                    if (product != null) {
                        products.add(product);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products: " + e.getMessage());
        }
        return products;
    }

    // READ - Get product by ID
    public Product getProductById(int id) {
        List<Product> products = getAllProducts();
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // UPDATE - Update product details
    public boolean updateProduct(int id, String name, int quantity, double price, int minimumStock, String category) {
        List<Product> products = getAllProducts();
        boolean found = false;

        for (Product p : products) {
            if (p.getId() == id) {
                p.setName(name);
                p.setQuantity(quantity);
                p.setPrice(price);
                p.setMinimumStock(minimumStock);
                p.setCategory(category);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllProducts(products);
            return true;
        }
        return false;
    }

    // UPDATE - Update stock quantity
    public boolean updateStock(int id, int quantity) {
        List<Product> products = getAllProducts();
        boolean found = false;

        for (Product p : products) {
            if (p.getId() == id) {
                p.setQuantity(quantity);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllProducts(products);
            return true;
        }
        return false;
    }

    // DELETE - Remove product by ID
    public boolean deleteProduct(int id) {
        List<Product> products = getAllProducts();
        boolean found = products.removeIf(p -> p.getId() == id);

        if (found) {
            saveAllProducts(products);
            return true;
        }
        return false;
    }

    // Helper method - Save all products to file
    private void saveAllProducts(List<Product> products) {
        try (FileWriter fw = new FileWriter(FILE_NAME);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Product p : products) {
                bw.write(p.toString());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    // Get count of products
    public int getProductCount() {
        return getAllProducts().size();
    }

    // Get low stock products count
    public int getLowStockCount() {
        List<Product> products = getAllProducts();
        int count = 0;
        for (Product p : products) {
            if (p.isLowStock()) {
                count++;
            }
        }
        return count;
    }
}
