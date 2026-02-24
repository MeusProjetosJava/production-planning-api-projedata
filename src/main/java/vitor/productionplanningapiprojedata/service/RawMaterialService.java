package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.repository.RawMaterialRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterial create(RawMaterial rawMaterial) {

        if (rawMaterialRepository.existsByCode(rawMaterial.getCode())) {
            throw new RuntimeException("Raw material code already exists.");
        }

        return rawMaterialRepository.save(rawMaterial);
    }

    public RawMaterial update(Long id, RawMaterial updatedRawMaterial) {

        RawMaterial existing = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found."));

        existing.setName(updatedRawMaterial.getName());
        existing.setStockQuantity(updatedRawMaterial.getStockQuantity());

        return rawMaterialRepository.save(existing);
    }

    public void delete(Long id) {

        if (!rawMaterialRepository.existsById(id)) {
            throw new RuntimeException("Raw material not found.");
        }

        rawMaterialRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<RawMaterial> findAll() {
        return rawMaterialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RawMaterial findById(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found."));
    }
}