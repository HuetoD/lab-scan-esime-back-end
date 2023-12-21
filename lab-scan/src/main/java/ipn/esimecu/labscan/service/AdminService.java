package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.AttendanceBaseDTO;
import ipn.esimecu.labscan.dto.request.AttendanceFiltersRequest;
import ipn.esimecu.labscan.dto.response.AttendanceResponse;
import ipn.esimecu.labscan.entity.AttendanceEntity;
import ipn.esimecu.labscan.entity.StudentEntity;
import ipn.esimecu.labscan.entity.StudentSubjectEntity;
import ipn.esimecu.labscan.entity.SubjectEntity;
import ipn.esimecu.labscan.entity.SubjectLaboratoryEntity;
import ipn.esimecu.labscan.entity.constant.Day;
import ipn.esimecu.labscan.mapper.AttendanceMapper;
import ipn.esimecu.labscan.repository.AttendanceRepository;
import ipn.esimecu.labscan.repository.ScheduleRepository;
import ipn.esimecu.labscan.repository.StudentRepository;
import ipn.esimecu.labscan.repository.StudentSubjectRepository;
import ipn.esimecu.labscan.repository.SubjectLaboratoryRepository;
import ipn.esimecu.labscan.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminService {

    private final WebSocketManagerService webSocketManagerService;

    private final AttendanceRepository attendanceRepository;

    private final SubjectLaboratoryRepository subjectLaboratoryRepository;

    private final StudentRepository studentRepository;

    private final StudentSubjectRepository studentSubjectRepository;

    private final SubjectRepository subjectRepository;

    private final ScheduleRepository scheduleRepository;

    private final AttendanceMapper attendanceMapper;

    @Transactional(readOnly = true)
    public List<AttendanceBaseDTO> findAttendancesByFilters(AttendanceFiltersRequest request) {
        return attendanceMapper.mapAllDTOs(attendanceRepository.findByFilter(request));
    }

    @Transactional(readOnly = true)
    public AttendanceResponse generateTemplate(int subjectLabId) {
        SubjectLaboratoryEntity subjectLab = subjectLaboratoryRepository.findById(subjectLabId)
                                                                        .orElseThrow(() -> new EntityNotFoundException(""));

        SubjectEntity subject = subjectLab.getSubject();

        final Day today = Day.today();
        if(!this.hasClassOn(subject, today))
             throw new RuntimeException("Usted ha elegido la asignatura de "
                                        + subject.getCourse().getCourseName()
                                        + " , y el dia de hoy (" + today.stringValue().toLowerCase() + ") "
                                        + " no tiene clase en el " + subjectLab.getLaboratory().getName());

        List<StudentEntity> students = subject.getStudentSubjects()
                                              .stream()
                                              .map(StudentSubjectEntity::getStudent)
                                              .collect(Collectors.toList());

        AttendanceResponse response = createResponseOfTemplate(students, subjectLab);
        String room = webSocketManagerService.addAttendance(subject, response.getAttendances());
        response.setWsCode(room);
        return response;
    }

    @Transactional
    public AttendanceBaseDTO createAttendance(AttendanceBaseDTO request) {
        AttendanceEntity attendance = attendanceMapper.map(request);

        attendance.setStudent(studentRepository.findById(request.getSubjectId())
                                               .orElseThrow(() -> new EntityNotFoundException("")));

        attendance.setSubject(subjectRepository.findById(request.getSubjectId())
                                               .orElseThrow(() -> new EntityNotFoundException("")));

        AttendanceBaseDTO created = attendanceMapper.map(attendanceRepository.save(attendance));
        webSocketManagerService.updateChanges(attendance.getSubject(), created);
        return created;
    }

    @Transactional
    public void updateAttendance(AttendanceBaseDTO request) {
        AttendanceEntity attendance = attendanceRepository.getReferenceById(request.getAttendanceId());
        attendance.setObservation(request.getObservations());
        attendance.setAttendance(request.isAttendance());
        attendanceRepository.save(attendance);
    }

    @Transactional
    public void removeAttendance(int attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }

    public boolean hasClassOn(SubjectEntity subject, Day day) {
        return subject.getSchedules()
                      .parallelStream()
                      .anyMatch(schedule -> schedule.getDay().equals(day));
    }

    private AttendanceResponse createResponseOfTemplate(List<StudentEntity> students, SubjectLaboratoryEntity subjectLab) {
        return AttendanceResponse.builder()
                .attendances(
                        students.stream()
                                .map(student -> {
                                    AttendanceBaseDTO attendance = attendanceMapper.map(student);
                                    attendance.setSubjectId(subjectLab.getSubject().getSubjectId());
                                    attendance.setAttendance(false);
                                    attendance.setObservations("");
                                    return attendance;
                                })
                                .collect(Collectors.toList())
                )
                .when(LocalDate.now().toString())
                .teacherFullName(subjectLab.getSubject().getTeacher().getFullName())
                .groupName(subjectLab.getSubject().getGroup().getName())
                .laboratoryName(subjectLab.getLaboratory().getName())
                .build();
    }

}
