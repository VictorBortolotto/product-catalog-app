package br.com.example.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.example.api.mocks.ProductMock;
import br.com.example.api.model.Product;
import br.com.example.api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        final var product = ProductMock.getProduct();
        when(productRepository.save(any(Product.class))).thenReturn(product);
        final var result = productService.save(product);

        assertEquals(result.getId(), product.getId());
        assertEquals(result.getName(), product.getName());
        assertEquals(result.getDescription(), product.getDescription());
        assertEquals(result.getPrice(), product.getPrice());
    }

    @Test
    void testUpdate() {
        final var product = ProductMock.getProduct();
        product.setName("Product Description 1");
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        final var result = productService.update(product);

        assertEquals(result.getId(), product.getId());
        assertEquals(result.getName(), product.getName());
        assertEquals(result.getDescription(), product.getDescription());
        assertEquals(result.getPrice(), product.getPrice());
    }

    @Test
    void testFindById() {
        final var product = ProductMock.getProduct();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        final var result = productService.findById(1);

        assertEquals(result.getId(), product.getId());
        assertEquals(result.getName(), product.getName());
        assertEquals(result.getDescription(), product.getDescription());
        assertEquals(result.getPrice(), product.getPrice());
    }

    @Test
    void testFindAll() {
        final var products = ProductMock.getProductList();
        when(productRepository.findAll()).thenReturn(products);
        final var results = productService.findAll();

        assertEquals(results.get(0).getId(), products.get(0).getId());
        assertEquals(results.get(0).getName(), products.get(0).getName());
        assertEquals(results.get(0).getDescription(), products.get(0).getDescription());
        assertEquals(results.get(0).getPrice(), products.get(0).getPrice());
    }

    @Test
    void testDelete() {
        final var product = ProductMock.getProduct();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        productService.delete(1);

        verify(productRepository, times(1)).deleteById(product.getId());
    }
}
