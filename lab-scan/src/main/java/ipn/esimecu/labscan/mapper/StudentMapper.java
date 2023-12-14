package ipn.esimecu.labscan.mapper;

import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.dto.request.StudentRequest;
import ipn.esimecu.labscan.entity.StudentEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentMapper implements SuperMapper<StudentEntity, StudentDTO> {

    @Override
    public StudentEntity map(StudentDTO studentDTO) {
        StudentEntity entity = new StudentEntity();
        entity.setPcNumber(studentDTO.getStudentPcNumber());
        entity.setIdentification(studentDTO.getStudentReportNumber());
        entity.setFullName(studentDTO.getStudentFullName());
        entity.setQrCode(studentDTO.getStudentQrCode());
        return entity;
    }

    @Override
    public StudentDTO map(StudentEntity entity) {
        return null;
    }

    public StudentEntity map(StudentRequest request, StudentEntity source) {
        StudentEntity mapped = map(request);
        source.setQrCode(mapped.getQrCode());
        source.setSacademDate(mapped.getSacademDate());
        source.setFullName(mapped.getFullName());
        source.setIdentification(mapped.getIdentification());
        source.setPcNumber(mapped.getPcNumber());
        source.setPhoto(mapped.getPhoto());
        return source;
    }

    public StudentEntity map(StudentRequest request) {
        StudentEntity student = map((StudentDTO) request);
        student.setSacademDate(request.getSacademRegisterDate());
        student.setPhoto(Optional.ofNullable(request.getPhoto()).map(String::getBytes).orElse(null));
        return student;
    }
}
