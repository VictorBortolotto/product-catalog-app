package br.com.example.api.controller;

import br.com.example.api.mocks.CategoryMock;
import br.com.example.api.model.Category;
import br.com.example.api.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {CategoryController.class})
public class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSave() throws Exception {
        final var category = CategoryMock.getCategory();
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/category")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"))
                .andExpect(jsonPath("$.description").value("Category Description"));
    }

    @Test
    public void testUpdate() throws Exception {
        final var category = CategoryMock.getCategory();
        category.setName("Category test 1");
        when(categoryService.update(any(Category.class))).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/category")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category test 1"))
                .andExpect(jsonPath("$.description").value("Category Description"));
    }

    @Test
    public void testFindById() throws Exception {
        final var category = CategoryMock.getCategory();
        when(categoryService.findById(anyLong())).thenReturn(category);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/category/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Category 1"))
                .andExpect(jsonPath("$.description").value("Category Description"));
    }

    @Test
    public void testFindAll() throws Exception {
        final var categories = CategoryMock.getCategoryList();
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/category")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("Category 1"))
                .andExpect(jsonPath("$.[0].description").value("Category Description 1"));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/category/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
