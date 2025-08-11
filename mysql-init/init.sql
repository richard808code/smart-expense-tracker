-- Create the database if it does not exist yet
CREATE DATABASE IF NOT EXISTS smart_expense_db;

-- Create database user 'appuser' with password 'apppass' for any host
CREATE USER IF NOT EXISTS 'appuser'@'%' IDENTIFIED BY 'apppass';

-- Create database user 'appuser' with password 'apppass' for localhost only
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'apppass';

-- Grant all privileges on the smart_expense_db database to 'appuser' from any host
GRANT ALL PRIVILEGES ON smart_expense_db.* TO 'appuser'@'%';

-- Grant all privileges on the smart_expense_db database to 'appuser' from localhost
GRANT ALL PRIVILEGES ON smart_expense_db.* TO 'appuser'@'localhost';

-- Apply the privilege changes immediately
FLUSH PRIVILEGES;