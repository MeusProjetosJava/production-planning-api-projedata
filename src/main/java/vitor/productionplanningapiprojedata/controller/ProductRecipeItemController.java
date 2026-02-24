package vitor.productionplanningapiprojedata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.productionplanningapiprojedata.entity.ProductRecipeItem;
import vitor.productionplanningapiprojedata.service.ProductRecipeItemService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class ProductRecipeItemController {

    private final ProductRecipeItemService service;

    @PostMapping
    public ResponseEntity<ProductRecipeItem> addRawMaterial(
            @RequestParam Long productId,
            @RequestParam Long rawMaterialId,
            @RequestParam Integer quantityRequired
    ) {

        ProductRecipeItem saved =
                service.addRawMaterialToProduct(productId, rawMaterialId, quantityRequired);

        return ResponseEntity
                .created(URI.create("/api/recipes/" + saved.getId()))
                .body(saved);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductRecipeItem>> listByProduct(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(service.findByProduct(productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.removeItem(id);
        return ResponseEntity.noContent().build();
    }
}