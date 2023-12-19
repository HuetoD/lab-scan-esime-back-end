package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.dto.SubjectLabNameResultDTO;
import ipn.esimecu.labscan.entity.SubjectLaboratoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectLaboratoryRepository extends JpaRepository<SubjectLaboratoryEntity, Integer> {

    @Query("SELECT NEW ipn.esimecu.labscan.dto.SubjectLabNameResultDTO(al.id, a.subjectId, g.name, l.name) " +
            "FROM SubjectLaboratoryEntity al " +
            "LEFT JOIN al.subject a " +
            "LEFT JOIN al.laboratory l " +
            "LEFT JOIN a.group g " +
            "RIGHT JOIN a.schedules h " +
            "WHERE l.name = :labName AND a.semester.semesterId = :semester AND h.dayValue = :day ")
    List<SubjectLabNameResultDTO> findGroupLabNamesByDayAndSemester(@Param("labName") String labName,
                                                                    @Param("semester") int semester,
                                                                    @Param("day") int day);

}
