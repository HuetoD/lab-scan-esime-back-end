package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.StudentSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSubjectRepository extends JpaRepository<StudentSubjectEntity, Integer> {
}