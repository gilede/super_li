Supplier Module - Inventory Management System

Project Overview

This project is part of a larger Inventory Management System designed to facilitate supplier management, inventory tracking, and automated order processing. The Supplier Module is responsible for handling supplier data, managing product supplies, and optimizing order fulfillment based on availability and pricing.

Features

Supplier Management: Add, update, and remove supplier details.

Order Processing: Generate automated orders based on inventory shortages.

Price Optimization: Select the best supplier based on pricing and availability.

Database Integration: Uses an SQLite database for persistent storage.

Unit Testing: Includes automated unit tests for core functionalities.

Installation and Setup

Prerequisites

Java (JDK 17 or later)

SQLite database

Maven (for dependency management)

Steps to Run the Application

Clone the repository:

git clone https://github.com/gilede/super_li/tree/main
cd project-directory

Build the project using Maven:

mvn clean install

Run the application:

java -jar release/adss2024_v02.jar

Directory Structure

project-directory/
│── src/              # Source code
│── docs/             # Documentation and diagrams
│── release/          # Compiled JAR file
│── test/             # Unit tests
│── README.md         # This file

Database Schema

The module interacts with an SQLite database containing the following main tables:

Suppliers (id, name, contact_info, rating)

Products (id, name, supplier_id, price, stock_level)

Orders (id, supplier_id, date, status)

Running Tests

To execute the unit tests, run:

mvn test

Usage Instructions

Add new suppliers through the user interface or API.

Automatically generate orders when stock is below the minimum threshold.

Optimize orders by selecting the supplier with the best price and availability.

Authors

Gil Eden - gilede

License

This project is for educational purposes and follows the course guidelines.

Additional Notes

Ensure the database is properly initialized before running the system.

All transactions are logged for debugging and traceability.

