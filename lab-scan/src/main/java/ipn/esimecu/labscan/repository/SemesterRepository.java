package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<SemesterEntity, Integer> {
}