package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {
}