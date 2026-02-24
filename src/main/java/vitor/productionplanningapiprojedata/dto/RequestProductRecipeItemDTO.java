package vitor.productionplanningapiprojedata.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestProductRecipeItemDTO(

        @NotNull
        Long productId,

        @NotNull
        Long rawMaterialId,

        @NotNull
        @Positive
        Integer quantityRequired

) {}