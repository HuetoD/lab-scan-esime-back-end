package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.LaboratoryDTO;
import ipn.esimecu.labscan.dto.response.SemesterResponse;
import ipn.esimecu.labscan.entity.IdentificationTypeEntity;
import ipn.esimecu.labscan.entity.constant.Day;
import ipn.esimecu.labscan.mapper.LaboratoryMapper;
import ipn.esimecu.labscan.mapper.SemesterMapper;
import ipn.esimecu.labscan.repository.IdentificationTypeRepository;
import ipn.esimecu.labscan.repository.LaboratoryRepository;
import ipn.esimecu.labscan.repository.SemesterRepository;
import ipn.esimecu.labscan.repository.SubjectLaboratoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final SemesterRepository semesterRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final SubjectLaboratoryRepository subjectLaboratoryRepository;
    private final SemesterMapper semesterMapper;
    private final LaboratoryMapper laboratoryMapper;

    public List<SemesterResponse> getSemesters() {
        return semesterMapper.mapAllDTOs(semesterRepository.findAll());
    }

    public List<LaboratoryDTO> getLabs() {
        return laboratoryMapper.mapAllDTOs(laboratoryRepository.findAll());
    }

    public List<GroupDTO> getGroups(String laboratory, int semester, LocalDate date) {
        return subjectLaboratoryRepository.findGroupLabNamesByDayAndSemester(laboratory, semester, Day.of(date).value())
                .stream()
                .map(result -> GroupDTO.builder()
                                    .subjectId(result.getSubjectId())
                                    .groupName(result.getGroupName().concat(" - ").concat(result.getLabName()))
                                    .build())
                .collect(Collectors.toList());
    }

    public List<String> loadAllIdentifiers() {
        return identificationTypeRepository.findAll()
                                        .stream()
                                        .map(IdentificationTypeEntity::getIdentificationType)
                                        .collect(Collectors.toList());
    }

}
