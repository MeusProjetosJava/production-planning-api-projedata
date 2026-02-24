package vitor.productionplanningapiprojedata.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.productionplanningapiprojedata.dto.ResponseProductRecipeItemDTO;
import vitor.productionplanningapiprojedata.dto.RequestProductRecipeItemDTO;
import vitor.productionplanningapiprojedata.service.ProductRecipeItemService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class ProductRecipeItemController {

    private final ProductRecipeItemService service;

    @PostMapping
    public ResponseEntity<ResponseProductRecipeItemDTO> addRawMaterial(
            @Valid @RequestBody RequestProductRecipeItemDTO dto
    ) {

        ResponseProductRecipeItemDTO saved =
                service.addRawMaterialToProduct(dto);

        return ResponseEntity
                .created(URI.create("/api/recipes/" + saved.id()))
                .body(saved);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ResponseProductRecipeItemDTO>> listByProduct(
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