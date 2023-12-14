package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.LaboratoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LaboratoryRepository extends JpaRepository<LaboratoryEntity, Integer> {

    Optional<LaboratoryEntity> findByName(String name);

    List<LaboratoryEntity> findByNameIn(Collection<String> names);

}