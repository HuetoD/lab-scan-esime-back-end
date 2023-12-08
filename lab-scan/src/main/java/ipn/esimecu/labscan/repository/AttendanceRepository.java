package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {
}