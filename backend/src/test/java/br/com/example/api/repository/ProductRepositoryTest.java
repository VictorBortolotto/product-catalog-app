package br.com.example.api.repository;

import br.com.example.api.mocks.ProductMock;
import br.com.example.api.services.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class ProductRepositoryTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSave() {
        final var product = ProductMock.getProduct();

        final var savedProduct = productRepository.save(product);
        final var result = productService.findById(savedProduct.getId());

        assertEquals(result.getId(), savedProduct.getId());
        assertEquals(result.getName(), savedProduct.getName());
        assertEquals(result.getDescription(), savedProduct.getDescription());
        assertEquals(result.getPrice(), savedProduct.getPrice());
    }

    @Test
    public void testFindById() {
        final var product = ProductMock.getProduct();

        final var savedProduct = productRepository.save(product);
        final var result = productService.findById(savedProduct.getId());

        assertEquals(result.getId(), savedProduct.getId());
        assertEquals(result.getName(), savedProduct.getName());
        assertEquals(result.getDescription(), savedProduct.getDescription());
    }

    @Test
    public void testFindAll() {
        final var product = ProductMock.getProduct();

        productRepository.save(product);
        final var results = productService.findAll();

        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Product test")));
    }

    @Test
    public void testUpdate() {
        final var product = ProductMock.getProduct();
        product.setName("Product test 1");
        product.setName("Product description 1");
        final var id = productRepository.save(product).getId();
        final var result = productService.findById(id);

        assertEquals(result.getName(), product.getName());
        assertEquals(result.getDescription(), product.getDescription());
    }

    @Test
    public void testDelete() {
        final var product = ProductMock.getProduct();
        final var id = productRepository.save(product).getId();
        productRepository.deleteById(id);

        assertFalse(productRepository.existsById(id));
    }
}