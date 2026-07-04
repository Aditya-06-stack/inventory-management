package com.inventory.service;

import com.inventory.model.Product;
import com.inventory.datastructure.CustomLinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * InventoryService - Business logic with sorting and searching algorithms
 * Implements DSA concepts like sorting and searching
 */
public class InventoryService {

    // LINEAR SEARCH - Find product by name
    // Time Complexity: O(n)
    public static Product linearSearchByName(List<Product> products, String name) {
        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    // LINEAR SEARCH - Find products by category
    public static List<Product> linearSearchByCategory(List<Product> products, String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return result;
    }

    // BUBBLE SORT - Sort products by name (A to Z)
    // Time Complexity: O(n^2)
    public static List<Product> bubbleSortByName(List<Product> products) {
        List<Product> result = new ArrayList<>(products);
        int n = result.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (result.get(j).getName().compareTo(result.get(j + 1).getName()) > 0) {
                    // Swap
                    Product temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }
        return result;
    }

    // BUBBLE SORT - Sort products by quantity (ascending)
    public static List<Product> bubbleSortByQuantity(List<Product> products) {
        List<Product> result = new ArrayList<>(products);
        int n = result.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (result.get(j).getQuantity() > result.get(j + 1).getQuantity()) {
                    // Swap
                    Product temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }
        return result;
    }

    // QUICK SORT - Sort products by name
    // Time Complexity: O(n log n) average case
    public static List<Product> quickSortByName(List<Product> products) {
        if (products.size() <= 1) {
            return new ArrayList<>(products);
        }

        List<Product> result = new ArrayList<>(products);
        quickSortHelper(result, 0, result.size() - 1);
        return result;
    }

    // Helper method for Quick Sort
    private static void quickSortHelper(List<Product> products, int low, int high) {
        if (low < high) {
            int pi = partition(products, low, high);
            quickSortHelper(products, low, pi - 1);
            quickSortHelper(products, pi + 1, high);
        }
    }

    // Partition method for Quick Sort
    private static int partition(List<Product> products, int low, int high) {
        Product pivot = products.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (products.get(j).getName().compareTo(pivot.getName()) < 0) {
                i++;
                // Swap
                Product temp = products.get(i);
                products.set(i, products.get(j));
                products.set(j, temp);
            }
        }
        // Swap
        Product temp = products.get(i + 1);
        products.set(i + 1, products.get(high));
        products.set(high, temp);
        return i + 1;
    }

    // Get low stock products
    public static List<Product> getLowStockProducts(List<Product> products) {
        List<Product> lowStockProducts = new ArrayList<>();
        for (Product p : products) {
            if (p.isLowStock()) {
                lowStockProducts.add(p);
            }
        }
        return lowStockProducts;
    }

    // Get total inventory value
    public static double getTotalInventoryValue(List<Product> products) {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    // Get products using custom linked list
    public static CustomLinkedList toCustomLinkedList(List<Product> products) {
        CustomLinkedList list = new CustomLinkedList();
        for (Product p : products) {
            list.addLast(p);
        }
        return list;
    }

    // Convert custom linked list to array
    public static Product[] fromCustomLinkedList(CustomLinkedList list) {
        Object[] array = list.toArray();
        Product[] products = new Product[array.length];
        for (int i = 0; i < array.length; i++) {
            products[i] = (Product) array[i];
        }
        return products;
    }
}
