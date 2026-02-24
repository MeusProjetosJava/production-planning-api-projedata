package vitor.productionplanningapiprojedata.dto;

import java.math.BigDecimal;

public record ProductionPlanningItemDTO(

        Long productId,
        String productCode,
        String productName,
        Integer quantityToProduce,
        BigDecimal unitPrice,
        BigDecimal totalValue

) {}