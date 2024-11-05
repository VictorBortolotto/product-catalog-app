package br.com.example.api.services;

import br.com.example.api.mocks.CategoryMock;
import br.com.example.api.model.Category;
import br.com.example.api.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        final var category = CategoryMock.getCategory();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        final var result = categoryService.save(category);

        assertEquals(result.getId(), category.getId());
        assertEquals(result.getName(), category.getName());
        assertEquals(result.getDescription(), category.getDescription());
    }

    @Test
    void testUpdate() {
        final var category = CategoryMock.getCategory();
        category.setName("Product Description 1");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        final var result = categoryService.update(category);

        assertEquals(result.getId(), category.getId());
        assertEquals(result.getName(), category.getName());
        assertEquals(result.getDescription(), category.getDescription());
    }

    @Test
    void testFindById() {
        final var category = CategoryMock.getCategory();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        final var result = categoryService.findById(1);

        assertEquals(result.getId(), category.getId());
        assertEquals(result.getName(), category.getName());
        assertEquals(result.getDescription(), category.getDescription());
    }

    @Test
    void testFindAll() {
        final var categories = CategoryMock.getCategoryList();
        when(categoryRepository.findAll()).thenReturn(categories);
        final var results = categoryService.findAll();

        assertEquals(results.get(0).getId(), categories.get(0).getId());
        assertEquals(results.get(0).getName(), categories.get(0).getName());
        assertEquals(results.get(0).getDescription(), categories.get(0).getDescription());
    }

    @Test
    void testDelete() {
        final var category = CategoryMock.getCategory();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        categoryService.delete(1);

        verify(categoryRepository, times(1)).deleteById(category.getId());
    }
}
