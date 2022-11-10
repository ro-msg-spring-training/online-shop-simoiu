CREATE TABLE customer
(
    id            INT AUTO_INCREMENT NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    username      VARCHAR(255),
    password      VARCHAR(255),
    email_address VARCHAR(255),
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE location
(
    id      INT AUTO_INCREMENT NOT NULL,
    name    VARCHAR(255),
    country VARCHAR(255),
    city    VARCHAR(255),
    county  VARCHAR(255),
    street  VARCHAR(255),
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    id          INT AUTO_INCREMENT NOT NULL,
    customer_id INT,
    created_at  TIMESTAMP,
    country     VARCHAR(255),
    city        VARCHAR(255),
    county      VARCHAR(255),
    street      VARCHAR(255),
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_locations
(
    locations_id INT NOT NULL,
    order_id     INT NOT NULL,
    CONSTRAINT pk_order_locations PRIMARY KEY (locations_id, order_id)
);

CREATE TABLE order_detail
(
    id         INT AUTO_INCREMENT NOT NULL,
    order_id   INT,
    product_id INT,
    quantity   INT                NOT NULL,
    CONSTRAINT pk_order_detail PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          INT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    price       DOUBLE             NOT NULL,
    weight      DOUBLE             NOT NULL,
    image_url   VARCHAR(255),
    category_id INT,
    supplier_id INT,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_category
(
    id          INT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_product_category PRIMARY KEY (id)
);

CREATE TABLE revenue
(
    id          INT AUTO_INCREMENT NOT NULL,
    location_id INT,
    date        date,
    sum         DECIMAL,
    CONSTRAINT pk_revenue PRIMARY KEY (id)
);

CREATE TABLE stock
(
    id          INT AUTO_INCREMENT NOT NULL,
    product_id  INT,
    location_id INT,
    quantity    INT                NOT NULL,
    CONSTRAINT pk_stock PRIMARY KEY (id)
);

CREATE TABLE supplier
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_supplier PRIMARY KEY (id)
);

ALTER TABLE supplier
    ADD CONSTRAINT uc_supplier_name UNIQUE (name);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_ORDER FOREIGN KEY (order_id) REFERENCES `order` (id);

ALTER TABLE order_detail
    ADD CONSTRAINT FK_ORDER_DETAIL_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE `order`
    ADD CONSTRAINT FK_ORDER_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES product_category (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE revenue
    ADD CONSTRAINT FK_REVENUE_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE stock
    ADD CONSTRAINT FK_STOCK_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);

ALTER TABLE stock
    ADD CONSTRAINT FK_STOCK_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE order_locations
    ADD CONSTRAINT fk_ordloc_on_location FOREIGN KEY (locations_id) REFERENCES location (id);

ALTER TABLE order_locations
    ADD CONSTRAINT fk_ordloc_on_order FOREIGN KEY (order_id) REFERENCES `order` (id);