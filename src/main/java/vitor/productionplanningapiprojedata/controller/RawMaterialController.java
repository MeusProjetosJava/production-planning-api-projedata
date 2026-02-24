package vitor.productionplanningapiprojedata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.service.RawMaterialService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
@RequiredArgsConstructor
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @PostMapping
    public ResponseEntity<RawMaterial> create(@RequestBody RawMaterial rawMaterial) {
        RawMaterial saved = rawMaterialService.create(rawMaterial);
        return ResponseEntity
                .created(URI.create("/api/raw-materials/" + saved.getId()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<RawMaterial>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterial> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rawMaterialService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterial> update(
            @PathVariable Long id,
            @RequestBody RawMaterial rawMaterial
    ) {
        return ResponseEntity.ok(rawMaterialService.update(id, rawMaterial));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}