CREATE TABLE companies
(
    id          bigint       NOT NULL AUTO_INCREMENT,
    description varchar(255),
    name        varchar(255) NOT NULL,
    CONSTRAINT PK_companies PRIMARY KEY (id)
);

CREATE TABLE persons
(
    id          bigint       NOT NULL AUTO_INCREMENT,
    father_name varchar(255),
    first_name  varchar(255) NOT NULL,
    last_name   varchar(255) NOT NULL,
    company_id  bigint,
    CONSTRAINT PK_persons PRIMARY KEY (id),
    CONSTRAINT FK_persons_companies FOREIGN KEY (company_id) REFERENCES companies (id)
);

CREATE TABLE phones
(
    id           bigint       NOT NULL AUTO_INCREMENT,
    phone_number varchar(255) NOT NULL,
    company_id   bigint NOT NULL,
    CONSTRAINT PK_phones PRIMARY KEY (id),
    CONSTRAINT UQ_phones_phone_number UNIQUE KEY (phone_number),
    CONSTRAINT FK_phones_companies FOREIGN KEY (company_id) REFERENCES companies (id)
);





