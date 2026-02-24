package vitor.productionplanningapiprojedata.dto;

import java.math.BigDecimal;

public record ResponseProductDTO(

        Long id,
        String code,
        String name,
        BigDecimal price

) {}