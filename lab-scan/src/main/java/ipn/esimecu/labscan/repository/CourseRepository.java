package ipn.esimecu.labscan.repository;


import ipn.esimecu.labscan.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
}