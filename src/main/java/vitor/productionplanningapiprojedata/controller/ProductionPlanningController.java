package vitor.productionplanningapiprojedata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitor.productionplanningapiprojedata.dto.ProductionPlanningResponseDTO;
import vitor.productionplanningapiprojedata.service.ProductionPlanningService;

@RestController
@RequestMapping("/api/production-plan")
@RequiredArgsConstructor
public class ProductionPlanningController {

    private final ProductionPlanningService productionPlanningService;

    @GetMapping
    public ResponseEntity<ProductionPlanningResponseDTO> generatePlan() {
        return ResponseEntity.ok(productionPlanningService.generatePlan());
    }
}