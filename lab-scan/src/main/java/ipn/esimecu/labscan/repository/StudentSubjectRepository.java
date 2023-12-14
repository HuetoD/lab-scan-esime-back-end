package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.StudentSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentSubjectRepository extends JpaRepository<StudentSubjectEntity, Integer> {

    List<StudentSubjectEntity> findByStudentStudentId(int studentId);

}