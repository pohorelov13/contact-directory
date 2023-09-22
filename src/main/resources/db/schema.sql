CREATE TABLE companies
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE persons
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `father_name` varchar(255) DEFAULT NULL,
    `first_name`  varchar(255) NOT NULL,
    `last_name`   varchar(255) NOT NULL,
    `company_id`  bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`company_id`) REFERENCES companies (`id`)
);

CREATE TABLE phones
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `phone_number` varchar(255) NOT NULL,
    `company_id`   bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY (`phone_number`),
    FOREIGN KEY (`company_id`) REFERENCES companies (`id`)
);





