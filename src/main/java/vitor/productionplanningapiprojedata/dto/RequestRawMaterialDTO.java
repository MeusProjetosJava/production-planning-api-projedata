package vitor.productionplanningapiprojedata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record RequestRawMaterialDTO(

        @NotBlank
        String code,

        @NotBlank
        String name,

        @NotNull
        @PositiveOrZero
        Integer stockQuantity

) {}