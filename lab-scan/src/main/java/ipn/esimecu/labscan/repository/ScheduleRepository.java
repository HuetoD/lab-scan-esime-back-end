package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.ScheduleEntity;
import ipn.esimecu.labscan.entity.constant.Day;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository< ScheduleEntity, Integer> {
}

