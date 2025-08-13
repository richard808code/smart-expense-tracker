# Smart Expense Tracker

A simple application to track personal expenses within specific budgets and categories.  

For example: **Budget:** Weekend BBQ, **Category:** Food

## Features

- Each budget has a limit. Every transaction counts towards reaching that limit.
- Negative transactions (refunds or corrections) decrease the amount counted toward the budget.
- You can create multiple users and switch between them.
- Each user has their own categories, budgets, and transactions.
- Users, categories, budgets, and transactions can be **added, edited, and deleted**.

## Screenshots

### Dashboard
![Dashboard](screenshots/dashboard.png)

### Users
![Users](screenshots/users.png)

### Categories
![Categories](screenshots/categories.png)

### Budgets
![Budgets](screenshots/budgets.png)

### Transactions
![Transactions](screenshots/transactions.png)

## Technologies

- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, JavaScript
- **Database:** MySQL
- **Tools:** Maven, Docker

## Project Structure

- **`src/`**: Source code of the application
- **`mysql-init/`**: Database initialization scripts
- **`docker-compose.yml`**: Configuration for running the application with Docker
- **`pom.xml`**: Maven project configuration

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/richard808code/smart-expense-tracker.git
   
2.	**Open it in an IDE**

   •	You should be prompted to connect to the database

   •	Check application.yml for the password
   
3. Run the app via:
   SmartExpenseTrackerApplication class


4. Open it in you browser at:
   [Smart Expense Tracker](http://localhost:63342/smart-expense-tracker/static/startbootstrap-sb-admin-2-gh-pages/index.html?_ijt=64um946662jl3tu6mc1rgrmlb6&_ij_reload=RELOAD_ON_SAVE)


5. Edit the test User to your liking.

## License

This project is licensed under the MIT License.