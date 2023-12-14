package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {

    Optional<TeacherEntity> findByFullName(String fullName);

}