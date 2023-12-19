package ipn.esimecu.labscan.mapper;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.StudentBaseDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.entity.GroupEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.StudentEntity;
import ipn.esimecu.labscan.entity.SubjectEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StudentMapper implements SuperMapper<StudentEntity, StudentBaseDTO> {

    @Override
    public StudentEntity map(StudentBaseDTO studentBaseDTO) {
        StudentEntity entity = new StudentEntity();
        entity.setPcNumber(studentBaseDTO.getStudentPcNumber());
        entity.setIdentification(studentBaseDTO.getStudentReportNumber());
        entity.setFullName(studentBaseDTO.getStudentFullName());
        entity.setQrCode(studentBaseDTO.getStudentQrCode());
        return entity;
    }

    @Override
    public StudentBaseDTO map(StudentEntity entity) {
        StudentBaseDTO response = new StudentBaseDTO();
        response.setStudentId(entity.getStudentId());
        response.setStudentPcNumber(entity.getPcNumber());
        response.setStudentReportNumber(entity.getIdentification());
        response.setStudentQrCode(entity.getQrCode());
        response.setStudentFullName(entity.getFullName());
        response.setStudentIdentificationType(entity.getIdentificationType().getIdentificationType());
        return response;
    }

    public StudentEntity map(StudentDTO request, StudentEntity source) {
        StudentEntity mapped = map(request);
        source.setQrCode(mapped.getQrCode());
        source.setSacademDate(mapped.getSacademDate());
        source.setFullName(mapped.getFullName());
        source.setIdentification(mapped.getIdentification());
        source.setPcNumber(mapped.getPcNumber());
        source.setPhoto(mapped.getPhoto());
        return source;
    }

    public StudentEntity map(StudentDTO request) {
        StudentEntity student = map((StudentBaseDTO) request);
        student.setSacademDate(request.getSacademRegisterDate());
        student.setPhoto(Optional.ofNullable(request.getPhoto()).map(String::getBytes).orElse(null));
        return student;
    }

    public StudentDTO mapAll(StudentEntity entity) {
        final StudentDTO dto = new StudentDTO();
        final Map<String, List<GroupDTO>> groups = new HashMap<>();
        dto.setSemesterId(-1);
        dto.setStudentId(entity.getStudentId());
        dto.setStudentPcNumber(entity.getPcNumber());
        dto.setStudentReportNumber(entity.getIdentification());
        dto.setStudentQrCode(entity.getQrCode());
        dto.setStudentFullName(entity.getFullName());
        dto.setStudentIdentificationType(entity.getIdentificationType().getIdentificationType());
        dto.setSacademRegisterDate(entity.getSacademDate());
        dto.setPhoto(Optional.ofNullable(entity.getPhoto()).map(Object::toString).orElse(null)); // WRONG
        entity.getStudentSubjects().stream().flatMap(s -> s.getSubject().getSubjectLaboratories().stream())
                .forEach(subjectLab -> {
                    LaboratoryEntity laboratory = subjectLab.getLaboratory();
                    SubjectEntity subject = subjectLab.getSubject();
                    GroupEntity groupEntity = subject.getGroup();
                    final String key = laboratory.getName();
                    if (!groups.containsKey(key))
                        groups.put(key, new ArrayList<>());
                    groups.get(key).add(GroupDTO.builder()
                            .groupName(groupEntity.getName() + " - " + key)
                            .subjectId(subject.getSubjectId())
                            .subjectLabId(subjectLab.getLaboratory().getLaboratoryId())
                            .build());

                    if (dto.getSemesterId() == -1)
                        dto.setSemesterId(subject.getSemester().getSemesterId());
                });
        dto.setSemesterId(dto.getSemesterId() == -1 ? 0 : dto.getSemesterId());
        dto.setGroups(groups);
        return dto;
    }
}
