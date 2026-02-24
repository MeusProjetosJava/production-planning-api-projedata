package vitor.productionplanningapiprojedata.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.productionplanningapiprojedata.dto.RequestProductDTO;
import vitor.productionplanningapiprojedata.dto.ResponseProductDTO;
import vitor.productionplanningapiprojedata.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseProductDTO> create(@Valid @RequestBody RequestProductDTO dto) {
        ResponseProductDTO saved = productService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/products/" + saved.id()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> update(
            @PathVariable Long id, @Valid
            @RequestBody RequestProductDTO dto
    ) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}