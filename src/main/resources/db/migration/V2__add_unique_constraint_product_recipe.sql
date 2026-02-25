ALTER TABLE product_recipe_items
    ADD CONSTRAINT uk_product_raw_material
        UNIQUE (product_id, raw_material_id);