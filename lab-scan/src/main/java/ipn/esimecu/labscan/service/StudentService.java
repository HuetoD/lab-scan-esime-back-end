package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.StudentBaseDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.entity.StudentEntity;
import ipn.esimecu.labscan.exception.DaeServiceException;
import ipn.esimecu.labscan.exception.StudentNotFoundException;
import ipn.esimecu.labscan.mapper.StudentMapper;
import ipn.esimecu.labscan.repository.IdentificationTypeRepository;
import ipn.esimecu.labscan.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final IdentificationTypeRepository identificationTypeRepository;

    private final StudentMapper studentMapper;

    private final SubjectService subjectService;

    private final StudentSubjectService studentSubjectService;
    private final DaeService daeService;

    @Transactional
    public StudentDTO save(StudentDTO request) {
        StudentEntity student = studentMapper.map(request);
        student.setIdentificationType(
                identificationTypeRepository.findByIdentificationType(request.getStudentIdentificationType())
                                            .orElseThrow(() -> new EntityNotFoundException("No se encontrado un tipo de identificacion de nombre: "
                                                                                            + request.getStudentIdentificationType())));

        student = studentRepository.save(student);
        studentSubjectService.save(student, subjectService.saveSubjects(request));
        return (StudentDTO) studentMapper.map(student);
    }

    @Transactional
    public void update(StudentDTO request) {
        StudentEntity student = studentMapper.map(request, studentRepository.findById(request.getStudentId())
                                                                            .orElseThrow(createSupplierStudentNotFound(request.getStudentId())));

        studentSubjectService.deleteAll(student.getStudentSubjects());

        studentSubjectService.save(studentRepository.save(student), subjectService.saveSubjects(request));
    }

    @Transactional
    public void transfer(int fromStudentId, int toStudentId) {
        StudentEntity from = studentRepository.findById(fromStudentId)
                                              .orElseThrow(createSupplierStudentNotFound(fromStudentId));

        StudentEntity to = studentRepository.findById(toStudentId)
                                            .orElseThrow(createSupplierStudentNotFound(toStudentId));
        String temporal = from.getPcNumber();
        from.setPcNumber(to.getPcNumber());
        to.setPcNumber(temporal);
        studentRepository.saveAll(Arrays.asList(from, to));
    }

    @Transactional
    public StudentDTO findStudent(String qrCode) {
        return studentRepository.findByQrCode(qrCode)
                                .map(studentMapper::mapAll)
                                .orElseGet(() -> findStudentWithDaeService(qrCode));
    }

    @Transactional(readOnly = true)
    public StudentDTO findStudentByReportNumber(String reportNumber) {
        return studentRepository.findByIdentification(Objects.requireNonNull(reportNumber, "El numero de identificacion no debe ser nulo"))
                .map(studentMapper::mapAll)
                .orElseThrow(() -> new StudentNotFoundException("No se ha podido recuperar la inforamcion del estudiante con identificacion: " + reportNumber));
    }

    @Transactional(noRollbackFor = {DaeServiceException.class})
    public StudentDTO findStudentWithDaeService(String qrcode) throws DaeServiceException {
        BiFunction<StudentEntity, StudentBaseDTO, StudentEntity> studentFn = (student, base) -> {
            student.setSacademDate(null);
            student.setQrCode(qrcode);
            student.setFullName(base.getStudentFullName());
            student.setIdentification(base.getStudentReportNumber());
            return student;
        };
        return Optional.ofNullable(daeService.findStudent(qrcode))
                        .map(base -> studentRepository.findByIdentification(base.getStudentReportNumber())
                                                    .map(student -> studentFn.apply(student, base))
                                                    .orElseGet(() -> studentFn.apply(new StudentEntity(), base))
                        )
                        .map(studentRepository::save)
                        .map(student -> findStudentByReportNumber(student.getIdentification()))
                        .orElseThrow();
    }

    public void getAllStudentsOfSubject() {

    }

    private Supplier<EntityNotFoundException> createSupplierStudentNotFound(int id) {
        return () -> new EntityNotFoundException("No se ha encontrado al estudiante con id: " + id);
    }

}