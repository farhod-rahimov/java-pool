package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private EmbeddedDatabase db;
    private ProductsRepository productsRepository;
    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Collections.unmodifiableList(
            new LinkedList<Product>() {{
                add(new Product(1L, "product-1", 100.00f));
                add(new Product(2L, "product-2", 125.00f));
                add(new Product(3L, "product-3", 155.00f));
                add(new Product(4L, "product-4", 175.00f));
                add(new Product(5L, "product-5", 195.00f));
                add(new Product(6L, "product-6", 225.00f));
                add(new Product(7L, "product-7", 248.00f));
            }});
    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(7L, "product-7", 248f);
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(7L, "updated-7", 248f);

    @BeforeEach
    void init() {
        this.db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        this.productsRepository = new ProductsRepositoryJdbcImpl(this.db);
    }

    @AfterEach
    void shutDownConnection() {
        this.db.shutdown();
    }

    @Test
    void testConnection() {
        Connection connection = null;

        try {
            connection = this.db.getConnection();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            fail();
            return ;
        }
        assertNotNull(connection);
    }

    @Test
    void trueAllProductsFound() {
        List<Product> list = productsRepository.findAll();

        assertEquals(list.size(), EXPECTED_FIND_ALL_PRODUCTS.size());

        for (int i = 0; i < EXPECTED_FIND_ALL_PRODUCTS.size(); i++) {
            assertEquals(list.get(i), EXPECTED_FIND_ALL_PRODUCTS.get(i));
        }
    }

    @Test
    void trueFindById() {
        Optional product = productsRepository.findById(7L);

        assertTrue(product.isPresent());
        assertEquals(product.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    void falseFindById() {
        Optional product = productsRepository.findById(5L);

        assertTrue(product.isPresent());
        assertNotEquals(product.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    void trueUpdate() {
        Product product = new Product(7L, "updated-7", 248f);

        productsRepository.update(product);

        assertTrue(productsRepository.findById(7L).isPresent());
        assertEquals(productsRepository.findById(7L).get(), EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    void falseUpdate() {
        Product product = new Product(7L, "updated-7-false", 248f);

        productsRepository.update(product);

        assertTrue(productsRepository.findById(7L).isPresent());
        assertNotEquals(productsRepository.findById(7L).get(), EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    void trueSave() {
        Product product = new Product(10L, "new-product-10", 1010f);

        productsRepository.save(product);

        assertTrue(productsRepository.findById(10L).isPresent());
        assertEquals(productsRepository.findById(10L).get(), product);
    }

    @Test
    void falseSave() {
        Product product = new Product(10L, "new-product-10-false", 1010f);

        productsRepository.save(product);
        product.setName("fresh-new-product-10");
        productsRepository.save(product);

        assertTrue(productsRepository.findById(10L).isPresent());
        assertNotEquals(productsRepository.findById(10L).get(), product);
    }

    @Test
    void trueDelete() {
        Product product = new Product(10L, "new-product-10", 1010f);

        productsRepository.save(product);
        assertTrue(productsRepository.findById(10L).isPresent());

        productsRepository.delete(10L);
        assertTrue(!productsRepository.findById(10L).isPresent());
    }

    @Test
    void falseDelete() {
        assertTrue(!productsRepository.findById(11L).isPresent());
        productsRepository.delete(11L);
        assertTrue(!productsRepository.findById(11L).isPresent());
    }
}
