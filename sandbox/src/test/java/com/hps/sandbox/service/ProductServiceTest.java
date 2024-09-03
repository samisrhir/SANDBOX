package com.hps.sandbox.service;

import com.hps.sandbox.entity.Product;
import com.hps.sandbox.repository.ProductRepository;
import com.hps.sandbox.service.dtos.ProductDto;
import com.hps.sandbox.service.mappers.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductServiceTest {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetProductById() {
        // Arrange
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Test Description");
        productRepository.save(product);

        // Act
        ProductDto result = productService.getProductById(product.getProductId());

        // Assert
        assertNotNull(result);
        assertEquals(product.getName(), result.getName()  );
        assertEquals(product.getDescription(), result.getDescription());
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setName("Product 1");
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Product 2");
        productRepository.save(product2);

        // Act
        List<ProductDto> result = productService.getAllProducts();

        // Assert
        assertNotNull(result);
    }
}
