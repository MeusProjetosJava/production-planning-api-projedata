package vitor.productionplanningapiprojedata.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.productionplanningapiprojedata.dto.RequestRawMaterialDTO;
import vitor.productionplanningapiprojedata.dto.ResponseRawMaterialDTO;
import vitor.productionplanningapiprojedata.service.RawMaterialService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/raw-materials")
@RequiredArgsConstructor
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    @PostMapping
    public ResponseEntity<ResponseRawMaterialDTO> create(
            @Valid @RequestBody RequestRawMaterialDTO dto
    ) {

        ResponseRawMaterialDTO saved = rawMaterialService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/raw-materials/" + saved.id()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ResponseRawMaterialDTO>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRawMaterialDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rawMaterialService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRawMaterialDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RequestRawMaterialDTO dto
    ) {
        return ResponseEntity.ok(rawMaterialService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}