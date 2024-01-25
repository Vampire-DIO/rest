-- home_rest.`order` definition

CREATE TABLE `order` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(100) DEFAULT NULL,
                         `description` text,
                         `create_time` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- home_rest.picture definition

CREATE TABLE `picture` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `order_id` int NOT NULL,
                           `content` text,
                           `create_time` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;