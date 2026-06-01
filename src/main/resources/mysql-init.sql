CREATE DATABASE IF NOT EXISTS tcp_product
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'tcp_user'@'localhost' IDENTIFIED BY 'tcp_pass';

GRANT ALL PRIVILEGES ON tcp_product.* TO 'tcp_user'@'localhost';

FLUSH PRIVILEGES;
