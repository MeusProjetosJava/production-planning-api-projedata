CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          code VARCHAR(50) NOT NULL UNIQUE,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(15,2) NOT NULL
);

CREATE TABLE raw_materials (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               code VARCHAR(50) NOT NULL UNIQUE,
                               name VARCHAR(255) NOT NULL,
                               stock_quantity INT NOT NULL
);

CREATE TABLE product_recipe_items (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      product_id BIGINT NOT NULL,
                                      raw_material_id BIGINT NOT NULL,
                                      quantity_required INT NOT NULL,
                                      CONSTRAINT fk_product
                                          FOREIGN KEY (product_id)
                                              REFERENCES products(id)
                                              ON DELETE CASCADE,
                                      CONSTRAINT fk_raw_material
                                          FOREIGN KEY (raw_material_id)
                                              REFERENCES raw_materials(id)
);