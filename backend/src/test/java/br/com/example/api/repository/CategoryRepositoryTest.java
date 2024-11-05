package br.com.example.api.repository;

import br.com.example.api.mocks.CategoryMock;
import br.com.example.api.services.CategoryService;
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
public class CategoryRepositoryTest {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testSave() {
        final var category = CategoryMock.getCategory();

        final var savedCategory = categoryRepository.save(category);
        final var result = categoryService.findById(savedCategory.getId());

        assertEquals(result.getId(), savedCategory.getId());
        assertEquals(result.getName(), savedCategory.getName());
        assertEquals(result.getDescription(), savedCategory.getDescription());
    }

    @Test
    public void testFindById() {
        final var category = CategoryMock.getCategory();

        final var savedCategory = categoryRepository.save(category);
        final var result = categoryService.findById(savedCategory.getId());

        assertEquals(result.getId(), savedCategory.getId());
        assertEquals(result.getName(), savedCategory.getName());
        assertEquals(result.getDescription(), savedCategory.getDescription());
    }

    @Test
    public void testFindAll() {
        final var category = CategoryMock.getCategory();

        categoryRepository.save(category);
        final var results = categoryService.findAll();

        assertTrue(results.stream().anyMatch(c -> c.getName().equals("Category 1")));
    }

    @Test
    public void testUpdate() {
        final var category = CategoryMock.getCategory();
        category.setName("Category test 1");
        category.setName("Category description 1");
        final var id = categoryRepository.save(category).getId();
        final var result = categoryService.findById(id);

        assertEquals(result.getName(), category.getName());
        assertEquals(result.getDescription(), category.getDescription());
    }

    @Test
    public void testDelete() {
        final var category = CategoryMock.getCategory();
        final var id = categoryRepository.save(category).getId();
        categoryRepository.deleteById(id);

        assertFalse(categoryRepository.existsById(id));
    }
}
