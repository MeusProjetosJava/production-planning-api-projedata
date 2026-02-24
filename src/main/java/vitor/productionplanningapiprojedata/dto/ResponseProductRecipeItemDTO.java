package vitor.productionplanningapiprojedata.dto;

public record ResponseProductRecipeItemDTO(

        Long id,
        Long productId,
        String productCode,
        Long rawMaterialId,
        String rawMaterialCode,
        Integer quantityRequired

) {}