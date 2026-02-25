package vitor.productionplanningapiprojedata.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vitor.productionplanningapiprojedata.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

    @EntityGraph(attributePaths = {"recipeItems", "recipeItems.rawMaterial"})
    @Query("select p from Product p")
    List<Product> findAllWithRecipeItems();
}