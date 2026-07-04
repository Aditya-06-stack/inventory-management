# Inventory Management & Low Stock Alert System

A beginner-friendly Java Swing GUI application for managing inventory with database operations and data structure implementations.

## Features

### Core Functionality
- ✅ **Add Products**: Add new products with name, quantity, price, minimum stock, and category
- ✅ **View Products**: Display all products in a table with sortable columns
- ✅ **Update Stock**: Modify product details and quantities
- ✅ **Delete Products**: Remove products from inventory
- ✅ **Search Products**: Find products by name (Linear Search)
- ✅ **Low Stock Alerts**: Automatic alerts when stock falls below minimum threshold
- ✅ **Dashboard**: Real-time overview of total products, low stock count, and inventory value

### Data Structures & Algorithms (DSA)
- ✅ **Custom LinkedList**: Implemented from scratch for storing products
- ✅ **Linear Search**: O(n) search algorithm by product name
- ✅ **Bubble Sort**: O(n²) sorting by name and quantity
- ✅ **Quick Sort**: O(n log n) efficient sorting by name
- ✅ **CRUD Operations**: Complete Create, Read, Update, Delete functionality

### Database Management
- ✅ **File-based Storage**: Products saved in `inventory_data.txt`
- ✅ **Data Persistence**: All changes automatically saved to file
- ✅ **Data Serialization**: Products converted to string format for file storage
- ✅ **Error Handling**: Graceful error handling for file operations

## Project Structure

```
inventory-management/
├── src/
│   └── com/inventory/
│       ├── Main.java                    # Application entry point
│       ├── model/
│       │   └── Product.java             # Product data model
│       ├── database/
│       │   └── DatabaseManager.java     # CRUD operations
│       ├── datastructure/
│       │   └── CustomLinkedList.java    # LinkedList implementation
│       ├── service/
│       │   └── InventoryService.java    # Sorting and searching algorithms
│       └── ui/
│           └── InventoryGUI.java        # Swing GUI interface
└── README.md
```

## How to Run

### Step 1: Compile
Open terminal/command prompt in the project directory and run:
```bash
javac -d bin src/com/inventory/*.java src/com/inventory/*/*.java
```

### Step 2: Run
```bash
java -cp bin com.inventory.Main
```

The application window will open automatically!

## How to Use

### Add a Product
1. Fill in all fields: Product Name, Quantity, Price, Minimum Stock, Category
2. Click "Add Product" button
3. Product is saved to database automatically

### Search for Product
1. Enter product name in "Search:" field
2. Click "Search" button
3. Table shows matching product

### Sort Products
1. Select sort option from dropdown (Name, Quantity, or Quick Sort)
2. Click "Sort" button
3. Table displays sorted products

### Update Product
1. Click on a product row to select it
2. Click "Update Selected" button
3. Fields populate with product details
4. Modify values and click "Update Selected" again
5. Product is updated in database

### Delete Product
1. Click on a product row to select it
2. Click "Delete Selected" button
3. Confirm deletion
4. Product is removed from database

### View Low Stock Items
1. Click "View Low Stock" button
2. Table shows only products below minimum stock
3. Dashboard shows low stock count

### Refresh
1. Click "Refresh" button to view all products

## Technologies Used

- **Language**: Java 8+
- **GUI Framework**: Swing (JFrame, JTable, JPanel, JButton, etc.)
- **Database**: File-based storage (Text file)
- **Data Structures**: Custom LinkedList
- **Algorithms**: Linear Search, Bubble Sort, Quick Sort

## File Format

Products are stored in `inventory_data.txt` with pipe-separated values:
```
1|Laptop|10|999.99|5|Electronics
2|Mouse|50|29.99|10|Electronics
3|Desk|3|199.99|2|Furniture
```

Format: `ID|Name|Quantity|Price|MinimumStock|Category`

## Learning Outcomes

This project demonstrates:
1. **Object-Oriented Programming**: Classes, objects, encapsulation
2. **Data Structures**: Custom LinkedList implementation
3. **Algorithms**: Sorting (Bubble Sort, Quick Sort), Searching (Linear Search)
4. **File I/O**: Reading and writing data from/to files
5. **GUI Development**: Java Swing components and event handling
6. **Database Basics**: CRUD operations and data persistence
7. **Error Handling**: Try-catch blocks and validation

## Perfect For

- **Certifications**: Ideal for Java, DSA, and DBMS certifications
- **Learning**: Great for beginners to understand core programming concepts
- **Portfolio**: Demonstrates practical application development skills

## License

MIT
