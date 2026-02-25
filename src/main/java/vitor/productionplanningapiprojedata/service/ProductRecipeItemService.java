package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.dto.ResponseProductRecipeItemDTO;
import vitor.productionplanningapiprojedata.dto.RequestProductRecipeItemDTO;
import vitor.productionplanningapiprojedata.entity.Product;
import vitor.productionplanningapiprojedata.entity.ProductRecipeItem;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.exception.DuplicateResourceException;
import vitor.productionplanningapiprojedata.exception.ResourceNotFoundException;
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

    public ResponseProductRecipeItemDTO addRawMaterialToProduct(
            RequestProductRecipeItemDTO dto
    ) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        if (recipeRepository.existsByProductIdAndRawMaterialId(
                dto.productId(), dto.rawMaterialId()
        )) {
            throw new DuplicateResourceException("This raw material is already linked to the product");
        }

        ProductRecipeItem item = ProductRecipeItem.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .quantityRequired(dto.quantityRequired())
                .build();

        ProductRecipeItem saved = recipeRepository.save(item);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ResponseProductRecipeItemDTO> findByProduct(Long productId) {

        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return recipeRepository.findByProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void removeItem(Long recipeItemId) {

        if (!recipeRepository.existsById(recipeItemId)) {
            throw new ResourceNotFoundException("Recipe item not found");
        }

        recipeRepository.deleteById(recipeItemId);
    }

    private ResponseProductRecipeItemDTO mapToResponse(ProductRecipeItem item) {
        return new ResponseProductRecipeItemDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getCode(),
                item.getRawMaterial().getId(),
                item.getRawMaterial().getCode(),
                item.getQuantityRequired()
        );
    }
}