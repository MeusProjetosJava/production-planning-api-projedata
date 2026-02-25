package vitor.productionplanningapiprojedata.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import vitor.productionplanningapiprojedata.dto.ProductionPlanningResponseDTO;
import vitor.productionplanningapiprojedata.entity.Product;
import vitor.productionplanningapiprojedata.entity.ProductRecipeItem;
import vitor.productionplanningapiprojedata.entity.RawMaterial;
import vitor.productionplanningapiprojedata.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductionPlanningServiceTest {

    private ProductRepository productRepository;
    private ProductionPlanningService service;

    @BeforeEach
    void setup() {
        productRepository = Mockito.mock(ProductRepository.class);
        service = new ProductionPlanningService(productRepository);
    }

    @Test
    void shouldGenerateCorrectPlan() {

        RawMaterial wood = rawMaterial(2L, 30);

        Product desk = product(2L, "P001", 800);
        ProductRecipeItem deskRecipe = recipe(desk, wood, 8);
        desk.setRecipeItems(List.of(deskRecipe));

        Product door = product(3L, "P002", 290);
        ProductRecipeItem doorRecipe = recipe(door, wood, 3);
        door.setRecipeItems(List.of(doorRecipe));

        when(productRepository.findAllWithRecipeItems())
                .thenReturn(new ArrayList<>(List.of(desk, door)));

        ProductionPlanningResponseDTO response = service.generatePlan();

        assertEquals(3, response.items().get(0).quantityToProduce());
        assertEquals(2, response.items().get(1).quantityToProduce());

        assertEquals(0,
                new BigDecimal("2980").compareTo(response.overallTotalValue()));
    }

    @Test
    void shouldReturnZeroWhenStockIsInsufficient() {

        RawMaterial wood = rawMaterial(2L, 2);

        Product desk = product(2L, "P001", 800);
        desk.setRecipeItems(List.of(recipe(desk, wood, 8)));

        when(productRepository.findAllWithRecipeItems())
                .thenReturn(new ArrayList<>(List.of(desk)));

        ProductionPlanningResponseDTO response = service.generatePlan();

        assertEquals(0, response.items().get(0).quantityToProduce());
        assertEquals(BigDecimal.ZERO, response.overallTotalValue());
    }

    @Test
    void shouldReturnZeroWhenProductHasNoRecipe() {

        Product desk = product(2L, "P001", 800);
        desk.setRecipeItems(List.of());

        when(productRepository.findAllWithRecipeItems())
                .thenReturn(new ArrayList<>(List.of(desk)));

        ProductionPlanningResponseDTO response = service.generatePlan();

        assertEquals(0, response.items().get(0).quantityToProduce());
    }


    @Test
    void shouldCalculateUsingMinimumRawMaterial() {

        RawMaterial wood = rawMaterial(1L, 30);
        RawMaterial metal = rawMaterial(2L, 10);

        Product table = product(1L, "P001", 500);

        ProductRecipeItem woodRecipe = recipe(table, wood, 5);
        ProductRecipeItem metalRecipe = recipe(table, metal, 4);

        table.setRecipeItems(List.of(woodRecipe, metalRecipe));

        when(productRepository.findAllWithRecipeItems())
                .thenReturn(new ArrayList<>(List.of(table)));

        ProductionPlanningResponseDTO response = service.generatePlan();


        assertEquals(2, response.items().get(0).quantityToProduce());
    }


    @Test
    void shouldPrioritizeHigherPriceProduct() {

        RawMaterial wood = rawMaterial(1L, 10);

        Product expensive = product(1L, "EXP", 1000);
        expensive.setRecipeItems(List.of(recipe(expensive, wood, 5)));

        Product cheap = product(2L, "CHP", 100);
        cheap.setRecipeItems(List.of(recipe(cheap, wood, 2)));

        when(productRepository.findAllWithRecipeItems())
                .thenReturn(new ArrayList<>(List.of(cheap, expensive)));

        ProductionPlanningResponseDTO response = service.generatePlan();


        assertEquals("EXP", response.items().get(0).productCode());
        assertEquals(2, response.items().get(0).quantityToProduce());
        assertEquals(0, response.items().get(1).quantityToProduce());
    }


    @Test
    void shouldUseIntegerDivision() {

        RawMaterial wood = rawMaterial(1L, 9);

        Product desk = product(1L, "P001", 800);
        desk.setRecipeItems(List.of(recipe(desk, wood, 4)));

        when(productRepository.findAllWithRecipeItems())
                .thenReturn(new ArrayList<>(List.of(desk)));

        ProductionPlanningResponseDTO response = service.generatePlan();


        assertEquals(2, response.items().get(0).quantityToProduce());
    }


    private Product product(Long id, String code, int price) {
        return Product.builder()
                .id(id)
                .code(code)
                .name("Test")
                .price(BigDecimal.valueOf(price))
                .build();
    }

    private RawMaterial rawMaterial(Long id, int stock) {
        return RawMaterial.builder()
                .id(id)
                .code("RM")
                .name("Material")
                .stockQuantity(stock)
                .build();
    }

    private ProductRecipeItem recipe(Product product, RawMaterial rawMaterial, int quantity) {
        return ProductRecipeItem.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .quantityRequired(quantity)
                .build();
    }
}