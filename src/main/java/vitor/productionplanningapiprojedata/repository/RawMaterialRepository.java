package vitor.productionplanningapiprojedata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitor.productionplanningapiprojedata.entity.RawMaterial;

import java.util.Optional;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

    Optional<RawMaterial> findByCode(String code);
    boolean existsByCode(String code);

}
