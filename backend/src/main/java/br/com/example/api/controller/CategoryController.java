package br.com.example.api.controller;

import br.com.example.api.model.Category;
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
}
