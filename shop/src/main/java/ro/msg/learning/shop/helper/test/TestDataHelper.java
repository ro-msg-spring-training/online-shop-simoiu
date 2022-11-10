package ro.msg.learning.shop.helper.test;

import lombok.experimental.UtilityClass;
import ro.msg.learning.shop.model.entities.*;

import java.util.List;

@UtilityClass
public class TestDataHelper {
    public static final ProductCategory laptop = ProductCategory.builder().id(1).description("Laptop category").name("Laptop").build();
    public static final ProductCategory pcComponent = ProductCategory.builder().id(1).description("Any kind of PC component").name("PC Component").build();
    public static final ProductCategory mobile = ProductCategory.builder().id(1).description("Any mobile device").name("Mobile").build();
    public static final Supplier asus = Supplier.builder().name("Asus").build();
    public static final Supplier gigabyte = Supplier.builder().name("Gigabyte").build();
    public static final Supplier samsung = Supplier.builder().name("Samsung").build();
    public static final Location pcGarage = Location.builder().name("PCGarage").address(Address.builder().city("New York").country("USA").street("450 W 33rd St").build()).build();
    public static final Location amazon = Location.builder().name("amazon.com").address(Address.builder().city("Seattle").country("USA").street("410 Terry Ave N").build()).build();
    public static final Product rogZephyrus = Product.builder().name("ROG Zephyrus Duo 16").description("ASUS Gaming ROG Zephyrus Duo 16 GX650RX")
            .price(5000.0).weight(2.6).category(laptop).supplier(asus).imageUrl("https://dlcdnwebimgs.asus.com/gain/BB98E379-16FF-4415-8128-F695A5E1DC58/w250").build();
    public static final Product rtx3080 = Product.builder().name("ASUS RTX 3080 Ti ROG STRIX").description("ASUS GeForce RTX 3080 Ti ROG STRIX O12G LHR 12GB GDDR6X 384-bit")
            .price(2200.0).weight(1.76).category(pcComponent).supplier(asus).imageUrl("https://dlcdnwebimgs.asus.com/gain/520BAA57-81E1-4995-8421-FCA6145BB17A/w717/h525").build();
    public static final Product rx6900 = Product.builder().name("GIGABYTE Radeon RX 6900 XT").description("GIGABYTE Radeon RX 6900 XT GAMING OC 16GB GDDR6 256-bit")
            .price(1200.0).weight(1.5).category(pcComponent).supplier(gigabyte).imageUrl("https://www.gigabyte.com/FileUpload/Global/KeyFeature/1707/innergigabyteimages/kf-img.png").build();
    public static final Product samsungS22Ultra = Product.builder().name("Galaxy S22 Ultra").description("Samsung Galaxy S22 Ultra, Dual SIM, 512GB, 12GB RAM, 5G, Phantom Black")
            .price(1400.0).weight(0.228).category(mobile).supplier(samsung).imageUrl("https://www.gigabyte.com/FileUpload/Global/KeyFeature/1707/innergigabyteimages/kf-img.png").build();
    public static final Customer admin = Customer.builder().emailAddress("admin@gmail.com").firstName("Admin").lastName("Test").username("admin").password("admin").build();
    public static final List<Stock> stocks = List.of(
            Stock.builder().product(rogZephyrus).location(amazon).quantity(6).build(),
            Stock.builder().product(rtx3080).location(pcGarage).quantity(19).build(),
            Stock.builder().product(rtx3080).location(amazon).quantity(13).build(),
            Stock.builder().product(rx6900).location(pcGarage).quantity(9).build(),
            Stock.builder().product(rx6900).location(amazon).quantity(8).build(),
            Stock.builder().product(samsungS22Ultra).location(amazon).quantity(11).build()
    );
}
