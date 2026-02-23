package vitor.productionplanningapiprojedata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitor.productionplanningapiprojedata.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByCode(String code);
    boolean existsByCode(String code);

}
