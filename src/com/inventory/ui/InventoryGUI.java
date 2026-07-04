package com.inventory.ui;

import com.inventory.model.Product;
import com.inventory.database.DatabaseManager;
import com.inventory.service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * InventoryGUI - Main GUI using Java Swing
 * Provides user interface for inventory management
 */
public class InventoryGUI extends JFrame {
    private DatabaseManager dbManager;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, priceField, quantityField, minStockField, categoryField, searchField;
    private JLabel lowStockLabel, totalProductsLabel, totalValueLabel;
    private JComboBox<String> sortCombo;

    // Constructor
    public InventoryGUI() {
        dbManager = new DatabaseManager();
        initializeUI();
        refreshTable();
    }

    // Initialize UI components
    private void initializeUI() {
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top panel - Dashboard
        JPanel dashboardPanel = createDashboardPanel();
        mainPanel.add(dashboardPanel, BorderLayout.NORTH);

        // Middle panel - Input form
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Bottom panel - Table and buttons
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Create dashboard panel
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Dashboard"));

        // Total Products
        totalProductsLabel = new JLabel("Total Products: 0");
        totalProductsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(totalProductsLabel);

        // Low Stock Alert
        lowStockLabel = new JLabel("Low Stock Items: 0");
        lowStockLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lowStockLabel.setForeground(Color.RED);
        panel.add(lowStockLabel);

        // Total Inventory Value
        totalValueLabel = new JLabel("Total Inventory Value: $0.00");
        totalValueLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(totalValueLabel);

        return panel;
    }

    // Create form panel
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Add/Update Product"));

        // Name field
        panel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        // Quantity field
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        // Price field
        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        // Minimum Stock field
        panel.add(new JLabel("Minimum Stock:"));
        minStockField = new JTextField();
        panel.add(minStockField);

        // Category field
        panel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        panel.add(categoryField);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(e -> addProduct());
        buttonPanel.add(addButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        buttonPanel.add(clearButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(panel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        return rightPanel;
    }

    // Create bottom panel with table and controls
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Search and sort panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Search & Sort"));

        controlPanel.add(new JLabel("Search:"));
        searchField = new JTextField(15);
        controlPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());
        controlPanel.add(searchButton);

        controlPanel.add(new JLabel("Sort By:"));
        sortCombo = new JComboBox<>(new String[]{"Name (A-Z)", "Quantity (Low to High)", "Quick Sort"});
        controlPanel.add(sortCombo);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> sortProducts());
        controlPanel.add(sortButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshTable());
        controlPanel.add(refreshButton);

        panel.add(controlPanel, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Products"));

        String[] columns = {"ID", "Name", "Quantity", "Price", "Min Stock", "Category", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Action buttons panel
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton updateButton = new JButton("Update Selected");
        updateButton.addActionListener(e -> updateSelected());
        actionPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(e -> deleteSelected());
        actionPanel.add(deleteButton);

        JButton viewLowStockButton = new JButton("View Low Stock");
        viewLowStockButton.addActionListener(e -> viewLowStock());
        actionPanel.add(viewLowStockButton);

        tablePanel.add(actionPanel, BorderLayout.SOUTH);
        panel.add(tablePanel, BorderLayout.CENTER);

        return panel;
    }

    // Add product
    private void addProduct() {
        try {
            String name = nameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            int minStock = Integer.parseInt(minStockField.getText());
            String category = categoryField.getText();

            if (name.isEmpty() || category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dbManager.addProduct(name, quantity, price, minStock, category)) {
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding product!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete selected product
    private void deleteSelected() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (option == JOptionPane.YES_OPTION) {
            if (dbManager.deleteProduct(id)) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshTable();
            }
        }
    }

    // Update selected product
    private void updateSelected() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Product product = dbManager.getProductById(id);

        if (product != null) {
            nameField.setText(product.getName());
            quantityField.setText(String.valueOf(product.getQuantity()));
            priceField.setText(String.valueOf(product.getPrice()));
            minStockField.setText(String.valueOf(product.getMinimumStock()));
            categoryField.setText(product.getCategory());

            int option = JOptionPane.showConfirmDialog(this, "Update this product?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    String name = nameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    int minStock = Integer.parseInt(minStockField.getText());
                    String category = categoryField.getText();

                    if (dbManager.updateProduct(id, name, quantity, price, minStock, category)) {
                        JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                        refreshTable();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // Search products
    private void searchProducts() {
        String searchTerm = searchField.getText();
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }

        List<Product> allProducts = dbManager.getAllProducts();
        Product found = InventoryService.linearSearchByName(allProducts, searchTerm);

        tableModel.setRowCount(0);
        if (found != null) {
            addRowToTable(found);
        } else {
            JOptionPane.showMessageDialog(this, "Product not found!", "Search", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Sort products
    private void sortProducts() {
        List<Product> products = dbManager.getAllProducts();
        List<Product> sorted = new ArrayList<>();

        int selectedIndex = sortCombo.getSelectedIndex();
        switch (selectedIndex) {
            case 0: // Name sort
                sorted = InventoryService.bubbleSortByName(products);
                break;
            case 1: // Quantity sort
                sorted = InventoryService.bubbleSortByQuantity(products);
                break;
            case 2: // Quick sort
                sorted = InventoryService.quickSortByName(products);
                break;
        }

        tableModel.setRowCount(0);
        for (Product p : sorted) {
            addRowToTable(p);
        }
    }

    // View low stock products
    private void viewLowStock() {
        List<Product> allProducts = dbManager.getAllProducts();
        List<Product> lowStockProducts = InventoryService.getLowStockProducts(allProducts);

        tableModel.setRowCount(0);
        for (Product p : lowStockProducts) {
            addRowToTable(p);
        }

        JOptionPane.showMessageDialog(this, "Low Stock Items: " + lowStockProducts.size(), "Low Stock Alert", JOptionPane.WARNING_MESSAGE);
    }

    // Refresh table with all products
    private void refreshTable() {
        List<Product> products = dbManager.getAllProducts();
        tableModel.setRowCount(0);

        for (Product p : products) {
            addRowToTable(p);
        }

        // Update dashboard
        updateDashboard();
    }

    // Add row to table
    private void addRowToTable(Product p) {
        String status = p.isLowStock() ? "LOW STOCK" : "OK";
        tableModel.addRow(new Object[]{
            p.getId(),
            p.getName(),
            p.getQuantity(),
            String.format("$%.2f", p.getPrice()),
            p.getMinimumStock(),
            p.getCategory(),
            status
        });
    }

    // Update dashboard labels
    private void updateDashboard() {
        List<Product> products = dbManager.getAllProducts();
        int totalCount = products.size();
        int lowStockCount = dbManager.getLowStockCount();
        double totalValue = InventoryService.getTotalInventoryValue(products);

        totalProductsLabel.setText("Total Products: " + totalCount);
        lowStockLabel.setText("Low Stock Items: " + lowStockCount);
        totalValueLabel.setText(String.format("Total Inventory Value: $%.2f", totalValue));
    }

    // Clear input fields
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        minStockField.setText("");
        categoryField.setText("");
        searchField.setText("");
    }
}
