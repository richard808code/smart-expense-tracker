CREATE DATABASE IF NOT EXISTS smart_expense_db;

CREATE USER IF NOT EXISTS 'appuser'@'%' IDENTIFIED BY 'apppass';
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'apppass';

GRANT ALL PRIVILEGES ON smart_expense_db.* TO 'appuser'@'%';
GRANT ALL PRIVILEGES ON smart_expense_db.* TO 'appuser'@'localhost';

FLUSH PRIVILEGES;