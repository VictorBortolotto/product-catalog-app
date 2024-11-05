package br.com.example.api.services;

import br.com.example.api.model.Category;
import br.com.example.api.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        findById(category.getId());
        return categoryRepository.save(category);
    }

    public void delete(long id) {
        findById(id);
        categoryRepository.deleteById(id);
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found!"));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
