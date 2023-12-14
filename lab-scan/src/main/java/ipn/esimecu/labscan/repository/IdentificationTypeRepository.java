package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.IdentificationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationTypeEntity, Integer> {

    Optional<IdentificationTypeEntity> findByIdentificationType(String name);

}