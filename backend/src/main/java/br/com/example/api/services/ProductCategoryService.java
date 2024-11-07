package br.com.example.api.services;

import br.com.example.api.model.Category;
import br.com.example.api.model.Product;
import br.com.example.api.model.ProductCategory;
import br.com.example.api.repository.CategoryRepository;
import br.com.example.api.repository.ProductCategoryRepository;
import br.com.example.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Transactional
    public void addProductToCategory(long productId, long categoryId) {
        final var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        final var product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        final var productCategory = new ProductCategory(category, product);

        productCategoryRepository.save(productCategory);
    }

    public List<Product> getProductsByCategoryId(long categoryId) {
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryId(categoryId);
        return productCategories.stream()
                .map(ProductCategory::getProduct)
                .collect(Collectors.toList());
    }

    public Category getCategoryByProductId(long productId) {
        List<ProductCategory> productCategories = productCategoryRepository.findByProductId(productId);
        return productCategories.isEmpty() ? null : productCategories.get(0).getCategory();
    }

    public void deleteByCategoryIdAndProductId(long categoryId, long productId) {
        productCategoryRepository.deleteByCategoryIdAndProductId(categoryId, productId);
    }
}
