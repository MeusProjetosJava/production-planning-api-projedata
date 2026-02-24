package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.entity.Product;
import vitor.productionplanningapiprojedata.entity.ProductRecipeItem;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.repository.ProductRecipeItemRepository;
import vitor.productionplanningapiprojedata.repository.ProductRepository;
import vitor.productionplanningapiprojedata.repository.RawMaterialRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductRecipeItemService {

    private final ProductRecipeItemRepository recipeRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductRecipeItem addRawMaterialToProduct(
            Long productId,
            Long rawMaterialId,
            Integer quantityRequired
    ) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        ProductRecipeItem item = ProductRecipeItem.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .quantityRequired(quantityRequired)
                .build();

        return recipeRepository.save(item);
    }

    @Transactional(readOnly = true)
    public List<ProductRecipeItem> findByProduct(Long productId) {

        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return recipeRepository.findByProductId(productId);
    }

    public void removeItem(Long recipeItemId) {

        if (!recipeRepository.existsById(recipeItemId)) {
            throw new RuntimeException("Recipe item not found");
        }

        recipeRepository.deleteById(recipeItemId);
    }
}