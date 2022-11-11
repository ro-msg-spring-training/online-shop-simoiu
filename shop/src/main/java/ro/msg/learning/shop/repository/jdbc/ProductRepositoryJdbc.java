package ro.msg.learning.shop.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.Product;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.repository.SupplierRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static ro.msg.learning.shop.helper.MapperHelper.getIdFromEntity;

@Repository
public class ProductRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;
    private final ProductMapper productMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final StockRepository stockRepository;

    public ProductRepositoryJdbc(JdbcTemplate jdbcTemplate, ProductMapper productMapper, StockRepository stockRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.productMapper = productMapper;
        this.stockRepository = stockRepository;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("PRODUCT").usingGeneratedKeyColumns("ID");
    }

    @SneakyThrows
    public List<Product> findAll() {
        var sql = "select * from PRODUCT";
        return jdbcTemplate.query(sql, productMapper);
    }

    public Optional<Product> findById(Integer id) {
        var sql = "select * from PRODUCT where ID = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, productMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Integer id) {
        deleteStocksContainingProductId(id);
        var sql = "delete from PRODUCT where ID = ?";
        jdbcTemplate.update(sql, id);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            createProduct(product);
        } else {
            updateProduct(product);
        }

        return product;
    }

    private void updateProduct(Product product) {
        var sql = """
                update PRODUCT set
                NAME = ?,
                DESCRIPTION = ?,
                PRICE = ?,
                WEIGHT = ?,
                IMAGE_URL = ?,
                CATEGORY_ID = ?,
                SUPPLIER_ID = ?
                where ID = ?
                """;
        jdbcTemplate.update(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getWeight(),
                product.getImageUrl(),
                product.getCategory().getId(),
                product.getSupplier().getId(),
                product.getId()
        );
    }

    private void createProduct(Product product) {
        var parameters = new HashMap<String, Object>();
        parameters.put("NAME", product.getName());
        parameters.put("DESCRIPTION", product.getDescription());
        parameters.put("PRICE", product.getPrice());
        parameters.put("WEIGHT", product.getWeight());
        parameters.put("IMAGE_URL", product.getImageUrl());
        parameters.put("CATEGORY_ID", getIdFromEntity(product.getCategory()));
        parameters.put("SUPPLIER_ID", getIdFromEntity(product.getSupplier()));
        var productId = simpleJdbcInsert.executeAndReturnKey(parameters);
        product.setId(productId.intValue());
    }

    private void deleteStocksContainingProductId(Integer productId) {
        var stocks = stockRepository.findAllByProductId(productId);
        stockRepository.deleteAllInBatch(stocks);
    }

    @Component
    @RequiredArgsConstructor
    static final class ProductMapper implements RowMapper<Product> {
        private final ProductCategoryRepositoryJdbc productCategoryRepository;
        private final SupplierRepository supplierRepository;

        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Product.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .price(rs.getDouble("price"))
                    .weight(rs.getDouble("weight"))
                    .imageUrl(rs.getString("image_url"))
                    .category(productCategoryRepository.findById(rs.getInt("category_id")).orElse(null))
                    .supplier(supplierRepository.findById(rs.getInt("supplier_id")).orElse(null))
                    .build();
        }
    }
}
