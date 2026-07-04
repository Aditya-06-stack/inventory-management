package com.inventory;

import com.inventory.ui.InventoryGUI;
import javax.swing.SwingUtilities;

/**
 * Main - Application entry point
 */
public class Main {
    public static void main(String[] args) {
        // Run GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new InventoryGUI();
        });
    }
}
