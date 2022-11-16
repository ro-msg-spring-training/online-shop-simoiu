package ro.msg.learning.shop.helper.test;

import lombok.experimental.UtilityClass;
import ro.msg.learning.shop.model.entities.*;

import java.util.List;

@UtilityClass
public class TestDataHelper {
    public static final ProductCategory laptop = ProductCategory.builder().description("Laptop category").name("Laptop").build();
    public static final ProductCategory pcComponent = ProductCategory.builder().description("Any kind of PC component").name("PC Component").build();
    public static final ProductCategory mobile = ProductCategory.builder().description("Any mobile device").name("Mobile").build();
    public static final ProductCategory networking = ProductCategory.builder().description("NAS, Routers, Range Extenders, Mesh Systems, Access Points, etc.").name("Networking").build();
    public static final ProductCategory monitors = ProductCategory.builder().description("Monitor, Keyboard, Mouse, Controller, etc.").name("Monitors & Peripherals").build();
    public static final List<ProductCategory> categories = List.of(laptop, pcComponent, mobile, networking, monitors);

    public static final Supplier asus = Supplier.builder().name("Asus").build();
    public static final Supplier gigabyte = Supplier.builder().name("Gigabyte").build();
    public static final Supplier samsung = Supplier.builder().name("Samsung").build();
    public static final Supplier msi = Supplier.builder().name("MSI").build();
    public static final Supplier lg = Supplier.builder().name("LG").build();
    public static final List<Supplier> suppliers = List.of(asus, gigabyte, samsung, msi, lg);

    public static final Location pcGarage = Location.builder().name("PCGarage").address(Address.builder().city("New York").country("USA").street("450 W 33rd St").build()).build();
    public static final Location amazon = Location.builder().name("amazon.com").address(Address.builder().city("Seattle").country("USA").street("410 Terry Ave N").build()).build();
    public static final Location amazonUK = Location.builder().name("amazon.co.uk").address(Address.builder().city("Washington").country("USA").street("1667 K Street NW").build()).build();
    public static final Location eMAG = Location.builder().name("eMAG").address(Address.builder().city("Texas").country("USA").street("2601 West 7th St.").build()).build();
    public static final List<Location> locations = List.of(pcGarage, amazon, amazonUK, eMAG);

    public static final Product rogZephyrus = Product.builder().name("ROG Zephyrus Duo 16").description("ASUS Gaming ROG Zephyrus Duo 16 GX650RX")
            .price(5000.0).weight(2.6).category(laptop).supplier(asus).imageUrl("https://dlcdnwebimgs.asus.com/gain/BB98E379-16FF-4415-8128-F695A5E1DC58/w250").build();
    public static final Product rtx3080 = Product.builder().name("ASUS RTX 3080 Ti ROG STRIX").description("ASUS GeForce RTX 3080 Ti ROG STRIX O12G LHR 12GB GDDR6X 384-bit")
            .price(2200.0).weight(1.76).category(pcComponent).supplier(asus).imageUrl("https://dlcdnwebimgs.asus.com/gain/520BAA57-81E1-4995-8421-FCA6145BB17A/w717/h525").build();
    public static final Product gtAxe11000 = Product.builder().name("ASUS ROG Rapture GT-AXE11000").description("Tri-band WiFi 6E (802.11ax), MU-MIMO, IPv6, OFDMA, AiMesh, AiProtection Pro, 4804Mbps")
            .price(500.0).weight(1.782).category(networking).supplier(asus).imageUrl("https://dlcdnwebimgs.asus.com/gain/3815E9C4-8899-4C4E-A98F-6B6D3C8C79E5/w717/h525").build();
    public static final Product rx6900 = Product.builder().name("GIGABYTE Radeon RX 6900 XT").description("GIGABYTE Radeon RX 6900 XT GAMING OC 16GB GDDR6 256-bit")
            .price(1200.0).weight(1.5).category(pcComponent).supplier(gigabyte).imageUrl("https://www.gigabyte.com/FileUpload/Global/KeyFeature/1707/innergigabyteimages/kf-img.png").build();
    public static final Product samsungS22Ultra = Product.builder().name("Galaxy S22 Ultra").description("Samsung Galaxy S22 Ultra, Dual SIM, 512GB, 12GB RAM, 5G, Phantom Black")
            .price(1400.0).weight(0.228).category(mobile).supplier(samsung).imageUrl("https://www.gigabyte.com/FileUpload/Global/KeyFeature/1707/innergigabyteimages/kf-img.png").build();
    public static final Product rtx4090 = Product.builder().name("RTX 4090 Gaming X Trio 24G").description("MSI GeForce RTX® 4090 Gaming X Trio 24G").price(2500.0).weight(2.17)
            .category(pcComponent).supplier(msi).imageUrl("https://storage-asset.msi.com/global/picture/image/feature/vga/Geforce/RTX4090/GeForce-RTX-4090-Gaming-X-Trio-24G/kv-pd.png").build();
    public static final Product lgMonitor = Product.builder().name("LG 27GP950-B UHD 144Hz").description("27' UltraGear UHD Nano IPS 1ms 144Hz HDR600 Monitor with G-SYNC® Compatibility")
            .price(700.0).weight(7.89).category(monitors).supplier(lg).imageUrl("https://www.lg.com/us/images/monitors/md08000330/gallery/medium01.jpg").build();
    public static final List<Product> products = List.of(rogZephyrus, rtx3080, gtAxe11000, rx6900, samsungS22Ultra, rtx4090, lgMonitor);

    public static final Customer admin = Customer.builder().emailAddress("admin@gmail.com").firstName("Admin").lastName("Test").username("admin")
            .password("$2a$10$utgAmjC0nVcGw9Wra9Uc4.IzN7LLR5aianzqSNs7.FvmtlCXBCihy").build();
    public static final Customer kassandra = Customer.builder().emailAddress("Kassandra.Cassidy@gmail.com").firstName("Kassandra").lastName("Cassidy").username("kcassidy")
            .password("$2a$10$a376KFz/6NMZUT.MWfmvp.0FOR8.CbLkQZOKvwY4goiGTjLxAo/XO").build();
    public static final List<Customer> users = List.of(admin, kassandra);

    public static final List<Stock> stocks = List.of(
            Stock.builder().product(rtx4090).location(pcGarage).quantity(5).build(),
            Stock.builder().product(rtx4090).location(amazonUK).quantity(10).build(),
            Stock.builder().product(rtx4090).location(amazon).quantity(6).build(),
            Stock.builder().product(rtx3080).location(pcGarage).quantity(1).build(),
            Stock.builder().product(rtx3080).location(eMAG).quantity(2).build(),
            Stock.builder().product(rtx3080).location(amazonUK).quantity(20).build(),
            Stock.builder().product(rtx3080).location(amazon).quantity(25).build(),
            Stock.builder().product(rogZephyrus).location(eMAG).quantity(9).build(),
            Stock.builder().product(rogZephyrus).location(amazon).quantity(8).build(),
            Stock.builder().product(rx6900).location(pcGarage).quantity(6).build(),
            Stock.builder().product(rx6900).location(amazonUK).quantity(5).build(),
            Stock.builder().product(rx6900).location(amazon).quantity(3).build(),
            Stock.builder().product(gtAxe11000).location(eMAG).quantity(2).build(),
            Stock.builder().product(gtAxe11000).location(amazonUK).quantity(9).build(),
            Stock.builder().product(lgMonitor).location(eMAG).quantity(4).build(),
            Stock.builder().product(lgMonitor).location(amazon).quantity(12).build(),
            Stock.builder().product(samsungS22Ultra).location(pcGarage).quantity(34).build(),
            Stock.builder().product(samsungS22Ultra).location(eMAG).quantity(55).build(),
            Stock.builder().product(samsungS22Ultra).location(amazonUK).quantity(82).build(),
            Stock.builder().product(samsungS22Ultra).location(amazon).quantity(142).build()
    );
}
