CREATE DATABASE IF NOT EXISTS vintage_for_life_poc;
 
USE vintage_for_life_poc;

CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (name, password, email) VALUES ('admin', 'password', 'daansturm@gmail.com');