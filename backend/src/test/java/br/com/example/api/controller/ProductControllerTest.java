package br.com.example.api.controller;

import br.com.example.api.mocks.ProductMock;
import br.com.example.api.model.Product;
import br.com.example.api.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {ProductController.class})
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSave() throws Exception {
        final var product = ProductMock.getProduct();
        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/product")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product test"))
                .andExpect(jsonPath("$.description").value("Product Description"))
                .andExpect(jsonPath("$.price").value(30.5));
    }

    @Test
    public void testUpdate() throws Exception {
        final var product = ProductMock.getProduct();
        product.setName("Product test 1");
        when(productService.update(any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/product")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product test 1"))
                .andExpect(jsonPath("$.description").value("Product Description"))
                .andExpect(jsonPath("$.price").value(30.5));
    }

    @Test
    public void testFindById() throws Exception {
        final var product = ProductMock.getProduct();
        when(productService.findById(anyLong())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product test"))
                .andExpect(jsonPath("$.description").value("Product Description"))
                .andExpect(jsonPath("$.price").value(30.5));
    }

    @Test
    public void testFindAll() throws Exception {
        final var products = ProductMock.getProductList();
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/product")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("Product test 1"))
                .andExpect(jsonPath("$.[0].description").value("Product Description 1"))
                .andExpect(jsonPath("$.[0].price").value(30.5));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
