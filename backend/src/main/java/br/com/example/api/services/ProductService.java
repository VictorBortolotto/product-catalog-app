package br.com.example.api.services;

import br.com.example.api.model.Product;
import br.com.example.api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        findById(product.getId());
        return productRepository.save(product);
    }

    public void delete(long id) {
        findById(id);
        productRepository.deleteById(id);
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found!"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
