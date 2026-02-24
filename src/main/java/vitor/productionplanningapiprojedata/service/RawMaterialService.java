package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.dto.RequestRawMaterialDTO;
import vitor.productionplanningapiprojedata.dto.ResponseRawMaterialDTO;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.repository.RawMaterialRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public ResponseRawMaterialDTO create(RequestRawMaterialDTO dto) {

        if (rawMaterialRepository.existsByCode(dto.code())) {
            throw new RuntimeException("Raw material code already exists.");
        }

        RawMaterial rawMaterial = RawMaterial.builder()
                .code(dto.code())
                .name(dto.name())
                .stockQuantity(dto.stockQuantity())
                .build();

        RawMaterial saved = rawMaterialRepository.save(rawMaterial);

        return mapToResponse(saved);
    }

    public ResponseRawMaterialDTO update(Long id, RequestRawMaterialDTO dto) {

        RawMaterial existing = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found."));

        if (!existing.getCode().equals(dto.code())
                && rawMaterialRepository.existsByCode(dto.code())) {
            throw new RuntimeException("Raw material code already exists.");
        }

        existing.setCode(dto.code());
        existing.setName(dto.name());
        existing.setStockQuantity(dto.stockQuantity());

        RawMaterial saved = rawMaterialRepository.save(existing);

        return mapToResponse(saved);
    }

    public void delete(Long id) {

        RawMaterial existing = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found."));

        rawMaterialRepository.delete(existing);
    }

    @Transactional(readOnly = true)
    public List<ResponseRawMaterialDTO> findAll() {

        return rawMaterialRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ResponseRawMaterialDTO findById(Long id) {

        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found."));

        return mapToResponse(rawMaterial);
    }

    private ResponseRawMaterialDTO mapToResponse(RawMaterial rawMaterial) {
        return new ResponseRawMaterialDTO(
                rawMaterial.getId(),
                rawMaterial.getCode(),
                rawMaterial.getName(),
                rawMaterial.getStockQuantity()
        );
    }
}