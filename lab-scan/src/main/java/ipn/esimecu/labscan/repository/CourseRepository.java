package ipn.esimecu.labscan.repository;


import ipn.esimecu.labscan.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {

    Optional<CourseEntity> findByCourseName(String name);

}