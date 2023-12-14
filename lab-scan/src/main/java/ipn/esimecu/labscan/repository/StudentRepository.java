package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    Optional<StudentEntity> findByQrCode(String qrCode);

}