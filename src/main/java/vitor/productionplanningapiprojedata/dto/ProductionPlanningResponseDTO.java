package vitor.productionplanningapiprojedata.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductionPlanningResponseDTO(

        List<ProductionPlanningItemDTO> items,
        BigDecimal overallTotalValue

) {}