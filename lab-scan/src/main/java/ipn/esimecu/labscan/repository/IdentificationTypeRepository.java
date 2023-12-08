package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.IdentificationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationTypeEntity, Integer> {
}