package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.LaboratoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryRepository extends JpaRepository<LaboratoryEntity, Integer> {
}