package vitor.productionplanningapiprojedata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitor.productionplanningapiprojedata.dto.ProductionPlanningItemDTO;
import vitor.productionplanningapiprojedata.dto.ProductionPlanningResponseDTO;
import vitor.productionplanningapiprojedata.entity.Product;
import vitor.productionplanningapiprojedata.entity.ProductRecipeItem;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductionPlanningService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductionPlanningResponseDTO generatePlan() {

        List<Product> products = productRepository.findAllWithRecipeItems();

        products.sort(Comparator.comparing(Product::getPrice, Comparator.nullsLast(BigDecimal::compareTo)).reversed());

        Map<Long, Integer> virtualStock = new HashMap<>();
        for (Product p : products) {
            if (p.getRecipeItems() == null) continue;

            for (ProductRecipeItem item : p.getRecipeItems()) {
                RawMaterial rm = item.getRawMaterial();
                if (rm == null) continue;

                virtualStock.putIfAbsent(rm.getId(), safeInt(rm.getStockQuantity()));
            }
        }

        List<ProductionPlanningItemDTO> planItems = new ArrayList<>();
        BigDecimal overallTotalValue = BigDecimal.ZERO;

        for (Product product : products) {

            int maxProducible = calculateMaxProducible(product, virtualStock);

            BigDecimal unitPrice = product.getPrice() == null ? BigDecimal.ZERO : product.getPrice();
            BigDecimal totalValue = unitPrice.multiply(BigDecimal.valueOf(maxProducible));

            if (maxProducible > 0) {
                consumeVirtualStock(product, virtualStock, maxProducible);
            }

            ProductionPlanningItemDTO itemDTO = new ProductionPlanningItemDTO(
                    product.getId(),
                    product.getCode(),
                    product.getName(),
                    maxProducible,
                    unitPrice,
                    totalValue
            );

            planItems.add(itemDTO);
            overallTotalValue = overallTotalValue.add(totalValue);
        }

        return new ProductionPlanningResponseDTO(planItems, overallTotalValue);
    }

    private int calculateMaxProducible(Product product, Map<Long, Integer> virtualStock) {

        List<ProductRecipeItem> recipeItems = product.getRecipeItems();

        if (recipeItems == null || recipeItems.isEmpty()) return 0;

        int max = Integer.MAX_VALUE;

        for (ProductRecipeItem item : recipeItems) {

            RawMaterial rm = item.getRawMaterial();
            if (rm == null) return 0;

            int required = safeInt(item.getQuantityRequired());
            if (required <= 0) return 0; // receita inválida -> não produz

            Integer available = virtualStock.get(rm.getId());
            if (available == null) available = safeInt(rm.getStockQuantity());

            int possible = available / required; // divisão inteira
            max = Math.min(max, possible);
        }

        return max == Integer.MAX_VALUE ? 0 : max;
    }

    private void consumeVirtualStock(Product product, Map<Long, Integer> virtualStock, int unitsProduced) {

        for (ProductRecipeItem item : product.getRecipeItems()) {

            RawMaterial rm = item.getRawMaterial();
            int required = safeInt(item.getQuantityRequired());

            long rmId = rm.getId();
            int current = virtualStock.getOrDefault(rmId, safeInt(rm.getStockQuantity()));

            int consumption = required * unitsProduced;
            int newValue = current - consumption;

            virtualStock.put(rmId, Math.max(newValue, 0));
        }
    }

    private int safeInt(Integer value) {
        return value == null ? 0 : value;
    }
}