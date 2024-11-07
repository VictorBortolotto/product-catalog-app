package br.com.example.api.controller;

import br.com.example.api.model.Category;
import br.com.example.api.model.Product;
import br.com.example.api.model.ProductCategory;
import br.com.example.api.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping
    public Category update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") long id) {
        categoryService.delete(id);
    }

    @GetMapping(path = "/{id}")
    public Category findById(@PathVariable("id") long id) {
        return categoryService.findById(id);
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @PostMapping(path = "/product")
    public void addProductToCategory(@RequestBody ProductCategory productCategory) {
        categoryService.addProductToCategory(productCategory.getProduct().getId(), productCategory.getCategory().getId());
    }

    @GetMapping(path = "/{id}/products")
    public List<Product> getProductsByCategoryId(@PathVariable("id") long id) {
        return categoryService.getProductsByCategoryId(id);
    }

    @DeleteMapping(path = "/{categoryId}/product/{productId}")
    void deleteByCategoryIdAndProductId(@PathVariable("categoryId") long categotyId, @PathVariable("productId") long productId) {
        categoryService.deleteByCategoryIdAndProductId(categotyId, productId);
    }
}
