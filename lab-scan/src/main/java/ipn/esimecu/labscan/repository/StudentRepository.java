package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
}