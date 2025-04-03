# Food Donation Platform

This Java-based Food Donation Platform is designed to connect donors, charities, and administrators to facilitate the donation and distribution of food items. Built with JavaFX and MySQL, the system enables secure logins, donation management, user roles, and administrative control, providing a complete solution for food donation logistics.

---

## Features

### General
- User-friendly JavaFX GUI
- Secure login with password hashing (SHA-256)
- Role-based dashboards (Admin, Donor, Charity Staff)

### Donor
- Submit food donations
- View donation history

### Charity Staff
- Request items from available donations
- Track request status (Pending, Approved, Rejected)

### Administrator
- View and manage users
- Approve or reject user accounts
- Update user roles and statuses
- Delete user accounts
- View all donations and charity requests

---

## Technologies Used

- Java 17
- JavaFX
- MySQL
- JDBC
- Git (Version Control)

---

## Setup Instructions

Follow these steps to set up and run the project locally:

### 1. Clone the Repository
```bash
git clone https://github.com/Devarshi2323/FoodDonationPlatform.git

---

## 2. Open in IDE

Open the project folder in an IDE like **Eclipse** or **IntelliJ IDEA**.

---

## 3. Install Dependencies

Ensure that the following are installed:

- **Java 17+**
- **JavaFX SDK** (configured in project libraries)
- **MySQL Server** and **MySQL Workbench**

---

## 4. Configure Database

1. Create a MySQL database named `food_donation`.
2. Execute the provided SQL schema file (if available), or manually create the required tables such as:
   - `users`
   - `donations`
   - `charity_requests`
   - `orders`
   - `returns`
   - `repairs`
3. Use the following credentials (or update `DBUtil.java`):
   - **Username:** `root`
   - **Password:** `root123`

---

## 5. Run the Application

Launch the `Main.java` file from your IDE to start the application.

---

## Git & Collaboration Notes

- **Version Control:** Git is used to track changes over time.
- **Team Collaboration:** Developers can create branches to work independently and merge them into the main branch after testing or review.
- **Backup:** All changes are stored in a remote GitHub repository for recovery and collaboration.

---

## Folder Structure

FoodDonationPlatform/
├── src/
│   └── com.fooddonation/
│       ├── controller/
│       ├── model/
│       ├── utils/
│       └── Main.java
├── resources/
│   └── com.fooddonation.view/
├── README.md
└── .gitignore

---

## Notes

- **The platform uses SHA-256 hashing for password security.**
- **Email uniqueness is enforced in the users table to prevent duplicate accounts.**
- **Admin can approve or reject new user registrations.**

## License

- **This project is developed for academic purposes and is open for further development or improvement by future teams.**
