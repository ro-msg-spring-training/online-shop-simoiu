package ro.msg.learning.shop.repository.jdbc;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ro.msg.learning.shop.model.entities.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductCategoryRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;
    private final ProductCategoryMapper productCategoryMapper;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductCategoryRepositoryJdbc(JdbcTemplate jdbcTemplate, ProductCategoryMapper productCategoryMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.productCategoryMapper = productCategoryMapper;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("PRODUCT_CATEGORY").usingGeneratedKeyColumns("ID");
    }

    @SneakyThrows
    public List<ProductCategory> findAll() {
        var sql = "select * from PRODUCT_CATEGORY";
        return jdbcTemplate.query(sql, productCategoryMapper);
    }

    public Optional<ProductCategory> findById(Integer id) {
        var sql = "select * from PRODUCT_CATEGORY where ID = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, productCategoryMapper, id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(Integer id) {
        var sql = "delete from PRODUCT_CATEGORY where ID = ?";
        jdbcTemplate.update(sql, id);
    }

    public ProductCategory save(ProductCategory productCategory) {
        if (productCategory.getId() == null) {
            createProductCategory(productCategory);
        } else {
            updateProductCategory(productCategory);
        }

        return productCategory;
    }

    private void updateProductCategory(ProductCategory product) {
        var sql = """
                update PRODUCT_CATEGORY set
                NAME = ?,
                DESCRIPTION = ?
                where ID = ?
                """;
        jdbcTemplate.update(sql,
                product.getName(),
                product.getDescription(),
                product.getId()
        );
    }

    private void createProductCategory(ProductCategory productCategory) {
        var parameters = new HashMap<String, Object>();
        parameters.put("NAME", productCategory.getName());
        parameters.put("DESCRIPTION", productCategory.getDescription());
        var productCategoryId = simpleJdbcInsert.executeAndReturnKey(parameters);
        productCategory.setId(productCategoryId.intValue());
    }

    @Component
    @RequiredArgsConstructor
    static final class ProductCategoryMapper implements RowMapper<ProductCategory> {
        public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ProductCategory.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .build();
        }
    }
}
