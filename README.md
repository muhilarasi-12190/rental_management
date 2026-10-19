# Rental Resource Management System (RRMS)

## 1. Project Overview
The Rental Resource Management System is a Java-based application used to manage rental resources, customers, and rental transactions.

## 2. Technologies Used
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Java Swing
- REST API
- Maven

## 3. Main Modules

### Resource Management
- Add resources
- View resources
- Update resource details
- Delete resources

### Customer Management
- Register customers
- View customer details
- Update customer details
- Delete customers

### Rental Management
- Create rental records
- View rental records
- Update return dates
- Delete rental records

## 4. System Architecture
- Model: Represents resources, customers, and rentals.
- Repository: Communicates with the database using JPA.
- Service: Handles application operations.
- Controller: Exposes REST API endpoints.
- GUI: Provides a desktop interface using Java Swing.
- Database: MySQL stores application data.

## 5. API Endpoints

| Module | Method | Endpoint | Operation |
|---|---|---|---|
| Resources | POST | /resources | Add resource |
| Resources | GET | /resources | View resources |
| Resources | PUT | /resources/{id} | Update resource |
| Resources | DELETE | /resources/{id} | Delete resource |
| Customers | POST | /customers | Add customer |
| Customers | GET | /customers | View customers |
| Customers | PUT | /customers/{id} | Update customer |
| Customers | DELETE | /customers/{id} | Delete customer |
| Rentals | POST | /rentals | Add rental |
| Rentals | GET | /rentals | View rentals |
| Rentals | PUT | /rentals/{id} | Update rental |
| Rentals | DELETE | /rentals/{id} | Delete rental |

## 6. Database
Database name: rental_resource_management

Spring Data JPA and Hibernate manage the database tables.

## 7. How to Run
1. Start the MySQL server.
2. Configure database credentials in application.properties.
3. Run RentalManagementApplication.java.
4. Run RentalManagementGUI.java.
5. Use the desktop GUI to manage resources, customers, and rentals.

## 8. Validation
- Resource name and category are required.
- Rental price cannot be negative.
- Customer phone number must contain 10 digits.
- Rental records require a customer, resource, and rental date.