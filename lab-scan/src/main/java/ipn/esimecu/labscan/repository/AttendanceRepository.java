package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.dto.request.AttendanceFiltersRequest;
import ipn.esimecu.labscan.entity.AttendanceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Integer> {

    @Query("SELECT attendance FROM AttendanceEntity attendance "
           + "LEFT JOIN attendance.student student "
           + "LEFT JOIN attendance.subjectLab sl "
           + "LEFT JOIN sl.subject subject "
           + "LEFT JOIN subject.semester semester "
           + "WHERE semester.semesterId = :#{#filters.semester} "
           + "AND ( :#{#filters.likeStudentName} IS NULL OR student.fullName LIKE %:#{#filters.likeStudentName}% ) "
           + "AND ( :#{#filters.subjectId} < 1 OR subject.subjectId = :#{#filters.subjectId} ) "
           + "AND ( :#{#filters.subjectLabId} < 1 OR sl.id = :#{#filters.subjectLabId} ) "
           + "AND DATE(attendance.date) BETWEEN :#{#filters.start} AND :#{#filters.end} ")
    List<AttendanceEntity> findByFilter(@Param("filters") AttendanceFiltersRequest filters);

    @Query("SELECT attendance FROM AttendanceEntity attendance "
           + "LEFT JOIN attendance.student student "
           + "WHERE student.studentId = :studentId "
           + "AND DATE(attendance.date) = :date ")
    Optional<AttendanceEntity> findByStudentAndDate(int studentId, LocalDate date);
}