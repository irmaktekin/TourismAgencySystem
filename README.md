# TourismAgencySystem
Hotel Management System

The Hotel Management System is a Java Swing application designed to facilitate the management of hotels, rooms, and reservations. It allows users to perform CRUD (Create, Read, Update, Delete) operations on hotels, manage rooms within each hotel, and make reservations for these rooms.

Features
Hotel Management:

Create new hotels with details such as name, address, hostel type information, etc.
Update existing hotel details.
Delete hotels from the system.
Room Management:

Add rooms to hotels with specific details like room type, bed count, stock_count, etc.

Reservation Management:

Make reservations for rooms in hotels for specified dates.
View existing reservations according to address, terms and guest count.
Cancel reservations if needed.

Technologies Used
Java: Core programming language.
Swing: GUI toolkit for building the application's graphical interface.
PostgreSQL: Database management system used to store hotel, room, and reservation data.
Getting Started
To run the Hotel Management System on your local machine, follow these steps:

Clone the Repository:

bash
Copy code
git clone https://github.com/your/repository.git
cd hotel-management-system
Database Setup:

Install PostgreSQL and create a database for the application.
Update the database connection details in the application configuration (src/main/resources/application.properties).
Build and Run the Application:

Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
Build and run the application from the IDE.
Using the Application:

Upon launching the application, you'll see a GUI with options to manage hotels, rooms, and reservations.
Navigate through the different functionalities using the menu or buttons provided.
Create hotels, add rooms to them, and make reservations to see how the system operates.
