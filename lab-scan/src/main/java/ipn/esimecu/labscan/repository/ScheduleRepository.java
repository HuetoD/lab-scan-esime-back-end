package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository< ScheduleEntity, Integer> {
}

