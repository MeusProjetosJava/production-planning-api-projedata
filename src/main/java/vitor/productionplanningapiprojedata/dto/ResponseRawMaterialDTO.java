package vitor.productionplanningapiprojedata.dto;

public record ResponseRawMaterialDTO(
        Long id,
        String code,
        String name,
        Integer stockQuantity
) {}