package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.SemesterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface SemesterRepository extends JpaRepository<SemesterEntity, Integer> {

    @Query("FROM SemesterEntity s WHERE :when BETWEEN s.startDate AND s.endDate")
    Optional<SemesterEntity> findOneBetween(LocalDate when);

}