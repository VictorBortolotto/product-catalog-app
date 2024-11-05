package br.com.example.api.mocks;

import br.com.example.api.model.Category;

import java.util.List;

public class CategoryMock {

    public static Category getCategory() {
        return new Category(1, "Category 1", "Category Description");
    }

    public static List<Category> getCategoryList() {
        return List.of(
                new Category(1, "Category 1", "Category Description 1"),
                new Category(2, "Category 2", "Category Description 2"),
                new Category(3, "Category 3", "Category Description 3")
        );
    }
}
