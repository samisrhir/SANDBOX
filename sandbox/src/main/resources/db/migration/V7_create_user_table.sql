CREATE TABLE sa_user (
                         user_id INT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255),
                         password VARCHAR(255),
                         username VARCHAR(255),
                         phone VARCHAR(255),
                         full_name VARCHAR(255),
                         product_ids VARCHAR(255),
                         account_non_expired BOOLEAN DEFAULT true,
                         account_non_locked BOOLEAN DEFAULT true,
                         credentials_non_expired BOOLEAN DEFAULT true,
                         validity_range_date VARCHAR(255)
);