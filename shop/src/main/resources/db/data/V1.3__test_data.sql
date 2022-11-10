insert into PRODUCT_CATEGORY (DESCRIPTION, NAME)
values ('Any laptop, ultrabook, notebook, or accessories', 'Laptop'),
       ('Any kind of PC component, including Motherboard, CPU, GPU, Memories, SSD, HDD, Power Supply, etc.', 'PC Component'),
       ('NAS, Routers, Range Extenders, Mesh Systems, Access Points, etc.', 'Networking'),
       ('Monitor, Keyboard, Mouse, Controller, etc.', 'Monitors & Peripherals'),
       ('Any mobile device, including Smartphone, Tablets, Smartwatches, etc.', 'Mobile');


insert into SUPPLIER (NAME)
values ('Samsung'),
       ('Asus'),
       ('MSI'),
       ('Gigabyte'),
       ('LG');


insert into LOCATION (CITY, COUNTRY, STREET, NAME)
values ('New York', 'USA', '450 W 33rd St', 'PCGarage'),
       ('Texas', 'USA', '2601 West 7th St.', 'eMAG'),
       ('Washington', 'USA', '1667 K Street NW', 'amazon.co.uk'),
       ('Seattle', 'USA', '410 Terry Ave N', 'amazon.com');

insert into PRODUCT (NAME, DESCRIPTION, PRICE, WEIGHT, CATEGORY_ID, SUPPLIER_ID, IMAGE_URL)
values ('MSI GeForce RTX® 4090 Gaming X Trio 24G', 'MSI GeForce RTX 4090 Gaming X Trio 24G', 2500, 2.17, 2, 3,
        'https://storage-asset.msi.com/global/picture/image/feature/vga/Geforce/RTX4090/GeForce-RTX-4090-Gaming-X-Trio-24G/kv-pd.png'),
       ('ASUS GeForce RTX 3080 Ti ROG STRIX', 'ASUS GeForce RTX 3080 Ti ROG STRIX O12G LHR 12GB GDDR6X 384-bit', 2200, 1.76, 2, 2,
        'https://dlcdnwebimgs.asus.com/gain/520BAA57-81E1-4995-8421-FCA6145BB17A/w717/h525'),
       ('ASUS Gaming ROG Zephyrus Duo 16 GX650RX',
        'ASUS Gaming ROG Zephyrus Duo 16 GX650RX, QHD+ 165Hz, Procesor AMD Ryzen™ 9 6900HX (16M Cache, up to 4.9 GHz), 64GB DDR5, 2x 2TB SSD, GeForce RTX 3080 Ti 16GB', 5000, 2.6, 1, 2,
        'https://dlcdnwebimgs.asus.com/gain/BB98E379-16FF-4415-8128-F695A5E1DC58/w250'),
       ('GIGABYTE Radeon RX 6900 XT', 'GIGABYTE Radeon RX 6900 XT GAMING OC 16GB GDDR6 256-bit', 1200, 1.5, 2, 4,
        'https://www.gigabyte.com/FileUpload/Global/KeyFeature/1707/innergigabyteimages/kf-img.png'),
       ('ASUS ROG Rapture GT-AXE11000', 'Router de Gaming Wireless ASUS ROG Rapture GT-AXE11000, Tri-band WiFi 6E (802.11ax), MU-MIMO, IPv6, OFDMA, AiMesh, AiProtection Pro, 4804Mbps', 500, 1.782,
        3, 2, 'https://dlcdnwebimgs.asus.com/gain/3815E9C4-8899-4C4E-A98F-6B6D3C8C79E5/w717/h525'),
       ('LG 27GP950-B UHD 144Hz', '27'' UltraGear UHD Nano IPS 1ms 144Hz HDR600 Monitor with G-SYNC® Compatibility', 700, 7.89, 4, 5,
        'https://www.lg.com/us/images/monitors/md08000330/gallery/medium01.jpg'),
       ('Galaxy S22 Ultra', 'Samsung Galaxy S22 Ultra, Dual SIM, 512GB, 12GB RAM, 5G, Phantom Black', 1400, 0.228, 5, 1,
        'https://images.samsung.com/ro/smartphones/galaxy-s22-ultra/buy/03_color-selection/01_device-images/s22ultra_colorselection_burgundy_mo.jpg?imwidth=480');



insert into STOCK (QUANTITY, PRODUCT_ID, LOCATION_ID)
values (5, 1, 1),
       (10, 1, 3),
       (6, 1, 4),
       (1, 2, 1),
       (2, 2, 2),
       (20, 2, 3),
       (25, 2, 4),
       (9, 3, 2),
       (8, 3, 4),
       (6, 4, 1),
       (5, 4, 3),
       (3, 4, 4),
       (2, 5, 2),
       (9, 5, 3),
       (4, 6, 2),
       (12, 6, 4),
       (34, 7, 1),
       (55, 7, 2),
       (82, 7, 3),
       (142, 7, 4);


insert into CUSTOMER (EMAIL_ADDRESS, FIRST_NAME, LAST_NAME, USERNAME, PASSWORD)
values ('Kassandra.Cassidy@gmail.com', 'Kassandra', 'Cassidy', 'kcassidy', '$2a$10$a376KFz/6NMZUT.MWfmvp.0FOR8.CbLkQZOKvwY4goiGTjLxAo/XO'),
       ('tamyagomes@yahoo.com', 'Tamya', 'Gomes', 'gomestamya', '$2a$10$txax6tGOAoQS7s4XAnUuIO1Z9mxCzGVsYG/X4BDu4Zoe8U/CAJlUS'),
       ('hillevili@outlook.com', 'Hillevi', 'Li', 'hillevili', '$2a$10$qZ4aBpcd68h.Zr4ZCGIMlu/Ev6L5.kjnK8nTcEc4qzTpgRXJ1jnBi'),
       ('kaja.flora.alessandri@gmail.com', 'Kaja', 'Alessandri', 'Kfalessandri', '$2a$10$duAJukBT.17c0wCiISoNI.OOitkFRfjurvx9ED4JvnAJhOLYXWcUS'),
       ('admin@gmail.com', 'Admin', 'Test', 'admin', '$2a$10$utgAmjC0nVcGw9Wra9Uc4.IzN7LLR5aianzqSNs7.FvmtlCXBCihy');

insert into REVENUE (date, sum, LOCATION_ID)
values ('2022-05-01', 324941, 1),
       ('2022-05-01', 1256321, 2),
       ('2022-05-01', 125580941, 3),
       ('2022-05-01', 304580941, 4);



