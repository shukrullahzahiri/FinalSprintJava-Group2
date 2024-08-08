-- Users table
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL
);

-- Products table
CREATE TABLE Products (
    product_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    seller_id INT REFERENCES Users(user_id)
);

-- Sample data for testing
INSERT INTO Users (username, password, email, role) VALUES 
('admin', '$2a$12$KIXmcz1FZzvZbRoH6DFg.ei/BsoXt/o7uCOrzhA5e6v2/WfRXJtkW', 'admin@example.com', 'admin'); -- Password: admin123
