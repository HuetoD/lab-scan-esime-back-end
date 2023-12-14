package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.dto.request.StudentRequest;
import ipn.esimecu.labscan.entity.StudentEntity;
import ipn.esimecu.labscan.mapper.StudentMapper;
import ipn.esimecu.labscan.repository.IdentificationTypeRepository;
import ipn.esimecu.labscan.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
    public void save(StudentRequest request) {
        StudentEntity student = studentMapper.map(request);
        student.setIdentificationType(
                identificationTypeRepository.findByIdentificationType(request.getStudentIdentificationType())
                                            .orElseThrow(() -> new EntityNotFoundException("No se encontrado un tipo de identificacion de nombre: "
                                                                                            + request.getStudentIdentificationType())));

        studentSubjectService.save(studentRepository.save(student), subjectService.saveSubjects(request));
    }

    @Transactional
    public void update(StudentRequest request) {
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
    @Transactional(readOnly = true)
    public StudentDTO findStudent(String qrCode) {
        return studentRepository.findByQrCode(qrCode)
                                .map(studentMapper::map)
                                .orElse(daeService.findStudent(qrCode));
    }

    public void getAllStudentsOfSubject() {

    }

    private Supplier<EntityNotFoundException> createSupplierStudentNotFound(int id) {
        return () -> new EntityNotFoundException("No se ha encontrado al estudiante con id: " + id);
    }

}
