package vitor.productionplanningapiprojedata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitor.productionplanningapiprojedata.entity.ProductRecipeItem;

import java.util.List;

public interface ProductRecipeItemRepository extends JpaRepository<ProductRecipeItem, Long> {

    List<ProductRecipeItem> findByProductId(long productId);
    boolean existsByProductIdAndRawMaterialId(Long productId, Long rawMaterialId);
}
