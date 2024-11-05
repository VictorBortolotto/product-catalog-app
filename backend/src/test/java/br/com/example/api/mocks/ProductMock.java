package br.com.example.api.mocks;

import br.com.example.api.model.Product;

import java.util.List;

public class ProductMock {

    public static Product getProduct() {
        return new Product(1, "Product test", "Product Description", 30.5);
    }

    public static List<Product> getProductList() {
        return List.of(
                new Product(1, "Product test 1", "Product Description 1", 30.5),
                new Product(2, "Product test 2", "Product Description 2", 60.5),
                new Product(3, "Product test 3", "Product Description 3", 90.5)
        );
    }
}
