package br.com.example.api.repository;

import br.com.example.api.model.ProductCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByCategoryId(long categoryId);
    List<ProductCategory> findByProductId(long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductCategory pc WHERE pc.category.id = :categoryId AND pc.product.id = :productId")
    void deleteByCategoryIdAndProductId(long categoryId, long productId);
}
