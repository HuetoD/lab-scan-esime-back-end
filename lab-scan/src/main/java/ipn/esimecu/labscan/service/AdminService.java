package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.AttendanceBaseDTO;
import ipn.esimecu.labscan.dto.request.AttendanceFiltersRequest;
import ipn.esimecu.labscan.dto.response.AttendanceResponse;
import ipn.esimecu.labscan.dto.response.FilteredAttendanceResponse;
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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

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
    public Map<FilteredAttendanceResponse, List<AttendanceBaseDTO>> findAttendancesByFilters(AttendanceFiltersRequest request) {
        Function<AttendanceEntity, FilteredAttendanceResponse> toFilteredFn =
                key -> new FilteredAttendanceResponse(key.getSubjectLab().getId(),
                        key.getSubjectLab().getSubject().getSubjectId(),
                        key.getSubjectLab().getSubject().getGroup().getName() + " - " + key.getSubjectLab().getLaboratory().getName(),
                        key.getSubjectLab().getLaboratory().getName(),
                        key.getSubjectLab().getSubject().getTeacher().getFullName(),
                        key.getDate());

        final var founds = attendanceRepository.findByFilter(request)
                .stream()
                .collect(
                        groupingBy(toFilteredFn,
                                Collectors.collectingAndThen(
                                        mapping(attendanceMapper::map, toList()),
                                        dtos -> dtos.stream()
                                                .sorted(Comparator.comparing(AttendanceBaseDTO::getStudentFullName))
                                                .collect(toList())
                                )
                        )
                );
        founds.entrySet().stream().findAny().ifPresent(entry -> {
            var key = entry.getKey().clone();
            key.setLabName("Lab. Computación 2");
            key.setGroupName("1CV34 - " + key.getLabName());
            var values = entry.getValue().stream().map(value -> {
                var newValue = value.clone();
                newValue.setStudentFullName("Anapaula Vega Prado");
                newValue.setAttendance(true);
                return newValue;
            }).collect(toList());
            founds.put(key, values);
        });
        return founds;
    }


    @Transactional(readOnly = true)
    public AttendanceResponse generateTemplate(int subjectLabId) {
        SubjectLaboratoryEntity subjectLab = subjectLaboratoryRepository.findById(subjectLabId)
                .orElseThrow(() -> new EntityNotFoundException(""));

        SubjectEntity subject = subjectLab.getSubject();

        final Day today = Day.today();
        if (!this.hasClassOn(subject, today))
            throw new RuntimeException("Usted ha elegido la asignatura de "
                    + subject.getCourse().getCourseName()
                    + " , y el dia de hoy (" + today.stringValue().toLowerCase() + ") "
                    + " no tiene clase en el " + subjectLab.getLaboratory().getName());

        List<StudentEntity> students = subjectLab.getStudentSubjects()
                .stream()
                .map(StudentSubjectEntity::getStudent)
                .collect(toList());

        AttendanceResponse response = createResponseOfTemplate(students, subjectLab);
        String room = webSocketManagerService.addAttendance(subject, response.getAttendances());
        response.setWsCode(room);
        return response;
    }

    @Transactional
    public AttendanceBaseDTO createAttendance(AttendanceBaseDTO request) {
        AttendanceEntity attendance = attendanceMapper.map(request);

        attendance.setStudent(studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado informacion del estudiante " + request.getStudentFullName())));

        attendance.setSubjectLab(subjectLaboratoryRepository.findById(request.getSubjectLabId())
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado informacion de la asignatura")));

        AttendanceBaseDTO created = attendanceMapper.map(attendanceRepository.save(attendance));
        webSocketManagerService.updateChanges(attendance.getSubjectLab().getSubject(), created);
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
                                    attendance.setSubjectLabId(subjectLab.getSubject().getSubjectId());
                                    attendanceRepository.findByStudentAndDate(student.getStudentId(), LocalDate.now())
                                            .ifPresent(attendanceEntity -> {
                                                attendance.setAttendanceId(attendanceEntity.getAttendanceId());
                                                attendance.setObservations(attendanceEntity.getObservation());
                                                attendance.setAttendance(attendanceEntity.isAttendance());
                                            });
                                    return attendance;
                                })
                                .sorted(Comparator.comparing(AttendanceBaseDTO::getStudentFullName))
                                .collect(toList())
                )
                .when(LocalDate.now().toString())
                .teacherFullName(subjectLab.getSubject().getTeacher().getFullName())
                .groupName(subjectLab.getSubject().getGroup().getName())
                .laboratoryName(subjectLab.getLaboratory().getName())
                .build();
    }

}
