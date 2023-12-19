package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.dto.request.AttendanceFiltersRequest;
import ipn.esimecu.labscan.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

    @Query("SELECT attendance FROM AttendanceEntity attendance "
           + "LEFT JOIN attendance.student student "
           + "LEFT JOIN attendance.subject subject "
           + "RIGHT JOIN subject.subjectLaboratories sl "
           + "LEFT JOIN subject.semester semester "
           + "WHERE semester.semesterId = :#{#filters.semester} "
           + "AND ( :#{#filters.likeStudentName} IS NULL OR student.fullName LIKE %:#{#filters.likeStudentName}% ) "
           + "AND sl.id = :#{#filters.subjectLabId} "
           + "AND DATE(attendance.date) BETWEEN :#{#filters.start} AND :#{#filters.end} ")
    List<AttendanceEntity> findByFilter(@Param("filters")AttendanceFiltersRequest filters);

}